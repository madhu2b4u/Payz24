package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.AvailableBusesActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 3/31/2018.
 */

public class MarkUpFeeHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public MarkUpFeeHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType() {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_MARK_UP_FEE, null), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_MARK_UP_FEE);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_MARK_UP_FEE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_MARK_UP_FEE);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_MARK_UP_FEE);
                }
            }
        }, Constants.SERVICE_MARK_UP_FEE);
    }
}
