package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.adapter.DroppingPointAdapter;
import com.payz24.application.AppController;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.availableBuses.BoardingPoint;
import com.payz24.responseModels.blockTicket.DroppingPoint;
import com.payz24.utils.PreferenceConnector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahesh on 1/29/2018.
 */

public class DroppingPointActivity extends BaseActivity implements View.OnClickListener, TextWatcher, DroppingPointAdapter.ItemClickListener {

    private RecyclerView rvDroppingPoints;
    private TextView tvError;
    private ImageView ivBack;
    private EditText etDroppingPoints;
    private ImageView ivClear;
    private ApiAvailableBus selectedAvailableBus;
    private String leavingFromStationName = "";
    private String goingToStationName = "";
    private String travellingDate = "";
    private String travelsName = "";
    private String arrivalTime;
    private String seatName = "";
    DroppingPointAdapter droppingPointAdapter;
    private List<DroppingPoint> listOfBoardingPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dropping_point);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        initializeAdapter();
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
        rvDroppingPoints = findViewById(R.id.rvDroppingPoints);
        tvError = findViewById(R.id.tvError);
        ivBack = findViewById(R.id.ivBack);
        etDroppingPoints = findViewById(R.id.etDroppingPoints);
        etDroppingPoints.setHint("Search for dropping points");
        ivClear = findViewById(R.id.ivClear);
    }



    private void initializeListeners() {
        ivBack.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        etDroppingPoints.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivClear:
                etDroppingPoints.setText("");
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
       /* if (droppingPointAdapter != null) {
            filter(editable.toString());
        }*/

    }

/*

    void filter(String text) {
        List<DroppingPoint> temp = new ArrayList();
        for (DroppingPoint d : listOfBoardingPoints) {
            if (d.getLocation().toLowerCase().contains(text.toLowerCase()) ) {
                temp.add(d);
            }
        }
        droppingPointAdapter.updateList(temp);
    }
*/

    private void initializeAdapter() {
        selectedAvailableBus = AppController.getInstance().getSelectedAvailableBus();
        if (selectedAvailableBus != null) {
            arrivalTime = selectedAvailableBus.getArrivalTime();
            droppingPointAdapter = new DroppingPointAdapter(this, goingToStationName, arrivalTime);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvDroppingPoints.setLayoutManager(layoutManager);
            rvDroppingPoints.setItemAnimator(new DefaultItemAnimator());
            droppingPointAdapter.setClickListener(this);
            rvDroppingPoints.setAdapter(droppingPointAdapter);
        } else {
            Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent passengerDetails = new Intent(this, PassengerDetails.class);
        PreferenceConnector.writeString(DroppingPointActivity.this,getString(R.string.dp), ""+position);
        passengerDetails.putExtra(getString(R.string.arrival_time), arrivalTime);
        passengerDetails.putExtra(getString(R.string.leaving_from), leavingFromStationName);
        passengerDetails.putExtra(getString(R.string.going_to), goingToStationName);
        passengerDetails.putExtra(getString(R.string.doj), travellingDate);
        passengerDetails.putExtra(getString(R.string.travels_names), travelsName);
        if (!seatName.isEmpty())
            passengerDetails.putExtra(getString(R.string.seat_names), seatName);
        startActivity(passengerDetails);
    }
}
