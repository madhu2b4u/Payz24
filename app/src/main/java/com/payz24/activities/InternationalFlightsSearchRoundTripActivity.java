package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.adapter.InternationalFlightsSearchRoundTripAdapter;
import com.payz24.application.AppController;
import com.payz24.http.InternationalFlightsSearchRoundTripHttpClient;
import com.payz24.http.SearchDomesticFlightsHttpClient;
import com.payz24.http.SearchFlightsHttpClient;
import com.payz24.interfaces.FlightFilterCallBack;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightSearchResults.FlightSearchResults;
import com.payz24.responseModels.flightSearchResults.FlightSegment;
import com.payz24.responseModels.flightSearchResults.OriginDestinationOption;
import com.payz24.responseModels.flightSearchResults.Result;
import com.payz24.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;
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
 * Created by mahesh on 2/17/2018.
 */

public class InternationalFlightsSearchRoundTripActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack, InternationalFlightsSearchRoundTripAdapter.ItemClickListener, FlightFilterCallBack {

    private ImageView ivBack;
    private TextView tvSource, tvDestination, tvDetails, tvCancel;
    private ImageView ivFlightImage;
    private LinearLayout llLoading, llFlightsDetails;
    private RecyclerView rvFlightDetails;
    private TextView tvError;
    private ImageView ivFilter;
    private Map<String, String> mapOfRequestParams;
    private String sourceFullName = "";
    private String destinationFullName = "";
    private String departSelectedDate = "";
    private String returnSelectedDate = "";
    private String sourceCountry = "";
    private String destinationCountry = "";
    private int adultCount = 0;
    private int childrenCount = 0;
    private int infantCount = 0;
    private List<OriginDestinationOption> listOfOriginDestinations;
    private List<OriginDestinationOption> listOfTempOriginDestinations;
    private String departureAirLineCode = "";
    private String departureAirwayName = "";


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
    private InternationalFlightsSearchRoundTripAdapter internationalFlightsSearchRoundTripAdapter;

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
        setContentView(R.layout.layout_international_flights_search_round_trip);
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
            returnSelectedDate = bundle.getString(getString(R.string.selected_return_date));
            sourceCountry = bundle.getString(getString(R.string.source_country));
            destinationCountry = bundle.getString(getString(R.string.destination_country));
            adultCount = bundle.getInt(getString(R.string.adult_count));
            childrenCount = bundle.getInt(getString(R.string.children_count));
            infantCount = bundle.getInt(getString(R.string.infant_count));
        }
    }

    private void initializeUi() {
        ivBack = findViewById(R.id.ivBack);
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvDetails = findViewById(R.id.tvDetails);
        tvCancel = findViewById(R.id.tvCancel);
        ivFlightImage = findViewById(R.id.ivFlightImage);
        ivFilter = findViewById(R.id.ivFilter);
        llLoading = findViewById(R.id.llLoading);
        llFlightsDetails = findViewById(R.id.llFlightsDetails);
        rvFlightDetails = findViewById(R.id.rvFlightDetails);
        tvError = findViewById(R.id.tvError);

        Glide.with(this).load(R.drawable.ic_flight_loading).into(ivFlightImage);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(departSelectedDate).append(" - ").append(returnSelectedDate).append(" | ");
        if (adultCount != 0) {
            stringBuilder.append(String.valueOf(adultCount)).append(" ").append(getString(R.string.adult));
        }
        if (childrenCount != 0) {
            stringBuilder.append(String.valueOf(childrenCount)).append(" ").append(getString(R.string.child));
        }
        if (infantCount != 0) {
            stringBuilder.append(String.valueOf(infantCount)).append(" ").append(getString(R.string.infant));
        }
        tvSource.setText(sourceCountry);
        tvDestination.setText(destinationCountry);
        tvDetails.setText(stringBuilder.toString());

        listOfTempOriginDestinations = new ArrayList<>();
        listOfAirwayNames = new LinkedList<>();
        listOfAirwayImages = new LinkedList<>();
        listOfAirwayFares = new LinkedList<>();
        listOfNumberOfStops = new LinkedList<>();
        listOfDepartureDateTime = new LinkedList<>();

        listOfAllAirwayNames = new LinkedList<>();
        listOfAllAirwayImages = new LinkedList<>();
        listOfAllAirwayFares = new LinkedList<>();
        listOfAllNumberOfStops = new LinkedList<>();
        listOfAllDepartureDateTime = new LinkedList<>();
        mapOfAllAirwayNameOriginDestinationOption = new LinkedHashMap<>();

        AppController.getInstance().setInternationalFlightsSearchRoundTripActivityContext(this);
    }

    private void initializeListeners() {
        ivBack.setOnClickListener(this);
        ivFilter.setOnClickListener(this);
    }

    private void serviceCallForFlightsResults() {
        InternationalFlightsSearchRoundTripHttpClient internationalFlightsSearchRoundTripHttpClient = new InternationalFlightsSearchRoundTripHttpClient(this);
        internationalFlightsSearchRoundTripHttpClient.callBack = this;
        internationalFlightsSearchRoundTripHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivFilter:
                Intent flightsFilterIntent = new Intent(this, FlightsFilterActivity.class);
                AppController.getInstance().setListOfAirwayNames(listOfAirwayNames);
                AppController.getInstance().setListOfAirwayImages(listOfAirwayImages);
                AppController.getInstance().setListOfAirwayFares(listOfAirwayFares);
                flightsFilterIntent.putExtra(getString(R.string.from), getString(R.string.international_round_trip));
                flightsFilterIntent.putExtra("isBeforeElevenAMs",isBeforeElevenAMs);
                flightsFilterIntent.putExtra("isElevenAMs",isElevenAMs);
                flightsFilterIntent.putExtra("isFivePMs",isFivePMs);
                flightsFilterIntent.putExtra("isAfterNinePMs",isAfterNinePMs);
                flightsFilterIntent.putExtra("isNonStopSelecteds",isNonStopSelecteds);
                flightsFilterIntent.putExtra("isOneStopSelecteds",isOneStopSelecteds);
                flightsFilterIntent.putExtra("isTwoStopSelecteds",isTwoStopSelecteds);

                startActivity(flightsFilterIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_INTERNATIONAL_SEARCH_FLIGHTS_ROUND_TRIP:
                if (jsonResponse != null && !jsonResponse.isEmpty()) {
                    try {
                        FlightSearchResults flightSearchResults = new Gson().fromJson(jsonResponse, FlightSearchResults.class);
                        String status = flightSearchResults.getStatus();
                        AppController.getInstance().setAvailRequest(flightSearchResults.getResult().getAvailRequest());
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            Result result = flightSearchResults.getResult();
                            listOfOriginDestinations = result.getAvailResponse().getOriginDestinationOptions().getOriginDestinationOption();
                            if (listOfOriginDestinations != null && listOfOriginDestinations.size() != 0) {
                                listOfTempOriginDestinations.addAll(listOfOriginDestinations);
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
        }
    }

    private void prepareListOfFlightNames(List<OriginDestinationOption> listOfOriginDestinations) {
        listOfAirwayNames = new LinkedList<>();
        listOfAirwayImages = new LinkedList<>();
        listOfAirwayFares = new LinkedList<>();
        listOfNumberOfStops = new LinkedList<>();
        listOfDepartureDateTime = new LinkedList<>();

        listOfAllAirwayNames = new LinkedList<>();
        listOfAllAirwayImages = new LinkedList<>();
        listOfAllAirwayFares = new LinkedList<>();
        listOfAllNumberOfStops = new LinkedList<>();
        listOfAllDepartureDateTime = new LinkedList<>();
        mapOfAllAirwayNameOriginDestinationOption = new LinkedHashMap<>();

        for (int index = 0; index < listOfOriginDestinations.size(); index++) {
            OriginDestinationOption originDestinationOption = listOfOriginDestinations.get(index);
            JsonObject jsonObject = listOfOriginDestinations.get(index).getOnward().flightSegments.getAsJsonObject();
            String returnJsonObject = "";
            if (listOfOriginDestinations.get(index).getReturnJsonObject() instanceof JSONArray) {

            } else {
                Gson gson = new Gson();
                returnJsonObject = gson.toJson(listOfOriginDestinations.get(index).getReturnJsonObject());
            }
            String fare = listOfOriginDestinations.get(index).getFareDetails().getActualBaseFare();
            try {
                JSONObject jo2 = new JSONObject(jsonObject.toString());
                JSONObject returnJsonObj = new JSONObject(returnJsonObject);
                JSONObject flightsSegmentsJsonObject = new JSONObject(returnJsonObj.getString("FlightSegments"));
                Object json = new JSONTokener(jo2.getString("FlightSegment")).nextValue();
                Object jsonReturn = new JSONTokener(flightsSegmentsJsonObject.getString("FlightSegment")).nextValue();
                if (json instanceof JSONObject) {
                    FlightSegment flightSegment = new Gson().fromJson(jo2.getString("FlightSegment"), FlightSegment.class);
                    departureAirLineCode = flightSegment.getOperatingAirlineCode();
                    if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {
                        departureAirwayName = flightSegment.getOperatingAirlineName().toString();
                        String departureDateTime = flightSegment.getDepartureDateTime();
                        String numberOfStops = "0";
                        String imageUrl = Constants.IMAGE_URL_BASE + "" + departureAirLineCode + ".gif";
                        if (!listOfAirwayNames.contains(departureAirwayName)) {
                            listOfAirwayNames.add(departureAirwayName);
                            listOfAirwayImages.add(imageUrl);
                            listOfAirwayFares.add(fare);
                            listOfDepartureDateTime.add(departureDateTime);
                            listOfNumberOfStops.add(numberOfStops);
                        }

                        listOfAllAirwayNames.add(departureAirwayName);
                        listOfAllAirwayImages.add(imageUrl);
                        listOfAllAirwayFares.add(fare);
                        listOfAllDepartureDateTime.add(departureDateTime);
                        listOfAllNumberOfStops.add(numberOfStops);
                        mapOfAllAirwayNameOriginDestinationOption.put(index, originDestinationOption);
                    } else {
                        departureAirwayName = flightSegment.getOperatingAirlineName().toString();
                        String departureDateTime = flightSegment.getDepartureDateTime();
                        String numberOfStops = flightSegment.getNumStops();
                        numberOfStops = "0";
                        String imageUrl = Constants.IMAGE_URL_BASE + "" + departureAirLineCode + ".gif";
                        if (!listOfAirwayNames.contains(departureAirwayName)) {
                            listOfAirwayNames.add(departureAirwayName);
                            listOfAirwayImages.add(imageUrl);
                            listOfAirwayFares.add(fare);
                            listOfDepartureDateTime.add(departureDateTime);
                            listOfNumberOfStops.add(numberOfStops);
                        }

                        listOfAllAirwayNames.add(departureAirwayName);
                        listOfAllAirwayImages.add(imageUrl);
                        listOfAllAirwayFares.add(fare);
                        listOfAllDepartureDateTime.add(departureDateTime);
                        listOfAllNumberOfStops.add(numberOfStops);
                        mapOfAllAirwayNameOriginDestinationOption.put(index, originDestinationOption);
                    }
                } else if (json instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(jo2.getString("FlightSegment"));
                    String response = jsonArray.getString(0);
                    FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                    departureAirLineCode = flightSegment.getOperatingAirlineCode();
                    if (flightSegment.getOperatingAirlineName() instanceof JSONArray) {
                        departureAirwayName = "";
                    } else {
                        departureAirwayName = flightSegment.getOperatingAirlineName().toString();
                    }

                    String departureDateTime = flightSegment.getDepartureDateTime();
                    String numberOfStops = String.valueOf(jsonArray.length() - 1);
                    String imageUrl = Constants.IMAGE_URL_BASE + "" + departureAirLineCode + ".gif";
                    if (!listOfAirwayNames.contains(departureAirwayName)) {
                        listOfAirwayNames.add(departureAirwayName);
                        listOfAirwayImages.add(imageUrl);
                        listOfAirwayFares.add(fare);
                        listOfDepartureDateTime.add(departureDateTime);
                        listOfNumberOfStops.add(numberOfStops);
                    }

                    listOfAllAirwayNames.add(departureAirwayName);
                    listOfAllAirwayImages.add(imageUrl);
                    listOfAllAirwayFares.add(fare);
                    listOfAllDepartureDateTime.add(departureDateTime);
                    listOfAllNumberOfStops.add(numberOfStops);
                    mapOfAllAirwayNameOriginDestinationOption.put(index, originDestinationOption);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void initializeAdapter(List<OriginDestinationOption> listOfOriginDestinations) {
        internationalFlightsSearchRoundTripAdapter = new InternationalFlightsSearchRoundTripAdapter(this, listOfOriginDestinations);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvFlightDetails.setLayoutManager(layoutManager);
        internationalFlightsSearchRoundTripAdapter.setClickListener(this);
        rvFlightDetails.setAdapter(internationalFlightsSearchRoundTripAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        AppController.getInstance().setSelectedOriginDestinationOption(listOfOriginDestinations.get(position));
        Intent internationalFlightRoundTripReviewIntent = new Intent(this, InternationalFlightRoundTripReviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.origin), sourceFullName);
        bundle.putString(getString(R.string.destination), destinationFullName);
        bundle.putString(getString(R.string.selected_depature_date), departSelectedDate);
        bundle.putString(getString(R.string.selected_return_date), returnSelectedDate);
        bundle.putInt(getString(R.string.adult_count), adultCount);
        bundle.putInt(getString(R.string.children_count), childrenCount);
        bundle.putInt(getString(R.string.infant_count), infantCount);
        internationalFlightRoundTripReviewIntent.putExtras(bundle);
        startActivity(internationalFlightRoundTripReviewIntent);
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

        List<OriginDestinationOption> listOfOriginDestinations = new ArrayList<>();
        if (!isNonStopSelected && !isOneStopSelected && !isTwoStopSelected && !isBeforeElevenAM && !isElevenAM && !isFivePM && !isAfterNinePM && listOfCheckNames.size() == 0) {
            this.listOfOriginDestinations.clear();
            this.listOfOriginDestinations.addAll(listOfTempOriginDestinations);
            rvFlightDetails.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            internationalFlightsSearchRoundTripAdapter.update(this.listOfOriginDestinations);
        } else {
            if (listOfCheckNames != null && listOfCheckNames.size() != 0) {
                for (int index = 0; index < listOfAllAirwayNames.size(); index++) {
                    String airwayName = listOfAllAirwayNames.get(index);
                    String numberOfStops = listOfAllNumberOfStops.get(index);
                    String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                    String departureTime = departureDateTime[1];
                    departureTime = departureTime.substring(0, departureTime.length() - 3);
                    OriginDestinationOption originDestinationOption = mapOfAllAirwayNameOriginDestinationOption.get(index);
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
                                if (inBetweenTwoTimes("21:01", "23:55", departureTime))
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
                    String[] departureDateTime = listOfAllDepartureDateTime.get(index).split("T");
                    String departureTime = departureDateTime[1];
                    departureTime = departureTime.substring(0, departureTime.length() - 3);
                    OriginDestinationOption originDestinationOption = mapOfAllAirwayNameOriginDestinationOption.get(index);
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
                            if (inBetweenTwoTimes("21:01", "23:55", departureTime))
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
                        if (inBetweenTwoTimes("21:01", "23:55", departureTime))
                            listOfOriginDestinations.add(originDestinationOption);
                    } else {
                        listOfOriginDestinations.add(originDestinationOption);
                    }
                }
            } else {
                listOfOriginDestinations.addAll(this.listOfTempOriginDestinations);
            }
            this.listOfOriginDestinations.clear();
            this.listOfOriginDestinations.addAll(listOfOriginDestinations);
            if (this.listOfOriginDestinations.size() != 0) {
                rvFlightDetails.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
                internationalFlightsSearchRoundTripAdapter.update(this.listOfOriginDestinations);
            } else {
                rvFlightDetails.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
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
