package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.interfaces.FlightFilterCallBack;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Created by mahesh on 2/24/2018.
 */

public class FlightsFilterActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView ivBack, ivAirLineImage;
    private TextView tvReset, tvAirwayName, tvAirwayFare;
    private TextView tvBeforeElevenAM, tvElevenAM, tvFivePM, tvAfterNinePM;
    private TextView tvNonstopCount, tvNonstop, tvStopCount, tvOneStop, tvTwoPlusCount, tvTwoPlusStops, tvApplyFilter;
    private LinearLayout llNonStops, llOneStop, llTwoPlusStops;
    private LinearLayout llPreferredAirlinesNames;
    private CheckBox checkbox;
    private LinkedList<String> listOfAirwayNames;
    private LinkedList<String> listOfAirwayImages;
    private LinkedList<String> listOfAirwayFares;

    private boolean isBeforeElevenAM;
    private boolean isElevenAM;
    private boolean isFivePM;
    private boolean isAfterNinePM;
    private LinkedList<String> listOfCheckNames;
    private boolean isNonStopSelected;
    private boolean isOneStopSelected;
    private boolean isTwoStopSelected;
    private String from = "";

    private boolean isBeforeElevenAMs=false;
    private boolean isElevenAMs=false;
    private boolean isFivePMs=false;
    private boolean isAfterNinePMs=false;
    private boolean isNonStopSelecteds=false;
    private boolean isOneStopSelecteds=false;
    private boolean isTwoStopSelecteds=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flight_filters);
        initializeUi();
        getDataFromIntent();
        initializeListeners();
        initializeAirwaysView();
    }

    private void getDataFromIntent() {
        listOfAirwayNames = AppController.getInstance().getListOfAirwayNames();
        Log.e("listOfAirwayNames",listOfAirwayNames.toString());
        listOfAirwayImages = AppController.getInstance().getListOfAirwayImages();
        listOfAirwayFares = AppController.getInstance().getListOfAirwayFares();
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.from))) {
            from = intent.getStringExtra(getString(R.string.from));
        }

        isBeforeElevenAMs = intent.getBooleanExtra("isBeforeElevenAMs",false);
        Log.e("isBeforeElevenAMs",""+isBeforeElevenAMs);
        if (isBeforeElevenAMs==true)
        {
            isBeforeElevenAM = true;
            isElevenAM = false;
            isFivePM = false;
            isAfterNinePM = false;

            tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvElevenAM.setTextColor(getResources().getColor(R.color.black_90));
            tvFivePM.setTextColor(getResources().getColor(R.color.black_90));
            tvAfterNinePM.setTextColor(getResources().getColor(R.color.black_90));

        }else if (isBeforeElevenAMs==false)
        {


        }
        isElevenAMs = intent.getBooleanExtra("isElevenAMs",false);
        Log.e("isElevenAMs",""+isElevenAMs);
        if (isElevenAMs==true)
        {
            isBeforeElevenAM = false;
            isElevenAM = true;
            isFivePM = false;
            isAfterNinePM = false;

            tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.black_90));
            tvElevenAM.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvFivePM.setTextColor(getResources().getColor(R.color.black_90));
            tvAfterNinePM.setTextColor(getResources().getColor(R.color.black_90));

        }else if (isElevenAMs==false)
        {


        }



        isFivePMs = intent.getBooleanExtra("isFivePMs",false);
        Log.e("isFivePMs",""+isFivePMs);
        if (isFivePMs==true)
        {
            isBeforeElevenAM = false;
            isElevenAM = false;
            isFivePM = true;
            isAfterNinePM = false;

            tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.black_90));
            tvElevenAM.setTextColor(getResources().getColor(R.color.black_90));
            tvFivePM.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvAfterNinePM.setTextColor(getResources().getColor(R.color.black_90));

        }else if (isFivePMs==false)
        {


        }
        isAfterNinePMs = intent.getBooleanExtra("isAfterNinePMs",false);
        Log.e("isAfterNinePMs",""+isAfterNinePMs);
        if (isAfterNinePMs==true)
        {
            isBeforeElevenAM = false;
            isElevenAM = false;
            isFivePM = false;
            isAfterNinePM = true;

            tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.black_90));
            tvElevenAM.setTextColor(getResources().getColor(R.color.black_90));
            tvFivePM.setTextColor(getResources().getColor(R.color.black_90));
            tvAfterNinePM.setTextColor(getResources().getColor(R.color.colorPrimary));

        }else if (isAfterNinePMs==false)
        {


        }
        isNonStopSelecteds = intent.getBooleanExtra("isNonStopSelecteds",false);
        Log.e("isNonStopSelecteds",""+isNonStopSelecteds);
        if (isNonStopSelecteds==true)
        {
            isNonStopSelected = true;
            isOneStopSelected = false;
            isTwoStopSelected = false;
            tvNonstop.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvNonstopCount.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvStopCount.setTextColor(getResources().getColor(R.color.black_90));
            tvOneStop.setTextColor(getResources().getColor(R.color.black_90));
            tvTwoPlusCount.setTextColor(getResources().getColor(R.color.black_90));
            tvTwoPlusStops.setTextColor(getResources().getColor(R.color.black_90));

        }else if (isNonStopSelecteds==false)
        {


        }


        isOneStopSelecteds = intent.getBooleanExtra("isOneStopSelecteds",false);

        Log.e("isOneStopSelecteds",""+isOneStopSelecteds);
        if (isOneStopSelecteds==true)
        {
            isNonStopSelected = false;
            isOneStopSelected = true;
            isTwoStopSelected = false;
            tvNonstop.setTextColor(getResources().getColor(R.color.black_90));
            tvNonstopCount.setTextColor(getResources().getColor(R.color.black_90));
            tvStopCount.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvOneStop.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvTwoPlusCount.setTextColor(getResources().getColor(R.color.black_90));
            tvTwoPlusStops.setTextColor(getResources().getColor(R.color.black_90));

        }else if (isOneStopSelecteds==false)
        {


        }
        isTwoStopSelecteds = intent.getBooleanExtra("isTwoStopSelecteds",false);

        Log.e("isTwoStopSelecteds",""+isTwoStopSelecteds);
        if (isTwoStopSelecteds==true)
        {
            isNonStopSelected = false;
            isOneStopSelected = false;
            isTwoStopSelected = true;
            tvNonstop.setTextColor(getResources().getColor(R.color.black_90));
            tvNonstopCount.setTextColor(getResources().getColor(R.color.black_90));
            tvStopCount.setTextColor(getResources().getColor(R.color.black_90));
            tvOneStop.setTextColor(getResources().getColor(R.color.black_90));
            tvTwoPlusCount.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvTwoPlusStops.setTextColor(getResources().getColor(R.color.colorPrimary));

        }else if (isTwoStopSelecteds==false)
        {


        }

    }

    private void initializeUi() {
        ivBack = findViewById(R.id.ivBack);
        tvReset = findViewById(R.id.tvReset);
        tvBeforeElevenAM = findViewById(R.id.tvBeforeElevenAM);
        tvElevenAM = findViewById(R.id.tvElevenAM);
        tvFivePM = findViewById(R.id.tvFivePM);
        tvAfterNinePM = findViewById(R.id.tvAfterNinePM);
        tvNonstopCount = findViewById(R.id.tvNonstopCount);
        tvNonstop = findViewById(R.id.tvNonstop);
        tvStopCount = findViewById(R.id.tvStopCount);
        tvOneStop = findViewById(R.id.tvOneStop);
        tvTwoPlusCount = findViewById(R.id.tvTwoPlusCount);
        tvTwoPlusStops = findViewById(R.id.tvTwoPlusStops);
        tvApplyFilter = findViewById(R.id.tvApplyFilter);
        llNonStops = findViewById(R.id.llNonStops);
        llOneStop = findViewById(R.id.llOneStop);
        llTwoPlusStops = findViewById(R.id.llTwoPlusStops);
        llPreferredAirlinesNames = findViewById(R.id.llPreferredAirlinesNames);

        listOfCheckNames = new LinkedList<>();
    }

    private void initializeListeners() {
        tvBeforeElevenAM.setOnClickListener(this);
        tvElevenAM.setOnClickListener(this);
        tvFivePM.setOnClickListener(this);
        tvAfterNinePM.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        tvApplyFilter.setOnClickListener(this);
        llNonStops.setOnClickListener(this);
        llOneStop.setOnClickListener(this);
        llTwoPlusStops.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvBeforeElevenAM:
                isBeforeElevenAM = true;
                isElevenAM = false;
                isFivePM = false;
                isAfterNinePM = false;

                tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvFivePM.setTextColor(getResources().getColor(R.color.black_90));
                tvAfterNinePM.setTextColor(getResources().getColor(R.color.black_90));
                break;
            case R.id.tvElevenAM:
                isBeforeElevenAM = false;
                isElevenAM = true;
                isFivePM = false;
                isAfterNinePM = false;

                tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvElevenAM.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvFivePM.setTextColor(getResources().getColor(R.color.black_90));
                tvAfterNinePM.setTextColor(getResources().getColor(R.color.black_90));
                break;
            case R.id.tvFivePM:
                isBeforeElevenAM = false;
                isElevenAM = false;
                isFivePM = true;
                isAfterNinePM = false;

                tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvFivePM.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvAfterNinePM.setTextColor(getResources().getColor(R.color.black_90));
                break;
            case R.id.tvAfterNinePM:
                isBeforeElevenAM = false;
                isElevenAM = false;
                isFivePM = false;
                isAfterNinePM = true;

                tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvFivePM.setTextColor(getResources().getColor(R.color.black_90));
                tvAfterNinePM.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvReset:
                isNonStopSelected = false;
                isOneStopSelected = false;
                isTwoStopSelected = false;
                isBeforeElevenAM = false;
                isElevenAM = false;
                isFivePM = false;
                isAfterNinePM = false;
                tvBeforeElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvElevenAM.setTextColor(getResources().getColor(R.color.black_90));
                tvFivePM.setTextColor(getResources().getColor(R.color.black_90));
                tvAfterNinePM.setTextColor(getResources().getColor(R.color.black_90));
                tvNonstop.setTextColor(getResources().getColor(R.color.black_90));
                tvNonstopCount.setTextColor(getResources().getColor(R.color.black_90));
                tvStopCount.setTextColor(getResources().getColor(R.color.black_90));
                tvOneStop.setTextColor(getResources().getColor(R.color.black_90));
                tvTwoPlusCount.setTextColor(getResources().getColor(R.color.black_90));
                tvTwoPlusStops.setTextColor(getResources().getColor(R.color.black_90));
                listOfCheckNames = new LinkedList<>();
                listOfCheckNames.clear();
                llPreferredAirlinesNames.removeAllViews();
                initializeAirwaysView();
                break;
            case R.id.llNonStops:
                isNonStopSelected = true;
                isOneStopSelected = false;
                isTwoStopSelected = false;
                tvNonstop.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvNonstopCount.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvStopCount.setTextColor(getResources().getColor(R.color.black_90));
                tvOneStop.setTextColor(getResources().getColor(R.color.black_90));
                tvTwoPlusCount.setTextColor(getResources().getColor(R.color.black_90));
                tvTwoPlusStops.setTextColor(getResources().getColor(R.color.black_90));
                break;
            case R.id.llOneStop:
                isNonStopSelected = false;
                isOneStopSelected = true;
                isTwoStopSelected = false;
                tvNonstop.setTextColor(getResources().getColor(R.color.black_90));
                tvNonstopCount.setTextColor(getResources().getColor(R.color.black_90));
                tvStopCount.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvOneStop.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvTwoPlusCount.setTextColor(getResources().getColor(R.color.black_90));
                tvTwoPlusStops.setTextColor(getResources().getColor(R.color.black_90));
                break;
            case R.id.llTwoPlusStops:
                isNonStopSelected = false;
                isOneStopSelected = false;
                isTwoStopSelected = true;
                tvNonstop.setTextColor(getResources().getColor(R.color.black_90));
                tvNonstopCount.setTextColor(getResources().getColor(R.color.black_90));
                tvStopCount.setTextColor(getResources().getColor(R.color.black_90));
                tvOneStop.setTextColor(getResources().getColor(R.color.black_90));
                tvTwoPlusCount.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvTwoPlusStops.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.tvApplyFilter:
                if (from.equalsIgnoreCase(getString(R.string.international_round_trip))) {
                    FlightFilterCallBack flightFilterCallBack = (FlightFilterCallBack) AppController.getInstance().getInternationalFlightsSearchRoundTripActivityContext();
                    flightFilterCallBack.flightFilter(isNonStopSelected, isOneStopSelected, isTwoStopSelected, isBeforeElevenAM, isElevenAM, isFivePM, isAfterNinePM, listOfCheckNames);
                }else if(from.equalsIgnoreCase(getString(R.string.domestic_flight_search_round_trip_depart))){
                    FlightFilterCallBack flightFilterCallBack =  AppController.getInstance().getDomesticFlightsSearchRoundTripDepartFragmentContext();
                    flightFilterCallBack.flightFilter(isNonStopSelected, isOneStopSelected, isTwoStopSelected, isBeforeElevenAM, isElevenAM, isFivePM, isAfterNinePM, listOfCheckNames);
                }else if(from.equalsIgnoreCase(getString(R.string.domestic_flight_search_round_trip_return))){
                    FlightFilterCallBack flightFilterCallBack = (FlightFilterCallBack) AppController.getInstance().getDomesticFlightsSearchRoundTripReturnFragmentContext();
                    flightFilterCallBack.flightFilter(isNonStopSelected, isOneStopSelected, isTwoStopSelected, isBeforeElevenAM, isElevenAM, isFivePM, isAfterNinePM, listOfCheckNames);
                } else {
                    FlightFilterCallBack flightFilterCallBack = (FlightFilterCallBack) AppController.getInstance().getFlightSearchResultsActivityContext();
                    flightFilterCallBack.flightFilter(isNonStopSelected, isOneStopSelected, isTwoStopSelected, isBeforeElevenAM, isElevenAM, isFivePM, isAfterNinePM, listOfCheckNames);
                }
                finish();
                break;
            default:
                break;
        }
    }

    private void initializeAirwaysView() {
        for (int index = 0; index < listOfAirwayNames.size(); index++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_flights_filters_airlines, null);
            String airwayName = listOfAirwayNames.get(index);
            String airwayFare = listOfAirwayFares.get(index);
            String airwayImageURL = listOfAirwayImages.get(index);
            tvAirwayFare = view.findViewById(R.id.tvAirwayFare);
            tvAirwayName = view.findViewById(R.id.tvAirwayName);
            ivAirLineImage = view.findViewById(R.id.ivAirLineImage);
            checkbox = view.findViewById(R.id.checkbox);
            checkbox.setTag(index);
            checkbox.setOnCheckedChangeListener(this);
            view.setTag(index);
            tvAirwayName.setText(airwayName);
            tvAirwayFare.setText(getString(R.string.Rs) + " " + airwayFare);
            tvAirwayFare.setVisibility(View.GONE);
            Picasso.with(this).load(airwayImageURL).error(R.mipmap.ic_launcher_round).into(ivAirLineImage);
           // llPreferredAirlinesNames.removeAllViews();
            llPreferredAirlinesNames.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    CheckBox checkBox = view.findViewById(R.id.checkbox);
                    checkBox.setOnCheckedChangeListener(FlightsFilterActivity.this);
                    checkBox.setTag(position);
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                    }
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        String airwayName = listOfAirwayNames.get(position);
        if (isChecked) {
            listOfCheckNames.add(airwayName);
        } else {
            int checkValueId = listOfCheckNames.indexOf(airwayName);
            if (checkValueId != -1)
                listOfCheckNames.remove(checkValueId);
        }
    }
}
