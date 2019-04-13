package com.payz24.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payz24.R;
import com.payz24.activities.BaseActivity;
import com.payz24.activities.CancelFlightTicketConfirmationActivity;
import com.payz24.activities.DashboardActivity;
import com.payz24.adapter.WalletAdapter;
import com.payz24.application.AppController;
import com.payz24.http.CancelTicketHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightTicketOverviewScreen.PassengerDetail;
import com.payz24.utils.Constants;
import com.payz24.utils.NetworkDetection;
import com.payz24.utils.PreferenceConnector;
import com.payz24.utils.WalletItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View.MeasureSpec;
import android.widget.Toast;

import static com.payz24.utils.Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;

public class FlightDetails extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,HttpReqResCallBack {


    private TextView otvLeavingFromValue, otvGoingToValue;
    private TextView rtvLeavingFromValue, rtvGoingToValue;
    private TextView octvBookingId,rctvBookingId, otvOnwardOperatorName,rtvOnwardOperatorName, otvDepartureDate, rtvDepartureDate, otvSelectedTrip,rtvSelectedTrip;
    private LinearLayout ollPassengerDetailsContainer,rllPassengerDetailsContainer,cancelReturn;
    private NetworkDetection networkDetection;

    String pnr="";
    JSONArray  onlist;
    JSONArray  returnList;

    private Button btnCancel;
    private RadioButton rbRefundToWallet, rbRefundToBank;
    private String selectedRadioButtonText = "";
    private String radioButtonText = "";
    String flight_type;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_cancel);
        onlist = new JSONArray();
        returnList = new JSONArray() ;
        userid = PreferenceConnector.readString(this,getString(R.string.user_id),"");
        initializeUi();
        enableActionBar(true);
        getDataFromIntent();
        initializeListeners();
        //A086730
        //fetchSales("https://payz24.com/rest/listfdata?fid=A087816&userid=18");
        fetchSales(TRAVEX_SOFT_UTILITIES_BASE_URL+"/rest/listfdata?fid="+pnr+"&userid="+userid);

    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle("Ticket Overview");
        setSupportActionBar(toolbar);


        networkDetection = new NetworkDetection();
        otvOnwardOperatorName = findViewById(R.id.otvOnwardOperatorName);
        ollPassengerDetailsContainer = findViewById(R.id.ollPassengerDetailsContainer);
        rtvOnwardOperatorName = findViewById(R.id.rtvOnwardOperatorName);
        rllPassengerDetailsContainer = findViewById(R.id.rllPassengerDetailsContainer);
        cancelReturn = findViewById(R.id.cancelReturn);
        cancelReturn.setVisibility(View.GONE);
        octvBookingId = findViewById(R.id.octvBookingId);
        rctvBookingId = findViewById(R.id.rctvBookingId);

        otvLeavingFromValue = findViewById(R.id.otvLeavingFromValue);
        otvGoingToValue = findViewById(R.id.otvGoingToValue);

        rtvLeavingFromValue = findViewById(R.id.rtvLeavingFromValue);
        rtvGoingToValue = findViewById(R.id.rtvGoingToValue);

        otvDepartureDate = findViewById(R.id.otvDepartureDate);
        rtvDepartureDate = findViewById(R.id.rtvDepartureDate);

        otvSelectedTrip = findViewById(R.id.otvSelectedTrip);
        rtvSelectedTrip = findViewById(R.id.rtvSelectedTrip);

        rbRefundToBank = findViewById(R.id.crbRefundToBank);
        rbRefundToWallet = findViewById(R.id.crbRefundToWallet);
        rbRefundToBank.setVisibility(View.GONE);
        rbRefundToWallet.setVisibility(View.GONE);
        btnCancel = findViewById(R.id.cbtnCancel);
        btnCancel.setVisibility(View.GONE);




    }

    private void initializeListeners() {
        btnCancel.setOnClickListener(this);
        rbRefundToWallet.setOnCheckedChangeListener(this);
        rbRefundToBank.setOnCheckedChangeListener(this);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            pnr = intent.getStringExtra("pnr");
//            Log.e("pnr",pnr);
        }
    }


    public void fetchSales(String url)
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
            if(feedArray==null||feedArray.length()==0)
            {
               Log.e("NoData","NoData");
            }else
            {
                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);

                    try
                    {

                        String flight_type=feedObj.getString("flight_type");
                        String journeyType=feedObj.getString("journeyType");

                        if (flight_type.equals("intl"))
                        {


                            if (journeyType.equals("O")) {
                                cancelReturn.setVisibility(View.GONE);
                                String onward = feedObj.getJSONObject("bookingdata").getJSONObject("OriginDestinationOption").getJSONObject("onward").getJSONObject(  "FlightSegments").getJSONObject("FlightSegment").getString("pnrnumber");
                                octvBookingId.setText(onward);

                            }else if (journeyType.equals("R"))
                            {

                                cancelReturn.setVisibility(View.VISIBLE);
                                JSONArray returnArray = feedObj.getJSONArray("return");
                                JSONArray onward = feedObj.getJSONObject("bookingdata").getJSONObject("OriginDestinationOption").getJSONObject("onward").getJSONObject(  "FlightSegments").getJSONArray("FlightSegment");
                                JSONArray returns = feedObj.getJSONObject("bookingdata").getJSONObject("OriginDestinationOption").getJSONObject("Return").getJSONObject(  "FlightSegments").getJSONArray("FlightSegment");
                                String oneway = onward.getJSONObject(0).getString("pnrnumber");
                                String returnway = returns.getJSONObject(1).getString("pnrnumber");
                                String odepartureDate = returns.getJSONObject(1).getString("DepartureDateTime");
                                Log.e("pnr",oneway.toString());
                                Log.e("pnr",returnway.toString());

                                String[] parts = odepartureDate.split("T");
                                String part1 = parts[0]; // 004
                                String part2 = parts[1]; // 034556

                                octvBookingId.setText(oneway);
                                rctvBookingId.setText(returnway);
                                rtvDepartureDate.setText(part1);



                                for (int j = 0; j < returnArray.length(); j++) {
                                    JSONObject returnObj = (JSONObject) returnArray.get(j);

                                    String airline=returnObj.getString("flight_name").replaceAll("(\\s*<[Bb][Rr]\\s*//*?>)+\\s*$", "");
                                    String flight_code=returnObj.getString("flight_code").replaceAll("(\\s*<[Bb][Rr]\\s*//*?>)+\\s*$", "");
                                    String flight_number=returnObj.getString("flight_number").replaceAll("(\\s*<[Bb][Rr]\\s*//*?>)+\\s*$", "");
                                    rtvOnwardOperatorName.setText(airline+" - "+flight_code+ " "+flight_number);
                                }

                            }






                        }else if (flight_type.equals("dom"))
                        {

                            if (journeyType.equals("O")) {
                                cancelReturn.setVisibility(View.GONE);
                                String confirmationUid = feedObj.getJSONObject("bookingdata").getJSONObject("requestedPNR").getJSONObject("origindestinationoptions").getJSONObject("OriDestPNRRequest").getString("confirmationid");
                                String onewaypnr = feedObj.getJSONObject("bookingdata").getJSONObject("requestedPNR").getJSONObject("origindestinationoptions").getJSONObject("OriDestPNRRequest").getString("pnrnumber");
                                octvBookingId.setText(onewaypnr);

                            }else if (journeyType.equals("R"))
                            {

                                cancelReturn.setVisibility(View.VISIBLE);
                                JSONArray returnArray = feedObj.getJSONArray("return");
                                JSONArray returnpnr = feedObj.getJSONObject("bookingdata").getJSONObject("requestedPNR").getJSONObject("origindestinationoptions").getJSONArray("OriDestPNRRequest");
                                String oneway = returnpnr.getJSONObject(0).getString("pnrnumber");
                                String returnway = returnpnr.getJSONObject(1).getString("pnrnumber");
                                String departureDate = returnpnr.getJSONObject(1).getString("depttime");
                                Log.e("pnr",oneway.toString());
                                Log.e("pnr",returnway.toString());

                                String[] parts = departureDate.split("T");
                                String part1 = parts[0]; // 004
                                String part2 = parts[1]; // 034556

                                octvBookingId.setText(oneway);
                                rctvBookingId.setText(returnway);
                                rtvDepartureDate.setText(part1);



                                for (int j = 0; j < returnArray.length(); j++) {
                                    JSONObject returnObj = (JSONObject) returnArray.get(j);

                                    String airline=returnObj.getString("flight_name").replaceAll("(\\s*<[Bb][Rr]\\s*//*?>)+\\s*$", "");
                                    String flight_code=returnObj.getString("flight_code").replaceAll("(\\s*<[Bb][Rr]\\s*//*?>)+\\s*$", "");
                                    String flight_number=returnObj.getString("flight_number").replaceAll("(\\s*<[Bb][Rr]\\s*//*?>)+\\s*$", "");
                                    rtvOnwardOperatorName.setText(airline+" - "+flight_code+ " "+flight_number);
                                }

                            }


                        }



                        String from_city=feedObj.getString("from_city").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                        String to_city=feedObj.getString("to_city").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                        String departDate=feedObj.getString("departDate").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                        String pl=feedObj.getString("Passenger_details");
                        JSONArray onwardArray = feedObj.getJSONArray("Onward");

                        for (int j = 0; j < onwardArray.length(); j++) {
                            JSONObject onwardObj = (JSONObject) onwardArray.get(j);

                            String airline=onwardObj.getString("flight_name").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                            String flight_code=onwardObj.getString("flight_code").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                            flight_type=onwardObj.getString("flight_number").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");

                            otvOnwardOperatorName.setText(airline+" - "+flight_code+ " "+flight_type);
                            otvLeavingFromValue.setText(from_city);
                            otvGoingToValue.setText(to_city);
                            rtvLeavingFromValue.setText(to_city);
                            rtvGoingToValue.setText(from_city);
                            otvDepartureDate.setText(departDate);
                        }

                        JSONObject passengerObject=new JSONObject("{\"passengerlist\":"+pl+"}");
                        JSONArray passengerArray = passengerObject.getJSONArray("passengerlist");

                        for (int k = 0; k < passengerArray.length(); k++) {
                            JSONObject passObj = (JSONObject) passengerArray.get(k);

                            String adult=passObj.getString("type").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                            String title=passObj.getString("title").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                            String firstname=passObj.getString("first_name").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                            String last_name=passObj.getString("last_name").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                            String name=title+" "+firstname+" "+last_name;
                            initializePassengerView(name,adult);
                            initializeReturnPassengerView(name,adult);

                        }






                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void initializePassengerView(String name, String seatNumber) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_pass_details, null);
        TextView tvPassengerSeatDetails = view.findViewById(R.id.pdesc);
        TextView tvPassengerSeatNumber = view.findViewById(R.id.pamount);
        CheckBox checkBox = view.findViewById(R.id.passcheckbox);
        checkBox.setVisibility(View.GONE);
        tvPassengerSeatDetails.setText(name);
        tvPassengerSeatNumber.setText(seatNumber);
        ollPassengerDetailsContainer.addView(view);
//        rllPassengerDetailsContainer.addView(view);
    }

    private void initializeReturnPassengerView(String name, String seatNumber) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_pass_details, null);
        TextView tvPassengerSeatDetails = view.findViewById(R.id.pdesc);
        TextView tvPassengerSeatNumber = view.findViewById(R.id.pamount);
        CheckBox checkBox = view.findViewById(R.id.passcheckbox);
        checkBox.setVisibility(View.GONE);
        tvPassengerSeatDetails.setText(name);
        tvPassengerSeatNumber.setText(seatNumber);
        rllPassengerDetailsContainer.addView(view);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cbtnCancel:
                if (!selectedRadioButtonText.isEmpty()) {
                    showProgressBar();
                    serviceCallForCancelTicket();
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

    private void serviceCallForCancelTicket() {
        if (networkDetection.isWifiAvailable(this) || networkDetection.isWifiAvailable(this)) {
            Map<String, Object> mapOfRequestParams = new HashMap<>();
            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_PARAM_PNR, pnr);
            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_PARAM_FLIGHT_TYPE, flight_type);
            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_PARAM_REFUND_TO, radioButtonText);

            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_ONWARD, onlist);
            mapOfRequestParams.put(Constants.FLIGHT_CANCEL_RETURN, returnList);

            CancelTicketHttpClient cancelTicketHttpClient = new CancelTicketHttpClient(this);
            cancelTicketHttpClient.callBack = this;
            cancelTicketHttpClient.getJsonForType(mapOfRequestParams);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jsonResponseReceived(String jsonResponse, int statusCode, int requestType) {
        switch (requestType) {
            case Constants.SERVICE_CANCEL_TICKET:
                if (jsonResponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        Log.e("jsonResponse",jsonResponse);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if (status.equalsIgnoreCase(getString(R.string.success))) {


                            Toast.makeText(this, "Successfully Canceled", Toast.LENGTH_SHORT).show();
                            Intent cancelFlightTicketConfirmationIntent = new Intent(this, DashboardActivity.class);
                            startActivity(cancelFlightTicketConfirmationIntent);
                            finish();
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
