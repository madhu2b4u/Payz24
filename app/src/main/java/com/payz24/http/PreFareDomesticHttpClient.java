package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.FlightReviewDomesticOneWayActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

/**
 * Created by mahesh on 3/20/2018.
 */

public class PreFareDomesticHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public PreFareDomesticHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.post(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_PRE_FARE_DOMESTIC, mapOfRequestParams), mapOfRequestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_PRE_FARE_DOMESTIC);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_PRE_FARE_DOMESTIC);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_PRE_FARE_DOMESTIC);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_PRE_FARE_DOMESTIC);
                }
            }
        }, Constants.CONTENT_TYPE_JSON, Constants.SERVICE_PRE_FARE_DOMESTIC);
    }
}
