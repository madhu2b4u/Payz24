package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.FlightsStationNamesActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 2/4/2018.
 */

public class FlightsStationsListHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public FlightsStationsListHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType() {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_FLIGHT_STATIONS, null), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_FLIGHT_STATIONS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_FLIGHT_STATIONS);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_FLIGHT_STATIONS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_FLIGHT_STATIONS);
                }
            }
        }, Constants.SERVICE_FLIGHT_STATIONS);
    }
}
