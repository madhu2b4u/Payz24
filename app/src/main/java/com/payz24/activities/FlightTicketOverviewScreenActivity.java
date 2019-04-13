package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.http.FlightTicketOverviewScreenHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightTicketOverviewScreen.FlightTicketOverviewScreen;
import com.payz24.responseModels.flightTicketOverviewScreen.Onward;
import com.payz24.responseModels.flightTicketOverviewScreen.PassengerDetail;
import com.payz24.responseModels.flightTicketOverviewScreen.Result;
import com.payz24.responseModels.flightTicketOverviewScreen.Return;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mahesh on 3/9/2018.
 */

public class FlightTicketOverviewScreenActivity extends BaseActivity implements HttpReqResCallBack {

    private TextView tvLeavingFromValue, tvGoingToValue;
    private TextView tvBookingId, tvOnwardOperatorName, tvReturnOperatorName, tvDepartureDate, tvArrivalDate, tvSelectedTrip;
    private LinearLayout llPassengerDetailsContainer, llReturnOperatorName, llReturn;
    private String fid = "";
    private String onwardFlightOperatorName = "";
    private String returnFlightOperatorName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flight_ticket_overview_screen);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        serviceCallForFlightTicketOverview();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            fid = intent.getStringExtra(Constants.FLIGHT_TICKET_OVER_VIEW_SCREEN_FID);
        }
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.ticket_over_view));
        setSupportActionBar(toolbar);

        llPassengerDetailsContainer = findViewById(R.id.llPassengerDetailsContainer);
        llReturnOperatorName = findViewById(R.id.llReturnOperatorName);
        llReturn = findViewById(R.id.llReturn);
        tvBookingId = findViewById(R.id.tvBookingId);
        tvLeavingFromValue = findViewById(R.id.tvLeavingFromValue);
        tvGoingToValue = findViewById(R.id.tvGoingToValue);
        tvOnwardOperatorName = findViewById(R.id.tvOnwardOperatorName);
        tvReturnOperatorName = findViewById(R.id.tvReturnOperatorName);
        tvDepartureDate = findViewById(R.id.tvDepartureDate);
        tvArrivalDate = findViewById(R.id.tvArrivalDate);
        tvSelectedTrip = findViewById(R.id.tvSelectedTrip);
    }

    private void serviceCallForFlightTicketOverview() {
        String userId = PreferenceConnector.readString(this,getString(R.string.user_id),"");
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.FLIGHT_BOOKING_HISTORY_PARAM_USER_ID, userId);
        mapOfRequestParams.put(Constants.FLIGHT_TICKET_OVER_VIEW_SCREEN_FID, fid);

        FlightTicketOverviewScreenHttpClient flightTicketOverviewScreenHttpClient = new FlightTicketOverviewScreenHttpClient(this);
        flightTicketOverviewScreenHttpClient.callBack = this;
        flightTicketOverviewScreenHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_FLIGHT_TICKET_OVERVIEW:
                if (jsonResponse != null) {
                    FlightTicketOverviewScreen flightTicketOverviewScreen = new Gson().fromJson(jsonResponse, FlightTicketOverviewScreen.class);
                    if (flightTicketOverviewScreen != null) {
                        List<Result> listOfResults = flightTicketOverviewScreen.getResult();
                        for (int index = 0; index < listOfResults.size(); index++) {
                            String pnr = listOfResults.get(index).getPnr();
                            String source = listOfResults.get(index).getFromCity();
                            String destination = listOfResults.get(index).getToCity();
                            String departureDate = listOfResults.get(index).getDepartDate();
                            String arrivalDate = listOfResults.get(index).getReturnDate();
                            String journeyType = listOfResults.get(index).getJourneyType();
                            if (journeyType.equalsIgnoreCase("r")) {
                                llReturn.setVisibility(View.VISIBLE);
                                llReturnOperatorName.setVisibility(View.VISIBLE);
                                tvSelectedTrip.setText(getString(R.string.round_trip));
                            } else {
                                llReturn.setVisibility(View.GONE);
                                llReturnOperatorName.setVisibility(View.GONE);
                                tvSelectedTrip.setText(getString(R.string.one_way));
                            }
                            List<Onward> listOfOnwardResults = listOfResults.get(index).getOnward();
                            List<Return> listOfReturnResults = new ArrayList<>();
                            if (listOfResults.get(index).getReturn() != null) {
                                listOfReturnResults = listOfResults.get(index).getReturn();
                                for (int returnIndex = 0; returnIndex < listOfReturnResults.size(); returnIndex++) {
                                    Return returnTrip = listOfReturnResults.get(returnIndex);
                                    returnFlightOperatorName = returnTrip.getFlightName();
                                }
                            }
                            for (int onwardIndex = 0; onwardIndex < listOfOnwardResults.size(); onwardIndex++) {
                                Onward onward = listOfOnwardResults.get(onwardIndex);
                                onwardFlightOperatorName = onward.getFlightName();
                            }
                            tvBookingId.setText(pnr);
                            tvLeavingFromValue.setText(source);
                            tvGoingToValue.setText(destination);
                            tvDepartureDate.setText(departureDate);
                            tvOnwardOperatorName.setText(onwardFlightOperatorName);
                            tvReturnOperatorName.setText(returnFlightOperatorName);
                            tvArrivalDate.setText(arrivalDate);
                            List<PassengerDetail> listOfPassengerDetails = listOfResults.get(index).getPassengerDetails();
                            for (int passengerDetailsIndex = 0; passengerDetailsIndex < listOfPassengerDetails.size(); passengerDetailsIndex++) {
                                initializePassengerView(listOfPassengerDetails.get(passengerDetailsIndex).getTitle()+" "+
                                        listOfPassengerDetails.get(passengerDetailsIndex).getFirstName() + " " +
                                        listOfPassengerDetails.get(passengerDetailsIndex).getLastName(),
                                        listOfPassengerDetails.get(passengerDetailsIndex).getType());
                            }
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void initializePassengerView(String name, String seatNumber) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_passenger_details_items, null);
        TextView tvPassengerSeatDetails = view.findViewById(R.id.tvPassengerSeatDetails);
        TextView tvPassengerSeatNumber = view.findViewById(R.id.tvPassengerSeatNumber);
        tvPassengerSeatDetails.setText(name);
        tvPassengerSeatNumber.setText(toCamelCase(seatNumber));
        llPassengerDetailsContainer.addView(view);
    }

    public static String toCamelCase(final String init) {
        if (init==null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length()==init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }
}
