package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.HomeActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 1/11/2018.
 */

public class UtilitiesServicesListHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public UtilitiesServicesListHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType() {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_UTILITIES_LIST, null), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_UTILITIES_LIST);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_UTILITIES_LIST);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_UTILITIES_LIST);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_UTILITIES_LIST);
                }
            }
        }, Constants.SERVICE_UTILITIES_LIST);
    }
}
