package com.payz24.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.utils.PreferenceConnector;

import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * Created by mahesh on 2/16/2018.
 */

public class DomesticFlightsFareBreakUpActivity extends BaseActivity implements View.OnClickListener {


    private TextView tvSource, tvDestination, tvClose;
    private LinearLayout llFareContainer;
    private int adultCount = 0;
    private int childrenCount = 0;
    private int infantCount = 0;
    private double adultFare = 0;
    private double childFare = 0;
    private double infantFare = 0;
    private String origin = "";
    private String destination = "";
    private LinkedList<String> listOfKeys;
    private LinkedList<String> listOfValues;
    private double airportTax = 0;
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_domestic_flights_fare_break_up);
        Log.e ("flights","oneway");
        getDataFromIntent();
        initializeUI();
        initializeListeners();
        prepareResults();
        initializeView();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            adultCount = bundle.getInt(getString(R.string.adult_count));
            childrenCount = bundle.getInt(getString(R.string.children_count));
            infantCount = bundle.getInt(getString(R.string.infant_count));
            adultFare = bundle.getDouble(getString(R.string.adult_fare));
            childFare = bundle.getDouble(getString(R.string.child_fare));
            infantFare = bundle.getDouble(getString(R.string.infant_fare));
            origin = bundle.getString(getString(R.string.origin));
            airportTax = bundle.getDouble(getString(R.string.airport_tax));
            destination = bundle.getString(getString(R.string.destination));
            markUpFee = bundle.getDouble(getString(R.string.mark_up_fee));
            conventionalFee = Math.round(bundle.getDouble(getString(R.string.conventional_fee)));
        }
    }

    private void initializeUI() {
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvClose = findViewById(R.id.tvClose);
        llFareContainer = findViewById(R.id.llFareContainer);

        listOfKeys = new LinkedList<>();
        listOfValues = new LinkedList<>();
    }

    private void initializeListeners() {
        tvClose.setOnClickListener(this);
    }

    private void prepareResults() {
        tvSource.setText(origin);
        tvDestination.setText(destination);


        listOfKeys.add(getString(R.string.adult_base_fare));
        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
        if (busMType.equalsIgnoreCase("M"))
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(adultFare-markUpFee+childFare+infantFare));
        else
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(adultFare+markUpFee+childFare+infantFare));

        if (childrenCount != 0) {
            listOfKeys.add(getString(R.string.children_base_fare));
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(childFare));
        }
        if(infantCount != 0){
            listOfKeys.add(getString(R.string.infant_base_fare));
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(infantFare));
        }

        double grandTotal = Math.round(adultFare + childFare + infantFare + airportTax);

        Log.e("markup",""+markUpFee);
        Log.e("grandTotal",""+grandTotal);
        Log.e("conventionalFee",""+conventionalFee);
        Log.e("airportTax",""+airportTax);




        int totalFeeWithTax = 0;
        Log.e("bustype",busMType);

        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (grandTotal - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (grandTotal +markUpFee+conventionalFee);



        listOfKeys.add(getString(R.string.surcharges_and_fees));
        listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(airportTax));
      /*  listOfKeys.add(getString(R.string.mark_up_fee));
        listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(markUpFee));*/
        listOfKeys.add(getString(R.string.conventional_fee));
        listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(conventionalFee));
        listOfKeys.add(getString(R.string.grand_total));
        listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));
    }

    private void initializeView() {
        for (int index = 0; index < listOfKeys.size(); index++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_fare_break_up_items, null);
            TextView tvKey = view.findViewById(R.id.tvKey);
            TextView tvValue = view.findViewById(R.id.tvValue);
            if(listOfKeys.get(index).equalsIgnoreCase(getString(R.string.grand_total))){
                view.setBackgroundColor(getResources().getColor(R.color.medium_grey));
            }else {
                view.setBackgroundColor(getResources().getColor(R.color.white));
            }
            tvKey.setText(listOfKeys.get(index));
            tvValue.setText(listOfValues.get(index));
            llFareContainer.addView(view);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvClose:
                finish();
                break;
            default:
                break;
        }
    }
}
