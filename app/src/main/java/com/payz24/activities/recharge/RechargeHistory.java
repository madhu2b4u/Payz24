package com.payz24.activities.recharge;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.payz24.R;
import com.payz24.activities.BaseActivity;
import com.payz24.activities.FlightTicketOverviewScreenActivity;
import com.payz24.adapter.FlightsBookingHistoryAdapter;
import com.payz24.adapter.RechargeHistoryAdapter;
import com.payz24.adapter.WalletAdapter;
import com.payz24.application.AppController;
import com.payz24.fragment.RechargeItem;
import com.payz24.http.FlightBookingHistoryHttpClient;
import com.payz24.http.RechargeHistoryHttpClient;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.flightBookingHistory.FlightBookingHistory;
import com.payz24.responseModels.flightBookingHistory.Result;
import com.payz24.utils.Constants;
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

import static com.payz24.utils.Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;

public class RechargeHistory extends BaseActivity  {

    private RecyclerView rvFlightBookingHistory;
    private ArrayList<RechargeItem> feedItems;
    private RechargeHistoryAdapter listAdapter;
    TextView tvError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flights_booking_history);
        initializeUi();
        String userId = PreferenceConnector.readString(this, getString(R.string.user_id), "");
        enableActionBar(true);
        fetchSales(TRAVEX_SOFT_UTILITIES_BASE_URL+"/rest/getRechargeList?userid="+userId+"&type=3");
    }

    private void initializeUi() {
        Toolbar toolbar = findViewById(R.id.action_toolbar);
        toolbar.setTitle("Recharge History");
        setSupportActionBar(toolbar);
        feedItems=new ArrayList<RechargeItem>();
        rvFlightBookingHistory = findViewById(R.id.rvFlightBookingHistory);
        tvError = findViewById(R.id.tvError);
        listAdapter = new RechargeHistoryAdapter(this, feedItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvFlightBookingHistory.setLayoutManager(layoutManager);
        rvFlightBookingHistory.setItemAnimator(new DefaultItemAnimator());
        rvFlightBookingHistory.setAdapter(listAdapter);



    }

    public void fetchSales(String url)
    {
        // We first check for cached request
        //Log.e("url",url);
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
            Log.e("response",response.toString());

            if(feedArray==null||feedArray.length()==0)
            {
                tvError.setVisibility(View.VISIBLE);
            } else
            {
                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);

                    RechargeItem item = new RechargeItem();

                    item.setRc_amount(feedObj.getString("rc_amount"));
                    item.setRc_no(feedObj.getString("rc_no"));
                    item.setCreated(feedObj.getString("created"));
                    item.setProvider_name(feedObj.getString("provider_name"));
                    item.setRc_type(feedObj.getString("rc_type"));
                    item.setStatus(feedObj.getString("status"));


                    feedItems.add(item);
                    listAdapter.notifyDataSetChanged();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}
