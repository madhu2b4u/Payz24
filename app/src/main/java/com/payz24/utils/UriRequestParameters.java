package com.payz24.utils;

import com.loopj.android.http.RequestParams;

import java.util.Map;

/**
 * Created by mahesh on 1/8/2018.
 */

public class UriRequestParameters {

    public static RequestParams getRequestParamsForType(int requestType, Map<String, String> mapOfRequestParams) {

        RequestParams params = null;
        switch (requestType) {
            case Constants.SERVICE_UTILITIES_OPERATORS_LIST:
                params = new RequestParams();
                params.put(Constants.UTILITY_SERVICE_ID, mapOfRequestParams.get(Constants.UTILITY_SERVICE_ID));
                params.put(Constants.MOBILE_RECHARGE_PARAM_IS_PREPAID, mapOfRequestParams.get(Constants.MOBILE_RECHARGE_PARAM_IS_PREPAID));
                break;
            case Constants.SERVICE_GET_AVAILABLE_BUS:
                params = new RequestParams();
                params.put(Constants.AVAILABLE_BUS_PARAM_SOURCE, mapOfRequestParams.get(Constants.AVAILABLE_BUS_PARAM_SOURCE));
                params.put(Constants.AVAILABLE_BUS_PARAM_DESTINATION, mapOfRequestParams.get(Constants.AVAILABLE_BUS_PARAM_DESTINATION));
                params.put(Constants.AVAILABLE_BUS_PARAM_DOJ, mapOfRequestParams.get(Constants.AVAILABLE_BUS_PARAM_DOJ));
                break;
            case Constants.SERVICE_BUS_LAYOUT:
                params = new RequestParams();
                params.put(Constants.BUS_LAYOUT_PARAM_SOURCE_CITY, mapOfRequestParams.get(Constants.BUS_LAYOUT_PARAM_SOURCE_CITY));
                params.put(Constants.BUS_LAYOUT_PARAM_DESTINATION_CITY, mapOfRequestParams.get(Constants.BUS_LAYOUT_PARAM_DESTINATION_CITY));
                params.put(Constants.BUS_LAYOUT_PARAM_DOJ, mapOfRequestParams.get(Constants.BUS_LAYOUT_PARAM_DOJ));
                params.put(Constants.BUS_LAYOUT_PARAM_INVENTORY_TYPE, mapOfRequestParams.get(Constants.BUS_LAYOUT_PARAM_INVENTORY_TYPE));
                params.put(Constants.BUS_LAYOUT_PARAM_ROUTE_SCHEDULE_ID, mapOfRequestParams.get(Constants.BUS_LAYOUT_PARAM_ROUTE_SCHEDULE_ID));
                break;
            case Constants.SERVICE_BUS_SEAT_BOOKING:
                params = new RequestParams();
                params.put(Constants.BUS_SEAT_BOOKING_PARAM_BLOCK_TICKET_KEY, mapOfRequestParams.get(Constants.BUS_SEAT_BOOKING_PARAM_BLOCK_TICKET_KEY));
                break;
            case Constants.SERVICE_BUS_BOOKING_HISTORY:
                params = new RequestParams();
                params.put(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID, mapOfRequestParams.get(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID));
                break;
            case Constants.SERVICE_FLIGHT_BOOKING_HISTORY:
                params = new RequestParams();
                params.put(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID, mapOfRequestParams.get(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID));
                break;

            case Constants.SERVICE_BUS_TICKET_OVER_VIEW:
                params = new RequestParams();
                params.put(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID, mapOfRequestParams.get(Constants.BUS_BOOKING_HISTORY_PARAM_USER_ID));
                params.put(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID, mapOfRequestParams.get(Constants.BUS_TICKET_OVER_VIEW_SCREEN_BID));
                break;
            case Constants.SERVICE_FLIGHT_TICKET_OVERVIEW:
                params = new RequestParams();
                params.put(Constants.FLIGHT_BOOKING_HISTORY_PARAM_USER_ID, mapOfRequestParams.get(Constants.FLIGHT_BOOKING_HISTORY_PARAM_USER_ID));
                params.put(Constants.FLIGHT_TICKET_OVER_VIEW_SCREEN_FID, mapOfRequestParams.get(Constants.FLIGHT_TICKET_OVER_VIEW_SCREEN_FID));
                break;
            case Constants.SERVICE_PROFILE:
                params = new RequestParams();
                params.put(Constants.PROFILE_PARAM_USER_ID, mapOfRequestParams.get(Constants.PROFILE_PARAM_USER_ID));
                break;
            default:
                break;
        }
        return params;
    }
}
