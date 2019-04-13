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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mahesh on 2/9/2018.
 */

public class FlightReviewActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSource, tvDestination, tvDetails, tvFlightName, tvFlightDepartureDate, tvFlightDepartureShortName, tvFlightDepartureTime, tvFlightDepartureFullName;
    private TextView tvFlightTravelTime, tvFlightArrivalDate, tvFlightArrivalShortName, tvFlightArrivalTime, tvFlightArrivalFullName, tvLayOver;
    private LinearLayout llContainer;
    private OriginDestinationOption originDestinationOption;
    private String sourceFullName;
    private String destinationFullName;
    private String departSelectedDate;
    String rule;
    private String details;
    private String totalJourneyTime = "";
    private String[] departureDateTime;
    private String[] arrivalDateTime;
    private String airwayName = "";
    private String fltTm;
    private LinearLayout llLayover;
    private String departDate;
    private LinearLayout llFare;
    private TextView tvContinue, tvFare, tvNumberOfPersons;
    private int totalFare = 0;
    private int totalTax = 0;
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
    private String sourceCountry = "";
    private String destinationCountry = "";
    private int adultCount = 0;
    private int childrenCount = 0;
    private int infantCount = 0;
    private double markUpFee = 0.0;
    double airportTax=0;
    private double conventionalFee = 0.0;
    LinearLayout llCancellationPolicy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flight_review);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareResults();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sourceFullName = bundle.getString(getString(R.string.source));
            destinationFullName = bundle.getString(getString(R.string.destination));
            departSelectedDate = bundle.getString(getString(R.string.selected_depature_date));
            sourceCountry = bundle.getString(getString(R.string.source_country));
            destinationCountry = bundle.getString(getString(R.string.destination_country));
            adultCount = bundle.getInt(getString(R.string.adult_count));
            childrenCount = bundle.getInt(getString(R.string.children_count));
            infantCount = bundle.getInt(getString(R.string.infant_count));
            //departSelectedDate = convertDateActionBarFormat(departSelectedDate);
            // String[] dateMonthYear = departSelectedDate.split(" ");
            // departDate = dateMonthYear[0] + " " + dateMonthYear[1] + "'" + " " + dateMonthYear[2];
            details = bundle.getString(getString(R.string.details));
        }
        originDestinationOption = AppController.getInstance().getSelectedOriginDestinationOption();
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.flight_review));
        setSupportActionBar(toolbar);

        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        llCancellationPolicy=findViewById(R.id.llCancellationPolicy);
        tvDetails = findViewById(R.id.tvDetails);
        tvFlightName = findViewById(R.id.tvFlightName);
        tvFlightDepartureDate = findViewById(R.id.tvFlightDepartureDate);
        tvFlightDepartureShortName = findViewById(R.id.tvFlightDepartureShortName);
        tvFlightDepartureTime = findViewById(R.id.tvFlightDepartureTime);
        tvFlightDepartureFullName = findViewById(R.id.tvFlightDepartureFullName);
        tvFlightTravelTime = findViewById(R.id.tvFlightTravelTime);
        tvFlightArrivalDate = findViewById(R.id.tvFlightArrivalDate);
        tvFlightArrivalShortName = findViewById(R.id.tvFlightArrivalShortName);
        tvFlightArrivalTime = findViewById(R.id.tvFlightArrivalTime);
        tvFlightArrivalFullName = findViewById(R.id.tvFlightArrivalFullName);
        llContainer = findViewById(R.id.llContainer);
        llLayover = findViewById(R.id.llLayover);
        llFare = findViewById(R.id.llFare);
        tvContinue = findViewById(R.id.tvContinue);
        tvFare = findViewById(R.id.tvFare);
        tvNumberOfPersons = findViewById(R.id.tvNumberOfPersons);
    }

    private void initializeListeners() {
        llFare.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        llCancellationPolicy.setOnClickListener(this);
    }

    @SuppressLint("LongLogTag")
    private void prepareResults() {
        JsonObject jsonObject = originDestinationOption.getOnward().flightSegments.getAsJsonObject();

        try {
            JSONObject flightSegmentsJsonObject = new JSONObject(jsonObject.toString());
            //Log.e("jsonObject",flightSegmentsJsonObject.toString());
            Object json = new JSONTokener(flightSegmentsJsonObject.getString("FlightSegment")).nextValue();
            JsonObject fareAryJsonObject = originDestinationOption.getFareDetails().getFareBreakup().getFareAry().getAsJsonObject();
            JSONObject fareAryJson = new JSONObject(fareAryJsonObject.toString());
            Object object = new JSONTokener(fareAryJson.getString("Fare")).nextValue();
            if (json instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightSegmentsJsonObject.getString("FlightSegment"), FlightSegment.class);
                totalJourneyTime = flightSegment.getJrnyTm();
                departureDateTime = flightSegment.getDepartureDateTime().split("T");
                arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
                if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                } else {
                    airwayName = flightSegment.getOperatingAirlineName().toString();
                }
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
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.cnn)) || psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                        totalChildrenTax = totalChildrenTax + tax;
                        totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                        numberOfChildren = numberOfChildren + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        totalInfantBaseFare = totalInfantBaseFare + baseFare;
                        totalInfantTax = totalInfantTax + tax;
                        totalInfantFareWithTax = totalInfantFareWithTax + fareTax;
                        numberOfInfants = numberOfInfants + 1;
                    }
                    airportTax=totalAdultTax+totalChildrenTax+totalInfantTax;

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



                    Log.e("international","international");
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
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.cnn)) || psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                            totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                            totalChildrenTax = totalChildrenTax + tax;
                            totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                            numberOfChildren = numberOfChildren + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                            totalInfantBaseFare = totalInfantBaseFare + baseFare;
                            totalInfantTax = totalInfantTax + tax;
                            totalInfantFareWithTax = totalInfantFareWithTax + fareTax;
                            numberOfInfants = numberOfInfants + 1;
                        }
                    }
                }
                airportTax=totalAdultTax+totalChildrenTax+totalInfantTax;

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
                String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
              //  double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);


                if (busMType.equalsIgnoreCase("M"))
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                } else
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                }



                Log.e("international","international");
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

                //tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(total));
                tvNumberOfPersons.setText(stringBuilder.toString());
                String via = flightSegment.getArrivalAirportCode();
                totalJourneyTime = flightSegment.getJrnyTm();
                initializeView(flightSegment, 0);
            } else if (json instanceof JSONArray) {
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
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.cnn)) || psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                        totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                        totalChildrenTax = totalChildrenTax + tax;
                        totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                        numberOfChildren = numberOfChildren + 1;
                    } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                        totalInfantBaseFare = totalInfantBaseFare + baseFare;
                        totalInfantTax = totalInfantTax + tax;
                        totalInfantFareWithTax = totalInfantFareWithTax + fareTax;
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
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.cnn)) || psgrType.equalsIgnoreCase(getString(R.string.chd))) {
                            totalChildrenBaseFare = totalChildrenBaseFare + baseFare;
                            totalChildrenTax = totalChildrenTax + tax;
                            totalChildrenFareWithTax = totalChildrenFareWithTax + fareTax;
                            numberOfChildren = numberOfChildren + 1;
                        } else if (psgrType.equalsIgnoreCase(getString(R.string.inf))) {
                            totalInfantBaseFare = totalInfantBaseFare + baseFare;
                            totalInfantTax = totalInfantTax + tax;
                            totalInfantFareWithTax = totalInfantFareWithTax + fareTax;
                            numberOfInfants = numberOfInfants + 1;
                        }
                    }
                }
                airportTax=totalAdultTax+totalChildrenTax+totalInfantTax;

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
                String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");
                Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
                Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
                markUpFee = ((totalAdultBaseFare + totalChildrenBaseFare + totalInfantBaseFare) * markUpPercentage);
                double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);
                //  double total = (adultCount * totalAdultFareWithTax) + (childrenCount * totalChildrenFareWithTax) + (infantCount * totalInfantFareWithTax);


                if (busMType.equalsIgnoreCase("M"))
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total-markUpFee) * conventionalFeePercentage)));
                } else
                {
                    conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((total+markUpFee) * conventionalFeePercentage)));
                }



                Log.e("international","international");
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
                //tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(total));
                tvNumberOfPersons.setText(stringBuilder.toString());
                JSONArray jsonArray = new JSONArray(flightSegmentsJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    FlightSegment flightSegment = new Gson().fromJson(jsonArray.getString(index), FlightSegment.class);
                    String via = flightSegment.getArrivalAirportCode();
                    totalJourneyTime = flightSegment.getJrnyTm();

                    String Conx = flightSegment.getConx();//Indicates this flight connects to the next flight
                    //llContainer.removeAllViews();
                    //if (Conx.equalsIgnoreCase("Y")) {
                    initializeView(flightSegment, index);
                    //}

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvSource.setText(sourceFullName);
        tvDestination.setText(destinationFullName);
    }

    private void initializeView(FlightSegment flightSegment, int index) {
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
        departureDateTime = flightSegment.getDepartureDateTime().split("T");
        arrivalDateTime = flightSegment.getArrivalDateTime().split("T");
        String departureAirportCode = flightSegment.getDepartureAirportCode();
        String arrivalAirportCode = flightSegment.getArrivalAirportCode();
        String departureAirportName = flightSegment.getDepartureAirportName();
        String arrivalAirportName = flightSegment.getArrivalAirportName();
        if (index != 0) {
            tvLayOver.setVisibility(View.VISIBLE);
            llLayover.setVisibility(View.VISIBLE);
            int differenceTime = Integer.parseInt(flightSegment.getFltTm()) - Integer.parseInt(fltTm);
            int hours = differenceTime / 60; //since both are ints, you get an int
            int minutes = differenceTime % 60;
            String overLay = departureAirportName + "(" + departureAirportCode + ")" + " " + hours + "h" + " " + minutes + "m" + " " + "Layover";
            tvLayOver.setText(overLay);
        } else {
            llLayover.setVisibility(View.GONE);
            tvLayOver.setVisibility(View.GONE);
        }
        fltTm = flightSegment.getFltTm();
        rule = flightSegment.getBookingClassFare().getRule();

        tvFlightName.setText(airwayName);
        tvFlightDepartureDate.setText(convertDateFormat(departureDateTime[0]));
        tvFlightArrivalDate.setText(convertDateFormat(arrivalDateTime[0]));
        tvFlightDepartureShortName.setText(departureAirportCode);
        tvFlightArrivalShortName.setText(arrivalAirportCode);
        String departureTime = departureDateTime[1];
        String arrivalTime = arrivalDateTime[1];
        tvFlightDepartureTime.setText(departureTime.substring(0, departureTime.length() - 3));
        tvFlightArrivalTime.setText(arrivalTime.substring(0, arrivalTime.length() - 3));
        int journeyTime = Integer.parseInt(fltTm);
        int hours = journeyTime / 60; //since both are ints, you get an int
        int minutes = journeyTime % 60;
        String travelTime = String.valueOf(hours) + "h" + " " + String.valueOf(minutes) + "m";
        tvFlightTravelTime.setText(travelTime);
        tvFlightDepartureFullName.setText(departureAirportName);
        tvFlightArrivalFullName.setText(arrivalAirportName);
        int stops = index - 1;
        int differenceTime = Integer.parseInt(totalJourneyTime);
        int totalHours = differenceTime / 60; //since both are ints, you get an int
        int totalMinutes = differenceTime % 60;
        String details = departSelectedDate + " | " + String.valueOf(stops) + " | " + totalHours + "h" + " " + totalMinutes + "m";
        tvDetails.setText(details);
        llContainer.addView(view);
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

    private String convertDateActionBarFormat(String dateString) {
        String formattedDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(dateString);
            SimpleDateFormat outputFormat = null;
            outputFormat = new SimpleDateFormat("dd MMM yy");
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
                bundle.putString(getString(R.string.source), sourceFullName);
                bundle.putString(getString(R.string.destination), destinationFullName);
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
                flightTravellerDetailsBundle.putString(getString(R.string.source), sourceFullName);
                flightTravellerDetailsBundle.putString(getString(R.string.destination), destinationFullName);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_base_fare), totalAdultBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_tax), totalAdultTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_base_fare), totalChildrenBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_tax), totalChildrenTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_base_fare), totalInfantBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_tax), totalInfantTax);
                flightTravellerDetailsBundle.putString(getString(R.string.source_country), sourceCountry);
                flightTravellerDetailsBundle.putString(getString(R.string.destination_country), destinationCountry);
                flightTravellerDetailsBundle.putInt(getString(R.string.adult_count), numberOfAdults);
                flightTravellerDetailsBundle.putInt(getString(R.string.children_count), numberOfChildren);
                flightTravellerDetailsBundle.putInt(getString(R.string.infant_count), numberOfInfants);
                flightTravellerDetailsBundle.putString(getString(R.string.from), getString(R.string.international_one_way));

                flightTravellerDetailsBundle.putDouble(getString(R.string.airport_tax), airportTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.mark_up_fee), markUpFee);
                flightTravellerDetailsBundle.putDouble(getString(R.string.conventional_fee), conventionalFee);
                flightTravellerDetailsIntent.putExtras(flightTravellerDetailsBundle);
                startActivity(flightTravellerDetailsIntent);
                break;
            default:
                break;
        }
    }

    private  void alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FlightReviewActivity.this);

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
