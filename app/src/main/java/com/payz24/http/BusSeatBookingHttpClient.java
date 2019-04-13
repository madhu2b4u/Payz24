package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.BusPaymentDetails;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UriRequestParameters;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 2/15/2018.
 */

public class BusSeatBookingHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public BusSeatBookingHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_BUS_SEAT_BOOKING, mapOfRequestParams), UriRequestParameters.getRequestParamsForType(Constants.SERVICE_BUS_SEAT_BOOKING, mapOfRequestParams), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BUS_SEAT_BOOKING);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BUS_SEAT_BOOKING);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BUS_SEAT_BOOKING);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BUS_SEAT_BOOKING);
                }
            }
        }, Constants.SERVICE_BUS_SEAT_BOOKING);
    }
}
