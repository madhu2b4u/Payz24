package com.payz24.activities;

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

import com.payz24.R;
import com.payz24.adapter.BusStationsAdapter;
import com.payz24.application.AppController;
import com.payz24.interfaces.BusStationsCallBack;
import com.payz24.interfaces.NoResultsCallBack;
import com.payz24.responseModels.busStations.StationList;

import java.util.List;

/**
 * Created by mahesh on 1/24/2018.
 */

public class BusStationsActivity extends BaseActivity implements BusStationsAdapter.ItemClickListener, View.OnClickListener, TextWatcher, NoResultsCallBack {

    private ImageView ivBack, ivClear;
    private EditText etSearchStations;
    private TextView tvError;
    private RecyclerView rvBusStations;
    private BusStationsAdapter busStationsAdapter;
    private BusStationsCallBack busStationsCallBack;

    private String clickedOn = "";
    private List<StationList> listOfBusStations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_stations);
        getDataFromIntent();
        enableActionBar(false);
        initializeUi();
        initializeListeners();
        initializeAdapter();
    }

    private void getDataFromIntent() {
        clickedOn = getIntent().getStringExtra(getString(R.string.clicked_on));
        listOfBusStations = AppController.getInstance().getListOfBusStations();
    }

    private void initializeUi() {
        ivBack = findViewById(R.id.ivBack);
        ivClear = findViewById(R.id.ivClear);
        etSearchStations = findViewById(R.id.etSearchStations);
        rvBusStations = findViewById(R.id.rvBusStations);
        tvError = findViewById(R.id.tvError);

        busStationsCallBack = (BusStationsCallBack) AppController.getInstance().getBusActivityContext();
    }

    private void initializeListeners() {
        ivBack.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        etSearchStations.addTextChangedListener(this);
    }

    private void initializeAdapter() {
        busStationsAdapter = new BusStationsAdapter(this, listOfBusStations, clickedOn);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvBusStations.setLayoutManager(layoutManager);
        rvBusStations.setItemAnimator(new DefaultItemAnimator());
        busStationsAdapter.setClickListener(this);
        rvBusStations.setAdapter(busStationsAdapter);
    }

    @Override
    public void onClick(View view, int position, StationList stationList) {
        if (clickedOn.equalsIgnoreCase(getString(R.string.leaving_from))) {
            busStationsCallBack.selectedBusStation(stationList, clickedOn);
        } else {
            busStationsCallBack.selectedBusStation(stationList, clickedOn);
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivClear:
                etSearchStations.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
        busStationsAdapter.setFilter(sequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void noResultsFound(boolean isResultsAvailable) {
        if (!isResultsAvailable) {
            rvBusStations.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        } else {
            rvBusStations.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        }
    }
}
