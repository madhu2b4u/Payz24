package com.payz24.activities.FlightCancel;

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

public class FlightCancel extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,HttpReqResCallBack {


    private TextView otvLeavingFromValue, otvGoingToValue;
    private TextView rtvLeavingFromValue, rtvGoingToValue;
    private TextView octvBookingId,rctvBookingId, otvOnwardOperatorName,rtvOnwardOperatorName, otvDepartureDate, rtvDepartureDate, otvSelectedTrip,rtvSelectedTrip;
    private LinearLayout ollPassengerDetailsContainer,rllPassengerDetailsContainer,cancelReturn;
    private NetworkDetection networkDetection;

    String pnr;
    JSONArray  onlist;
    JSONArray  returnList;

    private Button btnCancel;
    private RadioButton rbRefundToWallet, rbRefundToBank;
    private String selectedRadioButtonText = "";
    private String radioButtonText = "";
    String flight_type;
    CheckBox ret,on;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_cancel);
        onlist = new JSONArray();
        returnList = new JSONArray() ;

        initializeUi();
        enableActionBar(true);
        getDataFromIntent();
        initializeListeners();
        fetchSales(TRAVEX_SOFT_UTILITIES_BASE_URL+"/rest/cancelFlight?pnr="+pnr);

    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle("Cancel Ticket");
        setSupportActionBar(toolbar);


        networkDetection = new NetworkDetection();
        otvOnwardOperatorName = findViewById(R.id.otvOnwardOperatorName);
        ollPassengerDetailsContainer = findViewById(R.id.ollPassengerDetailsContainer);
        rtvOnwardOperatorName = findViewById(R.id.rtvOnwardOperatorName);
        rllPassengerDetailsContainer = findViewById(R.id.rllPassengerDetailsContainer);
        cancelReturn = findViewById(R.id.cancelReturn);

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
        btnCancel = findViewById(R.id.cbtnCancel);




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
            String feedArrays = response.getString("result");
            JSONObject jsonObject=new JSONObject("{\"result\":["+feedArrays+"]}");
            JSONArray feedArray = jsonObject.getJSONArray("result");


            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

             try
             {
                 String onward=feedObj.getString("onward");
                 String returns=feedObj.getString("return");

                 String pl=feedObj.getString("passengerlist");

                 JSONObject onwardObject=new JSONObject("{\"onward\":["+onward+"]}");
                 JSONArray onwardArray = onwardObject.getJSONArray("onward");

                 for (int j = 0; j < onwardArray.length(); j++) {
                     JSONObject onwardObj = (JSONObject) onwardArray.get(j);

                     String airline=onwardObj.getString("airline_name").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String from_city=onwardObj.getString("from_city").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String to_city=onwardObj.getString("to_city").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String departDate=onwardObj.getString("departDate").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String returnDate=onwardObj.getString("rturnDate").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     flight_type=onwardObj.getString("flight_type").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     otvOnwardOperatorName.setText(airline);
                     otvLeavingFromValue.setText(from_city);
                     otvGoingToValue.setText(to_city);
                     rtvLeavingFromValue.setText(to_city);
                     rtvGoingToValue.setText(from_city);
                     otvDepartureDate.setText(departDate);
                     rtvDepartureDate.setText(returnDate);
                     octvBookingId.setText(pnr);
                 }

                 if (returns.equals("[]"))
                 {

                     cancelReturn.setVisibility(View.GONE);
                 }else
                 {
                     JSONObject returnObject=new JSONObject("{\"return\":["+returns+"]}");
                     JSONArray returnArray = returnObject.getJSONArray("return");
                     if(returnArray==null||returnArray.length()==0)
                     {


                         cancelReturn.setVisibility(View.GONE);

                     }else
                     {
                         for (int j = 0; j < returnArray.length(); j++) {
                             JSONObject returnObj = (JSONObject) returnArray.get(j);

                             String airline=returnObj.getString("airline_name").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                             rtvOnwardOperatorName.setText(airline);
                             rctvBookingId.setText(pnr);
                         }
                     }
                 }


                 JSONObject passengerObject=new JSONObject("{\"passengerlist\":"+pl+"}");
                 JSONArray passengerArray = passengerObject.getJSONArray("passengerlist");

                 if (passengerArray.length()==0||passengerArray==null)
                 {
                     Log.e("Passenger","No");
                 }else
                 {

                 }

                 for (int k = 0; k < passengerArray.length(); k++) {
                     JSONObject passObj = (JSONObject) passengerArray.get(k);

                     String id=passObj.getString("id").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String adult=passObj.getString("type").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String title=passObj.getString("title").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String firstname=passObj.getString("first_name").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String last_name=passObj.getString("last_name").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String conward=passObj.getString("c_onward").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");
                     String creturn=passObj.getString("c_return").replaceAll("(\\s*<[Bb][Rr]\\s*/?>)+\\s*$", "");

                     String name=title+" "+firstname+" "+last_name;

                     if(conward.equals("1"))
                     {

                     }else
                     {
                         initializePassengerView(name,adult,id,conward,creturn);
                     }

                     if (creturn.equals("1"))
                     {

                     }else
                     {
                         initializeReturnPassengerView(name,adult,id,conward,creturn);
                     }




                 }

             }catch (JSONException e)
             {
                 e.printStackTrace();
             }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void initializePassengerView(String name, String seatNumber, final String id,String onward,String returns) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_pass_details, null);
        TextView tvPassengerSeatDetails = view.findViewById(R.id.pdesc);
        TextView tvPassengerSeatNumber = view.findViewById(R.id.pamount);
        on = view.findViewById(R.id.passcheckbox);
        on.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                   // Toast.makeText(FlightCancel.this, "" + id, Toast.LENGTH_LONG).show();
                    onlist.put("" + id + "");

                }

            }
        });
        tvPassengerSeatDetails.setText(name);
        tvPassengerSeatNumber.setText(seatNumber);
        ollPassengerDetailsContainer.addView(view);
    }

    private void initializeReturnPassengerView(String name, String seatNumber,final String id,String onward,String returns) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_pass_details, null);
        TextView tvPassengerSeatDetails = view.findViewById(R.id.pdesc);
        TextView tvPassengerSeatNumber = view.findViewById(R.id.pamount);
        ret = view.findViewById(R.id.passcheckbox);
        ret.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                  //  Toast.makeText(FlightCancel.this, "" + id, Toast.LENGTH_LONG).show();
                    returnList.put("" + id + "");

                }


            }
        });
        tvPassengerSeatDetails.setText(name);
        tvPassengerSeatNumber.setText(seatNumber);
        rllPassengerDetailsContainer.addView(view);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cbtnCancel:
                if (selectedRadioButtonText.isEmpty()) {
                    Toast.makeText(this, "Please select refund", Toast.LENGTH_SHORT).show();
                } else {
                    if (ret.isChecked()||on.isChecked())
                    {
                        showProgressBar();
                        serviceCallForCancelTicket();
                    } else
                    {
                        Toast.makeText(this, "Please select passenger to cancel", Toast.LENGTH_SHORT).show();
                    }

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
            closeProgressbar();
        }
    }

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
