package com.payz24.activities;

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
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mahesh on 2/13/2018.
 */

public class DomesticFlightsSearchRoundTripFlightsReviewActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llDepart, llDepartContainer, llReturn, llReturnContainer, llCancellationPolicy, llFare;
    private TextView tvDepartSource, tvDepartDestination, tvDepartDetails, tvReturnSource, tvReturnDestination, tvReturnDetails, tvFare, tvNumberOfPersons, tvContinue;
    private String origin = "";
    private String destination = "";
    private OriginDestinationOption toOriginDestinationOption;
    private OriginDestinationOption fromOriginDestinationOption;
    private double toAdultFare = 0;
    private double toChildFare = 0;
    private double toAdultAirportTax = 0;
    String rule;

    private double fromAdultFare = 0;
    private double fromChildFare = 0;
    private double fromAdultAirportTax = 0;
    private double fromChildAirportTax = 0;
    private double fromInfantAirportTax = 0;

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
    private String sourceCountry = "";
    private String destinationCountry = "";
    private double fromInfantFare = 0;
    private double toInfantFare = 0;
    private double toChildAirportTax = 0;
    private double toInfantAirportTax = 0;
    private double totalAdultBaseFare = 0;
    private double totalChildBaseFare = 0;
    private double totalInfantFare = 0;
    private double adultAirportTax = 0;
    private double childAirportTax = 0;
    private double infantAirportTax = 0;
    private double airportTax = 0;
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_domestic_flights_search_round_trip_flights_review_activity);
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
            sourceCountry = bundle.getString(getString(R.string.source_country));
            destinationCountry = bundle.getString(getString(R.string.destination_country));
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
        StringBuilder stringBuilder = new StringBuilder();
        if (adultCount != 0) {
            stringBuilder.append(String.valueOf(adultCount)).append(" ").append(getString(R.string.adult));
        }
        if (childrenCount != 0) {
            stringBuilder.append(String.valueOf(childrenCount)).append(" ").append(getString(R.string.child));
        }
        if (infantCount != 0) {
            stringBuilder.append(String.valueOf(infantCount)).append(" ").append(getString(R.string.infant));
        }
        tvNumberOfPersons.setText(stringBuilder.toString());
    }

    private void initializeListeners() {
        llFare.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        llCancellationPolicy.setOnClickListener(this);
    }

    private void prepareResults() {
        toOriginDestinationOption = AppController.getInstance().getSelectedToOriginDestinationOption();
        fromOriginDestinationOption = AppController.getInstance().getSelectedFromOriginDestinationOption();

        toOriginResults();
        fromOriginResults();

        adultAirportTax = (adultCount * toAdultAirportTax) + (adultCount * fromAdultAirportTax);
        childAirportTax = (childrenCount * toChildAirportTax) + (childrenCount * fromChildAirportTax);
        infantAirportTax = (infantCount * toInfantAirportTax) + (infantCount * fromInfantAirportTax);
        airportTax = adultAirportTax + childAirportTax + infantAirportTax;

        totalAdultBaseFare = (adultCount * toAdultFare) + (adultCount * fromAdultFare);
        totalChildBaseFare = (childrenCount * toChildFare) + (childrenCount * fromChildFare);
        totalInfantFare = (infantCount * toInfantFare) + (infantCount * fromInfantFare);
        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");


        int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
        int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

        Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
        Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;
        markUpFee = ((totalAdultBaseFare + totalChildBaseFare + totalInfantFare) * markUpPercentage);
        double totalFare = totalAdultBaseFare + totalChildBaseFare + totalInfantFare + airportTax;
        if (busMType.equalsIgnoreCase("M"))
        {
            conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((totalFare-markUpFee) * conventionalFeePercentage)));
        } else
        {
            conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((totalFare+markUpFee) * conventionalFeePercentage)));
        }

        //conventionalFee = Math.round(totalFare * conventionalFeePercentage);
       // conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format(totalFare * conventionalFeePercentage)));

        Log.e("markup",""+markUpFee);
        Log.e("conventionalFee",""+conventionalFee);
        Log.e("totalfare",""+totalFare);


        int totalFeeWithTax = 0;
        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (totalFare - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (totalFare  + markUpFee +conventionalFee);

        tvFare.setText(String.valueOf(totalFeeWithTax));
    }

    private void toOriginResults() {
        String fare = toOriginDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
        JsonObject toOriginDestinationOptionJsonObject = toOriginDestinationOption.getFlightSegments().getAsJsonObject();
        try {
            JSONObject flightSegmentJsonObject = new JSONObject(toOriginDestinationOptionJsonObject.toString());
            Object object = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            if (object instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), FlightSegment.class);
                String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                if (!adultFare.equalsIgnoreCase("1")) {
                    this.toAdultFare = Double.parseDouble(adultFare);
                }
                if (flightSegment.getBookingClassFare().getChildFare() != null) {
                    String childFare = flightSegment.getBookingClassFare().getChildFare();
                    if (!childFare.equalsIgnoreCase("1")) {
                        this.toChildFare = Double.parseDouble(childFare);
                    }
                }

                if (flightSegment.getBookingClassFare().getInfantfare() != null) {
                    String infantFare = flightSegment.getBookingClassFare().getInfantfare();
                    if (!infantFare.equalsIgnoreCase("1")) {
                        this.toInfantFare = Double.parseDouble(infantFare);
                    }
                }
                if (flightSegment.getAirportTaxChild() != null) {
                    String childAirportTax = flightSegment.getAirportTaxChild();
                    if (!childAirportTax.equalsIgnoreCase("0")) {
                        this.toChildAirportTax = Double.parseDouble(childAirportTax);
                    }
                }

                if (flightSegment.getAirportTaxInfant() != null) {
                    String infantAirportTax = flightSegment.getAirportTaxInfant();
                    if (!infantAirportTax.equalsIgnoreCase("0")) {
                        this.toInfantAirportTax = Double.parseDouble(infantAirportTax);
                    }
                }

                String airportTax = flightSegment.getAirportTax();
                if (!airportTax.equalsIgnoreCase("0"))
                    this.toAdultAirportTax = Double.parseDouble(airportTax);

                initializeView(flightSegment, 0, "to");
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    String response = jsonArray.getString(index);
                    FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                    String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                    if (!adultFare.equalsIgnoreCase("1")) {
                        this.toAdultFare = Double.parseDouble(adultFare);
                    }
                    if (flightSegment.getBookingClassFare().getChildFare() != null) {
                        String childFare = flightSegment.getBookingClassFare().getChildFare();
                        if (!childFare.equalsIgnoreCase("1")) {
                            this.toChildFare = Double.parseDouble(childFare);
                        }
                    }
                    if (flightSegment.getBookingClassFare().getInfantfare() != null) {
                        String infantFare = flightSegment.getBookingClassFare().getInfantfare();
                        if (!infantFare.equalsIgnoreCase("1")) {
                            this.toInfantFare = Double.parseDouble(infantFare);
                        }
                    }
                    if (flightSegment.getAirportTaxChild() != null) {
                        String childAirportTax = flightSegment.getAirportTaxChild();
                        if (!childAirportTax.equalsIgnoreCase("0")) {
                            this.toChildAirportTax = Double.parseDouble(childAirportTax);
                        }
                    }

                    if (flightSegment.getAirportTaxInfant() != null) {
                        String infantAirportTax = flightSegment.getAirportTaxInfant();
                        if (!infantAirportTax.equalsIgnoreCase("0")) {
                            this.toInfantAirportTax = Double.parseDouble(infantAirportTax);
                        }
                    }
                    String airportTax = flightSegment.getAirportTax();
                    if (!airportTax.equalsIgnoreCase("0"))
                        this.toAdultAirportTax = Double.parseDouble(airportTax);
                    initializeView(flightSegment, index, "to");

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fromOriginResults() {
        String fare = fromOriginDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
        JsonObject fromOriginDestinationOptionJsonObject = fromOriginDestinationOption.getFlightSegments().getAsJsonObject();
        try {
            JSONObject flightSegmentJsonObject = new JSONObject(fromOriginDestinationOptionJsonObject.toString());
            Object object = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            if (object instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), FlightSegment.class);
                String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                if (!adultFare.equalsIgnoreCase("1")) {
                    this.fromAdultFare = Double.parseDouble(adultFare);
                }
                if (flightSegment.getBookingClassFare().getChildFare() != null) {
                    String childFare = flightSegment.getBookingClassFare().getChildFare();
                    if (!childFare.equalsIgnoreCase("1")) {
                        this.fromChildFare = Double.parseDouble(childFare);
                    }
                }
                if (flightSegment.getBookingClassFare().getInfantfare() != null) {
                    String infantFare = flightSegment.getBookingClassFare().getInfantfare();
                    if (!infantFare.equalsIgnoreCase("1")) {
                        this.fromInfantFare = Double.parseDouble(infantFare);
                    }
                }
                if (flightSegment.getAirportTaxChild() != null) {
                    String childAirportTax = flightSegment.getAirportTaxChild();
                    if (!childAirportTax.equalsIgnoreCase("0")) {
                        this.fromChildAirportTax = Double.parseDouble(childAirportTax);
                    }
                }

                if (flightSegment.getAirportTaxInfant() != null) {
                    String infantAirportTax = flightSegment.getAirportTaxInfant();
                    if (!infantAirportTax.equalsIgnoreCase("0")) {
                        this.fromInfantAirportTax = Double.parseDouble(infantAirportTax);
                    }
                }

                String airportTax = flightSegment.getAirportTax();
                if (!airportTax.equalsIgnoreCase("0"))
                    this.fromAdultAirportTax = Double.parseDouble(airportTax);

                initializeView(flightSegment, 0, "from");
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    String response = jsonArray.getString(index);
                    FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                    String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                    if (!adultFare.equalsIgnoreCase("1")) {
                        this.fromAdultFare = Double.parseDouble(adultFare);
                    }
                    if (flightSegment.getBookingClassFare().getChildFare() != null) {
                        String childFare = flightSegment.getBookingClassFare().getChildFare();
                        if (!childFare.equalsIgnoreCase("1")) {
                            this.fromChildFare = Double.parseDouble(childFare);
                        }
                    }
                    if (flightSegment.getBookingClassFare().getInfantfare() != null) {
                        String infantFare = flightSegment.getBookingClassFare().getInfantfare();
                        if (!infantFare.equalsIgnoreCase("1")) {
                            this.fromInfantFare = Double.parseDouble(infantFare);
                        }
                    }
                    if (flightSegment.getAirportTaxChild() != null) {
                        String childAirportTax = flightSegment.getAirportTaxChild();
                        if (!childAirportTax.equalsIgnoreCase("0")) {
                            this.fromChildAirportTax = Double.parseDouble(childAirportTax);
                        }
                    }

                    if (flightSegment.getAirportTaxInfant() != null) {
                        String infantAirportTax = flightSegment.getAirportTaxInfant();
                        if (!infantAirportTax.equalsIgnoreCase("0")) {
                            this.fromInfantAirportTax = Double.parseDouble(infantAirportTax);
                        }
                    }

                    String airportTax = flightSegment.getAirportTax();
                    if (!airportTax.equalsIgnoreCase("0"))
                        this.fromAdultAirportTax = Double.parseDouble(airportTax);
                    initializeView(flightSegment, index, "from");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        airwayName = flightSegment.getAirLineName();

        String departureDateTime = flightSegment.getDepartureDateTime();
        String arrivalDateTime = flightSegment.getArrivalDateTime();
        String departureAirportCode = flightSegment.getDepartureAirportCode();
        String arrivalAirportCode = flightSegment.getArrivalAirportCode();
        String departureAirportName = flightSegment.getDepartureAirportCode();
        String arrivalAirportName = flightSegment.getArrivalAirportCode();
        rule = flightSegment.getBookingClassFare().getRule();
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
        String details = departSelectedDate + " | " + String.valueOf(index) + " | " + totalHoursAndMinutes[0] + "h" + " " + totalHoursAndMinutes[1] + "m";
        if (from.equalsIgnoreCase("to")) {
            tvDepartDetails.setText(details);
            llDepartContainer.addView(view);
        } else {
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
                Intent domesticFlightsFareBreakUpIntent = new Intent(this, DomesticFlightsFareBreakUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.adult_count), adultCount);
                bundle.putInt(getString(R.string.children_count), childrenCount);
                bundle.putInt(getString(R.string.infant_count), infantCount);

                adultAirportTax = (adultCount * toAdultAirportTax) + (adultCount * fromAdultAirportTax);
                childAirportTax = (childrenCount * toChildAirportTax) + (childrenCount * fromChildAirportTax);
                infantAirportTax = (infantCount * toInfantAirportTax) + (infantCount * fromInfantAirportTax);
                airportTax = adultAirportTax + childAirportTax + infantAirportTax;

                totalAdultBaseFare = (adultCount * toAdultFare) + (adultCount * fromAdultFare);
                totalChildBaseFare = (childrenCount * toChildFare) + (childrenCount * fromChildFare);
                totalInfantFare = (infantCount * toInfantFare) + (infantCount * fromInfantFare);


                bundle.putDouble(getString(R.string.adult_fare), totalAdultBaseFare);
                bundle.putDouble(getString(R.string.child_fare), totalChildBaseFare);
                bundle.putDouble(getString(R.string.infant_fare), totalInfantFare);
                bundle.putDouble(getString(R.string.airport_tax), airportTax);
                bundle.putString(getString(R.string.origin), origin);
                bundle.putString(getString(R.string.destination), destination);
                bundle.putDouble(getString(R.string.mark_up_fee), markUpFee);
                bundle.putDouble(getString(R.string.conventional_fee), conventionalFee);



                domesticFlightsFareBreakUpIntent.putExtras(bundle);
                startActivity(domesticFlightsFareBreakUpIntent);
                break;
            case R.id.tvContinue:
                totalAdultBaseFare = (adultCount * toAdultFare) + (adultCount * fromAdultFare);
                totalChildBaseFare = (childrenCount * toChildFare) + (childrenCount * fromChildFare);
                totalInfantFare = (infantCount * toInfantFare) + (infantCount * fromInfantFare);

                adultAirportTax = (adultCount * toAdultAirportTax) + (adultCount * fromAdultAirportTax);
                childAirportTax = (childrenCount * toChildAirportTax) + (childrenCount * fromChildAirportTax);
                infantAirportTax = (infantCount * toInfantAirportTax) + (infantCount * fromInfantAirportTax);
                airportTax = adultAirportTax + childAirportTax + infantAirportTax;

                Double grandTotal = totalAdultBaseFare + totalChildBaseFare + totalInfantFare + adultAirportTax + childAirportTax + infantAirportTax;
                Log.d("asd", String.valueOf(grandTotal));

                Intent flightTravellerDetailsIntent = new Intent(this, FlightTravellerDetailsActivity.class);
                Bundle flightTravellerDetailsBundle = new Bundle();
                flightTravellerDetailsBundle.putString(getString(R.string.source), origin);
                flightTravellerDetailsBundle.putString(getString(R.string.destination), destination);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_base_fare), totalAdultBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_tax), adultAirportTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_base_fare), totalChildBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_tax), childAirportTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_base_fare), totalInfantFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_tax), infantAirportTax);
                flightTravellerDetailsBundle.putInt(getString(R.string.adult_count), adultCount);
                flightTravellerDetailsBundle.putInt(getString(R.string.children_count), childrenCount);
                flightTravellerDetailsBundle.putInt(getString(R.string.infant_count), infantCount);
                flightTravellerDetailsBundle.putString(getString(R.string.from), getString(R.string.domestic_round_trip));
                flightTravellerDetailsBundle.putString(getString(R.string.source_country), sourceCountry);
                flightTravellerDetailsBundle.putString(getString(R.string.destination_country), destinationCountry);
                flightTravellerDetailsIntent.putExtras(flightTravellerDetailsBundle);
                startActivity(flightTravellerDetailsIntent);
                break;
            default:
                break;
        }
    }

    private  void alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DomesticFlightsSearchRoundTripFlightsReviewActivity.this);

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
