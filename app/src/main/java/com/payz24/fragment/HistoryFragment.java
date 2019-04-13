package com.payz24.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payz24.R;
import com.payz24.activities.BusBookingHistoryActivity;
import com.payz24.activities.FlightsBookingHistoryActivity;
import com.payz24.activities.recharge.RechargeHistory;
import com.payz24.adapter.HistoryAdapter;

import java.util.LinkedList;

/**
 * Created by mahesh on 3/5/2018.
 */

public class HistoryFragment extends BaseFragment implements HistoryAdapter.ItemClickListener {

    private RecyclerView rvHistory;
    private LinkedList<String> listOfBookingTitles;
    private LinkedList<Integer> listOfBookingTitlesImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_history_fragment, container, false);
        initializeUi(view);
        prepareList();
        initializeAdapter();
        return view;
    }

    private void initializeUi(View view) {
        rvHistory = view.findViewById(R.id.rvHistory);
        listOfBookingTitles = new LinkedList<>();
        listOfBookingTitlesImages = new LinkedList<>();
    }

    private void prepareList() {
        listOfBookingTitles.add(getString(R.string.bus_booking));
        listOfBookingTitles.add(getString(R.string.flight_booking));
        listOfBookingTitles.add(getString(R.string.recharge));

        listOfBookingTitlesImages.add(R.drawable.ic_bus);
        listOfBookingTitlesImages.add(R.drawable.ic_flights);
        listOfBookingTitlesImages.add(R.drawable.ic_recharge);
    }

    private void initializeAdapter() {
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), listOfBookingTitles, listOfBookingTitlesImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setLayoutManager(layoutManager);
        historyAdapter.setClickListener(this);
        rvHistory.setAdapter(historyAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        switch (position) {
            case 0:
                goToBusBookingHistory();
                break;
            case 1:
                goToFlightBookings();
                break;
            case 2:
                goToRecharge();
                break;
            default:
                break;
        }
    }

    private void goToBusBookingHistory() {
        Intent busBookingHistoryIntent = new Intent(getActivity(), BusBookingHistoryActivity.class);
        startActivity(busBookingHistoryIntent);
    }

    private void goToFlightBookings() {
        Intent flightBookingHistoryIntent = new Intent(getActivity(), FlightsBookingHistoryActivity.class);
        startActivity(flightBookingHistoryIntent);
    }

    private void goToRecharge() {
        Intent flightBookingHistoryIntent = new Intent(getActivity(), RechargeHistory.class);
        startActivity(flightBookingHistoryIntent);
    }
}
