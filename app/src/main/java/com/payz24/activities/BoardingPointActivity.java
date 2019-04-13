package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.adapter.BoardingPointAdapter;
import com.payz24.application.AppController;
import com.payz24.interfaces.NoResultsCallBack;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.availableBuses.BoardingPoint;
import com.payz24.utils.PreferenceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahesh on 1/29/2018.
 */

public class BoardingPointActivity extends BaseActivity implements View.OnClickListener, TextWatcher, BoardingPointAdapter.ItemClickListener, NoResultsCallBack {

    private EditText etBoardingPoints;
    private ImageView ivClear, ivBack;
    private RecyclerView rvBoardingPoints;
    private TextView tvError;
    private ApiAvailableBus selectedAvailableBus;
    private List<BoardingPoint> listOfBoardingPoints;
    private BoardingPointAdapter boardingPointAdapter;
    private String leavingFromStationName = "";
    private String goingToStationName = "";
    private String travellingDate = "";
    private String travelsName = "";
    private String seatName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boarding_point_activity);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareResults();
    }

    private void getDataFromIntent() {
        selectedAvailableBus = AppController.getInstance().getSelectedAvailableBus();
        if (getIntent() != null) {
            leavingFromStationName = getIntent().getStringExtra(getString(R.string.leaving_from));
            goingToStationName = getIntent().getStringExtra(getString(R.string.going_to));
            travellingDate = getIntent().getStringExtra(getString(R.string.doj));
            travelsName = getIntent().getStringExtra(getString(R.string.travels_names));
            if (getIntent().hasExtra(getString(R.string.seat_names)))
                seatName = getIntent().getStringExtra(getString(R.string.seat_names));
            else
                seatName = "";
        }
    }

    private void initializeUi() {
        etBoardingPoints = findViewById(R.id.etBoardingPoints);
        ivClear = findViewById(R.id.ivClear);
        ivBack = findViewById(R.id.ivBack);
        rvBoardingPoints = findViewById(R.id.rvBoardingPoints);
        tvError = findViewById(R.id.tvError);
    }

    private void initializeListeners() {
        etBoardingPoints.addTextChangedListener(this);
        ivClear.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void prepareResults() {
        if (selectedAvailableBus != null) {
            listOfBoardingPoints = selectedAvailableBus.getBoardingPoints();
            initializeAdapter();
        }
    }

    private void initializeAdapter() {
        boardingPointAdapter = new BoardingPointAdapter(this, listOfBoardingPoints);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvBoardingPoints.setItemAnimator(new DefaultItemAnimator());
        rvBoardingPoints.setLayoutManager(layoutManager);
        boardingPointAdapter.setClickListener(this);
        rvBoardingPoints.setAdapter(boardingPointAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivClear:
                etBoardingPoints.setText("");
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

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (boardingPointAdapter != null) {
            filter(editable.toString());
        }

    }



    void filter(String text) {
        List<BoardingPoint> temp = new ArrayList();
        for (BoardingPoint d : listOfBoardingPoints) {
            if (d.getLocation().toLowerCase().contains(text.toLowerCase()) ) {
                temp.add(d);
            }
        }
        boardingPointAdapter.updateList(temp);
    }

    @Override
    public void onClick(View view, int position) {
        AppController.getInstance().setSelectedBoardingPoint(listOfBoardingPoints.get(position));
       // Log.e("poistion",""+position);
        PreferenceConnector.writeString(BoardingPointActivity.this,getString(R.string.bp), ""+position);


        Intent droppingPointIntent = new Intent(this, DroppingPointActivity.class);
        droppingPointIntent.putExtra(getString(R.string.leaving_from), leavingFromStationName);
        droppingPointIntent.putExtra(getString(R.string.going_to), goingToStationName);
        droppingPointIntent.putExtra(getString(R.string.doj), travellingDate);
        droppingPointIntent.putExtra(getString(R.string.travels_names), travelsName);
        if (!seatName.isEmpty())
            droppingPointIntent.putExtra(getString(R.string.seat_names), seatName);
        startActivity(droppingPointIntent);
    }

    @Override
    public void noResultsFound(boolean isResultsAvailable) {
        if (!isResultsAvailable) {
            rvBoardingPoints.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        } else {
            rvBoardingPoints.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
        }
    }
}
