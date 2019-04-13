package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.BusLayoutActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UriRequestParameters;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 1/27/2018.
 */

public class BusLayoutHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public BusLayoutHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_BUS_LAYOUT, mapOfRequestParams), UriRequestParameters.getRequestParamsForType(Constants.SERVICE_BUS_LAYOUT,mapOfRequestParams), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BUS_LAYOUT);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BUS_LAYOUT);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BUS_LAYOUT);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BUS_LAYOUT);
                }
            }
        }, Constants.SERVICE_BUS_LAYOUT);
    }
}
