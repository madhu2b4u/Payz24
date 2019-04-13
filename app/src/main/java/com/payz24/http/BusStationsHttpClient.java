package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.BusActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 1/24/2018.
 */

public class BusStationsHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public BusStationsHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType() {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_BUS_STATIONS, null), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BUS_STATIONS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BUS_STATIONS);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BUS_STATIONS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BUS_STATIONS);
                }
            }
        }, Constants.SERVICE_BUS_STATIONS);
    }
}
