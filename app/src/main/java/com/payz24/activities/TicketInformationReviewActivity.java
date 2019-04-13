package com.payz24.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.application.AppController;
import com.payz24.http.SmsHttpClient;
import com.payz24.http.StoreBusDetailsHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.blockTicket.BlockSeatPaxDetail;
import com.payz24.responseModels.blockTicket.BlockTicket;
import com.payz24.responseModels.busSeatBookingResponse.BusSeatBookingResponse;
import com.payz24.responseModels.storeBusDetails.Result;
import com.payz24.responseModels.storeBusDetails.StoreBusDetails;
import com.payz24.utils.Constants;
import com.payz24.utils.PreferenceConnector;
import com.payz24.utils.WalletItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.payz24.utils.Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;

/**
 * Created by mahesh on 2/17/2018.
 */

public class TicketInformationReviewActivity extends BaseActivity implements View.OnClickListener, HttpReqResCallBack {

    private LinearLayout llBookingInformation, llBookReturn, llJourneyDetails, llPassengerDetailsContainer, llPaymentDetails,tvHomes;
    private TextView tvBookingId, tvBookReturn, tvSubmit;
    private TextView tvLeavingFromValue, tvGoingToValue, tvOperatorName, tvBusTypeValue, tvBoardingPointValue, tvDepartureDateTime, tvNumberOfSeats;
    private TextView tvPassengerSeatDetails, tvPassengerSeatNumber, tvContinue, tvNetPayableValue,tvJdate;
    private ImageView ivDropDown;
    private RatingBar ratingBar;
    String fromCity,toCity;


    private LinkedList<String> listOfPaymentDetailsKeys;
    private LinkedList<String> listOfPaymentDetailsValues;

    private String blockTicketString;
    private Double totalFareWithTaxes = 0.0;
    private Double serviceTax = 0.0;
    private Double operatorServiceChargeAbsolute = 0.0;
    private Float totalFareWithOutTaxes = 0f;
    private boolean isHeaderClicked;
    private String blockTicketKey = "";
    private String message = "";
    private String busDetails = "";
    private String orderId = "";
    private String userPhoneNumber = "";
    String conv,mark,fare,oPnr;
    String emailUrl=TRAVEX_SOFT_UTILITIES_BASE_URL+"/rest/busbookingsuccessmail?id=";


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ticket_information_review);
        getDataFromIntent();
        initializeUi();
        initializeListeners();
        showProgressBar();
        serviceCallForStoreBusDetails();

    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            blockTicketString = bundle.getString(getString(R.string.block_ticket_string));
            blockTicketKey = bundle.getString(getString(R.string.block_ticket_key));
            message = bundle.getString(getString(R.string.message));
            busDetails = bundle.getString(getString(R.string.bus_details));
            orderId = bundle.getString(getString(R.string.order_id));
            userPhoneNumber = bundle.getString(getString(R.string.mobile_number));
            toCity=bundle.getString("to");
            fromCity=bundle.getString("from");
            oPnr=bundle.getString(getString(R.string.pnr));

            conv=bundle.getString("conv");
            mark=bundle.getString("mark");
            fare=bundle.getString("fare");
        }
    }


    private void initializeUi() {
        llBookingInformation = findViewById(R.id.llBookingInformation);
        llBookReturn = findViewById(R.id.llBookReturn);
        tvBookingId = findViewById(R.id.tvBookingId);
        tvBookReturn = findViewById(R.id.tvBookReturn);
        tvHomes = findViewById(R.id.tvHomes);
       // tvSubmit = findViewById(R.id.tvSubmit);
        ivDropDown = findViewById(R.id.ivDropDown);
       // ratingBar = findViewById(R.id.ratingBar);
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
        tvJdate = findViewById(R.id.tvJdate);

        listOfPaymentDetailsKeys = new LinkedList<>();
        listOfPaymentDetailsValues = new LinkedList<>();

        tvBookingId.setText(oPnr);
    }

    private void initializeListeners() {
        llBookReturn.setOnClickListener(this);
     //   tvSubmit.setOnClickListener(this);
        tvBookReturn.setOnClickListener(this);
        llBookingInformation.setOnClickListener(this);
        ivDropDown.setOnClickListener(this);
        tvHomes.setOnClickListener(this);
    }

    private void serviceCallForStoreBusDetails() {
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.BUS_DETAILS_STORE_PARAM_BODY, busDetails);
        StoreBusDetailsHttpClient storeBusDetailsHttpClient = new StoreBusDetailsHttpClient(this);
        storeBusDetailsHttpClient.callBack = this;
        storeBusDetailsHttpClient.getJsonForType(mapOfRequestParams);
    }

    private void serviceCallForSMSGateWay(String status) {
        //String message = "Dear Customer," + " " + "PNR" + "  " + opPNR + " " + fromCity + " to " + toCity + "." + " " + operatorName + " " + busType + "." + boardingPointTime + ":" + TextUtils.join(",", listOfBusSeatsNames) + " " + boardingPoint + "," + "More: Report before 15m.";
        Map<String, String> mapOfRequestParams = new HashMap<>();
        mapOfRequestParams.put(Constants.SMS_PARAM_PHONE, userPhoneNumber);
        if (status.equalsIgnoreCase("success")) {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, message);
        } else {
            mapOfRequestParams.put(Constants.SMS_PARAM_MESSAGE, "Thanks for using Bus facilities and your order id is" + " " + orderId + "payment got failed for amount" + String.valueOf(totalFareWithTaxes));
        }
        SmsHttpClient smsHttpClient = new SmsHttpClient(this);
        smsHttpClient.callBack = this;
        smsHttpClient.getJsonForType(mapOfRequestParams);
    }

    private void preparePaymentDetailsList() {
        BlockTicket blockTicket = new Gson().fromJson(blockTicketString, BlockTicket.class);
        tvLeavingFromValue.setText(blockTicket.getSourceCity());
        tvGoingToValue.setText(blockTicket.getDestinationCity());
        ApiAvailableBus apiAvailableBus = AppController.getInstance().getSelectedAvailableBus();
        tvOperatorName.setText(apiAvailableBus.getOperatorName());
        tvBusTypeValue.setText(apiAvailableBus.getBusType());
        String boardingPoint = blockTicket.getBoardingPoint().getLocation();
        String boardingPointTime = blockTicket.getBoardingPoint().getTime();
        String doj=blockTicket.getDoj();
        tvJdate.setText(doj);


        tvBoardingPointValue.setText(boardingPoint);
        tvDepartureDateTime.setText(boardingPointTime);
        List<BlockSeatPaxDetail> listOfBlockSeatPaxDetails = blockTicket.getBlockSeatPaxDetails();
        tvNumberOfSeats.setText(String.valueOf(listOfBlockSeatPaxDetails.size()));
        int numberOfSeats = listOfBlockSeatPaxDetails.size();

        for (int index = 0; index < listOfBlockSeatPaxDetails.size(); index++) {
            BlockSeatPaxDetail blockSeatPaxDetail = listOfBlockSeatPaxDetails.get(index);
            Float fare = blockSeatPaxDetail.getFare();
            Double serviceTax = blockSeatPaxDetail.getServiceTaxAmount();
            Double operatorServiceChargeAbsolute = blockSeatPaxDetail.getOperatorServiceChargeAbsolute();
            Double totalFareWithTaxes = blockSeatPaxDetail.getTotalFareWithTaxes();

            this.totalFareWithOutTaxes = this.totalFareWithOutTaxes + fare;
            this.totalFareWithTaxes = this.totalFareWithTaxes + totalFareWithTaxes;
            this.serviceTax = this.serviceTax + serviceTax;
            this.operatorServiceChargeAbsolute = this.operatorServiceChargeAbsolute + operatorServiceChargeAbsolute;
            String name =  blockSeatPaxDetail.getName() + "," + blockSeatPaxDetail.getSex() + "," + blockSeatPaxDetail.getAge() + " Years";
            String seatNumber = blockSeatPaxDetail.getSeatNbr();
            initializePassengerView(name, seatNumber);
        }
        String seatsFareKey = getString(R.string.seat_fare) + "(For" + String.valueOf(numberOfSeats) + " seats)";
        listOfPaymentDetailsKeys.add(seatsFareKey);
        String busMType = PreferenceConnector.readString(this, getString(R.string.bus_m_type), "M");

        listOfPaymentDetailsKeys.add(getString(R.string.gst_amount));
        listOfPaymentDetailsKeys.add(getString(R.string.pgc));
        listOfPaymentDetailsKeys.add(getString(R.string.net_payable));





        if (busMType.equalsIgnoreCase("M"))

        {

            Double markFees= Double.parseDouble(mark);
            Double fareFee= Double.parseDouble(fare);
            Double conFee= Double.parseDouble(conv);


            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(totalFareWithOutTaxes-markFees));

            listOfPaymentDetailsValues.add(""+serviceTax);
            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(conv));
            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(fareFee-markFees+conFee));
        }


        else
        {
            Double markFees= Double.parseDouble(mark);
            Double fareFee= Double.parseDouble(fare);
            Double conFee= Double.parseDouble(conv);
            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(totalFareWithOutTaxes+markFees));
            listOfPaymentDetailsValues.add(""+serviceTax);
          //  listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(fareFee+markFees));
            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(conv));
            listOfPaymentDetailsValues.add(getString(R.string.Rs) + "" + String.valueOf(fareFee+markFees+conFee));
        }




        for (int paymentIndex = 0; paymentIndex < listOfPaymentDetailsKeys.size(); paymentIndex++) {
            initializePaymentDetailsView(listOfPaymentDetailsKeys.get(paymentIndex), listOfPaymentDetailsValues.get(paymentIndex));
        }
    }

    private void initializePaymentDetailsView(String key, String value) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_payment_details_items, null);
        TextView tvPaymentKey = view.findViewById(R.id.tvPaymentKey);
        TextView tvPaymentValue = view.findViewById(R.id.tvPaymentValue);
        tvPaymentKey.setText(key);
        tvPaymentValue.setText(value);
        llPaymentDetails.addView(view);
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





    private void goToBusActivity() {
        Intent busIntent = new Intent(this, BusActivity.class);
        busIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //busIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        busIntent.putExtra("to", fromCity);
        busIntent.putExtra("from", toCity);
        busIntent.putExtra("return", "return");
        startActivity(busIntent);
        finish();
    }

    private void gToDashboard() {
        Intent busIntent = new Intent(this, DashboardActivity.class);
        busIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(busIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
          /*  case R.id.tvSubmit:
                break;
         */   case R.id.llBookingInformation:
                if (!isHeaderClicked) {
                    llJourneyDetails.setVisibility(View.VISIBLE);
                    isHeaderClicked = true;
                } else {
                    llJourneyDetails.setVisibility(View.GONE);
                    isHeaderClicked = false;
                }
                break;
            case R.id.ivDropDown:
                if (!isHeaderClicked) {
                    llJourneyDetails.setVisibility(View.VISIBLE);
                    isHeaderClicked = true;
                } else {
                    llJourneyDetails.setVisibility(View.GONE);
                    isHeaderClicked = false;
                }
                break;
            case R.id.llBookReturn:
                goToBusActivity();
                break;
            case R.id.tvHomes:
                gToDashboard();
                break;
            case R.id.tvBookReturn:
                goToBusActivity();
                break;
            default:
                break;
        }
    }



    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_STORE_BUS_DETAILS:
                if (jsonResponse != null) {
                    Log.e("jsonResoponseBus",jsonResponse);
                    if (!jsonResponse.startsWith("<br />")) {
                        StoreBusDetails storeBusDetails = new Gson().fromJson(jsonResponse, StoreBusDetails.class);
                        String status = storeBusDetails.getStatus();
                        String message = storeBusDetails.getMessage();
                        Result result=storeBusDetails.getResult();
                        Log.e("Result",result.toString());
                        Log.e("result","resultpassed");
                        if (status.equalsIgnoreCase("success")) {
                          //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        } else {
                         //   Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                        serviceCallForSMSGateWay("success");
                        sendEmail(emailUrl+result.getMasterId());
                    } else {
                        serviceCallForSMSGateWay("success");

                    }
                    preparePaymentDetailsList();
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            case Constants.SERVICE_SMS:
                if (jsonResponse != null) {
                    // goToReviewActivity();
                } else {
                    Toast.makeText(this, getString(R.string.status_error), Toast.LENGTH_SHORT).show();
                }
                closeProgressbar();
                break;
            default:
                break;
        }
    }


    public void sendEmail(String url)
    {
        // We first check for cached request
        Log.e("url",url);
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null)
        {
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseCommentFeeds(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else {

            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    url, (String)null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d("Error", "Response: " + response.toString());
                    if (response != null) {
                        parseCommentFeeds(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }
    private void parseCommentFeeds(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("result");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
