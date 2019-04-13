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
import com.payz24.http.BusTicketOverViewHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.individualTicketOverview.IndividualTicketOverView;
import com.payz24.responseModels.individualTicketOverview.PassengerDetail;
import com.payz24.responseModels.individualTicketOverview.Result;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mahesh on 3/9/2018.
 */

public class BusTicketOverviewScreenActivity extends BaseActivity implements HttpReqResCallBack {

    private LinearLayout llBookingInformation, llBookReturn, llJourneyDetails, llPassengerDetailsContainer, llPaymentDetails;
    private TextView tvBookingId, tvBookReturn, tvSubmit,tvJdate;
    private TextView tvLeavingFromValue, tvGoingToValue, tvOperatorName, tvBusTypeValue, tvBoardingPointValue, tvDepartureDateTime, tvNumberOfSeats;
    private TextView tvPassengerSeatDetails, tvPassengerSeatNumber, tvContinue, tvNetPayableValue;
    private NetworkDetection networkDetection;
    private String bid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_ticket_overview_screen);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        serviceCallForBusTicketOverView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            bid = intent.getStringExtra(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID);
        }
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.ticket_over_view));
        setSupportActionBar(toolbar);

        llBookingInformation = findViewById(R.id.llBookingInformation);
        llBookReturn = findViewById(R.id.llBookReturn);
        tvJdate = findViewById(R.id.tvJdate);
        tvBookingId = findViewById(R.id.tvBookingId);
        tvBookReturn = findViewById(R.id.tvBookReturn);
       // tvSubmit = findViewById(R.id.tvSubmit);
        llJourneyDetails = findViewById(R.id.llJourneyDetails);
        llPassengerDetailsContainer = findViewById(R.id.llPassengerDetailsContainer);
        llPaymentDetails = findViewById(R.id.llPaymentDetails);
        tvLeavingFromValue = findViewById(R.id.tvLeavingFromValue);
        tvGoingToValue = findViewById(R.id.tvGoingToValue);
        tvOperatorName = findViewById(R.id.tvOperatorName);
        tvBusTypeValue = findViewById(R.id.tvBusTypeValue);
        tvBoardingPointValue = findViewById(R.id.tvBoardingPointValue);
        tvDepartureDateTime = findViewById(R.id.tvDepartureDateTime);
        tvNumberOfSeats = findViewById(R.id.tvNumberOfSeats);

        //tvBookingId.setText(blockTicketKey);

        networkDetection = new NetworkDetection();
    }

    private void serviceCallForBusTicketOverView() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            String userId = PreferenceConnector.readString(this,getString(R.string.user_id),"");
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID, userId);
            mapOfRequestParams.put(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID, bid);

            BusTicketOverViewHttpClient busTicketOverViewHttpClient = new BusTicketOverViewHttpClient(this);
            busTicketOverViewHttpClient.callBack = this;
            busTicketOverViewHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_BUS_TICKET_OVER_VIEW:
                if (jsonResponse != null) {
                    IndividualTicketOverView individualTicketOverView = new Gson().fromJson(jsonResponse, IndividualTicketOverView.class);
                    if (individualTicketOverView != null) {
                        String status = individualTicketOverView.getStatus();
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            List<Result> listOfResults = individualTicketOverView.getResult();
                            if (listOfResults != null && listOfResults.size() != 0) {
                                for (int index = 0; index < listOfResults.size(); index++) {
                                    Result result = listOfResults.get(index);
                                    String source = result.getSourceCity();
                                    String destination = result.getDestinationCity();
                                    String operatorName = result.getServiceProvider();
                                    String busType = result.getBusType();
                                    String boardingPoint = result.getBoardingPoint();
                                    String departureDate = result.getJourneyDate();
                                    String departuretime = result.getDepartureTime();
                                    String pnr = result.getPnr();
                                    tvBookingId.setText(pnr);
                                    List<PassengerDetail> listOfPassengerDetails = result.getPassengerDetails();
                                    String numberOfSeats = String.valueOf(listOfPassengerDetails.size());
                                    for (int passengerDetailsIndex = 0; passengerDetailsIndex < listOfPassengerDetails.size(); passengerDetailsIndex++) {
                                        initializePassengerView(listOfPassengerDetails.get(passengerDetailsIndex).getName() + " , " + listOfPassengerDetails.get(passengerDetailsIndex).getAge(), listOfPassengerDetails.get(passengerDetailsIndex).getSeatno());
                                    }
                                    tvLeavingFromValue.setText(source);
                                    tvGoingToValue.setText(destination);
                                    tvOperatorName.setText(operatorName);
                                    tvBusTypeValue.setText(busType);
                                    tvBoardingPointValue.setText(boardingPoint);
                                    tvDepartureDateTime.setText(departuretime);
                                    tvJdate.setText(departureDate);
                                    tvNumberOfSeats.setText(numberOfSeats);
                                }
                            } else {
                                Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
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
        tvPassengerSeatDetails = view.findViewById(R.id.tvPassengerSeatDetails);
        tvPassengerSeatNumber = view.findViewById(R.id.tvPassengerSeatNumber);
        tvPassengerSeatDetails.setText(name);
        tvPassengerSeatNumber.setText(seatNumber);
        llPassengerDetailsContainer.addView(view);
    }
}
