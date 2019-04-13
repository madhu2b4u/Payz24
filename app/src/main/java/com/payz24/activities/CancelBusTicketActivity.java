package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.http.BusTicketOverViewHttpClient;
import com.payz24.http.CancelBusTicketHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.CancelBusTicketRequest.CancelBusTicketRequest;
import com.payz24.responseModels.CancelBusTicketResponse.CancelBusTicketResponse;
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
 * Created by mahesh on 3/29/2018.
 */

public class CancelBusTicketActivity extends BaseActivity implements HttpReqResCallBack, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView tvSource, tvDestination, tvBookingDate, tvOperatorName, tvPnr, tvSelectedSeats;
    private RadioButton rbRefundToWallet, rbRefundToBank;
    private Button btnCancel;
    private NetworkDetection networkDetection;
    private String bid = "";
    private LinkedList<String> listOfSeats;
    private String etsTicketNo = "";
    private String source = "";
    private String destination = "";
    private String operatorName = "";
    private String bookingDate = "";
    private String pnr = "";
    private String refundType = "";
    private String selectedRadioButtonName = "";
    private CancelBusTicketResponse cancelBusTicketResponse;
    private String userMobileNumber = "";
    LinkedList<String> listOfName;

    String status;
    String selectedRadioButtonText,radioButtonText;
    String cancellationCharges,totalRefundAmount,sourceCity,destinationCity,journeyDate,departureTime,arrivalTime,serviceProvider,seatNo,pax_name;
    String gender,age,totalFare;
    TextView tvGender,tvAge,tvName,tvFare,tvCancelCharges,tvRefundFare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bus_cancel);
        getDataFromIntent();
        initializeUi();
        enableActionBar(true);
        initializeListeners();
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
        toolbar.setTitle(getString(R.string.bus_details));
        setSupportActionBar(toolbar);

        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvOperatorName = findViewById(R.id.tvOperatorName);
        tvPnr = findViewById(R.id.tvPnr);
        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        rbRefundToBank = findViewById(R.id.rbRefundToBank);
        rbRefundToWallet = findViewById(R.id.rbRefundToWallet);
        btnCancel = findViewById(R.id.btnCancel);

        networkDetection = new NetworkDetection();


        tvName = findViewById(R.id.tvName);

        tvFare = findViewById(R.id.tvFare);
        tvRefundFare = findViewById(R.id.tvRefundFare);
        tvCancelCharges = findViewById(R.id.tvCancelCharges);




    }

    private void initializeListeners() {
        btnCancel.setOnClickListener(this);
        rbRefundToWallet.setOnCheckedChangeListener(this);
        rbRefundToBank.setOnCheckedChangeListener(this);
    }

    private void serviceCallForBusTicketOverView() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            String userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
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

    private void serviceCallToCancelBusTicket() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isMobileNetworkAvailable(this)) {
            CancelBusTicketRequest cancelBusTicketRequest = new CancelBusTicketRequest(etsTicketNo, listOfSeats);
            String cancelBusTicketString = new Gson().toJson(cancelBusTicketRequest);

            Map<String, String> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.CANCEL_BUS_TICKET_PARAM_OBJECT, cancelBusTicketString);

            CancelBusTicketHttpClient cancelBusTicketHttpClient = new CancelBusTicketHttpClient(this);
            cancelBusTicketHttpClient.callBack = this;
            cancelBusTicketHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }


    private void goToCancelConfirmation(CancelBusTicketResponse cancelBusTicketResponse) {
        Intent cancelBusConfirmationIntent = new Intent(this, CancelBusConfirmationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.source), source);
        bundle.putString(getString(R.string.destination), destination);
        bundle.putString(getString(R.string.operator), operatorName);
        bundle.putString(getString(R.string.booking_date), bookingDate);
        bundle.putString(getString(R.string.pnr), pnr);
        bundle.putString(getString(R.string.refund_type), radioButtonText);
        bundle.putString(getString(R.string.ets_number), etsTicketNo);
        bundle.putString(getString(R.string.seats), TextUtils.join(",", listOfSeats));
        bundle.putString("names", TextUtils.join(",", listOfName));
        bundle.putString(getString(R.string.total_ticket_fare), cancelBusTicketResponse.getTotalTicketFare());
        bundle.putString(getString(R.string.total_refund_amount), cancelBusTicketResponse.getTotalRefundAmount());
        bundle.putString(getString(R.string.cancellation_charges), String.valueOf(cancelBusTicketResponse.getCancellationCharges()));
        bundle.putString(getString(R.string.user_mobile_number), userMobileNumber);
        cancelBusConfirmationIntent.putExtras(bundle);
        startActivity(cancelBusConfirmationIntent);
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_BUS_TICKET_OVER_VIEW:
                if (jsonResponse != null) {
                    Log.e("json",jsonResponse);
                    IndividualTicketOverView individualTicketOverView = new Gson().fromJson(jsonResponse, IndividualTicketOverView.class);
                    if (individualTicketOverView != null) {
                        String status = individualTicketOverView.getStatus();
                        if (status.equalsIgnoreCase(getString(R.string.success))) {
                            List<Result> listOfResults = individualTicketOverView.getResult();
                            if (listOfResults != null && listOfResults.size() != 0) {
                                for (int index = 0; index < listOfResults.size(); index++) {
                                    Result result = listOfResults.get(index);
                                    source = result.getSourceCity();
                                    destination = result.getDestinationCity();
                                    operatorName = result.getServiceProvider();
                                    bookingDate = result.getBookedDate();
                                    String busType = result.getBusType();
                                    String boardingPoint = result.getBoardingPoint();
                                    String departureDate = result.getJourneyDate();
                                    pnr = result.getPnr();
                                    userMobileNumber = result.getUserContact();
                                    etsTicketNo = result.getEtsTicketNo();
                                    result.getEtsTicketNo();
                                    List<PassengerDetail> listOfPassengerDetails = result.getPassengerDetails();
                                    String numberOfSeats = String.valueOf(listOfPassengerDetails.size());
                                    listOfSeats = new LinkedList<>();
                                    listOfName = new LinkedList<>();
                                    for (int passengerDetailsIndex = 0; passengerDetailsIndex < listOfPassengerDetails.size(); passengerDetailsIndex++) {
                                        listOfSeats.add(listOfPassengerDetails.get(passengerDetailsIndex).getSeatno());
                                    }
                                    for (int passengerDetailsIndex = 0; passengerDetailsIndex < listOfPassengerDetails.size(); passengerDetailsIndex++) {
                                        listOfName.add(listOfPassengerDetails.get(passengerDetailsIndex).getName());
                                    }

                                    tvSource.setText(source);
                                    tvDestination.setText(destination);
                                    tvBookingDate.setText(bookingDate);
                                    tvOperatorName.setText(operatorName);
                                    tvPnr.setText(pnr);
                                    tvSelectedSeats.setText(TextUtils.join(",", listOfSeats));
                                    tvName.setText(TextUtils.join(",", listOfName));
                                    serviceCallToCancelBusTicket();



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
            case Constants.SERVICE_CANCEL_BUS_TICKET:
                if (jsonResponse != null) {
                    Log.e("jsoNRespoBus",jsonResponse);
                    cancelBusTicketResponse = new Gson().fromJson(jsonResponse, CancelBusTicketResponse.class);
                    if (cancelBusTicketResponse != null) {
                        String message = cancelBusTicketResponse.getApiStatus().getMessage();
                        String fare=cancelBusTicketResponse.getTotalTicketFare();
                        double cancelFare=cancelBusTicketResponse.getCancellationCharges();
                        String refundAmount=cancelBusTicketResponse.getTotalRefundAmount();

                        tvFare.setText(getResources().getString(R.string.Rs)+" "+fare);
                        tvCancelCharges.setText(getResources().getString(R.string.Rs)+" "+""+cancelFare);
                        tvRefundFare.setText(getResources().getString(R.string.Rs)+" "+refundAmount);
                      //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                       // goToCancelConfirmation(cancelBusTicketResponse);
                        //serviceCallForCancelTicketStore(cancelBusTicketResponse.getTotalRefundAmount(), cancelBusTicketResponse.getCancellationCharges(), refundType);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnCancel:
                if (!radioButtonText.isEmpty()) {
                    goToCancelConfirmation(cancelBusTicketResponse);
                } else {
                    Toast.makeText(this, getString(R.string.please_select_how_to_refund_amount), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            selectedRadioButtonName = (String) buttonView.getText();
            if (selectedRadioButtonName.equalsIgnoreCase(getString(R.string.refund_to_wallet))) {
                radioButtonText = "W";
                rbRefundToWallet.setChecked(isChecked);
                rbRefundToBank.setChecked(false);
            } else {
                radioButtonText = "B";
                rbRefundToBank.setChecked(isChecked);
                rbRefundToWallet.setChecked(false);
            }
        } else {
            selectedRadioButtonText = "";
        }
    }
}
