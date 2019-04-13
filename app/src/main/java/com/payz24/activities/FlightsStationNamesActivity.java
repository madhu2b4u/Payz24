package com.payz24.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.FlightsStationsListHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.interfaces.NoResultsCallBack;
import com.payz24.interfaces.SelectedFlightsStationsCallBack;
import com.payz24.responseModels.flightStations.FlightStations;
import com.payz24.adapter.FlightsStationNamesAdapter;
import com.payz24.responseModels.flightStations.Station;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahesh on 2/3/2018.
 */

public class FlightsStationNamesActivity extends BaseActivity implements TextWatcher, View.OnClickListener, FlightsStationNamesAdapter.ItemClickListener, NoResultsCallBack {

    private EditText etSearch;
    private ImageView ivBack;
    private RecyclerView rvFlights;
    private TextView tvError;
    private Context context;
    private SelectedFlightsStationsCallBack selectedFlightsStationsCallBack;
    private List<Station> listOfFlightStations;
    private FlightsStationNamesAdapter flightsStationNamesAdapter;
    private String from = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flights_stations_names);
        getDataFromIntent();
        initializeUi();
        initializeListeners();
        initializeAdapter();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            from = intent.getStringExtra(getString(R.string.from));
        }
        listOfFlightStations = AppController.getInstance().getListOfFlightStations();
    }

    private void initializeUi() {
        etSearch = findViewById(R.id.etSearch);
        ivBack = findViewById(R.id.ivBack);
        rvFlights = findViewById(R.id.rvFlights);
        tvError = findViewById(R.id.tvError);

        context = AppController.getInstance().getFlightsActivityContext();
        selectedFlightsStationsCallBack = (SelectedFlightsStationsCallBack) context;
    }

    private void initializeListeners() {
        etSearch.addTextChangedListener(this);
        ivBack.setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        if (flightsStationNamesAdapter != null) {
            flightsStationNamesAdapter.filter(sequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                onBackPressed();
                break;
            default:
                break;
        }
    }


    private void initializeAdapter() {
        flightsStationNamesAdapter = new FlightsStationNamesAdapter(this, listOfFlightStations, from);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvFlights.setLayoutManager(layoutManager);
        rvFlights.setItemAnimator(new DefaultItemAnimator());
        flightsStationNamesAdapter.setClickListener(this);
        rvFlights.setAdapter(flightsStationNamesAdapter);
    }

    @Override
    public void onClick(View view, int position, String comingFrom,Station station) {
        selectedFlightsStationsCallBack.selectedFlightStations(station, comingFrom);
        finish();
    }

    @Override
    public void noResultsFound(boolean isResultsAvailable) {
        if (!isResultsAvailable) {
            rvFlights.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        } else {
            rvFlights.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        }
    }
}
