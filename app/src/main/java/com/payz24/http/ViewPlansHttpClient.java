package com.payz24.http;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 1/19/2018.
 */

public class ViewPlansHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public ViewPlansHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.post(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_VIEW_PLANS, mapOfRequestParams), mapOfRequestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_VIEW_PLANS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_VIEW_PLANS);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_VIEW_PLANS);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_VIEW_PLANS);
                }
            }
        }, Constants.CONTENT_TYPE_JSON, Constants.SERVICE_VIEW_PLANS);
    }
}
