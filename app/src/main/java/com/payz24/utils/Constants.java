package com.payz24.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.payz24.BuildConfig;
import com.payz24.application.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by mahesh on 1/8/2018.
 */

public class Constants {

    public static final String UTILITIES_BASE_URL = "http://dmt.secure365.com:6060";
   // public static final String TRAVEX_SOFT_UTILITIES_BASE_URL = "http://travexsoftsol.com/projects/payz24";
    public static final String TRAVEX_SOFT_UTILITIES_BASE_URL = "https://payz24.com";
    //public static final String BUS_BASE_URL = getBaseUrl();

    public static final String BUS_BASE_URL = "http://agent.etravelsmart.com/etsAPI/api";

    Activity activity;

    public Constants( Activity activity)
    {
       this.activity=activity;
    }

    /*public static String getBaseUrl() {
     if (BuildConfig.DEBUG) {
      return "http://test.etravelsmart.com/etsAPI/api";
     }
     return "http://agent.etravelsmart.com/etsAPI/api";
    }*/

    public static final String IMAGE_URL_BASE = "http://www.travexsoftsol.com/projects/nt/assets/images/airline_logo/";
    public static final int SERVICE_UTILITIES_LIST = 1;
    public static final int SERVICE_UTILITIES_OPERATORS_LIST = 2;
    public static final int SERVICE_MOBILE_RECHARGE = 3;
    public static final int SERVICE_VIEW_PLANS = 4;
    public static final int SERVICE_BUS_STATIONS = 5;
    public static final int SERVICE_GET_AVAILABLE_BUS = 6;
    public static final int SERVICE_BUS_LAYOUT = 7;
    public static final int SERVICE_BLOCK_BUS_TICKET = 8;
    public static final int SERVICE_FLIGHT_STATIONS = 9;
    public static final int SERVICE_SEARCH_FLIGHTS = 10;
    public static final int SERVICE_DOMESTIC_SEARCH_FLIGHTS = 11;
    public static final int SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP = 12;
    public static final int SERVICE_LOGIN = 13;
    public static final int SERVICE_SIGN_UP = 14;
    public static final int SERVICE_FORGOT_PASSWORD = 15;
    public static final int SERVICE_BUS_SEAT_BOOKING = 16;
    public static final int SERVICE_INTERNATIONAL_SEARCH_FLIGHTS_ROUND_TRIP = 17;
    public static final int SERVICE_INTERNATIONAL_FLIGHTS_ONE_WAY = 18;
    public static final int SERVICE_DOMESTIC_FLIGHT_BOOKING = 19;
    public static final int SERVICE_STORE_BUS_DETAILS = 20;
    public static final int SERVICE_BUS_BOOKING_HISTORY = 21;
    public static final int SERVICE_FLIGHT_BOOKING_HISTORY = 22;
    public static final int SERVICE_BUS_TICKET_OVER_VIEW = 23;
    public static final int SERVICE_FLIGHT_TICKET_OVERVIEW = 24;
    public static final int SERVICE_SMS = 25;
    public static final int SERVICE_EDIT_PROFILE = 26;
    public static final int SERVICE_CHANGE_PASSWORD = 27;
    public static final int SERVICE_ADD_MONEY = 28;
    public static final int SERVICE_PRE_FARE_DOMESTIC = 29;
    public static final int SERVICE_PROFILE = 30;
    public static final int SERVICE_CANCEL_TICKET = 31;
    public static final int SERVICE_CANCEL_BUS_TICKET = 32;
    public static final int SERVICE_STORE_CANCEL_BUS_DETAILS = 33;
    public static final int SERVICE_MARK_UP_FEE = 34;
    public static final int SERVICE_MOBILE_RECHARGES = 35;
    public static final int SERVICE_MOBILE_GET_RECHARGES = 36;


    //status error
    public static final int STATUS_CODE_SUCCESS = 200;
    public static final int STATUS_CODE_ERROR = 500;

    //permissions
    public static final int REQUEST_CODE_FOR_EXTERNAL_STORAGE_PERMISSION = 0;
    public static final int REQUEST_CODE_FOR_READ_CONTACTS_PERMISSION = 1;


    public static final String CONTENT_TYPE_EMPTY_STRING = "";
    public static final String CONTENT_TYPE_JSON = "application/json";


    public static final String UTILITY_SERVICE_ID = "utilityServiceId";

    //recharge
    public static final String MOBILE_RECHARGE_PARAM_IS_PREPAID = "isPrepaid";

    //recharge
    public static final String RECHARGE_PARAM_RECHARGE_NUMBER = "rechargeNumber";
    public static final String RECHARGE_PARAM_RECHARGE_AMOUNT = "rechargeAmount";
    public static final String RECHARGE_PARAM_UTILITY_OPERATOR_ID = "utilityOperatorId";
    public static final String RECHARGE_PARAM_ACCOUNT = "account";
    public static final String RECHARGE_PARAM_REMARKS = "remarks";

    /****New Recharge****/
    public static final String rechargeNumber = "rechargeNumber";
    public static final String rechargeAmount = "rechargeAmount";
    public static final String servicetype = "servicetype";
    public static final String OperatorName = "OperatorName";
    public static final String OperatorId = "OperatorId";
    public static final String typeofrc = "typeofrc";
    public static final String complete_j = "complete_j";
    public static final String PgMeTrnRefNo = "PgMeTrnRefNo";
    public static final String TrnAmt = "TrnAmt";
    public static final String StatusCode = "StatusCode";
    public static final String StatusDesc = "StatusDesc";
    public static final String TrnReqDate = "TrnReqDate";
    public static final String ResponseCode = "ResponseCode";
    public static final String Rrn = "Rrn";
    public static final String AuthZCode = "AuthZCode";
    public static final String Desc = "Desc";
    public static final String userid = "userid";
    public static final String usertype = "usertype";
    public static final String usewallet = "usewallet";
    public static final String wallet_amount = "wallet_amount";
    public static final String rem_amt = "rem_amt";
    public static final String TotalFare_org = "TotalFare_org";
    public static final String dis_comision = "dis_comision";
    public static final String agent_markup = "agent_markup";
    public static final String admin_m_type = "admin_m_type";


    //view plans params
    public static final String VIEW_PLANS_PARAM_CIRCLE_ID = "circleid";
    public static final String VIEW_PLANS_PARAM_OPERATOR_ID = "operatorid";
    public static final String VIEW_PLANS_PARAM_TYPE = "type";

    //available bus params
    public static final String AVAILABLE_BUS_PARAM_DOJ = "doj";
    public static final String AVAILABLE_BUS_PARAM_DESTINATION = "destinationCity";
    public static final String AVAILABLE_BUS_PARAM_SOURCE = "sourceCity";

    //bus layout params
    public static final String BUS_LAYOUT_PARAM_SOURCE_CITY = "sourceCity";
    public static final String BUS_LAYOUT_PARAM_DESTINATION_CITY = "destinationCity";
    public static final String BUS_LAYOUT_PARAM_DOJ = "doj";
    public static final String BUS_LAYOUT_PARAM_INVENTORY_TYPE = "inventoryType";
    public static final String BUS_LAYOUT_PARAM_ROUTE_SCHEDULE_ID = "routeScheduleId";

    //search international flights
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM = "from";
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_TO = "to";
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_FROM_DATE = "fdate";
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_RETURN_DATE = "rdate";
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_ADULT = "adult";
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CHILD = "child";
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_INFANT = "infant";
    public static final String SEARCH_INTERNATIONAL_FLIGHTS_PARAM_CLASS = "pclass";
    public static final String SERVICE_INTERNATIONAL_FLIGHTS_PARAM_JOURNEY_TYPE = "journey_type";


    public static final String SEARCH_RESULTS_MAP = "map";

    //Login params
    public static final String LOGIN_PARAM_EMAIL_ID = "email";
    public static final String LOGIN_PARAM_PASSWORD = "password";
    public static final String LOGIN_PARAM_SOCIAL = "sociallogin";

    //Signup params
    public static final String SIGN_UP_PARAM_NAME = "name";
    public static final String SIGN_UP_PARAM_EMAIL = "email";
    public static final String SIGN_UP_PARAM_MOBILE_NUMBER = "mobile";
    public static final String SIGN_UP_PARAM_PASSWORD = "password";
    public static final String SIGN_UP_PARAM_REFER = "ref_userid";
    public static final String SIGN_UP_PARAM_STATUS_REF    = "is_status_ref";
    public static final String SIGN_UP_PARAM_PROMOCODE = "promocode";

    //forgot password
    public static final String FORGOT_PASSWORD_PARAM_USER_NAME = "mobile";

    //block ticket
    public static final String BUS_SEAT_BOOKING_PARAM_BLOCK_TICKET_KEY = "blockTicketKey";
    //flight ticket
    public static final String FLIGHT_TICKET_BOOKING_DETAILS = "flightTicketBookingDetails";
    public static final String BUS_DETAILS_STORE_PARAM_BODY = "Body";
    public static final String FLIGHT_TICKET_OVER_VIEW_SCREEN_FID = "fid";
    public static final String SMS_PARAM_PHONE = "phone";
    public static final String SMS_PARAM_MESSAGE = "message";
    public static final String CHANGE_PASSWORD_PARAM_USER_ID = "user_id";
    public static final String CHANGE_PASSWORD_PARAM_CHANGE_PASSWORD = "oldPassword";
    public static final String CHANGE_PASSWORD_PARAM_NEW_PASSWORD = "password";
    public static String BLOCK_TICKET_PARAM_API_Block_TICKET_REQUEST = "blockTicketRequest";

    //BUS booking history
    public static final String BUS_BOOKING_HISTORY_PARAM_USER_ID = "userid";
    //FLIGHT booking history
    public static final String FLIGHT_BOOKING_HISTORY_PARAM_USER_ID = "userid";
    public static final String BUS_TICKET_OVER_VIEW_SCREEN_BID = "bid";
    public static final String TYPE = "type";

    //edit profile
    public static final String EDIT_PROFILE_PARAM_USER_ID = "user_id";
    public static final String EDIT_PROFILE_PARAM_NAME = "name";
    public static final String EDIT_PROFILE_PARAM_ADDRESS = "address";
    public static final String EDIT_PROFILE_PARAM_PIN_CODE = "pin_code";
    public static final String EDIT_PROFILE_PARAM_STATE = "ustate";
    public static final String EDIT_PROFILE_PARAM_CITY = "ucity";
    public static final String EDIT_PROFILE_PARAM_COUNTRY = "ucountry";
    public static final String EDIT_PROFILE_PARAM_DOB = "dob";


    //wallet balance
    public static final String WALLET_BALANCE_PARAM_ORDER_ID = "orderId";
    public static final String WALLET_BALANCE_PARAM_TRANSACTION_AMOUNT = "TrnAmt";
    public static final String WALLET_BALANCE_PARAM_REFERENCE_NUMBER = "PgMeTrnRefNo";
    public static final String WALLET_BALANCE_PARAM_STATUS_CODE = "StatusCode";
    public static final String WALLET_BALANCE_PARAM_TRANSACTION_REQUEST_DATE = "TrnReqDate";
    public static final String WALLET_BALANCE_PARAM_USER_ID = "userId";


    public static final String PRE_FARE_DOMESTIC_PARAM = "domesticParam";

    //profile param user id
    public static final String PROFILE_PARAM_USER_ID = "uid";

    // cancel flight ticket params
    public static final String FLIGHT_CANCEL_PARAM_PNR = "pnr";
    public static final String FLIGHT_CANCEL_PARAM_FLIGHT_TYPE = "flight_type";
    public static final String FLIGHT_CANCEL_PARAM_REFUND_TO = "refundto";
    public static final String FLIGHT_CANCEL_ONWARD= "oneway";
    public static final String FLIGHT_CANCEL_RETURN = "onewayr";

    //cancel bus ticket params
    public static final String CANCEL_BUS_TICKET_PARAM_OBJECT = "busTicketObject";
    public static final String CANCEL_BUS_TICKET_PARAM_TOTAL_REFUND_AMOUNT = "totalRefundAmount";
    public static final String CANCEL_BUS_TICKET_PARAM_CANCELLATION_CHARGES = "cancellationCharges";
    public static final String CANCEL_BUS_TICKET_PARAM_REFUND_TYPE = "refund_type";
    public static final String CANCEL_BUS_TICKET_PARAM_REFERENCE_NUMBER = "ref_number";




}





