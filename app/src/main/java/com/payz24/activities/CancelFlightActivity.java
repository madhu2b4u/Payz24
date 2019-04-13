package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.CancelTicketHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightBookingHistory.Result;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahesh on 3/28/2018.
 */

public class CancelFlightActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, HttpReqResCallBack {

    private TextView tvOrigin, tvDestination, tvBookingDate, tvJourneyType, tvTicketFare, tvPnr;
    private RadioButton rbRefundToWallet, rbRefundToBank;
    private NetworkDetection networkDetection;
    private Button btnCancel;
    private Result result;
    private String journeyType = "";
    private String selectedRadioButtonText = "";
    private String origin = "";
    private String destination = "";
    private String bookedDate = "";
    private String pnr = "";
    private String flightType = "";
    private String radioButtonText = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cancel_flight);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
        prepareResult();
    }

    private void getDataFromIntent() {
        result = AppController.getInstance().getSelectedFlightBookingHistory();
    }

    private void initializeUi() {

        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle(getString(R.string.flight_details));
        setSupportActionBar(toolbar);

        tvOrigin = findViewById(R.id.tvOrigin);
        tvDestination = findViewById(R.id.tvDestination);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvJourneyType = findViewById(R.id.tvJourneyType);
        tvTicketFare = findViewById(R.id.tvTicketFare);
        tvPnr = findViewById(R.id.tvPnr);
        rbRefundToBank = findViewById(R.id.rbRefundToBank);
        rbRefundToWallet = findViewById(R.id.rbRefundToWallet);
        btnCancel = findViewById(R.id.btnCancel);
        networkDetection = new NetworkDetection();
    }

    private void initializeListeners() {
        btnCancel.setOnClickListener(this);
        rbRefundToWallet.setOnCheckedChangeListener(this);
        rbRefundToBank.setOnCheckedChangeListener(this);
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
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnCancel:
                if (!selectedRadioButtonText.isEmpty()) {
                    showProgressBar();
                    //serviceCallForCancelTicket();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            selectedRadioButtonText = (String) buttonView.getText();
            if (selectedRadioButtonText.equalsIgnoreCase(getString(R.string.refund_to_wallet))) {
                radioButtonText = "W";

            } else {
                radioButtonText = "B";
            }
        } else {
            selectedRadioButtonText = "";
        }
    }

 /*   private void serviceCallForCancelTicket() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isWifiAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_PARAM_PNR, pnr);
            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_PARAM_FLIGHT_TYPE, flightType);
            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_PARAM_REFUND_TO, radioButtonText);

            CancelTicketHttpClient cancelTicketHttpClient = new CancelTicketHttpClient(this);
            cancelTicketHttpClient.callBack = this;
            cancelTicketHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_CANCEL_TICKET:
                if (jsonResponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            Intent cancelFlightTicketConfirmationIntent = new Intent(this, CancelFlightTicketConfirmationActivity.class);
                            startActivity(cancelFlightTicketConfirmationIntent);
                        }
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        closeProgressbar();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }
}
