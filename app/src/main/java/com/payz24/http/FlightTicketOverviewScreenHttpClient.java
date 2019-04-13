package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.FlightTicketOverviewScreenActivity;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UriRequestParameters;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 3/9/2018.
 */

public class FlightTicketOverviewScreenHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public FlightTicketOverviewScreenHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_FLIGHT_TICKET_OVERVIEW, mapOfRequestParams), UriRequestParameters.getRequestParamsForType(Constants.SERVICE_FLIGHT_TICKET_OVERVIEW, mapOfRequestParams), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_FLIGHT_TICKET_OVERVIEW);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_FLIGHT_TICKET_OVERVIEW);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_FLIGHT_TICKET_OVERVIEW);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_FLIGHT_TICKET_OVERVIEW);
                }
            }
        }, Constants.SERVICE_FLIGHT_TICKET_OVERVIEW);
    }
}
