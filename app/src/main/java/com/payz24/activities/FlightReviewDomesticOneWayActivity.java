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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.PreFareDomesticHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption;
import com.payz24.responseModels.domesticPreFare.DomesticPreFare;
import com.payz24.responseModels.domesticPreFare.Fare;
import com.payz24.responseModels.domesticPreFare.FlightDataOneway;
import com.payz24.responseModels.domesticPreFare.Leg;
import com.payz24.responseModels.domesticPreFare.Search;
import com.payz24.responseModels.domesticPreFare.SearchData;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mahesh on 2/19/2018.
 */

public class FlightReviewDomesticOneWayActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack {

    private OriginDestinationOption originDestinationOption;
    private String sourceFullName = "";
    private String destinationFullName = "";
    private String departSelectedDate = "";
    private String details = "";
    String rule;

    private TextView tvSource, tvDestination, tvDetails, tvFlightName, tvFlightDepartureDate, tvFlightDepartureShortName, tvFlightDepartureTime, tvFlightDepartureFullName;
    private TextView tvFlightTravelTime, tvFlightArrivalDate, tvFlightArrivalShortName, tvFlightArrivalTime, tvFlightArrivalFullName, tvLayOver;
    private LinearLayout llContainer;
    private String totalJourneyTime = "";
    private String[] departureDateTime;
    private String[] arrivalDateTime;
    private String airwayName = "";
    private String fltTm;
    private LinearLayout llLayover;
    private String departDate;
    private LinearLayout llFare;
    private TextView tvContinue, tvFare, tvNumberOfPersons;
    private double adultFare = 0;
    private double childFare = 0;
    private int adultCount = 0;
    private int childrenCount = 0;
    private int infantCount = 0;
    private String startingDate = "";
    private String endingDate = "";
    private String sourceCountry = "";
    private String destinationCountry = "";
    private double adultAirportTax = 0;
    private double infantFare = 0;
    private double childAirportTax = 0;
    private double infantAirportTax = 0;
    private double totalAdultBaseFare = 0;
    private double totalChildBaseFare = 0;
    private double totalInfantFare = 0;
    private double totalAdultTax = 0;
    private double totalChildTax = 0;
    private double totalInfantTax = 0;
    private double airportTax = 0;
    private Double totalFare;
    private String numberOfStops;
    private String operatingAirwayCode;
    private List<Leg> listOfLegs;
    private double markUpFee = 0.0;
    private double conventionalFee = 0.0;
    LinearLayout llCancellationPolicy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flight_review_one_way);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareResults();
        preparePreFareCheck();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sourceFullName = bundle.getString(getString(R.string.source));
            destinationFullName = bundle.getString(getString(R.string.destination));
            departSelectedDate = bundle.getString(getString(R.string.selected_depature_date));
            details = bundle.getString(getString(R.string.details));
            adultCount = bundle.getInt(getString(R.string.adult_count));
            childrenCount = bundle.getInt(getString(R.string.children_count));
            infantCount = bundle.getInt(getString(R.string.infant_count));
            sourceCountry = bundle.getString(getString(R.string.source_country));
            destinationCountry = bundle.getString(getString(R.string.destination_country));
        }
        originDestinationOption = AppController.getInstance().getSelectedToOriginDestinationOption();
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.flight_review));
        setSupportActionBar(toolbar);

        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
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
        llCancellationPolicy = findViewById(R.id.llCancellationPolicy);
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
        tvSource.setText(sourceFullName);
        tvDestination.setText(destinationFullName);
    }

    private void initializeListeners() {
        llFare.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        llCancellationPolicy.setOnClickListener(this);
    }

    private void prepareResults() {
        listOfLegs = new ArrayList<>();
        String fare = originDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
        JsonObject toOriginDestinationOptionJsonObject = originDestinationOption.getFlightSegments().getAsJsonObject();
        try {
            JSONObject flightSegmentJsonObject = new JSONObject(toOriginDestinationOptionJsonObject.toString());
            Object object = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
            if (object instanceof JSONObject) {
                FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), FlightSegment.class);
                String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                numberOfStops = flightSegment.getStopQuantity();
                legObject(flightSegment);
                if (!adultFare.equalsIgnoreCase("1")) {
                    this.adultFare = Double.parseDouble(adultFare);
                    Log.e("totalAdultBaseFare",adultFare);
                }
                if (flightSegment.getBookingClassFare().getChildFare() != null) {
                    String childFare = flightSegment.getBookingClassFare().getChildFare();
                    if (!childFare.equalsIgnoreCase("1")) {
                        this.childFare = Double.parseDouble(childFare);
                    }
                }
                if (flightSegment.getBookingClassFare().getInfantfare() != null) {
                    String infantFare = flightSegment.getBookingClassFare().getInfantfare();
                    if (!infantFare.equalsIgnoreCase("1")) {
                        this.infantFare = Double.parseDouble(infantFare);
                    }
                }
                if (flightSegment.getAirportTaxChild() != null) {
                    String childAirportTax = flightSegment.getAirportTaxChild();
                    if (!childAirportTax.equalsIgnoreCase("0")) {
                        this.childAirportTax = Double.parseDouble(childAirportTax);
                    }
                }

                if (flightSegment.getAirportTaxInfant() != null) {
                    String infantAirportTax = flightSegment.getAirportTaxInfant();
                    if (!infantAirportTax.equalsIgnoreCase("0")) {
                        this.infantAirportTax = Double.parseDouble(infantAirportTax);
                    }
                }
                String airportTax = flightSegment.getAirportTax();
                if (!airportTax.equalsIgnoreCase("0"))
                    this.adultAirportTax = Double.parseDouble(airportTax);
                initializeView(flightSegment, 0, "to");
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                for (int index = 0; index < jsonArray.length(); index++) {
                    String response = jsonArray.getString(index);
                    FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                    String adultFare = flightSegment.getBookingClassFare().getAdultFare();
                    legObject(flightSegment);
                    numberOfStops = flightSegment.getStopQuantity();
                    if (!adultFare.equalsIgnoreCase("1")) {
                        this.adultFare = Integer.parseInt(adultFare);
                        Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
                    }
                    if (flightSegment.getBookingClassFare().getChildFare() != null) {
                        String childFare = flightSegment.getBookingClassFare().getChildFare();
                        if (!childFare.equalsIgnoreCase("1")) {
                            this.childFare = Integer.parseInt(childFare);
                        }
                    }
                    if (flightSegment.getBookingClassFare().getInfantfare() != null) {
                        String infantFare = flightSegment.getBookingClassFare().getInfantfare();
                        if (!infantFare.equalsIgnoreCase("1")) {
                            this.infantFare = Double.parseDouble(infantFare);
                        }
                    }
                    if (flightSegment.getAirportTaxChild() != null) {
                        String childAirportTax = flightSegment.getAirportTaxChild();
                        if (!childAirportTax.equalsIgnoreCase("0")) {
                            this.childAirportTax = Double.parseDouble(childAirportTax);
                        }
                    }

                    if (flightSegment.getAirportTaxInfant() != null) {
                        String infantAirportTax = flightSegment.getAirportTaxInfant();
                        if (!infantAirportTax.equalsIgnoreCase("0")) {
                            this.infantAirportTax = Double.parseDouble(infantAirportTax);
                        }
                    }
                    String airportTax = flightSegment.getAirportTax();
                    if (!airportTax.equalsIgnoreCase("0"))
                        this.adultAirportTax = Double.parseDouble(airportTax);
                    initializeView(flightSegment, index, "to");

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        double totalAdultBaseFare = adultCount * adultFare;
        double totalChildBaseFare = childrenCount * childFare;
        double totalInfantFare = infantCount * infantFare;

        double totalAdultTax = adultCount * adultAirportTax;
        double totalChildTax = childrenCount * childAirportTax;
        double totalInfantTax = infantCount * infantAirportTax;


        String busMType = PreferenceConnector.readString(this, getString(R.string.flight_m_type), "");

        int fareMarkUp = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_mark_up), ""));
        int fareConventionalFee = Integer.parseInt(PreferenceConnector.readString(this, getString(R.string.flight_conventional_fee), ""));

        Double markUpPercentage = Double.parseDouble(String.valueOf(fareMarkUp)) / 100;
        Double conventionalFeePercentage = Double.parseDouble(String.valueOf(fareConventionalFee)) / 100;

        markUpFee =((totalAdultBaseFare + totalChildBaseFare + totalInfantFare) * markUpPercentage);
        totalFare = Double.valueOf(Math.round(totalAdultBaseFare + totalChildBaseFare + totalInfantFare + totalAdultTax + totalChildTax + totalInfantTax));


        if (busMType.equalsIgnoreCase("M"))
        {
            conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((totalFare-markUpFee) * conventionalFeePercentage)));
        } else
        {
            conventionalFee = Math.round(Double.parseDouble(new DecimalFormat("##.##").format((totalFare+markUpFee) * conventionalFeePercentage)));
        }



        Log.e("totalAdultBaseFare",""+totalAdultBaseFare);
        Log.e("totalChildBaseFare",""+totalChildBaseFare);
        Log.e("totalInfantFare",""+totalInfantFare);
        Log.e("totalAdultTax",""+totalAdultTax);
        Log.e("totalAdultTax",""+totalAdultTax);
        Log.e("totalInfantTax",""+totalInfantTax);
        Log.e("markup",""+markUpFee);
        Log.e("conventionalFee",""+conventionalFee);
        Log.e("basefare",""+totalFare);


        int totalFeeWithTax = 0;
        if (busMType.equalsIgnoreCase("M"))
            totalFeeWithTax = (int) (totalFare - markUpFee + conventionalFee);
        else
            totalFeeWithTax = (int) (totalFare + markUpFee + conventionalFee);

        tvFare.setText(getString(R.string.Rs) + " " + String.valueOf(totalFeeWithTax));
    }

    private void legObject(FlightSegment flightSegment) {
        String airEquipType = flightSegment.getAirEquipType();
        String arrivalAirportCode = flightSegment.getArrivalAirportCode();
        String arrivalDateTime = flightSegment.getArrivalDateTime();
        String departureAirportCode = flightSegment.getDepartureAirportCode();
        String departureDateTime = flightSegment.getDepartureDateTime();
        String flightNumber = flightSegment.getFlightNumber();
        String operatingAirlineCode = flightSegment.getOperatingAirlineCode();
        String operatingAirlineFlightNumber = flightSegment.getOperatingAirlineFlightNumber();
        String rPH = "";
        if (flightSegment.getRPH() instanceof JSONArray) {

        } else {
            airwayName = flightSegment.getRPH().toString();
            operatingAirwayCode = operatingAirlineCode;
        }
        String airLineName = flightSegment.getAirLineName();
        String airportTax = flightSegment.getAirportTax();
        String imageFileName = flightSegment.getImageFileName();
        String viaFlight = "";
        String stopQuantity = flightSegment.getStopQuantity();
        String availability = flightSegment.getBookingClass().getAvailability();
        String resBookDesigCode = flightSegment.getBookingClass().getResBookDesigCode();
        String adultFare = flightSegment.getBookingClassFare().getAdultFare();
        String bookingclass = flightSegment.getBookingClassFare().getBookingclass();
        String classType = flightSegment.getBookingClassFare().getClassType();
        String farebasiscode = flightSegment.getBookingClassFare().getFarebasiscode();
        rule = flightSegment.getBookingClassFare().getRule();
        String infantfare = "0";
        String childFare = "0";
        if (flightSegment.getBookingClassFare().getInfantfare() != null)
            infantfare = flightSegment.getBookingClassFare().getInfantfare();
        else
            infantfare = "0";
        if (flightSegment.getBookingClassFare().getChildFare() != null) {
            childFare = flightSegment.getBookingClassFare().getChildFare();
        } else {
            childFare = "0";
        }
        String adultCommission = flightSegment.getBookingClassFare().getAdultCommission();
        String childCommission = flightSegment.getBookingClassFare().getChildCommission();
        String commissionOnTCharge = flightSegment.getBookingClassFare().getCommissionOnTCharge();
        String discount = flightSegment.getDiscount();
        String airportTaxChild = flightSegment.getAirportTaxChild();
        String airportTaxInfant = flightSegment.getAirportTaxInfant();
        String adultTaxBreakup = flightSegment.getAdultTaxBreakup();
        String childTaxBreakup = flightSegment.getChildTaxBreakup();
        String infantTaxBreakup = flightSegment.getInfantTaxBreakup();
        String octax = flightSegment.getOctax();
        Leg leg = new Leg(airEquipType, arrivalAirportCode, arrivalDateTime, departureAirportCode, departureDateTime,
                flightNumber, operatingAirlineCode, operatingAirlineFlightNumber, rPH, airLineName, airportTax, imageFileName, viaFlight, stopQuantity, availability, resBookDesigCode,
                adultFare, bookingclass, classType, farebasiscode, rule, infantfare, childFare, adultCommission, childCommission, commissionOnTCharge, discount, airportTaxChild, airportTaxInfant,
                adultTaxBreakup, childTaxBreakup, infantTaxBreakup, octax);
        listOfLegs.add(leg);
    }


    private void preparePreFareCheck() {
        SearchData searchData = new SearchData(adultCount, childrenCount, infantCount, "O");
        String flightId = originDestinationOption.getId();
        String key = originDestinationOption.getKey();
        String userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        Fare fare = new Fare(originDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare(), originDestinationOption.getFareDetails().getChargeableFares().getTax(), originDestinationOption.getFareDetails().getChargeableFares().getSTax(),
                originDestinationOption.getFareDetails().getChargeableFares().getSCharge(), originDestinationOption.getFareDetails().getChargeableFares().getTDiscount(), originDestinationOption.getFareDetails().getChargeableFares().getTPartnerCommission(),
                originDestinationOption.getFareDetails().getNonchargeableFares().getTCharge(), originDestinationOption.getFareDetails().getNonchargeableFares().getTMarkup(), originDestinationOption.getFareDetails().getNonchargeableFares().getTSdiscount());
        FlightDataOneway flightDataOneway = new FlightDataOneway(Integer.parseInt(userId), flightId, key, fare, totalFare.intValue(), numberOfStops, listOfLegs);
        Search search = new Search(searchData, flightId, flightDataOneway, null);
        DomesticPreFare domesticPreFare = new DomesticPreFare(search);
        String domesticPreFareString = new Gson().toJson(domesticPreFare);
        serviceCallForDomesticPreFare(domesticPreFareString);
    }

    private void serviceCallForDomesticPreFare(String domesticPreFareString) {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.PRE_FARE_DOMESTIC_PARAM, domesticPreFareString);

        PreFareDomesticHttpClient preFareDomesticHttpClient = new PreFareDomesticHttpClient(this);
        preFareDomesticHttpClient.callBack = this;
        preFareDomesticHttpClient.getJsonForType(mapOfRequestParams);
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
            tvDetails.setText(details);
            llContainer.addView(view);
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



                totalAdultBaseFare = adultCount * adultFare;
                totalChildBaseFare = childrenCount * childFare;
                totalInfantFare = infantCount * infantFare;

                totalAdultTax = adultCount * adultAirportTax;
                totalChildTax = childrenCount * childAirportTax;
                totalInfantTax = infantCount * infantAirportTax;

                airportTax = totalAdultTax + totalChildTax + totalInfantTax;

                Intent domesticFlightsFareBreakUpIntent = new Intent(this, DomesticFlightsFareBreakUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.adult_count), adultCount);
                bundle.putInt(getString(R.string.children_count), childrenCount);
                bundle.putInt(getString(R.string.infant_count), infantCount);
                bundle.putDouble(getString(R.string.adult_fare), totalAdultBaseFare);
                bundle.putDouble(getString(R.string.child_fare), totalChildBaseFare);
                bundle.putDouble(getString(R.string.infant_fare), totalInfantFare);
                bundle.putDouble(getString(R.string.airport_tax), airportTax);
                bundle.putString(getString(R.string.origin), sourceFullName);
                bundle.putString(getString(R.string.destination), destinationFullName);
                bundle.putDouble(getString(R.string.mark_up_fee), markUpFee);
                bundle.putDouble(getString(R.string.conventional_fee), conventionalFee);





                domesticFlightsFareBreakUpIntent.putExtras(bundle);
                startActivity(domesticFlightsFareBreakUpIntent);

                break;
            case R.id.tvContinue:



                totalAdultBaseFare = adultCount * adultFare;
                totalChildBaseFare = childrenCount * childFare;
                totalInfantFare = infantCount * infantFare;

                totalAdultTax = adultCount * adultAirportTax;
                totalChildTax = childrenCount * childAirportTax;
                totalInfantTax = infantCount * infantAirportTax;

                airportTax = totalAdultTax + totalChildTax + totalInfantTax;

                Intent flightTravellerDetailsIntent = new Intent(this, FlightTravellerDetailsActivity.class);
                Bundle flightTravellerDetailsBundle = new Bundle();
                flightTravellerDetailsBundle.putString(getString(R.string.source), sourceFullName);
                flightTravellerDetailsBundle.putString(getString(R.string.destination), destinationFullName);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_base_fare), totalAdultBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_adult_tax), totalAdultTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_base_fare), totalChildBaseFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_children_tax), totalChildTax);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_base_fare), totalInfantFare);
                flightTravellerDetailsBundle.putDouble(getString(R.string.total_infant_tax), totalInfantTax);
                flightTravellerDetailsBundle.putInt(getString(R.string.adult_count), adultCount);
                flightTravellerDetailsBundle.putInt(getString(R.string.children_count), childrenCount);
                flightTravellerDetailsBundle.putInt(getString(R.string.infant_count), infantCount);
                flightTravellerDetailsBundle.putString(getString(R.string.from), getString(R.string.domestic_one_way));
                flightTravellerDetailsBundle.putString(getString(R.string.source_country), sourceCountry);
                flightTravellerDetailsBundle.putDouble(getString(R.string.airport_tax), airportTax);
                flightTravellerDetailsBundle.putString(getString(R.string.destination_country), destinationCountry);
                flightTravellerDetailsBundle.putDouble(getString(R.string.mark_up_fee), markUpFee);
                flightTravellerDetailsBundle.putDouble(getString(R.string.conventional_fee), conventionalFee);



                Log.e(getString(R.string.adult_fare), ""+totalAdultBaseFare);
                Log.e(getString(R.string.child_fare), ""+totalChildBaseFare);
                Log.e(getString(R.string.infant_fare), ""+totalInfantFare);
                Log.e(getString(R.string.airport_tax), ""+airportTax);



                flightTravellerDetailsIntent.putExtras(flightTravellerDetailsBundle);
                startActivity(flightTravellerDetailsIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_PRE_FARE_DOMESTIC:
                if (jsonResponse != null) {

                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private  void alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FlightReviewDomesticOneWayActivity.this);

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
