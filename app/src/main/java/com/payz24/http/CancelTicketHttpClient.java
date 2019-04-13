package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.CancelFlightActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 3/28/2018.
 */

public class CancelTicketHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public CancelTicketHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, Object> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.postCancel(UrlBuilder.formatRequestURLByRequestTypeCancel(Constants.SERVICE_CANCEL_TICKET, mapOfRequestParams), mapOfRequestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_CANCEL_TICKET);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_CANCEL_TICKET);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_CANCEL_TICKET);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_CANCEL_TICKET);
                }
            }
        }, Constants.CONTENT_TYPE_JSON, Constants.SERVICE_CANCEL_TICKET);
    }
}
