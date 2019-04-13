package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.interfaces.BusFilterCallBack;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.busStations.StationList;
import com.payz24.utils.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahesh on 2/18/2018.
 */

public class BusFilterActivity extends BaseActivity implements View.OnClickListener {

    private Button btnAC, btnNonAc, btnSeater, btnSleeper,clearfilter;
    private LinearLayout llBusOperators, llBusBoardingPoints;
    private TextView tvBusOperators, tvBusBoardingPoints, tvSixAM, tvTwelvePM, tvSixPM, tvTwelveAM, tvApply;
    private ImageView ivBusOperatorsDropDown, ivBusBoardingPointsDropDown;
    private List<ApiAvailableBus> listOfAvailableBuses;
    private List<StationList> listOfBusStations;
    private LinkedList<String> listOfSelectedOperatorsNames;
    private boolean isACSelected=false;
    private boolean isNonACSelected=false;
    private boolean isSeaterSelected=false;
    private boolean isSleeperSelected=false;
    private boolean isSixAMSelected=false;
    private boolean isTwelvePMSelected=false;
    private boolean isSixPMSelected=false;
    private boolean isTwelveAMSelected=false;

    private boolean isACSelecteds=false;
    private boolean isNonACSelecteds=false;
    private boolean isSeaterSelecteds=false;
    private boolean isSleeperSelecteds=false;
    private boolean isSixAMSelecteds=false;
    private boolean isTwelvePMSelecteds=false;
    private boolean isSixPMSelecteds=false;
    private boolean isTwelveAMSelecteds=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_filter);
        initializeUi();
        getDataFromIntent();
        enableActionBar(true);
        initializeListeners();
    }

    private void getDataFromIntent() {
        listOfAvailableBuses = AppController.getInstance().getListOfAvailableBuses();
        listOfBusStations = AppController.getInstance().getListOfBusStations();
        Intent intent = getIntent();
        if (intent != null) {
            isACSelecteds = intent.getBooleanExtra("isACSelecteds",false);
            if (isACSelecteds==true)
            {
                btnAC.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnAC.setTextColor(getResources().getColor(R.color.white));

                btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));

                isACSelected = true;
                isNonACSelected = false;
            }else if (isACSelecteds==false)
            {


            }

            isNonACSelecteds = intent.getBooleanExtra("isNonACSelecteds",false);
            if (isNonACSelecteds==true)
            {
                btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnNonAc.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnNonAc.setTextColor(getResources().getColor(R.color.white));

                isACSelected = false;
                isNonACSelected = true;


            }else if (isNonACSelecteds==false)
            {

            }

            isSeaterSelecteds = intent.getBooleanExtra("isSeaterSelecteds",false);
            if (isSeaterSelecteds==true)
            {
                btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnSeater.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnSeater.setTextColor(getResources().getColor(R.color.white));

                isSeaterSelected = true;
                isSleeperSelected = false;

            }else if (isSeaterSelecteds==false)
            {
                Log.e("isSeaterSelecteds","No"+isSeaterSelecteds);

            }

            isSleeperSelecteds = intent.getBooleanExtra("isSleeperSelecteds",false);
            if (isSleeperSelecteds==true)
            {
                btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnSleeper.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnSleeper.setTextColor(getResources().getColor(R.color.white));
                isSleeperSelected = true;
                isSeaterSelected = false;

            }else if (isSleeperSelecteds==false)
            {
                Log.e("isSleeperSelecteds","No"+isSleeperSelecteds);
            }


            isSixAMSelecteds = intent.getBooleanExtra("isSixAMSelecteds",false);
            Log.e("isSixAMSelected", String.valueOf(isSixAMSelecteds));


            if (isSixAMSelecteds==true) {
                Log.e("isSleeperSelecteds","Yes"+isSixAMSelecteds);
                tvSixAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tvSixAM.setTextColor(getResources().getColor(R.color.white));

                tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                isSixAMSelected = true;
                isSixPMSelected = false;
                isTwelveAMSelected = false;
                isTwelvePMSelected = false;

            }



            isTwelvePMSelecteds = intent.getBooleanExtra("isTwelvePMSelecteds",false);
            Log.e("isTwelvePMSelected", String.valueOf(isTwelvePMSelecteds));

            if (isTwelvePMSelecteds==true) {
                Log.e("isTwelvePMSelecteds","Yes"+isTwelvePMSelecteds);
                tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvTwelvePM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tvTwelvePM.setTextColor(getResources().getColor(R.color.white));

                isTwelvePMSelected = true;
                isSixAMSelected = false;
                isSixPMSelected = false;
                isTwelveAMSelected = false;
            }


            isSixPMSelecteds = intent.getBooleanExtra("isSixPMSelecteds",false);
            Log.e("isSixPMSelected", String.valueOf(isSixPMSelecteds));

            if (isSixPMSelecteds==true) {
                Log.e("isSixPMSelecteds","Yes"+isSixPMSelecteds);

                tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvSixPM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tvSixPM.setTextColor(getResources().getColor(R.color.white));

                tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                isSixAMSelected = false;
                isSixPMSelected = true;
                isTwelveAMSelected = false;
                isTwelvePMSelected = false;
            }



            isTwelveAMSelecteds = intent.getBooleanExtra("isTwelveAMSelecteds",false);
            Log.e("isTwelveAMSelected", String.valueOf(isTwelveAMSelecteds));

            if (isTwelveAMSelecteds==true) {
                Log.e("isTwelveAMSelecteds","Yes"+isTwelveAMSelecteds);
                tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                tvTwelveAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tvTwelveAM.setTextColor(getResources().getColor(R.color.white));

                tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                isTwelveAMSelected = true;
                isSixAMSelected = false;
                isSixPMSelected = false;
                isTwelvePMSelected = false;
            }


        }

    }



    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.filters));
        setSupportActionBar(toolbar);

        btnAC = findViewById(R.id.btnAC);
        btnNonAc = findViewById(R.id.btnNonAc);
        btnSeater = findViewById(R.id.btnSeater);
        btnSleeper = findViewById(R.id.btnSleeper);
        clearfilter = findViewById(R.id.clearfilter);

        llBusOperators = findViewById(R.id.llBusOperators);
        llBusBoardingPoints = findViewById(R.id.llBusBoardingPoints);
        tvBusOperators = findViewById(R.id.tvBusOperators);
        tvBusBoardingPoints = findViewById(R.id.tvBusBoardingPoints);
        ivBusOperatorsDropDown = findViewById(R.id.ivBusOperatorsDropDown);
        ivBusBoardingPointsDropDown = findViewById(R.id.ivBusBoardingPointsDropDown);

        tvSixAM = findViewById(R.id.tvSixAM);
        tvTwelvePM = findViewById(R.id.tvTwelvePM);
        tvSixPM = findViewById(R.id.tvSixPM);
        tvTwelveAM = findViewById(R.id.tvTwelveAM);
        tvApply = findViewById(R.id.tvApply);


       /* if (String.valueOf(isACSelecteds).equals("true")) {
            btnAC.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnAC.setTextColor(getResources().getColor(R.color.white));

            btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));

            isACSelected = true;
            isNonACSelected = false;
        } else if (String.valueOf(isACSelecteds).equals("false")){

            btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));

            isACSelected = false;
            isNonACSelected = false;
        }

        if (String.valueOf(isNonACSelecteds).equals("true")) {

            btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnNonAc.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnNonAc.setTextColor(getResources().getColor(R.color.white));

            isACSelected = false;
            isNonACSelected = true;
        } else if (String.valueOf(isNonACSelecteds).equals("false")) {

            btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));

            isACSelected = false;
            isNonACSelected = false;
        }

        if (String.valueOf(isSeaterSelecteds).equals("true")) {
            btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnSeater.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnSeater.setTextColor(getResources().getColor(R.color.white));

            isSeaterSelected = true;
            isSleeperSelected = false;
        } else if (String.valueOf(isSeaterSelecteds).equals("false")) {
            btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSeaterSelected = false;
            isSleeperSelected = false;
        }

        if (String.valueOf(isSleeperSelecteds).equals("true")) {

            btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnSleeper.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnSleeper.setTextColor(getResources().getColor(R.color.white));

            isSleeperSelected = true;
            isSeaterSelected = false;
        } else if (String.valueOf(isSleeperSelecteds).equals("false")) {

            btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));

            btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
            btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSeaterSelected = false;
            isSleeperSelected = false;
        }

        else if (String.valueOf(isSixAMSelected).equals("true")) {
            tvSixAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tvSixAM.setTextColor(getResources().getColor(R.color.white));

            tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSixAMSelected = true;
            isSixPMSelected = false;
            isTwelveAMSelected = false;
            isTwelvePMSelected = false;

        } else if (String.valueOf(isSixAMSelected).equals("false")) {

            tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSixAMSelected = false;
            isSixPMSelected = false;
            isTwelveAMSelected = false;
            isTwelvePMSelected = false;
        }

        else if (String.valueOf(isTwelvePMSelected).equals("true")) {

            tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelvePM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tvTwelvePM.setTextColor(getResources().getColor(R.color.white));

            isTwelvePMSelected = true;
            isSixAMSelected = false;
            isSixPMSelected = false;
            isTwelveAMSelected = false;
        } else if (String.valueOf(isTwelvePMSelected).equals("false")) {

            tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSixAMSelected = false;
            isSixPMSelected = false;
            isTwelveAMSelected = false;
            isTwelvePMSelected = false;
        }

        else  if (String.valueOf(isSixPMSelected).equals("true")) {

            tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvSixPM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tvSixPM.setTextColor(getResources().getColor(R.color.white));

            tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSixAMSelected = false;
            isSixPMSelected = true;
            isTwelveAMSelected = false;
            isTwelvePMSelected = false;
        } else if (String.valueOf(isSixPMSelected).equals("false")) {
            tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSixAMSelected = false;
            isSixPMSelected = false;
            isTwelveAMSelected = false;
            isTwelvePMSelected = false;
        }

        else if (String.valueOf(isTwelveAMSelected).equals("true")) {

            tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelveAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tvTwelveAM.setTextColor(getResources().getColor(R.color.white));

            tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

            isTwelveAMSelected = true;
            isSixAMSelected = false;
            isSixPMSelected = false;
            isTwelvePMSelected = false;
        } else if (String.valueOf(isTwelveAMSelected).equals("false")) {
            tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

            tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
            tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

            isSixAMSelected = false;
            isSixPMSelected = false;
            isTwelveAMSelected = false;
            isTwelvePMSelected = false;
        }*/

    }

    private void initializeListeners() {
        btnAC.setOnClickListener(this);
        btnNonAc.setOnClickListener(this);
        btnSeater.setOnClickListener(this);
        btnSleeper.setOnClickListener(this);
        llBusOperators.setOnClickListener(this);
        llBusBoardingPoints.setOnClickListener(this);
        tvSixAM.setOnClickListener(this);
        tvTwelvePM.setOnClickListener(this);
        tvSixPM.setOnClickListener(this);
        tvTwelveAM.setOnClickListener(this);
        tvApply.setOnClickListener(this);
        clearfilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnAC:
                if (!isACSelected) {
                    btnAC.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btnAC.setTextColor(getResources().getColor(R.color.white));

                    btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isACSelected = true;
                    isNonACSelected = false;
                } else {

                    btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));

                    btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isACSelected = false;
                    isNonACSelected = false;
                }
                break;
            case R.id.btnNonAc:
                if (!isNonACSelected) {

                    btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));

                    btnNonAc.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btnNonAc.setTextColor(getResources().getColor(R.color.white));

                    isACSelected = false;
                    isNonACSelected = true;
                } else {

                    btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));

                    btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isACSelected = false;
                    isNonACSelected = false;
                }
                break;
            case R.id.btnSeater:
                if (!isSeaterSelected) {
                    btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));

                    btnSeater.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btnSeater.setTextColor(getResources().getColor(R.color.white));

                    isSeaterSelected = true;
                    isSleeperSelected = false;
                } else {
                    btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));

                    btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isSeaterSelected = false;
                    isSleeperSelected = false;
                }
                break;
            case R.id.btnSleeper:
                if (!isSleeperSelected) {

                    btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));
                    btnSleeper.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btnSleeper.setTextColor(getResources().getColor(R.color.white));
                    isSleeperSelected = true;
                    isSeaterSelected = false;
                } else {

                    btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));
                    btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                    btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));
                    isSeaterSelected = false;
                    isSleeperSelected = false;
                }
                break;
            case R.id.llBusOperators:
                Intent busOperatorsIntent = new Intent(this, BusOperatorsFiltersActivity.class);
                startActivity(busOperatorsIntent);
                break;

            case R.id.clearfilter:

                btnSeater.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnSeater.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnSleeper.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnSleeper.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnAC.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnAC.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnNonAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));
                btnNonAc.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));
                isACSelected = false;
                isNonACSelected = false;
                isSeaterSelected = false;
                isSleeperSelected = false;
                isSixAMSelected = false;
                isSixPMSelected = false;
                isTwelveAMSelected = false;
                isTwelvePMSelected = false;
                break;

            case R.id.llBusBoardingPoints:
                break;
            case R.id.tvSixAM:
                if (!isSixAMSelected) {
                    tvSixAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvSixAM.setTextColor(getResources().getColor(R.color.white));

                    tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isSixAMSelected = true;
                    isSixPMSelected = false;
                    isTwelveAMSelected = false;
                    isTwelvePMSelected = false;

                } else {

                    tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isSixAMSelected = false;
                    isSixPMSelected = false;
                    isTwelveAMSelected = false;
                    isTwelvePMSelected = false;
                }
                break;
            case R.id.tvTwelvePM:
                if (!isTwelvePMSelected) {

                    tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelvePM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.white));

                    isTwelvePMSelected = true;
                    isSixAMSelected = false;
                    isSixPMSelected = false;
                    isTwelveAMSelected = false;
                } else {

                    tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isSixAMSelected = false;
                    isSixPMSelected = false;
                    isTwelveAMSelected = false;
                    isTwelvePMSelected = false;
                }
                break;
            case R.id.tvSixPM:
                if (!isSixPMSelected) {

                    tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvSixPM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvSixPM.setTextColor(getResources().getColor(R.color.white));

                    tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isSixAMSelected = false;
                    isSixPMSelected = true;
                    isTwelveAMSelected = false;
                    isTwelvePMSelected = false;
                } else {
                    tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isSixAMSelected = false;
                    isSixPMSelected = false;
                    isTwelveAMSelected = false;
                    isTwelvePMSelected = false;
                }
                break;
            case R.id.tvTwelveAM:
                if (!isTwelveAMSelected) {

                    tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelveAM.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.white));

                    tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isTwelveAMSelected = true;
                    isSixAMSelected = false;
                    isSixPMSelected = false;
                    isTwelvePMSelected = false;
                } else {
                    tvSixAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvSixPM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvSixPM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelveAM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelveAM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    tvTwelvePM.setBackgroundDrawable(getResources().getDrawable(R.drawable.bus_filter_bg));;
                    tvTwelvePM.setTextColor(getResources().getColor(R.color.colorPrimary));

                    isSixAMSelected = false;
                    isSixPMSelected = false;
                    isTwelveAMSelected = false;
                    isTwelvePMSelected = false;
                }
                break;
            case R.id.tvApply:
                listOfSelectedOperatorsNames = AppController.getInstance().getListOfSelectedOperatorsNames();
                BusFilterCallBack busFilterCallBack = (BusFilterCallBack) AppController.getInstance().getAvailableBusesContext();
                busFilterCallBack.busFilter(listOfSelectedOperatorsNames, isACSelected, isNonACSelected, isSeaterSelected, isSleeperSelected, isSixAMSelected, isTwelvePMSelected, isSixPMSelected, isTwelveAMSelected);
                finish();
                break;
            default:
                break;
        }
    }
}
