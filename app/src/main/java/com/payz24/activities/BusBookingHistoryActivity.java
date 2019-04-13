package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.adapter.BusBookingHistoryAdapter;
import com.payz24.http.BusBookingHistoryHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.busBookingHistory.BusBookingHistory;
import com.payz24.responseModels.busBookingHistory.Result;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahesh on 3/7/2018.
 */

public class BusBookingHistoryActivity extends BaseActivity implements HttpReqResCallBack, BusBookingHistoryAdapter.ItemClickListener {

    private RecyclerView rvBusBookingHistory;
    private TextView tvError;
    private List<Result> listOfResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_booking_history_fragment);
        initializeUi();
        enableActionBar(true);
        serviceCallToGetBusBookingHistory();
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.bus_booking_history));
        setSupportActionBar(toolbar);

        rvBusBookingHistory = findViewById(R.id.rvBusBookingHistory);
        tvError = findViewById(R.id.tvError);
    }

    private void serviceCallToGetBusBookingHistory() {
        String userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID, userId);
        mapOfRequestParams.put(Constants.TYPE, "3");
        BusBookingHistoryHttpClient busBookingHistoryHttpClient = new BusBookingHistoryHttpClient(this);
        busBookingHistoryHttpClient.callBack = this;
        busBookingHistoryHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_BUS_BOOKING_HISTORY:
                if (jsonResponse != null) {
                    Log.e("jsonRs",jsonResponse);
                    BusBookingHistory busBookingHistory = new Gson().fromJson(jsonResponse, BusBookingHistory.class);
                    if (busBookingHistory != null) {
                        String status = busBookingHistory.getStatus();
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            listOfResults = busBookingHistory.getResult();
                            if (listOfResults != null && listOfResults.size() != 0) {
                                initializeAdapter();
                            }else
                            {
                                tvError.setVisibility(View.VISIBLE);
                            }

                        } else
                        {
                            tvError.setVisibility(View.VISIBLE);
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
        BusBookingHistoryAdapter busBookingHistoryAdapter = new BusBookingHistoryAdapter(this, listOfResults);
        busBookingHistoryAdapter.setClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvBusBookingHistory.setLayoutManager(layoutManager);
        rvBusBookingHistory.setAdapter(busBookingHistoryAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        //  http://travexsoftsol.com/projects/payz24/rest/listfdata?userid=20&fid=125
        //http://travexsoftsol.com/projects/payz24/rest/listbData?userid=20&bid=105

        String bid = listOfResults.get(position).getBid();
        Intent busTicketOverViewScreenIntent = new Intent(this, BusTicketOverviewScreenActivity.class);
        busTicketOverViewScreenIntent.putExtra(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID, bid);
        startActivity(busTicketOverViewScreenIntent);
    }
}
