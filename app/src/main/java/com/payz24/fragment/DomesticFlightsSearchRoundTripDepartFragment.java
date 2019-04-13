package com.payz24.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payz24.R;
import com.payz24.activities.FlightsFilterActivity;
import com.payz24.adapter.DomesticFlightsSearchRoundTripDepartFragmentAdapter;
import com.payz24.application.AppController;
import com.payz24.interfaces.FlightFilterCallBack;
import com.payz24.interfaces.RoundTripFlightSelectedCallBack;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.FlightSegment;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.OriginDestinationOption;
import com.payz24.responseModels.DomesticFlightsSearchRoundTrip.ResponseDepart;
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

/**
 * Created by mahesh on 2/13/2018.
 */

public class DomesticFlightsSearchRoundTripDepartFragment extends Fragment implements DomesticFlightsSearchRoundTripDepartFragmentAdapter.ItemClickListener, FlightFilterCallBack, View.OnClickListener {

    private RecyclerView rvFlightDetails;
    private TextView tvError;
    private List<OriginDestinationOption> listOfOriginDestinationOption;
    private List<OriginDestinationOption> listOfTempOriginDestinationOption;

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

    private View previousView;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    public ImageView ivDepartFilter;
    public ImageView ivReturnFilter;
    private DomesticFlightsSearchRoundTripDepartFragmentAdapter domesticFlightsSearchRoundTripDepartFragmentAdapter;


    private boolean isBeforeElevenAMs=false;
    private boolean isElevenAMs=false;
    private boolean isFivePMs=false;
    private boolean isAfterNinePMs=false;
    private boolean isNonStopSelecteds=false;
    private boolean isOneStopSelecteds=false;
    private boolean isTwoStopSelecteds=false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_domestic_flights_search_round_trip_fragment, container, false);
        initializeUi(view);
        initializeListeners();
        prepareResult();
        prepareListOfFlightNames();
        initializeAdapter();
        return view;
    }

    private void initializeUi(View view) {
        rvFlightDetails = view.findViewById(R.id.rvFlightDetails);
        tvError = view.findViewById(R.id.tvError);
        listOfOriginDestinationOption = new ArrayList<>();
        listOfTempOriginDestinationOption = new ArrayList<>();
        AppController.getInstance().setDomesticFlightsSearchRoundTripDepartFragmentContext(this);
    }

    private void initializeListeners() {
        ivDepartFilter.setOnClickListener(this);
    }

    private void prepareResult() {
        ResponseDepart responseDepart = AppController.getInstance().getResponseDepart();
        if (responseDepart != null) {
            rvFlightDetails.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);
            listOfOriginDestinationOption = responseDepart.getOriginDestinationOptions().getOriginDestinationOption();
            listOfTempOriginDestinationOption.addAll(listOfOriginDestinationOption);
        } else {
            rvFlightDetails.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        }
    }

    private void prepareListOfFlightNames() {
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
        for (int index = 0; index < listOfTempOriginDestinationOption.size(); index++) {
            OriginDestinationOption originDestinationOption = listOfOriginDestinationOption.get(index);
            String fare = originDestinationOption.getFareDetails().getChargeableFares().getActualBaseFare();
            String tax = originDestinationOption.getFareDetails().getChargeableFares().getTax();
            int totalFare = Integer.parseInt(fare) + Integer.parseInt(tax);
            JsonObject jsonObject = originDestinationOption.getFlightSegments().getAsJsonObject();
            try {
                JSONObject flightSegmentJsonObject = new JSONObject(jsonObject.toString());
                Object json = new JSONTokener(flightSegmentJsonObject.getString("FlightSegment")).nextValue();
                String departureDateTime;
                String airLineCode;
                String airwayName;
                String imageUrl;
                String numberOfStops;
                if (json instanceof JSONObject) {
                    FlightSegment flightSegment = new Gson().fromJson(flightSegmentJsonObject.getString("FlightSegment"), FlightSegment.class);
                    departureDateTime = flightSegment.getDepartureDateTime();
                    airLineCode = flightSegment.getOperatingAirlineCode();
                    airwayName = flightSegment.getAirLineName();
                    imageUrl = Constants.IMAGE_URL_BASE + "" + airLineCode + ".gif";
                    numberOfStops = flightSegment.getStopQuantity();
                    listOfAllAirwayNames.add(airwayName);
                    listOfAllAirwayImages.add(imageUrl);
                    listOfAllAirwayFares.add(fare);
                    listOfAllDepartureDateTime.add(departureDateTime);
                    listOfAllNumberOfStops.add("0");
                    mapOfAllAirwayNameOriginDestinationOption.put(index, originDestinationOption);

                    if (!listOfAirwayNames.contains(airwayName)) {
                        listOfAirwayNames.add(airwayName);
                        listOfAirwayImages.add(imageUrl);
                        listOfAirwayFares.add(fare);
                        listOfDepartureDateTime.add(departureDateTime);
                        listOfNumberOfStops.add("0");
                    }
                } else if (json instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(flightSegmentJsonObject.getString("FlightSegment"));
                    String response = jsonArray.getString(0);
                    FlightSegment flightSegment = new Gson().fromJson(response, FlightSegment.class);
                    departureDateTime = flightSegment.getDepartureDateTime();
                    airLineCode = flightSegment.getOperatingAirlineCode();
                    airwayName = flightSegment.getAirLineName();
                    numberOfStops = String.valueOf(jsonArray.length() - 1);
                    imageUrl = Constants.IMAGE_URL_BASE + "" + airLineCode + ".gif";
                    listOfAllAirwayNames.add(airwayName);
                    listOfAllAirwayImages.add(imageUrl);
                    listOfAllAirwayFares.add(fare);
                    listOfAllDepartureDateTime.add(departureDateTime);
                    listOfAllNumberOfStops.add(numberOfStops);
                    mapOfAllAirwayNameOriginDestinationOption.put(index, originDestinationOption);

                    if (!listOfAirwayNames.contains(airwayName)) {
                        listOfAirwayNames.add(airwayName);
                        listOfAirwayImages.add(imageUrl);
                        listOfAirwayFares.add(fare);
                        listOfDepartureDateTime.add(departureDateTime);
                        listOfNumberOfStops.add(numberOfStops);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void initializeAdapter() {
        domesticFlightsSearchRoundTripDepartFragmentAdapter = new DomesticFlightsSearchRoundTripDepartFragmentAdapter(getActivity(), listOfOriginDestinationOption, rvFlightDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        domesticFlightsSearchRoundTripDepartFragmentAdapter.setClickListener(this);
        rvFlightDetails.setLayoutManager(layoutManager);
        rvFlightDetails.setItemAnimator(new DefaultItemAnimator());
        rvFlightDetails.setAdapter(domesticFlightsSearchRoundTripDepartFragmentAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        if (previousView != null) {
            previousView.setBackgroundColor(getResources().getColor(R.color.white));
        }
        viewPager.setCurrentItem(1);
        tabLayout.getTabAt(1).select();
        OriginDestinationOption originDestinationOption = listOfOriginDestinationOption.get(position);
        RoundTripFlightSelectedCallBack roundTripFlightSelectedCallBack = (RoundTripFlightSelectedCallBack) AppController.getInstance().getDomesticFlightsSearchRoundTripActivityContext();
        roundTripFlightSelectedCallBack.roundTripFlightSelected(originDestinationOption, getString(R.string.depart));
        previousView = view;
        view.setBackgroundColor(getResources().getColor(R.color.light_blue));
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
            this.listOfOriginDestinationOption.clear();
            this.listOfOriginDestinationOption.addAll(listOfTempOriginDestinationOption);
            domesticFlightsSearchRoundTripDepartFragmentAdapter.update(listOfOriginDestinations);
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
                listOfOriginDestinations.addAll(this.listOfTempOriginDestinationOption);
            }
            this.listOfOriginDestinationOption.clear();
            this.listOfOriginDestinationOption.addAll(listOfOriginDestinations);
            if (listOfOriginDestinations.size() != 0) {
                rvFlightDetails.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
            } else {
                rvFlightDetails.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
            }
            domesticFlightsSearchRoundTripDepartFragmentAdapter.update(listOfOriginDestinations);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivDepartFilter:
                Intent flightsFilterIntent = new Intent(getActivity(), FlightsFilterActivity.class);
                AppController.getInstance().setListOfAirwayNames(listOfAirwayNames);
                AppController.getInstance().setListOfAirwayImages(listOfAirwayImages);
                AppController.getInstance().setListOfAirwayFares(listOfAirwayFares);
                flightsFilterIntent.putExtra(getString(R.string.from), getString(R.string.domestic_flight_search_round_trip_depart));
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
}
