package com.payz24.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.payz24.application.AppController;
import com.payz24.utils.Constants;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.Map;

import cz.msebera.android.httpclient.auth.AuthScope;
import cz.msebera.android.httpclient.auth.UsernamePasswordCredentials;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by mahesh on 1/8/2018.
 */

public class HttpClient {

    static Context context;
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, int requestType) {
       // Log.i("Get Info", url + params);
        client.setTimeout(120 * 1000);
        switch (requestType) {
            case Constants.SERVICE_UTILITIES_LIST:
                client.addHeader("Content-Type", "application/json");
                client.addHeader("utilityUuid", "ce7ab58becf8431daddc19da693bfc57");
                client.addHeader("utilitySecretKey", "HrwROFC7sc");
                break;
            case Constants.SERVICE_BUS_STATIONS:
                Uri parsed = Uri.parse(url);
                client.setCredentials(
                        new AuthScope(parsed.getHost(), parsed.getPort() == -1 ? 80 : parsed.getPort()),
                        new UsernamePasswordCredentials(
                                "ram0438",
                                "Ram@t4j"
                        )
                );
                break;
            case Constants.SERVICE_GET_AVAILABLE_BUS:
                Uri availableBusesParsed = Uri.parse(url);
                client.setCredentials(
                        new AuthScope(availableBusesParsed.getHost(), availableBusesParsed.getPort() == -1 ? 80 : availableBusesParsed.getPort()),
                        new UsernamePasswordCredentials(
                                "ram0438",
                                "Ram@t4j"
                        )
                );
                break;
            case Constants.SERVICE_BUS_LAYOUT:
                Uri parsed1 = Uri.parse(url);
                client.setCredentials(
                        new AuthScope(parsed1.getHost(), parsed1.getPort() == -1 ? 80 : parsed1.getPort()),
                        new UsernamePasswordCredentials(
                                "ram0438",
                                "Ram@t4j"
                        )
                );
                break;
            default:
                break;
        }
        client.setTimeout(120 * 1000);
        client.get(url, params, responseHandler);
    }

    public static void post(String url, Map<String, String> mapOfRequestParams, AsyncHttpResponseHandler responseHandler, String contentType, int requestType) {
      //  Log.i("Get Info", url + mapOfRequestParams);
        client.setTimeout(120 * 1000);
        JSONObject jsonParams = new JSONObject();
        try {
            switch (requestType) {
                case Constants.SERVICE_MOBILE_RECHARGE:
                    jsonParams.put(Constants.RECHARGE_PARAM_RECHARGE_NUMBER, mapOfRequestParams.get(Constants.RECHARGE_PARAM_RECHARGE_NUMBER));
                    jsonParams.put(Constants.RECHARGE_PARAM_RECHARGE_AMOUNT, mapOfRequestParams.get(Constants.RECHARGE_PARAM_RECHARGE_AMOUNT));
                    jsonParams.put(Constants.RECHARGE_PARAM_UTILITY_OPERATOR_ID, mapOfRequestParams.get(Constants.RECHARGE_PARAM_UTILITY_OPERATOR_ID));
                    jsonParams.put(Constants.RECHARGE_PARAM_REMARKS, mapOfRequestParams.get(Constants.RECHARGE_PARAM_REMARKS));
                    break;

                case Constants.SERVICE_MOBILE_RECHARGES:




                    jsonParams.put(Constants.rechargeNumber, mapOfRequestParams.get(Constants.rechargeNumber));
                    jsonParams.put(Constants.rechargeAmount, mapOfRequestParams.get(Constants.rechargeAmount));
                    jsonParams.put(Constants.servicetype, mapOfRequestParams.get(Constants.servicetype));
                    jsonParams.put(Constants.OperatorName, mapOfRequestParams.get(Constants.OperatorName));
                    jsonParams.put(Constants.OperatorId, mapOfRequestParams.get(Constants.OperatorId));
                    jsonParams.put(Constants.typeofrc, mapOfRequestParams.get(Constants.typeofrc));
                    jsonParams.put(Constants.complete_j, mapOfRequestParams.get(Constants.complete_j));
                    jsonParams.put(Constants.PgMeTrnRefNo, mapOfRequestParams.get(Constants.PgMeTrnRefNo));
                    jsonParams.put(Constants.TrnAmt, mapOfRequestParams.get(Constants.TrnAmt));
                    jsonParams.put(Constants.StatusCode, mapOfRequestParams.get(Constants.StatusCode));
                    jsonParams.put(Constants.StatusDesc, mapOfRequestParams.get(Constants.StatusDesc));
                    jsonParams.put(Constants.TrnReqDate, mapOfRequestParams.get(Constants.TrnReqDate));
                    jsonParams.put(Constants.ResponseCode, mapOfRequestParams.get(Constants.ResponseCode));
                    jsonParams.put(Constants.Rrn, mapOfRequestParams.get(Constants.Rrn));
                    jsonParams.put(Constants.AuthZCode, mapOfRequestParams.get(Constants.AuthZCode));
                    jsonParams.put(Constants.Desc, mapOfRequestParams.get(Constants.Desc));
                    jsonParams.put(Constants.userid, mapOfRequestParams.get(Constants.userid));
                    jsonParams.put(Constants.usertype, mapOfRequestParams.get(Constants.usertype));
                    jsonParams.put(Constants.usewallet, mapOfRequestParams.get(Constants.usewallet));
                    jsonParams.put(Constants.wallet_amount, mapOfRequestParams.get(Constants.wallet_amount));
                    jsonParams.put(Constants.rem_amt, mapOfRequestParams.get(Constants.rem_amt));

                    jsonParams.put(Constants.admin_m_type, mapOfRequestParams.get(Constants.admin_m_type));
                    jsonParams.put(Constants.TotalFare_org, mapOfRequestParams.get(Constants.TotalFare_org));
                    jsonParams.put(Constants.dis_comision, mapOfRequestParams.get(Constants.dis_comision));
                    jsonParams.put(Constants.agent_markup, mapOfRequestParams.get(Constants.agent_markup));



                    break;






                case Constants.SERVICE_VIEW_PLANS:
                    jsonParams.put(Constants.VIEW_PLANS_PARAM_CIRCLE_ID, mapOfRequestParams.get(Constants.VIEW_PLANS_PARAM_CIRCLE_ID));
                    jsonParams.put(Constants.VIEW_PLANS_PARAM_OPERATOR_ID, mapOfRequestParams.get(Constants.VIEW_PLANS_PARAM_OPERATOR_ID));
                    jsonParams.put(Constants.VIEW_PLANS_PARAM_TYPE, mapOfRequestParams.get(Constants.VIEW_PLANS_PARAM_TYPE));
                    break;
                case Constants.SERVICE_BLOCK_BUS_TICKET:
                    Uri parsed1 = Uri.parse(url);
                    client.setCredentials(
                            new AuthScope(parsed1.getHost(), parsed1.getPort() == -1 ? 80 : parsed1.getPort()),
                            new UsernamePasswordCredentials(
                                    "ram0438",
                                    "Ram@t4j"
                            )
                    );

                    jsonParams = new JSONObject(mapOfRequestParams.get(Constants.BLOCK_TICKET_PARAM_API_Block_TICKET_REQUEST));
                    //jsonParams.putOpt(Constants.BLOCK_TICKET_PARAM_API_Block_TICKET_REQUEST, AppController.getInstance().getBlockTicket());
                    break;
                case Constants.SERVICE_SEARCH_FLIGHTS:
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS));
                    break;
                case Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS:
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS));
                    break;
                case Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP:
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_RETURN_DATE, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_RETURN_DATE));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS));
                    jsonParams.put(Constants.SERVICE_INTERNATIONAL_FLIGHTS_PARAM_JOURNEY_TYPE, mapOfRequestParams.get(Constants.SERVICE_INTERNATIONAL_FLIGHTS_PARAM_JOURNEY_TYPE));
                    break;
                case Constants.SERVICE_INTERNATIONAL_SEARCH_FLIGHTS_ROUND_TRIP:
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_RETURN_DATE, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_RETURN_DATE));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT));
                    jsonParams.put(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS, mapOfRequestParams.get(Constants.SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS));
                    jsonParams.put(Constants.SERVICE_INTERNATIONAL_FLIGHTS_PARAM_JOURNEY_TYPE, mapOfRequestParams.get(Constants.SERVICE_INTERNATIONAL_FLIGHTS_PARAM_JOURNEY_TYPE));
                    break;
                case Constants.SERVICE_LOGIN:
                    jsonParams.put(Constants.LOGIN_PARAM_EMAIL_ID, mapOfRequestParams.get(Constants.LOGIN_PARAM_EMAIL_ID));
                    jsonParams.put(Constants.LOGIN_PARAM_PASSWORD, mapOfRequestParams.get(Constants.LOGIN_PARAM_PASSWORD));
                    jsonParams.put(Constants.LOGIN_PARAM_SOCIAL, mapOfRequestParams.get(Constants.LOGIN_PARAM_SOCIAL));
                    break;
                case Constants.SERVICE_SIGN_UP:
                    jsonParams.put(Constants.SIGN_UP_PARAM_NAME, mapOfRequestParams.get(Constants.SIGN_UP_PARAM_NAME));
                    jsonParams.put(Constants.SIGN_UP_PARAM_EMAIL, mapOfRequestParams.get(Constants.SIGN_UP_PARAM_EMAIL));
                    jsonParams.put(Constants.SIGN_UP_PARAM_MOBILE_NUMBER, mapOfRequestParams.get(Constants.SIGN_UP_PARAM_MOBILE_NUMBER));
                    jsonParams.put(Constants.SIGN_UP_PARAM_PASSWORD, mapOfRequestParams.get(Constants.SIGN_UP_PARAM_PASSWORD));
                    jsonParams.put(Constants.SIGN_UP_PARAM_REFER, mapOfRequestParams.get(Constants.SIGN_UP_PARAM_REFER));
                    jsonParams.put(Constants.SIGN_UP_PARAM_STATUS_REF, mapOfRequestParams.get(Constants.SIGN_UP_PARAM_STATUS_REF));
                    jsonParams.put(Constants.SIGN_UP_PARAM_PROMOCODE, mapOfRequestParams.get(Constants.SIGN_UP_PARAM_PROMOCODE));



                    break;
                case Constants.SERVICE_FORGOT_PASSWORD:
                    jsonParams.put(Constants.FORGOT_PASSWORD_PARAM_USER_NAME, mapOfRequestParams.get(Constants.FORGOT_PASSWORD_PARAM_USER_NAME));
                    break;
                case Constants.SERVICE_INTERNATIONAL_FLIGHTS_ONE_WAY:
                    jsonParams = new JSONObject(mapOfRequestParams.get(Constants.FLIGHT_TICKET_BOOKING_DETAILS));
                    break;
                case Constants.SERVICE_DOMESTIC_FLIGHT_BOOKING:
                    jsonParams = new JSONObject(mapOfRequestParams.get(Constants.FLIGHT_TICKET_BOOKING_DETAILS));
                    break;
                case Constants.SERVICE_STORE_BUS_DETAILS:
                    jsonParams = new JSONObject(mapOfRequestParams.get(Constants.BUS_DETAILS_STORE_PARAM_BODY));
                    break;
                case Constants.SERVICE_BUS_BOOKING_HISTORY:
                    jsonParams.put(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID, mapOfRequestParams.get(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID));
                    break;
                case Constants.SERVICE_BUS_TICKET_OVER_VIEW:
                    jsonParams.put(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID, mapOfRequestParams.get(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID));
                    jsonParams.put(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID, mapOfRequestParams.get(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID));
                    break;
                case Constants.SERVICE_SMS:
                    jsonParams.put(Constants.SMS_PARAM_PHONE, mapOfRequestParams.get(Constants.SMS_PARAM_PHONE));
                    jsonParams.put(Constants.SMS_PARAM_MESSAGE, mapOfRequestParams.get(Constants.SMS_PARAM_MESSAGE));
                    break;
                case Constants.SERVICE_EDIT_PROFILE:
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_USER_ID, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_USER_ID));
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_NAME, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_NAME));
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_ADDRESS, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_ADDRESS));
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_PIN_CODE, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_PIN_CODE));
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_STATE, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_STATE));
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_COUNTRY, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_COUNTRY));
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_STATE, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_STATE));
                    jsonParams.put(Constants.EDIT_PROFILE_PARAM_DOB, mapOfRequestParams.get(Constants.EDIT_PROFILE_PARAM_DOB));




                    break;
                case Constants.SERVICE_CHANGE_PASSWORD:
                    jsonParams.put(Constants.CHANGE_PASSWORD_PARAM_USER_ID, mapOfRequestParams.get(Constants.CHANGE_PASSWORD_PARAM_USER_ID));
                    jsonParams.put(Constants.CHANGE_PASSWORD_PARAM_NEW_PASSWORD, mapOfRequestParams.get(Constants.CHANGE_PASSWORD_PARAM_NEW_PASSWORD));
                    break;
                case Constants.SERVICE_ADD_MONEY:
                    jsonParams.put(Constants.WALLET_BALANCE_PARAM_ORDER_ID, mapOfRequestParams.get(Constants.WALLET_BALANCE_PARAM_ORDER_ID));
                    jsonParams.put(Constants.WALLET_BALANCE_PARAM_REFERENCE_NUMBER, mapOfRequestParams.get(Constants.WALLET_BALANCE_PARAM_REFERENCE_NUMBER));
                    jsonParams.put(Constants.WALLET_BALANCE_PARAM_STATUS_CODE, mapOfRequestParams.get(Constants.WALLET_BALANCE_PARAM_STATUS_CODE));
                    jsonParams.put(Constants.WALLET_BALANCE_PARAM_TRANSACTION_AMOUNT, mapOfRequestParams.get(Constants.WALLET_BALANCE_PARAM_TRANSACTION_AMOUNT));
                    jsonParams.put(Constants.WALLET_BALANCE_PARAM_TRANSACTION_REQUEST_DATE, mapOfRequestParams.get(Constants.WALLET_BALANCE_PARAM_TRANSACTION_REQUEST_DATE));
                    jsonParams.put(Constants.WALLET_BALANCE_PARAM_USER_ID, mapOfRequestParams.get(Constants.WALLET_BALANCE_PARAM_USER_ID));
                    break;
                case Constants.SERVICE_PRE_FARE_DOMESTIC:
                    jsonParams = new JSONObject(mapOfRequestParams.get(Constants.PRE_FARE_DOMESTIC_PARAM));
                    Log.e("json",jsonParams.toString());
                    break;
                case Constants.SERVICE_CANCEL_TICKET:


                    jsonParams.put(Constants.FLIGHT_CANCEL_PARAM_PNR, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_PARAM_PNR));
                    jsonParams.put(Constants.FLIGHT_CANCEL_PARAM_FLIGHT_TYPE, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_PARAM_FLIGHT_TYPE));
                    jsonParams.put(Constants.FLIGHT_CANCEL_PARAM_REFUND_TO, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_PARAM_REFUND_TO));
                    jsonParams.put(Constants.FLIGHT_CANCEL_ONWARD, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_ONWARD));
                    jsonParams.put(Constants.FLIGHT_CANCEL_RETURN, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_RETURN));
                    Log.e("json",jsonParams.toString());
                    Log.e("json",mapOfRequestParams.toString());

                    break;
                case Constants.SERVICE_CANCEL_BUS_TICKET:
                    Uri cancelParsed = Uri.parse(url);
                    client.setCredentials(
                            new AuthScope(cancelParsed.getHost(), cancelParsed.getPort() == -1 ? 80 : cancelParsed.getPort()),
                            new UsernamePasswordCredentials(
                                    "ram0438",
                                    "Ram@t4j"
                            )
                    );
                    jsonParams = new JSONObject(mapOfRequestParams.get(Constants.CANCEL_BUS_TICKET_PARAM_OBJECT));
                    break;
                case Constants.SERVICE_STORE_CANCEL_BUS_DETAILS:
                    jsonParams.put(Constants.CANCEL_BUS_TICKET_PARAM_TOTAL_REFUND_AMOUNT,mapOfRequestParams.get(Constants.CANCEL_BUS_TICKET_PARAM_TOTAL_REFUND_AMOUNT));
                    jsonParams.put(Constants.CANCEL_BUS_TICKET_PARAM_CANCELLATION_CHARGES,mapOfRequestParams.get(Constants.CANCEL_BUS_TICKET_PARAM_CANCELLATION_CHARGES));
                    jsonParams.put(Constants.CANCEL_BUS_TICKET_PARAM_REFUND_TYPE,mapOfRequestParams.get(Constants.CANCEL_BUS_TICKET_PARAM_REFUND_TYPE));
                    jsonParams.put(Constants.CANCEL_BUS_TICKET_PARAM_REFERENCE_NUMBER,mapOfRequestParams.get(Constants.CANCEL_BUS_TICKET_PARAM_REFERENCE_NUMBER));
                    break;
                default:
                    break;
            }
            StringEntity entity = null;
            try {
                entity = new StringEntity(jsonParams.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            CustomSSLSocketFactory sslSocketFactory = trustAllSSLCertificates();
            if (sslSocketFactory != null)
                client.setSSLSocketFactory(sslSocketFactory);
            if (contentType.equalsIgnoreCase(Constants.CONTENT_TYPE_EMPTY_STRING))
                client.post(context, url, entity, null, responseHandler);
            else
                client.post(context, url, entity, contentType, responseHandler);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private static CustomSSLSocketFactory trustAllSSLCertificates() {
        KeyStore trustStore;
        CustomSSLSocketFactory sslSocketFactory = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sslSocketFactory = new CustomSSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception exception) {
            Log.d("exception", exception.getMessage());
        }
        return sslSocketFactory;
    }


    public static void postCancel(String url, Map<String, Object> mapOfRequestParams, AsyncHttpResponseHandler responseHandler, String contentType, int requestType) {
        Log.i("Get Info", url + mapOfRequestParams);
        client.setTimeout(120 * 1000);
        JSONObject jsonParams = new JSONObject();
        try {
            switch (requestType) {
                case Constants.SERVICE_CANCEL_TICKET:


                    jsonParams.put(Constants.FLIGHT_CANCEL_PARAM_PNR, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_PARAM_PNR));
                    jsonParams.put(Constants.FLIGHT_CANCEL_PARAM_FLIGHT_TYPE, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_PARAM_FLIGHT_TYPE));
                    jsonParams.put(Constants.FLIGHT_CANCEL_PARAM_REFUND_TO, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_PARAM_REFUND_TO));
                    jsonParams.put(Constants.FLIGHT_CANCEL_ONWARD, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_ONWARD));
                    jsonParams.put(Constants.FLIGHT_CANCEL_RETURN, mapOfRequestParams.get(Constants.FLIGHT_CANCEL_RETURN));
                    Log.e("json",jsonParams.toString());
                    Log.e("json",mapOfRequestParams.toString());

                    break;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CustomSSLSocketFactory sslSocketFactory = trustAllSSLCertificates();
        if (sslSocketFactory != null)
            client.setSSLSocketFactory(sslSocketFactory);
        if (contentType.equalsIgnoreCase(Constants.CONTENT_TYPE_EMPTY_STRING))
            client.post(context, url, entity, null, responseHandler);
        else
            client.post(context, url, entity, contentType, responseHandler);
    }
}
