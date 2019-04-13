package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.payz24.R;
import com.payz24.http.SmsHttpClient;
import com.payz24.http.StoreBusCancellationDetails;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.CancelBusTicketResponse.CancelBusTicketResponse;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahesh on 3/31/2018.
 */

public class CancelBusConfirmationActivity extends BaseActivity implements HttpReqResCallBack {

    private TextView tvSource, tvDestination, tvBookingDate, tvOperatorName, tvPnr, tvSelectedSeats, tvTotalAmount, tvRefundAmount,tvName;
    private NetworkDetection networkDetection;
    private String totalFare = "";
    private String refundAmount = "";
    private String cancellationCharges = "";
    private String source = "";
    private String destination = "";
    private String operatorName = "";
    private String bookingDate = "";
    private String pnr = "";
    private String seats = "";
    private String names = "";
    private String etsNumber = "";
    private String refundType = "";
    private String userMobileNumber = "";
    Button rHome;

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_cancel_confirmation);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle("Bus Cancellation");
        setSupportActionBar(toolbar);
        getDataFromIntent();
        initializeUi();
        prepareDetails();
        showProgressBar();
        serviceCallForCancelTicketStore(totalFare, cancellationCharges, refundType);
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            source = bundle.getString(getString(R.string.source));
            destination = bundle.getString(getString(R.string.destination));
            operatorName = bundle.getString(getString(R.string.operator));
            bookingDate = bundle.getString(getString(R.string.booking_date));
            pnr = bundle.getString(getString(R.string.pnr));
            seats = bundle.getString(getString(R.string.seats));
            totalFare = bundle.getString(getString(R.string.total_ticket_fare));
            refundAmount = bundle.getString(getString(R.string.total_refund_amount));
            refundType = bundle.getString(getString(R.string.refund_type));
            etsNumber = bundle.getString(getString(R.string.ets_number));
            cancellationCharges = bundle.getString(getString(R.string.cancellation_charges));
            userMobileNumber = bundle.getString(getString(R.string.user_mobile_number));
            names = bundle.getString("names");
        }
    }

    private void initializeUi() {
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvName = findViewById(R.id.tvName);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvOperatorName = findViewById(R.id.tvOperatorName);
        tvPnr = findViewById(R.id.tvPnr);
        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvRefundAmount = findViewById(R.id.tvRefundAmount);
        rHome = findViewById(R.id.rHome);
        rHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent busIntent= new Intent(CancelBusConfirmationActivity.this, DashboardActivity.class);
                busIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(busIntent);
            }
        });

        networkDetection = new NetworkDetection();
    }

    private void prepareDetails() {
        tvSource.setText(source);
        tvDestination.setText(destination);
        tvBookingDate.setText(bookingDate);
        tvOperatorName.setText(operatorName);
        tvPnr.setText(pnr);
        tvSelectedSeats.setText(seats);
        tvTotalAmount.setText(getResources().getString(R.string.Rs)+" "+totalFare);
        tvRefundAmount.setText(getResources().getString(R.string.Rs)+" "+refundAmount);
        tvName.setText(names);
    }

    private void serviceCallForCancelTicketStore(String totalRefundAmount, String cancellationCharges, String refundType) {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.CANCEL_BUS_TICKET_PARAM_TOTAL_REFUND_AMOUNT, totalRefundAmount);
            mapOfRequestParams.put(Constants.CANCEL_BUS_TICKET_PARAM_CANCELLATION_CHARGES, cancellationCharges);
            mapOfRequestParams.put(Constants.CANCEL_BUS_TICKET_PARAM_REFUND_TYPE, refundType);
            mapOfRequestParams.put(Constants.CANCEL_BUS_TICKET_PARAM_REFERENCE_NUMBER, etsNumber);

            StoreBusCancellationDetails storeBusCancellationDetails = new StoreBusCancellationDetails(this);
            storeBusCancellationDetails.callBack = this;
            storeBusCancellationDetails.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void serviceCallForSmsCancel() {
        String message = "Thanks for using Bus facilities and your cancel request was successful and status" +
                "pending for the PNR id" + pnr + " " + " Your refund request is under process";
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SMS_PARAM_PHONE, userMobileNumber);
        mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, message);
        SmsHttpClient smsHttpClient = new SmsHttpClient(this);
        smsHttpClient.callBack = this;
        smsHttpClient.getJsonForType(mapOfRequestParams);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_STORE_CANCEL_BUS_DETAILS:
                if (jsonResponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if (status.equals("success"))
                        {
                            serviceCallForSmsCancel();
                        }
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
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
