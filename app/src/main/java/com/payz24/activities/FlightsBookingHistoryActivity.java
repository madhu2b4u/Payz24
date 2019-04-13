package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.adapter.FlightsBookingHistoryAdapter;
import com.payz24.http.BusBookingHistoryHttpClient;
import com.payz24.http.FlightBookingHistoryHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightBookingHistory.FlightBookingHistory;
import com.payz24.responseModels.flightBookingHistory.Result;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahesh on 3/8/2018.
 */

public class FlightsBookingHistoryActivity extends BaseActivity implements HttpReqResCallBack, FlightsBookingHistoryAdapter.ItemClickListener {

    private RecyclerView rvFlightBookingHistory;
    private TextView tvError;
    private List<Result> listOfResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flights_booking_history);
        initializeUi();
        enableActionBar(true);
        serviceCallToGetFlightHistory();
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.flight_booking_history));
        setSupportActionBar(toolbar);

        rvFlightBookingHistory = findViewById(R.id.rvFlightBookingHistory);
        tvError = findViewById(R.id.tvError);
    }

    private void serviceCallToGetFlightHistory() {
        String userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.FLIGHT_BOOKING_HISTORY_PARAM_USER_ID, userId);
        mapOfRequestParams.put(Constants.TYPE, "3");

        FlightBookingHistoryHttpClient flightBookingHistoryHttpClient = new FlightBookingHistoryHttpClient(this);
        flightBookingHistoryHttpClient.callBack = this;
        flightBookingHistoryHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_FLIGHT_BOOKING_HISTORY:
                Log.e("json",jsonResponse);
                if (jsonResponse != null) {
                    FlightBookingHistory flightBookingHistory = new Gson().fromJson(jsonResponse, FlightBookingHistory.class);
                    if (flightBookingHistory != null) {
                        rvFlightBookingHistory.setVisibility(View.VISIBLE);
                        tvError.setVisibility(View.GONE);
                        String status = flightBookingHistory.getStatus();
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            listOfResults = flightBookingHistory.getResult();
                            if (listOfResults != null && listOfResults.size() != 0) {
                                initializeAdapter();
                            }else
                            {
                                rvFlightBookingHistory.setVisibility(View.GONE);
                                tvError.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                break;
        }
    }

    private void initializeAdapter() {
        FlightsBookingHistoryAdapter flightsBookingHistoryAdapter = new FlightsBookingHistoryAdapter(this, listOfResults);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvFlightBookingHistory.setLayoutManager(layoutManager);
        flightsBookingHistoryAdapter.setClickListener(this);
        rvFlightBookingHistory.setItemAnimator(new DefaultItemAnimator());
        rvFlightBookingHistory.setAdapter(flightsBookingHistoryAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        String fid = listOfResults.get(position).getFid();
        Intent busTicketOverViewScreenIntent = new Intent(this, FlightDetails.class);
        busTicketOverViewScreenIntent.putExtra("pnr", fid);
        startActivity(busTicketOverViewScreenIntent);
    }
}
