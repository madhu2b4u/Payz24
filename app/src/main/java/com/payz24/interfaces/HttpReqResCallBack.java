package com.payz24.interfaces;

/**
 * Created by mahesh on 1/8/2018.
 */

public interface HttpReqResCallBack {
    void jsonResponseReceived(String jsonResponse, int statusCode, int requestType);
}
