package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.FlightSearchResultsActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 2/11/2018.
 */

public class SearchDomesticFlightsHttpClient extends BaseHttpClient{

    public HttpReqResCallBack callBack;

    public SearchDomesticFlightsHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.post(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS, mapOfRequestParams), mapOfRequestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS);
                }
            }
        }, Constants.CONTENT_TYPE_JSON, Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS);
    }
}
