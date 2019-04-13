package com.payz24.utils;

import java.util.Map;

/**
 * Created by mahesh on 1/8/2018.
 */

public class UrlBuilder {

    public static String formatRequestURLByRequestType(int requestType, Map<String, String> mapOfRequestParams) {
        String requestURL = Constants.UTILITIES_BASE_URL;
        String travexSoftRequestURL = Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;
        String busRequestURL = Constants.BUS_BASE_URL;
        switch (requestType) {
            case Constants.SERVICE_UTILITIES_LIST:
                requestURL = String.format("%s/secureutility/utilityService/list", requestURL);
                break;
            case Constants.SERVICE_UTILITIES_OPERATORS_LIST:
                requestURL = String.format("%s/secureutility/utilityOperator/list", requestURL);
                break;
            case Constants.SERVICE_MOBILE_RECHARGE:
                requestURL = String.format("%s/rest/rechargeapi", travexSoftRequestURL);
                break;
            case Constants.SERVICE_VIEW_PLANS:
                requestURL = String.format("%s/rest/rechargfindplan", travexSoftRequestURL);
                break;
            case Constants.SERVICE_BUS_STATIONS:
                requestURL = String.format("%s/getStations", busRequestURL);
                break;
            case Constants.SERVICE_GET_AVAILABLE_BUS:
                requestURL = String.format("%s/getAvailableBuses?", busRequestURL);
                break;
            case Constants.SERVICE_BUS_LAYOUT:
                requestURL = String.format("%s/getBusLayout?", busRequestURL);
                break;
            case Constants.SERVICE_BLOCK_BUS_TICKET:
                requestURL = String.format("%s/blockTicket", busRequestURL);
                break;
            case Constants.SERVICE_FLIGHT_STATIONS:
                requestURL = String.format("%s/rest/fetchcitieslist", travexSoftRequestURL);
                break;
            case Constants.SERVICE_SEARCH_FLIGHTS:
                requestURL = String.format("%s/rest/flightintllist", travexSoftRequestURL);
                break;
            case Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS:
                requestURL = String.format("%s/rest/flightdomlist", travexSoftRequestURL);
                break;
            case Constants.SERVICE_DOMESTIC_SEARCH_FLIGHTS_ROUND_TRIP:
                requestURL = String.format("%s/rest/flightdomlist", travexSoftRequestURL);
                break;
            case Constants.SERVICE_INTERNATIONAL_SEARCH_FLIGHTS_ROUND_TRIP:
                requestURL = String.format("%s/rest/flightintllist", travexSoftRequestURL);
                break;
            case Constants.SERVICE_LOGIN:
                requestURL = String.format("%s/rest/login", travexSoftRequestURL);
                break;
            case Constants.SERVICE_SIGN_UP:
                requestURL = String.format("%s/rest/register", travexSoftRequestURL);
                break;
            case Constants.SERVICE_FORGOT_PASSWORD:
                requestURL = String.format("%s/rest/forget_pwd", travexSoftRequestURL);
                break;
            case Constants.SERVICE_BUS_SEAT_BOOKING:
                requestURL = String.format("%s/seatBooking?", busRequestURL);
                break;
            case Constants.SERVICE_INTERNATIONAL_FLIGHTS_ONE_WAY:
                requestURL = String.format("%s/rest/flightBookingIntl", travexSoftRequestURL);
                break;
            case Constants.SERVICE_DOMESTIC_FLIGHT_BOOKING:
                requestURL = String.format("%s/rest/flightBookingDom", travexSoftRequestURL);
                break;
            case Constants.SERVICE_STORE_BUS_DETAILS:
                requestURL = String.format("%s/rest/addBusBookingDetails", travexSoftRequestURL);
                break;
            case Constants.SERVICE_BUS_BOOKING_HISTORY:
                requestURL = String.format("%s/rest/listbbooking?type=3&", travexSoftRequestURL);
                break;
            case Constants.SERVICE_FLIGHT_BOOKING_HISTORY:
                requestURL = String.format("%s/rest/listfbooking?type=3&", travexSoftRequestURL);
                break;
            case Constants.SERVICE_BUS_TICKET_OVER_VIEW:
                requestURL = String.format("%s/rest/listbData", travexSoftRequestURL);
                break;
            case Constants.SERVICE_FLIGHT_TICKET_OVERVIEW:
                requestURL = String.format("%s/rest/listfdata", travexSoftRequestURL);
                break;
            case Constants.SERVICE_SMS:
                requestURL = String.format("%s/rest/sendSMS", travexSoftRequestURL);
                break;
            case Constants.SERVICE_EDIT_PROFILE:
                requestURL = String.format("%s/rest/profile_update", travexSoftRequestURL);
                break;
            case Constants.SERVICE_CHANGE_PASSWORD:
                requestURL = String.format("%s/rest/change_pass", travexSoftRequestURL);
                break;
            case Constants.SERVICE_ADD_MONEY:
                requestURL = String.format("%s/rest/fillWallet", travexSoftRequestURL);
                break;
            case Constants.SERVICE_PRE_FARE_DOMESTIC:
                requestURL = String.format("%s/rest/flightDetailsDom", travexSoftRequestURL);
                break;
            case Constants.SERVICE_PROFILE:
                requestURL = String.format("%s/rest/getUserProfileb2c", travexSoftRequestURL);
                break;
            case Constants.SERVICE_CANCEL_TICKET:
                requestURL = String.format("%s/rest/cancelFlightFinal", travexSoftRequestURL);
                break;
            case Constants.SERVICE_CANCEL_BUS_TICKET:
                requestURL = String.format("%s/cancelTicketConfirmation", busRequestURL);
                break;
            case Constants.SERVICE_STORE_CANCEL_BUS_DETAILS:
                requestURL = String.format("%s/rest/cancelBusFinal", travexSoftRequestURL);
                break;
            case Constants.SERVICE_MARK_UP_FEE:
                requestURL = String.format("%s/rest/getAdminMarkup", travexSoftRequestURL);
                break;

            case Constants.SERVICE_MOBILE_RECHARGES:
                requestURL = String.format("%s/rest/rechargeapiNew", travexSoftRequestURL);
                break;

            case Constants.SERVICE_MOBILE_GET_RECHARGES:
                requestURL = String.format("%s/rest/getRechargeList", travexSoftRequestURL);
                break;

            default:
                break;
        }
        return requestURL;
    }
    public static String formatRequestURLByRequestTypeCancel(int requestType, Map<String, Object> mapOfRequestParams) {
        String requestURL = Constants.UTILITIES_BASE_URL;
        String travexSoftRequestURL = Constants.TRAVEX_SOFT_UTILITIES_BASE_URL;
        String busRequestURL = Constants.BUS_BASE_URL;
        switch (requestType) {

            case Constants.SERVICE_CANCEL_TICKET:
                requestURL = String.format("%s/rest/cancelFlightFinal", travexSoftRequestURL);
                break;



            default:
                break;
        }
        return requestURL;
    }
}
