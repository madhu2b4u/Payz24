package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.adapter.DomesticFlightsSearchRoundTripDepartFragmentAdapter;
import com.payz24.adapter.FlightDetailsAdapter;
import com.payz24.application.AppController;
import com.payz24.http.SearchDomesticFlightsHttpClient;
import com.payz24.http.SearchFlightsHttpClient;
import com.payz24.interfaces.FlightFilterCallBack;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.DomesticFlightsSearchRoundTripResponse;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.ResponseDepart;
import com.payz24.responseModels.flightSearchResults.AvailRequest;
import com.payz24.responseModels.flightSearchResults.FlightSearchResults;
import com.payz24.responseModels.flightSearchResults.FlightSegment;
import com.payz24.responseModels.flightSearchResults.OriginDestinationOption;
import com.payz24.responseModels.flightSearchResults.Result;
import com.payz24.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mahesh on 2/7/2018.
 */

public class FlightSearchResultsActivity extends BaseActivity implements HttpReqResCallBack, View.OnClickListener, FlightDetailsAdapter.ItemClickListener, DomesticFlightsSearchRoundTripDepartFragmentAdapter.ItemClickListener, FlightFilterCallBack {

    private ImageView ivBack;
    private TextView tvSource, tvDestination, tvDetails;
    private RecyclerView rvFlightDetails;
    private TextView tvError, tvCancel;
    private ImageView ivFlightImage, ivFilter;
    private LinearLayout llFlightsDetails, llLoading;
    private Map<String, String> mapOfRequestParams;
    private String sourceFullName = "";
    private String destinationFullName = "";
    private String departSelectedDate = "";
    private String details = "";
    private List<OriginDestinationOption> listOfOriginDestinations;
    private List<OriginDestinationOption> listOfTempOriginDestinations;
    private String sourceCountry = "";
    private String destinationCountry = "";
    private String origin = "";
    private String destination = "";
    private ResponseDepart responseDepart;
    private int adultCount = 0;
    private int childrenCount = 0;
    private int infantCount = 0;
    private String airwayName = "";
    private LinkedList<String> listOfAirwayNames;
    private LinkedList<String> listOfAirwayImages;
    private LinkedList<String> listOfAirwayFares;
    private LinkedList<String> listOfNumberOfStops;
    private LinkedList<String> listOfDepartureDateTime;
    private LinkedList<String> listOfAllAirwayNames;
    private LinkedList<String> listOfAllAirwayImages;
    private LinkedList<String> listOfAllAirwayFares;
    private LinkedList<String> listOfAllNumberOfStops;
    private LinkedList<String> listOfAllDepartureDateTime;
    private LinkedHashMap<Integer, OriginDestinationOption> mapOfAllAirwayNameOriginDestinationOption;
    private LinkedHashMap<Integer, com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption> mapOfAllAirwayNameDomesticOriginDestinationOption;
    private FlightDetailsAdapter flightDetailsAdapter;
    private DomesticFlightsSearchRoundTripDepartFragmentAdapter domesticFlightsSearchRoundTripDepartFragmentAdapter;
    private List<com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption> listOfDomesticOriginDestinationOptions;
    private List<com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption> listOfTempDomesticOriginDestinationOptions;
    private String sourceShortCode = "";
    private String destinationShortCode = "";


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
        setContentView(R.layout.layout_flights_search_results);
        getDataFromIntent();
        initializeUi();
        initializeListeners();
        serviceCallForFlightsResults();
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mapOfRequestParams = (Map<String, String>) bundle.getSerializable(Constants.SEARCH_RESULTS_MAP);
            sourceFullName = bundle.getString(getString(R.string.source));
            destinationFullName = bundle.getString(getString(R.string.destination));
            departSelectedDate = bundle.getString(getString(R.string.selected_depature_date));
            details = bundle.getString(getString(R.string.details));
            sourceCountry = bundle.getString(getString(R.string.source_country));
            destinationCountry = bundle.getString(getString(R.string.destination_country));
            adultCount = bundle.getInt(getString(R.string.adult_count));
            childrenCount = bundle.getInt(getString(R.string.children_count));
            infantCount = bundle.getInt(getString(R.string.infant_count));
            sourceShortCode = bundle.getString(getString(R.string.source_short_code));
            destinationShortCode = bundle.getString(getString(R.string.destination_short_code));
        }
        AppController.getInstance().setFlightSearchResultsActivityContext(this);
    }

    private void initializeUi() {
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvDetails = findViewById(R.id.tvDetails);
        rvFlightDetails = findViewById(R.id.rvFlightDetails);
        tvError = findViewById(R.id.tvError);
        ivBack = findViewById(R.id.ivBack);
        llFlightsDetails = findViewById(R.id.llFlightsDetails);
        llLoading = findViewById(R.id.llLoading);
        ivFlightImage = findViewById(R.id.ivFlightImage);
        tvCancel = findViewById(R.id.tvCancel);
        ivFilter = findViewById(R.id.ivFilter);

        tvSource.setText(sourceShortCode);
        tvDestination.setText(destinationShortCode);
        tvDetails.setText(details);
        listOfTempOriginDestinations = new ArrayList<>();
        listOfTempDomesticOriginDestinationOptions = new ArrayList<>();
        Glide.with(this).load(R.drawable.ic_flight_loading).into(ivFlightImage);
    }

    private void initializeListeners() {
        ivBack.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivFilter.setOnClickListener(this);
    }

    private void serviceCallForFlightsResults() {
        if (!sourceCountry.equalsIgnoreCase(destinationCountry)) {
            SearchFlightsHttpClient searchFlightsHttpClient = new SearchFlightsHttpClient(this);
            searchFlightsHttpClient.callBack = this;
            searchFlightsHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            SearchDomesticFlightsHttpClient searchDomesticFlightsHttpClient = new SearchDomesticFlightsHttpClient(this);
            searchDomesticFlightsHttpClient.callBack = this;
            searchDomesticFlightsHttpClient.getJsonForType(mapOfRequestParams);
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_SEARCH_FLIGHTS:
                if (jsonResponse != null && !jsonResponse.isEmpty()) {
                    try {
                        FlightSearchResults flightSearchResults = new Gson().fromJson(jsonResponse, FlightSearchResults.class);
                        String status = flightSearchResults.getStatus();
                        AvailRequest availRequest = flightSearchResults.getResult().getAvailRequest();
                        AppController.getInstance().setAvailRequest(availRequest);
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            Result result = flightSearchResults.getResult();
                            listOfOriginDestinations = result.getAvailResponse().getOriginDestinationOptions().getOriginDestinationOption();
                            listOfTempOriginDestinations.addAll(listOfOriginDestinations);
                            if (listOfOriginDestinations != null && listOfOriginDestinations.size() != 0) {
                                prepareListOfFlightNames(listOfOriginDestinations);
                                initializeAdapter(listOfOriginDestinations);
                            }
                        } else {
                            rvFlightDetails.setVisibility(View.GONE);
                            tvError.setVisibility(View.VISIBLE);
                            Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception) {
                        rvFlightDetails.setVisibility(View.GONE);
                        tvError.setVisibility(View.VISIBLE);
                    }
                } else {
                    rvFlightDetails.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                llLoading.setVisibility(View.GONE);
                llFlightsDetails.setVisibility(View.VISIBLE);
                closeProgressbar();
                break;
            case Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS:
                if (jsonResponse != null && !jsonResponse.isEmpty()) {
                    Log.e("json",jsonResponse);
                    try {
                        DomesticFlightsSearchRoundTripResponse domesticFlightsSearchRoundTripResponse = new Gson().fromJson(jsonResponse, DomesticFlightsSearchRoundTripResponse.class);
                        if (domesticFlightsSearchRoundTripResponse != null) {
                            com.payz24.responseModels.DomesticFlightsSearchRoundTrip.Result result = domesticFlightsSearchRoundTripResponse.getResult();
                            AppController.getInstance().setResult(result);
                            origin = result.getRequest().getOrigin();
                            destination = result.getRequest().getDestination();
                            responseDepart = result.getResponseDepart();
                            listOfDomesticOriginDestinationOptions = responseDepart.getOriginDestinationOptions().getOriginDestinationOption();
                            listOfTempDomesticOriginDestinationOptions.addAll(listOfDomesticOriginDestinationOptions);
                            prepareListOfDomesticFlights(listOfDomesticOriginDestinationOptions);
                            initializeAdapter();
                            AppController.getInstance().setResponseDepart(responseDepart);
                        } else {
                            finish();
                            Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception) {
                        finish();
                        Toast.makeText(this, getString(R.string.no_results_found), Toast.LENGTH_SHORT).show();
                        exception.printStackTrace();
                    }
                } else {
                    rvFlightDetails.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                llLoading.setVisibility(View.GONE);
                llFlightsDetails.setVisibility(View.VISIBLE);
                closeProgressbar();
                break;
            default:
                break;
        }
    }

    private void prepareListOfDomesticFlights(List<com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption> listOfOriginDestinations) {
        listOfAirwayNames = new LinkedList<>();
        listOfAirwayImages = new LinkedList<>();
        listOfAirwayFares = new LinkedList<>();
        listOfNumberOfStops = new LinkedList<>();
        listOfDepartureDateTime = new LinkedList<>();
        //mapOfAirwayNameDomesticOriginDestinationOption = new LinkedHashMap<>();

        listOfAllAirwayNames = new LinkedList<>();
        listOfAllAirwayImages = new LinkedList<>();
        listOfAllAirwayFares = new LinkedList<>();
        listOfAllNumberOfStops = new LinkedList<>();
        listOfAllDepartureDateTime = new LinkedList<>();
        mapOfAllAirwayNameDomesticOriginDestinationOption = new LinkedHashMap<>();

        for (int index = 0; index < listOfOriginDestinations.size(); index++) {
            com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption originDestinationOption = listOfOriginDestinations.get(index);
            String fare = originDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
            JsonObject jsonObject = originDestinationOption.getFlightSegments().getAsJsonObject();
            try {
                JSONObject flightSegmentJsonObject = new JSONObject(jsonObject.toString());
                Object json = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
                if (json instanceof JSONObject) {
                    com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);
                    airwayName = flightSegment.getAirLineName();
                    String numberOfStops = flightSegment.getStopQuantity();
                    String departureDate = flightSegment.getDepartureDateTime();
                    String imageUrl = flightSegment.getImageFileName();
                    if (!listOfAirwayNames.contains(airwayName)) {
                        listOfAirwayNames.add(airwayName);
                        listOfNumberOfStops.add("0");
                        listOfAirwayFares.add(fare);
                        listOfAirwayImages.add(imageUrl);
                        listOfDepartureDateTime.add(departureDate);
                    }

                    listOfAllAirwayNames.add(airwayName);
                    listOfAllAirwayImages.add(imageUrl);
                    listOfAllAirwayFares.add(fare);
                    listOfAllDepartureDateTime.add(departureDate);
                    listOfAllNumberOfStops.add("0");
                    mapOfAllAirwayNameDomesticOriginDestinationOption.put(index, listOfOriginDestinations.get(index));

                } else if (json instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                    String response = jsonArray.getString(0);
                    com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment flightSegment = new Gson().fromJson(response, com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment.class);
                    airwayName = flightSegment.getAirLineName();
                    String numberOfStops = String.valueOf(jsonArray.length() - 1);
                    String departureDate = flightSegment.getDepartureDateTime();
                    String imageUrl = flightSegment.getImageFileName();
                    if (!listOfAirwayNames.contains(airwayName)) {
                        listOfAirwayNames.add(airwayName);
                        listOfNumberOfStops.add(numberOfStops);
                        listOfAirwayFares.add(fare);
                        listOfAirwayImages.add(imageUrl);
                        listOfDepartureDateTime.add(departureDate);
                    }


                    listOfAllAirwayNames.add(airwayName);
                    listOfAllAirwayImages.add(imageUrl);
                    listOfAllAirwayFares.add(fare);
                    listOfAllDepartureDateTime.add(departureDate);
                    numberOfStops = String.valueOf(Integer.parseInt(numberOfStops) + 1);
                    listOfAllNumberOfStops.add(numberOfStops);
                    mapOfAllAirwayNameDomesticOriginDestinationOption.put(index, listOfOriginDestinations.get(index));

                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        }
    }

    private void prepareListOfFlightNames(List<OriginDestinationOption> listOfOriginDestinations) {
        listOfAirwayNames = new LinkedList<>();
        listOfAirwayImages = new LinkedList<>();
        listOfAirwayFares = new LinkedList<>();
        listOfNumberOfStops = new LinkedList<>();
        listOfDepartureDateTime = new LinkedList<>();
        //mapOfAirwayNameOriginDestinationOption = new LinkedHashMap<>();

        listOfAllAirwayNames = new LinkedList<>();
        listOfAllAirwayImages = new LinkedList<>();
        listOfAllAirwayFares = new LinkedList<>();
        listOfAllNumberOfStops = new LinkedList<>();
        listOfAllDepartureDateTime = new LinkedList<>();
        mapOfAllAirwayNameOriginDestinationOption = new LinkedHashMap<>();

        for (int index = 0; index < listOfOriginDestinations.size(); index++) {
            String fare = listOfOriginDestinations.get(index).getFareDetails().getActualBaseFare();
            JsonObject jsonObject = listOfOriginDestinations.get(index).getOnward().flightSegments.getAsJsonObject();
            try {
                JSONObject jo2 = new JSONObject(jsonObject.toString());
                Object json = new JSONTokener(jo2.getString("FlightSegment")).nextValue();
                if (json instanceof JSONObject) {
                    FlightSegment flightSegment = new Gson().fromJson(jo2.getString("FlightSegment"), FlightSegment.class);
                    String airLineCode = flightSegment.getOperatingAirlineCode();
                    String numberOfStops = flightSegment.getNumStops();
                    String departureDate = flightSegment.getDepartureDateTime();
                    String imageUrl = Constants.IMAGE_URL_BASE + "" + airLineCode + ".gif";
                    if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {
                        listOfAirwayNames.add("N/A");
                        listOfAirwayImages.add(imageUrl);
                        listOfAirwayFares.add(fare);
                        listOfDepartureDateTime.add(departureDate);
                        listOfNumberOfStops.add(numberOfStops);

                        listOfAllAirwayNames.add("N/A");
                        listOfAllAirwayImages.add(imageUrl);
                        listOfAllAirwayFares.add(fare);
                        listOfAllDepartureDateTime.add(departureDate);
                        listOfAllNumberOfStops.add("0");
                        mapOfAllAirwayNameOriginDestinationOption.put(index, listOfOriginDestinations.get(index));
                        //mapOfAirwayNameOriginDestinationOption.put("N/A", listOfOriginDestinations.get(index));
                    } else {
                        airwayName = flightSegment.getOperatingAirlineName().toString();
                        listOfAllAirwayNames.add(airwayName);
                        listOfAllAirwayImages.add(imageUrl);
                        listOfAllAirwayFares.add(fare);
                        listOfAllDepartureDateTime.add(departureDate);
                        listOfAllNumberOfStops.add("0");
                        mapOfAllAirwayNameOriginDestinationOption.put(index, listOfOriginDestinations.get(index));

                        if (!listOfAirwayNames.contains(airwayName)) {
                            listOfAirwayNames.add(airwayName);
                            listOfAirwayImages.add(imageUrl);
                            listOfAirwayFares.add(fare);
                            listOfDepartureDateTime.add(departureDate);
                            listOfNumberOfStops.add(numberOfStops);
                            //mapOfAirwayNameOriginDestinationOption.put(airwayName, listOfOriginDestinations.get(index));
                        }
                    }
                } else if (json instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(jo2.getString("FlightSegment"));
                    String response = jsonArray.getString(0);
                    FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                    String airLineCode = flightSegment.getOperatingAirlineCode();
                    String numberOfStops = String.valueOf(jsonArray.length() - 1);
                    //numberOfStops = String.valueOf(Integer.parseInt(numberOfStops) + 1);
                    String departureDate = flightSegment.getDepartureDateTime();
                    String imageUrl = Constants.IMAGE_URL_BASE + "" + airLineCode + ".gif";
                    if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {

                        listOfAllAirwayNames.add("N/A");
                        listOfAllAirwayImages.add(imageUrl);
                        listOfAllAirwayFares.add(fare);
                        listOfAllNumberOfStops.add(numberOfStops);
                        listOfAllDepartureDateTime.add(departureDate);
                        mapOfAllAirwayNameOriginDestinationOption.put(index, listOfOriginDestinations.get(index));

                        listOfAirwayNames.add("N/A");
                        listOfAirwayImages.add(imageUrl);
                        listOfAirwayFares.add(fare);
                        listOfNumberOfStops.add(numberOfStops);
                        listOfDepartureDateTime.add(departureDate);
                        //mapOfAirwayNameOriginDestinationOption.put("N/A", listOfOriginDestinations.get(index));
                    } else {
                        airwayName = flightSegment.getOperatingAirlineName().toString();
                        listOfAllAirwayNames.add(airwayName);
                        listOfAllAirwayImages.add(imageUrl);
                        listOfAllAirwayFares.add(fare);
                        listOfAllDepartureDateTime.add(departureDate);
                        listOfAllNumberOfStops.add(numberOfStops);
                        mapOfAllAirwayNameOriginDestinationOption.put(index, listOfOriginDestinations.get(index));

                        if (!listOfAirwayNames.contains(airwayName)) {
                            listOfAirwayNames.add(airwayName);
                            listOfAirwayImages.add(imageUrl);
                            listOfAirwayFares.add(fare);
                            listOfDepartureDateTime.add(departureDate);
                            listOfNumberOfStops.add(numberOfStops);
                            //mapOfAirwayNameOriginDestinationOption.put(airwayName, listOfOriginDestinations.get(index));
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void initializeAdapter(List<OriginDestinationOption> listOfOriginDestinations) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        flightDetailsAdapter = new FlightDetailsAdapter(this, listOfOriginDestinations, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO));
        rvFlightDetails.setLayoutManager(layoutManager);
        flightDetailsAdapter.setClickListener(this);
        rvFlightDetails.setItemAnimator(new DefaultItemAnimator());
        rvFlightDetails.invalidate();
        rvFlightDetails.setAdapter(flightDetailsAdapter);
    }

    private void initializeAdapter() {
        domesticFlightsSearchRoundTripDepartFragmentAdapter = new DomesticFlightsSearchRoundTripDepartFragmentAdapter(this, listOfDomesticOriginDestinationOptions, rvFlightDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        domesticFlightsSearchRoundTripDepartFragmentAdapter.setClickListener(new DomesticFlightsSearchRoundTripDepartFragmentAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                AppController.getInstance().setSelectedToOriginDestinationOption(listOfDomesticOriginDestinationOptions.get(position));
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.source), origin);
                bundle.putString(getString(R.string.destination), destination);
                bundle.putString(getString(R.string.selected_depature_date), departSelectedDate);
                bundle.putString(getString(R.string.source_country), sourceCountry);
                bundle.putString(getString(R.string.destination_country), destinationCountry);
                bundle.putString(getString(R.string.details), details);
                bundle.putInt(getString(R.string.adult_count), adultCount);
                bundle.putInt(getString(R.string.children_count), childrenCount);
                bundle.putInt(getString(R.string.infant_count), infantCount);
                bundle.putString(getString(R.string.source_country), sourceCountry);
                bundle.putString(getString(R.string.destination_country), destinationCountry);
                Intent intent = new Intent(FlightSearchResultsActivity.this, FlightReviewDomesticOneWayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvFlightDetails.setLayoutManager(layoutManager);
        rvFlightDetails.setItemAnimator(new DefaultItemAnimator());
        rvFlightDetails.setAdapter(domesticFlightsSearchRoundTripDepartFragmentAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivFlightImage:
                onBackPressed();
                break;
            case R.id.ivFilter:
                Intent flightsFilterIntent = new Intent(this, FlightsFilterActivity.class);
                flightsFilterIntent.putExtra("isBeforeElevenAMs",isBeforeElevenAMs);
                flightsFilterIntent.putExtra("isElevenAMs",isElevenAMs);
                flightsFilterIntent.putExtra("isFivePMs",isFivePMs);
                flightsFilterIntent.putExtra("isAfterNinePMs",isAfterNinePMs);
                flightsFilterIntent.putExtra("isNonStopSelecteds",isNonStopSelecteds);
                flightsFilterIntent.putExtra("isOneStopSelecteds",isOneStopSelecteds);
                flightsFilterIntent.putExtra("isTwoStopSelecteds",isTwoStopSelecteds);
                AppController.getInstance().setListOfAirwayNames(listOfAirwayNames);
                AppController.getInstance().setListOfAirwayImages(listOfAirwayImages);
                AppController.getInstance().setListOfAirwayFares(listOfAirwayFares);
                startActivity(flightsFilterIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {
        AppController.getInstance().setSelectedOriginDestinationOption(listOfOriginDestinations.get(position));
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.source), sourceFullName);
        bundle.putString(getString(R.string.destination), destinationFullName);
        bundle.putString(getString(R.string.selected_depature_date), departSelectedDate);
        bundle.putString(getString(R.string.details), details);
        bundle.putInt(getString(R.string.adult_count), adultCount);
        bundle.putInt(getString(R.string.children_count), childrenCount);
        bundle.putInt(getString(R.string.infant_count), infantCount);
        Intent intent = new Intent(this, FlightReviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void flightFilter(boolean isNonStopSelected, boolean isOneStopSelected, boolean isTwoStopSelected, boolean isBeforeElevenAM, boolean isElevenAM, boolean isFivePM, boolean isAfterNinePM, LinkedList<String> listOfCheckNames) {

        isBeforeElevenAMs=isBeforeElevenAM;
        isElevenAMs=isElevenAM;
        isFivePMs=isFivePM;
        isAfterNinePMs=isAfterNinePM;
        isNonStopSelecteds=isNonStopSelected;
        isOneStopSelecteds=isOneStopSelected;
        isTwoStopSelecteds=isTwoStopSelected;

        if (!sourceCountry.equalsIgnoreCase(destinationCountry)) {

            List<OriginDestinationOption> listOfOriginDestinations = new ArrayList<>();
            if (!isNonStopSelected && !isOneStopSelected && !isTwoStopSelected && !isBeforeElevenAM && !isElevenAM && !isFivePM && !isAfterNinePM && listOfCheckNames.size() == 0) {
                this.listOfOriginDestinations.clear();
                this.listOfOriginDestinations.addAll(listOfTempOriginDestinations);
                rvFlightDetails.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
                flightDetailsAdapter.update(this.listOfOriginDestinations);
            } else {
                if (listOfCheckNames != null && listOfCheckNames.size() != 0) {
                    for (int index = 0; index < listOfAllAirwayNames.size(); index++) {
                        String airwayName = listOfAllAirwayNames.get(index);
                        String numberOfStops = listOfAllNumberOfStops.get(index);

                        String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                        String departureTime = departureDateTime[1];
                        Log.e("departuretume5",departureTime);
                        departureTime = departureTime.substring(0, departureTime.length() - 3);
                        OriginDestinationOption originDestinationOption = mapOfAllAirwayNameOriginDestinationOption.get(index);
                        if (listOfCheckNames.contains(airwayName)) {
                            if (isNonStopSelected) {
                                if (Integer.parseInt(numberOfStops) == 0) {
                                    Log.e("number",numberOfStops);

                                    if (isBeforeElevenAM) {
                                        if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isElevenAM) {
                                        if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isFivePM) {
                                        if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isAfterNinePM) {
                                        if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else {
                                        listOfOriginDestinations.add(originDestinationOption);
                                    }
                                }
                            } else if (isOneStopSelected) {
                                if (Integer.parseInt(numberOfStops)>= 1 && Integer.parseInt(numberOfStops)<=2) {
                                    Log.e("number",numberOfStops);

                                    if (isBeforeElevenAM) {
                                        if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isElevenAM) {
                                        if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isFivePM) {
                                        if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isAfterNinePM) {
                                        if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else {
                                        listOfOriginDestinations.add(originDestinationOption);
                                    }
                                }
                            } else if (isTwoStopSelected) {
                                Log.e("number",numberOfStops);

                                if (Integer.parseInt(numberOfStops) >= 3) {
                                    if (isBeforeElevenAM) {
                                        if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isElevenAM) {
                                        if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isFivePM) {
                                        if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isAfterNinePM) {
                                        if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else {
                                        listOfOriginDestinations.add(originDestinationOption);
                                    }
                                }
                            } else {
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        }
                    }
                } else if (listOfAllNumberOfStops != null && listOfAllNumberOfStops.size() != 0) {
                    for (int index = 0; index < listOfAllNumberOfStops.size(); index++) {
                        String numberOfStops = listOfAllNumberOfStops.get(index);
                        Log.e("number",numberOfStops);

                        String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                        String departureTime = departureDateTime[1];
                        Log.e("departuretume4",departureTime);
                        departureTime = departureTime.substring(0, departureTime.length() - 3);
                        OriginDestinationOption originDestinationOption = mapOfAllAirwayNameOriginDestinationOption.get(index);
                        if (isNonStopSelected) {
                            if (Integer.parseInt(numberOfStops) == 0) {
                                Log.e("number",numberOfStops);
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        } else if (isOneStopSelected) {
                            if (Integer.parseInt(numberOfStops)>= 1 && Integer.parseInt(numberOfStops)<=2 ) {
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        } else if (isTwoStopSelected) {
                            if (Integer.parseInt(numberOfStops) >=3) {
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        } else {
                            if (isBeforeElevenAM) {
                                if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);
                            } else if (isElevenAM) {
                                if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);
                            } else if (isFivePM) {
                                if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);
                            } else if (isAfterNinePM) {
                                if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);
                            }else {
                                listOfOriginDestinations.add(originDestinationOption);
                            }
                        }
                    }
                } else if (listOfAllDepartureDateTime != null && listOfAllDepartureDateTime.size() != 0) {
                    for (int index = 0; index < listOfAllDepartureDateTime.size(); index++) {
                        String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                        String departureTime = departureDateTime[1];
                        departureTime = departureTime.substring(0, departureTime.length() - 3);
                        OriginDestinationOption originDestinationOption = mapOfAllAirwayNameOriginDestinationOption.get(index);
                        if (isBeforeElevenAM) {
                            if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else if (isElevenAM) {
                            if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else if (isFivePM) {
                            if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else if (isAfterNinePM) {
                            if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else {
                            listOfOriginDestinations.add(originDestinationOption);
                        }
                    }
                } else {
                    listOfOriginDestinations.addAll(this.listOfOriginDestinations);
                }
                this.listOfOriginDestinations.clear();
                this.listOfOriginDestinations.addAll(listOfOriginDestinations);
                if (this.listOfOriginDestinations.size() != 0) {
                    rvFlightDetails.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                    flightDetailsAdapter.update(this.listOfOriginDestinations);
                } else {
                    rvFlightDetails.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        } else {
            List<com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption> listOfOriginDestinations = new ArrayList<>();
            if (!isNonStopSelected && !isOneStopSelected && !isTwoStopSelected && !isBeforeElevenAM && !isElevenAM && !isFivePM && !isAfterNinePM && listOfCheckNames.size() == 0) {
                this.listOfDomesticOriginDestinationOptions.clear();
                this.listOfDomesticOriginDestinationOptions.addAll(listOfTempDomesticOriginDestinationOptions);
                domesticFlightsSearchRoundTripDepartFragmentAdapter.update(listOfDomesticOriginDestinationOptions);
            } else {
                if (listOfCheckNames != null && listOfCheckNames.size() != 0) {
                    for (int index = 0; index < listOfAllAirwayNames.size(); index++) {
                        String airwayName = listOfAllAirwayNames.get(index);
                        String numberOfStops = listOfAllNumberOfStops.get(index);
                        String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                        String departureTime = departureDateTime[1];
                        departureTime = departureTime.substring(0, departureTime.length() - 3);
                        Log.e("departuretume3",departureTime);
                        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption originDestinationOption = mapOfAllAirwayNameDomesticOriginDestinationOption.get(index);
                        if (listOfCheckNames.contains(airwayName)) {
                            if (isNonStopSelected) {
                                if (Integer.parseInt(numberOfStops) == 0) {
                                    if (isBeforeElevenAM) {
                                        if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isElevenAM) {
                                        if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isFivePM) {
                                        if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isAfterNinePM) {
                                        if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    }
                                }
                            } else if (isOneStopSelected) {
                                if (Integer.parseInt(numberOfStops)>= 1 && Integer.parseInt(numberOfStops)<=2) {
                                    if (isBeforeElevenAM) {
                                        if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isElevenAM) {
                                        if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isFivePM) {
                                        if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isAfterNinePM) {
                                        if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    }
                                }
                            } else if (isTwoStopSelected) {
                                if (Integer.parseInt(numberOfStops) >= 3) {
                                    if (isBeforeElevenAM) {
                                        if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isElevenAM) {
                                        if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isFivePM) {
                                        if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    } else if (isAfterNinePM) {
                                        if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                                            listOfOriginDestinations.add(originDestinationOption);
                                    }
                                }
                            } else {
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        }
                    }
                }
                //selected
                else if (listOfAllNumberOfStops != null && listOfAllNumberOfStops.size() != 0) {
                    for (int index = 0; index < listOfAllNumberOfStops.size(); index++) {
                        String numberOfStops = listOfAllNumberOfStops.get(index);
                        String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                        String departureTime = departureDateTime[1];
                        departureTime = departureTime.substring(0, departureTime.length() - 3);
                        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption originDestinationOption = mapOfAllAirwayNameDomesticOriginDestinationOption.get(index);
                        if (isNonStopSelected) {
                            if (Integer.parseInt(numberOfStops) == 0) {
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        } else if (isOneStopSelected) {
                            if (Integer.parseInt(numberOfStops)>= 1 && Integer.parseInt(numberOfStops)<=2) {
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        } else if (isTwoStopSelected) {
                            if (Integer.parseInt(numberOfStops) >= 3) {
                                if (isBeforeElevenAM) {
                                    if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isElevenAM) {
                                    if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isFivePM) {
                                    if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else if (isAfterNinePM) {
                                    if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                                        listOfOriginDestinations.add(originDestinationOption);
                                } else {
                                    listOfOriginDestinations.add(originDestinationOption);
                                }
                            }
                        } else {
                            if (isBeforeElevenAM) {
                                if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);
                            } else if (isElevenAM) {
                                if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);
                            } else if (isFivePM) {
                                if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);
                            } else if (isAfterNinePM) {
                                if (inBetweenTwoTimes("21:00", "23:55", departureTime))
                                    listOfOriginDestinations.add(originDestinationOption);

                            }
                        }
                    }
                } else if (listOfAllDepartureDateTime != null && listOfAllDepartureDateTime.size() != 0) {
                    for (int index = 0; index < listOfAllDepartureDateTime.size(); index++) {
                        String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                        String departureTime = departureDateTime[1];
                        departureTime = departureTime.substring(0, departureTime.length() - 3);
                        Log.e("departuretume2",departureTime);
                        com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption originDestinationOption = mapOfAllAirwayNameDomesticOriginDestinationOption.get(index);
                        if (isBeforeElevenAM) {
                            if (inBetweenTwoTimes("00:01", "11:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else if (isElevenAM) {
                            if (inBetweenTwoTimes("11:01", "17:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else if (isFivePM) {
                            if (inBetweenTwoTimes("17:01", "21:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else if (isAfterNinePM) {
                            if (inBetweenTwoTimes("21:01", "00:00", departureTime))
                                listOfOriginDestinations.add(originDestinationOption);
                        } else {
                            listOfOriginDestinations.add(originDestinationOption);
                        }
                    }
                } else {
                    listOfOriginDestinations.addAll(this.listOfTempDomesticOriginDestinationOptions);
                }
                this.listOfDomesticOriginDestinationOptions.clear();
                this.listOfDomesticOriginDestinationOptions.addAll(listOfOriginDestinations);

                if (this.listOfDomesticOriginDestinationOptions.size() != 0) {
                    rvFlightDetails.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                    domesticFlightsSearchRoundTripDepartFragmentAdapter.update(listOfOriginDestinations);
                } else {
                    rvFlightDetails.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private boolean inBetweenTwoTimes(String fromDate, String toDate, String arrivalTime) {
        boolean hasSelectedTime = false;
        try {
            Date time1 = new SimpleDateFormat("hh:mm", Locale.getDefault()).parse(fromDate);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = new SimpleDateFormat("hh:mm", Locale.getDefault()).parse(toDate);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            Date d = new SimpleDateFormat("hh:mm", Locale.getDefault()).parse(arrivalTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);



            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                hasSelectedTime = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hasSelectedTime;
    }
}
