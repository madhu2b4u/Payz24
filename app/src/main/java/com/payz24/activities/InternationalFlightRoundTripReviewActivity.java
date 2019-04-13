package com.payz24.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.responseModels.flightSearchResults.Fare;
import com.payz24.responseModels.flightSearchResults.FlightSegment;
import com.payz24.responseModels.flightSearchResults.OriginDestinationOption;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mahesh on 2/17/2018.
 */

public class InternationalFlightRoundTripReviewActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llDepart, llDepartContainer, llReturn, llReturnContainer, llCancellationPolicy, llFare;
    private TextView tvDepartSource, tvDepartDestination, tvDepartDetails, tvReturnSource, tvReturnDestination, tvReturnDetails, tvFare, tvNumberOfPersons, tvContinue;
    private String origin = "";
    private String destination = "";
    private OriginDestinationOption originDestinationOption;
    private int adultFare = 0;
    private int childFare = 0;
    String rule;
    private int airportTax = 0;
    private LinearLayout llLayover;
    private TextView tvLayOver, tvFlightName, tvFlightDepartureDate, tvFlightDepartureShortName, tvFlightDepartureTime, tvFlightDepartureFullName, tvFlightTravelTime, tvFlightArrivalDate, tvFlightArrivalShortName, tvFlightArrivalTime, tvFlightArrivalFullName;
    private String airwayName;
    private String[] departureDateTime;
    private String[] arrivalDateTime;
    private String departSelectedDate = "";
    private String returnSelectedDate = "";
    private int adultCount = 0;
    private int childrenCount = 0;
    private int infantCount = 0;
    private String startingDate = "";
    private String endingDate = "";
    private String totalJourneyTime = "";
    private double totalAdultFareWithTax = 0;
    private double totalChildrenFareWithTax = 0;
    private double totalInfantFareWithTax = 0;
    private String psgrType = "";
    private int numberOfAdults = 0;
    private int numberOfChildren = 0;
    private int numberOfInfants = 0;
    private double totalAdultBaseFare = 0;
    private double totalAdultTax = 0;
    private double totalChildrenBaseFare = 0;
    private double totalChildrenTax = 0;
    private double totalInfantBaseFare = 0;
    private double totalInfantTax = 0;
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_international_flight_round_trip_review);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareResults();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            origin = bundle.getString(getString(R.string.origin));
            destination = bundle.getString(getString(R.string.destination));
            departSelectedDate = bundle.getString(getString(R.string.selected_depature_date));
            returnSelectedDate = bundle.getString(getString(R.string.selected_return_date));
            adultCount = bundle.getInt(getString(R.string.adult_count));
            childrenCount = bundle.getInt(getString(R.string.children_count));
            infantCount = bundle.getInt(getString(R.string.infant_count));
        }
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.flight_review));
        setSupportActionBar(toolbar);
        llDepart = findViewById(R.id.llDepart);
        llReturn = findViewById(R.id.llReturn);
        llDepartContainer = findViewById(R.id.llDepartContainer);
        llReturnContainer = findViewById(R.id.llReturnContainer);
        llCancellationPolicy = findViewById(R.id.llCancellationPolicy);
        llFare = findViewById(R.id.llFare);

        tvDepartSource = findViewById(R.id.tvDepartSource);
        tvDepartDestination = findViewById(R.id.tvDepartDestination);
        tvDepartDetails = findViewById(R.id.tvDepartDetails);
        tvReturnSource = findViewById(R.id.tvReturnSource);
        tvReturnDestination = findViewById(R.id.tvReturnDestination);
        tvReturnDetails = findViewById(R.id.tvReturnDetails);
        tvNumberOfPersons = findViewById(R.id.tvNumberOfPersons);
        tvFare = findViewById(R.id.tvFare);
        tvContinue = findViewById(R.id.tvContinue);

        tvDepartSource.setText(origin);
        tvDepartDestination.setText(destination);

        tvReturnSource.setText(destination);
        tvReturnDestination.setText(origin);
    }

    private void initializeListeners() {
        llFare.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        llCancellationPolicy.setOnClickListener(this);
    }

    @SuppressLint("LongLogTag")
    private void prepareResults() {
        originDestinationOption = AppController.getInstance().getSelectedOriginDestinationOption();
        JsonObject jsonObject = originDestinationOption.getOnward().flightSegments.getAsJsonObject();
        String returnJsonObject = "";
        if (originDestinationOption.getReturnJsonObject() instanceof JSONArray) {

        } else {
            Gson gson = new Gson();
            returnJsonObject = gson.toJson(originDestinationOption.getReturnJsonObject());
        }
        //JsonObject returnJsonObject = originDestinationOption.getReturnJsonObject().getAsJsonObject();
        try {
            JSONObject jo2 = new JSONObject(jsonObject.toString());
            JSONObject returnJsonObj = new JSONObject(returnJsonObject);
            JSONObject flightsSegmentsJsonObject = new JSONObject(returnJsonObj.getString("FlightSegments"));
            JsonObject fareAryJsonObject = originDestinationOption.getFareDetails().getFareBreakup().getFareAry().getAsJsonObject();
            JSONObject fareAryJson = new JSONObject(fareAryJsonObject.toString());
            Object object = new JSONTokener(fareAryJson.getString("Fare")).nextValue();
            Object json = new JSONTokener(jo2.getString("FlightSegment")).nextValue();
            Object jsonReturn = new JSONTokener(flightsSegmentsJsonObject.getString("FlightSegment")).nextValue();
            if (json instanceof JSONObject) {
                totalChildrenFareWithTax = 0;
                totalAdultFareWithTax = 0;
                totalInfantFareWithTax = 0;
                numberOfAdults = 0;
                numberOfChildren = 0;
                numberOfInfants = 0;
                totalAdultBaseFare = 0;
                totalAdultTax = 0;
                totalChildrenBaseFare = 0;
                totalChildrenTax = 0;
                totalInfantBaseFare = 0;
                totalInfantTax = 0;
                FlightSegment flightSegment = new Gson().fromJson(jo2.getString("FlightSegment"), FlightSegment.class);
                totalJourneyTime = flightSegment.getJrnyTm();
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
                rule = flightSegment.getBookingClassFare().getRule();

                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getOperatingAirlineName().toString();
                }
                if (object instanceof JSONObject) {
                    Fare fare = new Gson().fromJson(fareAryJson.getString("Fare"), Fare.class);
                    psgrType = fare.getPsgrType();
                    double baseFare = Double.parseDouble(fare.getBaseFare());
                    double tax = Double.parseDouble(fare.getTax());
                    double fareTax = baseFare + tax;
                    if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                        totalAdultBaseFare = totalAdultBaseFare + baseFare;
                        totalAdultTax = totalAdultTax + tax;
                        totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfAdults = numberOfAdults + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                        totalChildrenTax = totalChildrenTax + tax;
                        totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                        numberOfChildren = numberOfChildren + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        totalInfantBaseFare = totalInfantBaseFare + baseFare;

                        totalInfantTax = totalInfantTax + tax;
                        totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfInfants = numberOfInfants + 1;
                    }


                    StringBuilder stringBuilder = new StringBuilder();
                    if (numberOfAdults != 0) {
                        stringBuilder.append(String.valueOf(numberOfAdults)).append(" ").append("Adult");
                    }
                    if (numberOfChildren != 0) {
                        stringBuilder.append(String.valueOf(numberOfChildren)).append(" ").append("Childern");
                    }
                    if (numberOfInfants != 0) {
                        stringBuilder.append(String.valueOf(numberOfInfants)).append(" ").append("Infants");
                    }

                    int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
                    int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

                    Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                    Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                    markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                    // double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                    double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                    String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

                    if (busMType.equalsIgnoreCase("M"))
                    {
                        conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                    } else
                    {
                        conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                    }



                    Log.e("international","1");
                    Log.e("markup",""+markUpFee);
                    Log.e("conventionalFee",""+conventionalFee);
                    Log.e("basefare",""+total);

                    Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
                    Log.e("totalAdultFareWithTax",""+totalAdultFareWithTax);
                    Log.e("totalChildrenFareWithTax",""+totalChildrenFareWithTax);
                    Log.e("totalInfantFareWithTax",""+totalInfantFareWithTax);
                    Log.e("totalChildrenBaseFare",""+totalChildrenBaseFare);
                    Log.e("totalInfantBaseFare",""+totalInfantBaseFare);


                    int totalFeeWithTax = 0;
                    if (busMType.equalsIgnoreCase("M"))
                        totalFeeWithTax = (int) (total - markUpFee + conventionalFee);
                    else
                        totalFeeWithTax = (int) (total  + markUpFee +conventionalFee);

                    tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));




                    tvNumberOfPersons.setText(stringBuilder.toString());
                    String via = flightSegment.getArrivalAirportCode();
                    totalJourneyTime = flightSegment.getJrnyTm();

                } else if (object instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(fareAryJson.getString("Fare"));
                    for (int index = 0; index < jsonArray.length(); index++) {
                        Fare fare = new Gson().fromJson(jsonArray.getString(index), Fare.class);
                        psgrType = fare.getPsgrType();
                        double baseFare = Integer.parseInt(fare.getBaseFare());
                        double tax = Integer.parseInt(fare.getTax());
                        double fareTax = baseFare + tax;
                        if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                            totalAdultBaseFare = totalAdultBaseFare + baseFare;
                            totalAdultTax = totalAdultTax + tax;
                            totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfAdults = numberOfAdults + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                            totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                            totalChildrenTax = totalChildrenTax + tax;
                            totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                            numberOfChildren = numberOfChildren + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                            totalInfantBaseFare = totalInfantBaseFare + baseFare;
                            totalInfantTax = totalInfantTax + tax;
                            totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfInfants = numberOfInfants + 1;
                        }
                    }
                }

             //   double total = totalAdultFareWithTax + totalChildrenFareWithTax + totalInfantFareWithTax;
                StringBuilder stringBuilder = new StringBuilder();
                if (numberOfAdults != 0) {
                    stringBuilder.append(String.valueOf(numberOfAdults)).append(" ").append("Adult");
                }
                if (numberOfChildren != 0) {
                    stringBuilder.append(String.valueOf(numberOfChildren)).append(" ").append("Childern");
                }
                if (numberOfInfants != 0) {
                    stringBuilder.append(String.valueOf(numberOfInfants)).append(" ").append("Infants");
                }

                int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
                int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                // double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

                if (busMType.equalsIgnoreCase("M"))
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                } else
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                }



                Log.e("international","2");
                Log.e("markup",""+markUpFee);
                Log.e("conventionalFee",""+conventionalFee);
                Log.e("total",""+total);
                Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
                Log.e("totalAdultFareWithTax",""+totalAdultFareWithTax);
                Log.e("totalChildrenFareWithTax",""+totalChildrenFareWithTax);
                Log.e("totalInfantFareWithTax",""+totalInfantFareWithTax);
                Log.e("totalChildrenBaseFare",""+totalChildrenBaseFare);
                Log.e("totalInfantBaseFare",""+totalInfantBaseFare);


                int totalFeeWithTax = 0;
                if (busMType.equalsIgnoreCase("M"))
                    totalFeeWithTax = (int) (total - markUpFee + conventionalFee);
                else
                    totalFeeWithTax = (int) (total  + markUpFee +conventionalFee);

                tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));

                tvNumberOfPersons.setText(stringBuilder.toString());
                String via = flightSegment.getArrivalAirportCode();
                totalJourneyTime = flightSegment.getJrnyTm();
                initializeView(flightSegment, 0,"to");
            } else if (json instanceof JSONArray) {
                totalChildrenFareWithTax = 0;
                totalAdultFareWithTax = 0;
                totalInfantFareWithTax = 0;
                numberOfAdults = 0;
                numberOfChildren = 0;
                numberOfInfants = 0;
                totalAdultBaseFare = 0;
                totalAdultTax = 0;
                totalChildrenBaseFare = 0;
                totalChildrenTax = 0;
                totalInfantBaseFare = 0;
                totalInfantTax = 0;
                if (object instanceof JSONObject) {
                    Fare fare = new Gson().fromJson(fareAryJson.getString("Fare"), Fare.class);
                    psgrType = fare.getPsgrType();
                    double baseFare = Integer.parseInt(fare.getBaseFare());
                    double tax = Integer.parseInt(fare.getTax());
                    double fareTax = baseFare + tax;
                    if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                        totalAdultBaseFare = totalAdultBaseFare + baseFare;
                        totalAdultTax = totalAdultTax + tax;
                        totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfAdults = numberOfAdults + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                        totalChildrenTax = totalChildrenTax + tax;
                        totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                        numberOfChildren = numberOfChildren + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        totalInfantBaseFare = totalInfantBaseFare + baseFare;
                        totalInfantTax = totalInfantTax + tax;
                        totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfInfants = numberOfInfants + 1;
                    }
                } else if (object instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(fareAryJson.getString("Fare"));
                    for (int index = 0; index < jsonArray.length(); index++) {
                        Fare fare = new Gson().fromJson(jsonArray.getString(index), Fare.class);
                        psgrType = fare.getPsgrType();
                        double baseFare = Integer.parseInt(fare.getBaseFare());
                        double tax = Integer.parseInt(fare.getTax());
                        double fareTax = baseFare + tax;
                        if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                            totalAdultBaseFare = totalAdultBaseFare + baseFare;
                            totalAdultTax = totalAdultTax + tax;
                            totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfAdults = numberOfAdults + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                            totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                            totalChildrenTax = totalChildrenTax + tax;
                            totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                            numberOfChildren = numberOfChildren + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                            totalInfantBaseFare = totalInfantBaseFare + baseFare;
                            totalInfantTax = totalInfantTax + tax;
                            totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfInfants = numberOfInfants + 1;
                        }
                    }
                }

             //   double total = totalAdultFareWithTax + totalChildrenFareWithTax + totalInfantFareWithTax;
                StringBuilder stringBuilder = new StringBuilder();
                if (numberOfAdults != 0) {
                    stringBuilder.append(String.valueOf(numberOfAdults)).append(" ").append("Adult");
                }
                if (numberOfChildren != 0) {
                    stringBuilder.append(String.valueOf(numberOfChildren)).append(" ").append("Childern");
                }
                if (numberOfInfants != 0) {
                    stringBuilder.append(String.valueOf(numberOfInfants)).append(" ").append("Infants");
                }

                int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
                int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                // double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

                if (busMType.equalsIgnoreCase("M"))
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                } else
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                }



                Log.e("international","3");
                Log.e("markup",""+markUpFee);
                Log.e("conventionalFee",""+conventionalFee);
                Log.e("basefare",""+total);
                Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
                Log.e("totalAdultFareWithTax",""+totalAdultFareWithTax);
                Log.e("totalChildrenFareWithTax",""+totalChildrenFareWithTax);
                Log.e("totalInfantFareWithTax",""+totalInfantFareWithTax);
                Log.e("totalChildrenBaseFare",""+totalChildrenBaseFare);
                Log.e("totalInfantBaseFare",""+totalInfantBaseFare);


                int totalFeeWithTax = 0;
                if (busMType.equalsIgnoreCase("M"))
                    totalFeeWithTax = (int) (total - markUpFee + conventionalFee);
                else
                    totalFeeWithTax = (int) (total  + markUpFee +conventionalFee);

                tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));

                tvNumberOfPersons.setText(stringBuilder.toString());
                JSONArray jsonArray = new JSONArray(jo2.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    FlightSegment flightSegment = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class);
                    String via = flightSegment.getArrivalAirportCode();
                    totalJourneyTime = flightSegment.getJrnyTm();

                    String Conx = flightSegment.getConx();//Indicates this flight connects to the next flight
                    //llContainer.removeAllViews();
                    //if (Conx.equalsIgnoreCase("Y")) {
                    initializeView(flightSegment, index,"to");
                    //}

                }
            }





            if (jsonReturn instanceof JSONObject) {
                totalChildrenFareWithTax = 0;
                totalAdultFareWithTax = 0;
                totalInfantFareWithTax = 0;
                numberOfAdults = 0;
                numberOfChildren = 0;
                numberOfInfants = 0;
                totalAdultBaseFare = 0;
                totalAdultTax = 0;
                totalChildrenBaseFare = 0;
                totalChildrenTax = 0;
                totalInfantBaseFare = 0;
                totalInfantTax = 0;
                FlightSegment flightSegment = new Gson().fromJson(flightsSegmentsJsonObject.getString("FlightSegment"), FlightSegment.class);
                totalJourneyTime = flightSegment.getJrnyTm();
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getOperatingAirlineName().toString();
                }
                if (object instanceof JSONObject) {
                    Fare fare = new Gson().fromJson(fareAryJson.getString("Fare"), Fare.class);
                    psgrType = fare.getPsgrType();
                    double baseFare = Integer.parseInt(fare.getBaseFare());
                    double tax = Integer.parseInt(fare.getTax());
                    double fareTax = baseFare + tax;
                    if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                        totalAdultBaseFare = totalAdultBaseFare + baseFare;
                        totalAdultTax = totalAdultTax + tax;
                        totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfAdults = numberOfAdults + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                        totalChildrenTax = totalChildrenTax + tax;
                        totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                        numberOfChildren = numberOfChildren + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        totalInfantBaseFare = totalInfantBaseFare + baseFare;
                        totalInfantTax = totalInfantTax + tax;
                        totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfInfants = numberOfInfants + 1;
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    if (numberOfAdults != 0) {
                        stringBuilder.append(String.valueOf(numberOfAdults)).append(" ").append("Adult");
                    }
                    if (numberOfChildren != 0) {
                        stringBuilder.append(String.valueOf(numberOfChildren)).append(" ").append("Childern");
                    }
                    if (numberOfInfants != 0) {
                        stringBuilder.append(String.valueOf(numberOfInfants)).append(" ").append("Infants");
                    }

                    int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
                    int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

                    Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                    Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                    markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                    // double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                    double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                    String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

                    if (busMType.equalsIgnoreCase("M"))
                    {
                        conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                    } else
                    {
                        conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                    }



                    Log.e("international","4");
                    Log.e("markup",""+markUpFee);
                    Log.e("conventionalFee",""+conventionalFee);
                    Log.e("total",""+total);
                    Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
                    Log.e("totalAdultFareWithTax",""+totalAdultFareWithTax);
                    Log.e("totalChildrenFareWithTax",""+totalChildrenFareWithTax);
                    Log.e("totalInfantFareWithTax",""+totalInfantFareWithTax);
                    Log.e("totalChildrenBaseFare",""+totalChildrenBaseFare);
                    Log.e("totalInfantBaseFare",""+totalInfantBaseFare);


                    int totalFeeWithTax = 0;
                    if (busMType.equalsIgnoreCase("M"))
                        totalFeeWithTax = (int) (total - markUpFee + conventionalFee);
                    else
                        totalFeeWithTax = (int) (total  + markUpFee +conventionalFee);

                    tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));

                    tvNumberOfPersons.setText(stringBuilder.toString());
                    String via = flightSegment.getArrivalAirportCode();
                    totalJourneyTime = flightSegment.getJrnyTm();

                } else if (object instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(fareAryJson.getString("Fare"));
                    for (int index = 0; index < jsonArray.length(); index++) {
                        Fare fare = new Gson().fromJson(jsonArray.getString(index), Fare.class);
                        psgrType = fare.getPsgrType();
                        double baseFare = Integer.parseInt(fare.getBaseFare());
                        double tax = Integer.parseInt(fare.getTax());
                        double fareTax = baseFare + tax;
                        if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                            totalAdultBaseFare = totalAdultBaseFare + baseFare;
                            totalAdultTax = totalAdultTax + tax;
                            totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfAdults = numberOfAdults + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                            totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                            totalChildrenTax = totalChildrenTax + tax;
                            totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                            numberOfChildren = numberOfChildren + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                            totalInfantBaseFare = totalInfantBaseFare + baseFare;
                            totalInfantTax = totalInfantTax + tax;
                            totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfInfants = numberOfInfants + 1;
                        }
                    }
                }

                StringBuilder stringBuilder = new StringBuilder();
                if (numberOfAdults != 0) {
                    stringBuilder.append(String.valueOf(numberOfAdults)).append(" ").append("Adult");
                }
                if (numberOfChildren != 0) {
                    stringBuilder.append(String.valueOf(numberOfChildren)).append(" ").append("Childern");
                }
                if (numberOfInfants != 0) {
                    stringBuilder.append(String.valueOf(numberOfInfants)).append(" ").append("Infants");
                }

                int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
                int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                // double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

                if (busMType.equalsIgnoreCase("M"))
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                } else
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                }



                Log.e("international","5");
                Log.e("markup",""+markUpFee);
                Log.e("conventionalFee",""+conventionalFee);
                Log.e("total",""+total);
                Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
                Log.e("totalAdultFareWithTax",""+totalAdultFareWithTax);
                Log.e("totalChildrenFareWithTax",""+totalChildrenFareWithTax);
                Log.e("totalInfantFareWithTax",""+totalInfantFareWithTax);
                Log.e("totalChildrenBaseFare",""+totalChildrenBaseFare);
                Log.e("totalInfantBaseFare",""+totalInfantBaseFare);


                int totalFeeWithTax = 0;
                if (busMType.equalsIgnoreCase("M"))
                    totalFeeWithTax = (int) (total - markUpFee + conventionalFee);
                else
                    totalFeeWithTax = (int) (total  + markUpFee +conventionalFee);

                tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));

                tvNumberOfPersons.setText(stringBuilder.toString());
                String via = flightSegment.getArrivalAirportCode();
                totalJourneyTime = flightSegment.getJrnyTm();
                initializeView(flightSegment, 0,"from");
            } else if (jsonReturn instanceof JSONArray) {
                totalChildrenFareWithTax = 0;
                totalAdultFareWithTax = 0;
                totalInfantFareWithTax = 0;
                numberOfAdults = 0;
                numberOfChildren = 0;
                numberOfInfants = 0;
                totalAdultBaseFare = 0;
                totalAdultTax = 0;
                totalChildrenBaseFare = 0;
                totalChildrenTax = 0;
                totalInfantBaseFare = 0;
                totalInfantTax = 0;
                if (object instanceof JSONObject) {
                    Fare fare = new Gson().fromJson(fareAryJson.getString("Fare"), Fare.class);
                    totalChildrenFareWithTax = 0;
                    totalAdultFareWithTax = 0;
                    totalInfantFareWithTax = 0;
                    numberOfAdults = 0;
                    numberOfChildren = 0;
                    numberOfInfants = 0;
                    totalAdultBaseFare = 0;
                    totalAdultTax = 0;
                    totalChildrenBaseFare = 0;
                    totalChildrenTax = 0;
                    totalInfantBaseFare = 0;
                    totalInfantTax = 0;
                    psgrType = fare.getPsgrType();
                    double baseFare = Integer.parseInt(fare.getBaseFare());
                    double tax = Integer.parseInt(fare.getTax());
                    double fareTax = baseFare + tax;
                    if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                        totalAdultBaseFare = totalAdultBaseFare + baseFare;
                        totalAdultTax = totalAdultTax + tax;
                        totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfAdults = numberOfAdults + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                        totalChildrenTax = totalChildrenTax + tax;
                        totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                        numberOfChildren = numberOfChildren + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        totalInfantBaseFare = totalInfantBaseFare + baseFare;
                        totalInfantTax = totalInfantTax + tax;
                        totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                        numberOfInfants = numberOfInfants + 1;
                    }
                } else if (object instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(fareAryJson.getString("Fare"));
                    totalChildrenFareWithTax = 0;
                    totalAdultFareWithTax = 0;
                    totalInfantFareWithTax = 0;
                    numberOfAdults = 0;
                    numberOfChildren = 0;
                    numberOfInfants = 0;
                    totalAdultBaseFare = 0;
                    totalAdultTax = 0;
                    totalChildrenBaseFare = 0;
                    totalChildrenTax = 0;
                    totalInfantBaseFare = 0;
                    totalInfantTax = 0;
                    for (int index = 0; index < jsonArray.length(); index++) {
                        Fare fare = new Gson().fromJson(jsonArray.getString(index), Fare.class);
                        psgrType = fare.getPsgrType();
                        double baseFare = Integer.parseInt(fare.getBaseFare());
                        double tax = Integer.parseInt(fare.getTax());
                        double fareTax = baseFare + tax;
                        if (psgrType.equalsIgnoreCase(getString(R.string.adt))) {
                            totalAdultBaseFare = totalAdultBaseFare + baseFare;
                            totalAdultTax = totalAdultTax + tax;
                            totalAdultFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfAdults = numberOfAdults + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                            totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                            totalChildrenTax = totalChildrenTax + tax;
                            totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                            numberOfChildren = numberOfChildren + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                            totalInfantBaseFare = totalInfantBaseFare + baseFare;
                            totalInfantTax = totalInfantTax + tax;
                            totalInfantFareWithTax = totalAdultFareWithTax + fareTax;
                            numberOfInfants = numberOfInfants + 1;
                        }
                    }
                }

                StringBuilder stringBuilder = new StringBuilder();
                if (numberOfAdults != 0) {
                    stringBuilder.append(String.valueOf(numberOfAdults)).append(" ").append("Adult");
                }
                if (numberOfChildren != 0) {
                    stringBuilder.append(String.valueOf(numberOfChildren)).append(" ").append("Childern");
                }
                if (numberOfInfants != 0) {
                    stringBuilder.append(String.valueOf(numberOfInfants)).append(" ").append("Infants");
                }

                int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
                int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                // double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

                if (busMType.equalsIgnoreCase("M"))
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                } else
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                }



                Log.e("international","6");
                Log.e("markup",""+markUpFee);
                Log.e("conventionalFee",""+conventionalFee);
                Log.e("total",""+total);
                Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
                Log.e("totalAdultFareWithTax",""+totalAdultFareWithTax);
                Log.e("totalChildrenFareWithTax",""+totalChildrenFareWithTax);
                Log.e("totalInfantFareWithTax",""+totalInfantFareWithTax);
                Log.e("totalChildrenBaseFare",""+totalChildrenBaseFare);
                Log.e("totalInfantBaseFare",""+totalInfantBaseFare);


                int totalFeeWithTax = 0;
                if (busMType.equalsIgnoreCase("M"))
                    totalFeeWithTax = (int) (total - markUpFee + conventionalFee);
                else
                    totalFeeWithTax = (int) (total  + markUpFee +conventionalFee);

                tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));

                tvNumberOfPersons.setText(stringBuilder.toString());
                JSONArray jsonArray = new JSONArray(flightsSegmentsJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    FlightSegment flightSegment = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class);
                    String via = flightSegment.getArrivalAirportCode();
                    totalJourneyTime = flightSegment.getJrnyTm();

                    String Conx = flightSegment.getConx();//Indicates this flight connects to the next flight
                    //llContainer.removeAllViews();
                    //if (Conx.equalsIgnoreCase("Y")) {
                    initializeView(flightSegment, index,"from");
                    //}

                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void initializeView(FlightSegment flightSegment, int index, String from) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_flight_review_items, null);
        llLayover = view.findViewById(R.id.llLayover);
        tvLayOver = view.findViewById(R.id.tvLayOver);
        tvFlightName = view.findViewById(R.id.tvFlightName);
        tvFlightDepartureDate = view.findViewById(R.id.tvFlightDepartureDate);
        tvFlightDepartureShortName = view.findViewById(R.id.tvFlightDepartureShortName);
        tvFlightDepartureTime = view.findViewById(R.id.tvFlightDepartureTime);
        tvFlightDepartureFullName = view.findViewById(R.id.tvFlightDepartureFullName);
        tvFlightTravelTime = view.findViewById(R.id.tvFlightTravelTime);
        tvFlightArrivalDate = view.findViewById(R.id.tvFlightArrivalDate);
        tvFlightArrivalShortName = view.findViewById(R.id.tvFlightArrivalShortName);
        tvFlightArrivalTime = view.findViewById(R.id.tvFlightArrivalTime);
        tvFlightArrivalFullName = view.findViewById(R.id.tvFlightArrivalFullName);
        if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

        } else {
            airwayName = flightSegment.getOperatingAirlineName().toString();
        }

        String departureDateTime = flightSegment.getDepartureDateTime();
        String arrivalDateTime = flightSegment.getArrivalDateTime();
        String departureAirportCode = flightSegment.getDepartureAirportCode();
        String arrivalAirportCode = flightSegment.getArrivalAirportCode();
        String departureAirportName = flightSegment.getDepartureAirportCode();
        String arrivalAirportName = flightSegment.getArrivalAirportCode();
        if (index != 0) {
            tvLayOver.setVisibility(View.VISIBLE);
            llLayover.setVisibility(View.VISIBLE);
          /*  int differenceTime = Integer.parseInt(flightSegment.getFltTm()) - Integer.parseInt(fltTm);
            int hours = differenceTime / 60; //since both are ints, you get an int
            int minutes = differenceTime % 60;*/
            String[] hoursMinutes = hoursMinutesBetweenTwoTimes(endingDate, departureDateTime).split(":");
            String overLay = departureAirportName + "(" + departureAirportCode + ")" + " " + hoursMinutes[0] + "h" + " " + hoursMinutes[1] + "m" + " " + "Layover";
            tvLayOver.setText(overLay);
            endingDate = flightSegment.getArrivalDateTime();
        } else {
            startingDate = flightSegment.getDepartureDateTime();
            endingDate = flightSegment.getArrivalDateTime();
            llLayover.setVisibility(View.GONE);
            tvLayOver.setVisibility(View.GONE);
        }

        tvFlightName.setText(airwayName);
        this.departureDateTime = flightSegment.getDepartureDateTime().split("T");
        this.arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
        tvFlightDepartureDate.setText(convertDateFormat(this.departureDateTime[0]));
        tvFlightArrivalDate.setText(convertDateFormat(this.arrivalDateTime[0]));
        tvFlightDepartureShortName.setText(departureAirportCode);
        tvFlightArrivalShortName.setText(arrivalAirportCode);
        String departureTime = this.departureDateTime[1];
        String arrivalTime = this.arrivalDateTime[1];
        tvFlightDepartureTime.setText(departureTime.substring(0, departureTime.length() - 3));
        tvFlightArrivalTime.setText(arrivalTime.substring(0, arrivalTime.length() - 3));
        String[] hoursMinutes = hoursMinutesBetweenTwoTimes(departureDateTime, arrivalDateTime).split(":");
        String travelTime = String.valueOf(hoursMinutes[0]) + "h" + " " + String.valueOf(hoursMinutes[1]) + "m";
        tvFlightTravelTime.setText(travelTime);
        tvFlightDepartureFullName.setText(departureAirportName);
        tvFlightArrivalFullName.setText(arrivalAirportName);

        String[] totalHoursAndMinutes = hoursMinutesBetweenTwoTimes(startingDate, flightSegment.getArrivalDateTime()).split(":");
        if (from.equalsIgnoreCase("to")) {
            String details = departSelectedDate + " | " + String.valueOf(index) + " | " + totalHoursAndMinutes[0] + "h" + " " + totalHoursAndMinutes[1] + "m";
            tvDepartDetails.setText(details);
            llDepartContainer.addView(view);
        } else {
            String details = returnSelectedDate + " | " + String.valueOf(index) + " | " + totalHoursAndMinutes[0] + "h" + " " + totalHoursAndMinutes[1] + "m";
            tvReturnDetails.setText(details);
            llReturnContainer.addView(view);
        }
    }

    private String hoursMinutesBetweenTwoTimes(String departureDateTime, String arrivalDateTime) {
        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date d1 = null;
        Date d2 = null;
        String result = "";
        try {
            d1 = format.parse(departureDateTime);
            d2 = format.parse(arrivalDateTime);
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            result = String.valueOf(diffHours) + ":" + String.valueOf(diffMinutes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String convertDateFormat(String dateString) {
        String formattedDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(dateString);
            SimpleDateFormat outputFormat = null;
            outputFormat = new SimpleDateFormat("EEE dd MMM");
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.llCancellationPolicy:


                alert();

            break;


            case R.id.llFare:
                Intent fareBreakUpIntent = new Intent(this, FareBreakUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.source), origin);
                bundle.putString(getString(R.string.destination), destination);
                bundle.putDouble(getString(R.string.total_adult_base_fare), totalAdultBaseFare);
                bundle.putDouble(getString(R.string.total_adult_tax), totalAdultTax);
                bundle.putDouble(getString(R.string.total_children_base_fare), totalChildrenBaseFare);
                bundle.putDouble(getString(R.string.total_children_tax), totalChildrenTax);
                bundle.putDouble(getString(R.string.total_infant_base_fare), totalInfantBaseFare);
                bundle.putDouble(getString(R.string.total_infant_tax), totalInfantTax);
                bundle.putDouble(getString(R.string.mark_up_fee), markUpFee);
                bundle.putDouble(getString(R.string.conventional_fee), conventionalFee);

                fareBreakUpIntent.putExtras(bundle);
                startActivity(fareBreakUpIntent);
                break;
            case R.id.tvContinue:
                Intent flightTravellerDetailsIntent = new Intent(this, FlightTravellerDetailsActivity.class);
                Bundle flightTravellerDetailsBundle = new Bundle();
                flightTravellerDetailsBundle.putString(getString(R.string.source), origin);
                flightTravellerDetailsBundle.putString(getString(R.string.destination), destination);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_base_fare), totalAdultBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_tax), totalAdultTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_base_fare), totalChildrenBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_tax), totalChildrenTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_base_fare), totalInfantBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_tax), totalInfantTax);
                flightTravellerDetailsBundle.putInt(getString(R.string.adult_count), numberOfAdults);
                flightTravellerDetailsBundle.putInt(getString(R.string.children_count), numberOfChildren);
                flightTravellerDetailsBundle.putInt(getString(R.string.infant_count), numberOfInfants);
                flightTravellerDetailsBundle.putString(getString(R.string.from),getString(R.string.international_round_trip));
                flightTravellerDetailsIntent.putExtras(flightTravellerDetailsBundle);
                startActivity(flightTravellerDetailsIntent);
                break;
            default:
                break;
        }
    }

    private  void alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InternationalFlightRoundTripReviewActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle(getResources().getString(R.string.cancellation_policy));

        // Setting Dialog Message
        alertDialog.setMessage(Html.fromHtml(rule));

        // Setting Icon to Dialog

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // User pressed YES button. Write Logic Here

                dialog.dismiss();
            }
        });



        // Showing Alert Message
        alertDialog.show();
    }
}
