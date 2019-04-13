package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UriRequestParameters;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by someo on 29-05-2018.
 */
public class RechargeHistoryHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public RechargeHistoryHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.get(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_MOBILE_GET_RECHARGES, mapOfRequestParams), UriRequestParameters.getRequestParamsForType(Constants.SERVICE_MOBILE_GET_RECHARGES, mapOfRequestParams), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_MOBILE_GET_RECHARGES);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_MOBILE_GET_RECHARGES);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_MOBILE_GET_RECHARGES);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_MOBILE_GET_RECHARGES);
                }
            }
        }, Constants.SERVICE_MOBILE_GET_RECHARGES);
    }
}

