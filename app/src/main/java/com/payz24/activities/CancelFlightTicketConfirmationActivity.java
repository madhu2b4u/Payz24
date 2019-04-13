package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.payz24.R;
import com.payz24.activities.recharge.RechargeResponseActivity;
import com.payz24.application.AppController;
import com.payz24.http.SmsHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightBookingHistory.Result;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahesh on 3/29/2018.
 */

public class CancelFlightTicketConfirmationActivity extends BaseActivity implements HttpReqResCallBack {

    private TextView tvOrigin, tvDestination, tvBookingDate, tvJourneyType, tvTicketFare, tvPnr;
    private NetworkDetection networkDetection;
    private Result result;
    private String journeyType = "";
    private String selectedRadioButtonText = "";
    private String origin = "";
    private String destination = "";
    private String bookedDate = "";
    private String pnr = "";
    private String flightType = "";
    private String radioButtonText = "";
    Button rHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        prepareResult();
    }

    private void getDataFromIntent() {
        result = AppController.getInstance().getSelectedFlightBookingHistory();
    }


    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.flight_cancellation_details));
        setSupportActionBar(toolbar);

        tvOrigin = findViewById(R.id.tvOrigin);
        tvDestination = findViewById(R.id.tvDestination);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvJourneyType = findViewById(R.id.tvJourneyType);
        tvTicketFare = findViewById(R.id.tvTicketFare);

        tvPnr = findViewById(R.id.tvPnr);
        networkDetection = new NetworkDetection();
    }

    private void prepareResult() {
        origin = result.getFromCity();
        destination = result.getToCity();
        bookedDate = result.getBookedDate();
        //String ticketFare = result.get
        pnr = result.getPnr();
        journeyType = result.getJourneyType();
        flightType = result.getFlightType();
        if (journeyType.equalsIgnoreCase("O")) {
            journeyType = getString(R.string.one_way);
        } else {
            journeyType = getString(R.string.round_trip);
        }
        tvOrigin.setText(origin);
        tvDestination.setText(destination);
        tvBookingDate.setText(bookedDate);
        tvPnr.setText(pnr);
        tvJourneyType.setText(journeyType);

        serviceCallForSmsCancel();
    }

    private void serviceCallForSmsCancel() {
        String message = "Thanks for using Flight facilites and your cancel request was successful and status" +
                "pending for the PNR id" + pnr + " " + " Your refund request is under pending with id " + result.getPaymentTransectionId();
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SMS_PARAM_PHONE, result.getUserMobile());
        mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, message);
        SmsHttpClient smsHttpClient = new SmsHttpClient(this);
        smsHttpClient.callBack = this;
        smsHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {

    }
}
