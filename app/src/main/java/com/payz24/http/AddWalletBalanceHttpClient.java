package com.payz24.http;

import android.content.Context;
import android.view.View;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.fragment.WalletFragment;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 3/17/2018.
 */

public class AddWalletBalanceHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public AddWalletBalanceHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.post(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_ADD_MONEY, mapOfRequestParams), mapOfRequestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_ADD_MONEY);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_ADD_MONEY);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_ADD_MONEY);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_ADD_MONEY);
                }
            }
        }, Constants.CONTENT_TYPE_JSON, Constants.SERVICE_ADD_MONEY);
    }
}
