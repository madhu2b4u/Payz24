package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.DomesticFlightsSearchRoundTripActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 2/11/2018.
 */

public class DomesticFlightsSearchRoundTripHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public DomesticFlightsSearchRoundTripHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.post(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP, mapOfRequestParams), mapOfRequestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody != null){
                    callBack.jsonResponseReceived(new String(responseBody),statusCode,Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP);
                }else {
                    callBack.jsonResponseReceived(null,statusCode,Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(responseBody != null){
                    callBack.jsonResponseReceived(new String(responseBody),statusCode,Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP);
                }else {
                    callBack.jsonResponseReceived(null,statusCode,Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP);
                }
            }
        },Constants.CONTENT_TYPE_JSON,Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP);
    }
}
