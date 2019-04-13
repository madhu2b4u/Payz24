package com.payz24.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.utils.PreferenceConnector;

import java.util.LinkedList;

/**
 * Created by mahesh on 2/10/2018.
 */

public class FareBreakUpActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llFareContainer;
    private TextView tvSource, tvDestination, tvClose;
    private String sourceFullName = "";
    private String destinationFullName = "";
    private double totalAdultBaseFare = 0;
    private double totalAdultTax = 0;
    private double totalChildrenBaseFare = 0;
    private double totalChildrenTax = 0;
    private double totalInfantBaseFare = 0;
    private double totalInfantTax = 0;
    private LinkedList<String> listOfKeys;
    private LinkedList<String> listOfValues;
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;
    String busMType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fare_break_up);
        busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

        getDataFromIntent();
        initializeUi();
        initializeListeners();
        prepareResults();
        initializeView();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sourceFullName = bundle.getString(getString(R.string.source));
            destinationFullName = bundle.getString(getString(R.string.destination));
            totalAdultBaseFare = bundle.getDouble(getString(R.string.total_adult_base_fare));
            totalAdultTax = bundle.getDouble(getString(R.string.total_adult_tax));
            totalChildrenBaseFare = bundle.getDouble(getString(R.string.total_children_base_fare));
            totalChildrenTax = bundle.getDouble(getString(R.string.total_children_tax));
            totalInfantBaseFare = bundle.getDouble(getString(R.string.total_infant_base_fare));
            totalInfantTax = bundle.getDouble(getString(R.string.total_infant_tax));
            markUpFee = bundle.getDouble(getString(R.string.mark_up_fee));
            conventionalFee = Math.round(bundle.getDouble(getString(R.string.conventional_fee)));
        }
    }

    private void initializeUi() {
        tvClose = findViewById(R.id.tvClose);
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        llFareContainer = findViewById(R.id.llFareContainer);

        listOfKeys = new LinkedList<>();
        listOfValues = new LinkedList<>();
    }
    private void initializeListeners() {
        tvClose.setOnClickListener(this);
    }

    private void prepareResults() {
        tvSource.setText(sourceFullName);
        tvDestination.setText(destinationFullName);

        listOfKeys.add(getString(R.string.adult_base_fare));
        if (busMType.equalsIgnoreCase("M"))
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(totalAdultBaseFare-markUpFee+totalChildrenBaseFare+totalInfantBaseFare));
        else
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(totalAdultBaseFare+markUpFee+totalChildrenBaseFare+totalInfantBaseFare));

        if (totalChildrenBaseFare != 0) {
            listOfKeys.add(getString(R.string.children_base_fare));
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(totalChildrenBaseFare));
        }
        if (totalInfantBaseFare != 0) {
            listOfKeys.add(getString(R.string.infant_base_fare));
            listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(totalInfantBaseFare));
        }


        double tax = totalAdultTax + totalChildrenTax + totalInfantTax;
        double grandTotal =Math.round( totalAdultBaseFare + totalAdultTax + totalChildrenBaseFare + totalChildrenTax + totalInfantBaseFare + totalInfantTax);



        int totalFeeWithTax = 0;
        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (grandTotal - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (grandTotal + markUpFee+ conventionalFee);



        listOfKeys.add(getString(R.string.surcharges_and_fees));
        listOfValues.add(getResources().getString(R.string.Rs) + " " + String.valueOf(tax));
       /* listOfKeys.add(getString(R.string.mark_up_fee));
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
