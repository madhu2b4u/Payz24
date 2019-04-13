package com.payz24.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.payz24.activities.PassengerDetails;
import com.payz24.interfaces.HttpReqResCallBack;
import com.payz24.responseModels.blockTicket.BlockTicket;
import com.payz24.utils.Constants;
import com.payz24.utils.UrlBuilder;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mahesh on 2/1/2018.
 */

public class BlockBusTicketHttpClient extends BaseHttpClient {

    public HttpReqResCallBack callBack;

    public BlockBusTicketHttpClient(Context context) {
        super.context = context;
    }

    public void getJsonForType(Map<String, String> mapOfRequestParams) {
        HttpClient.context = context;
        HttpClient.post(UrlBuilder.formatRequestURLByRequestType(Constants.SERVICE_BLOCK_BUS_TICKET, null), mapOfRequestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BLOCK_BUS_TICKET);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BLOCK_BUS_TICKET);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    callBack.jsonResponseReceived(new String(responseBody), statusCode, Constants.SERVICE_BLOCK_BUS_TICKET);
                } else {
                    callBack.jsonResponseReceived(null, statusCode, Constants.SERVICE_BLOCK_BUS_TICKET);
                }
            }
        }, Constants.CONTENT_TYPE_JSON, Constants.SERVICE_BLOCK_BUS_TICKET);
    }
}
