package com.payz24.responseModels.domesticPreFare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/19/2018.
 */

public class Search {

    @SerializedName("searchData")
    @Expose
    private SearchData searchData;
    @SerializedName("flight_id")
    @Expose
    private String flightId;
    @SerializedName("flightDataOneway")
    @Expose
    private FlightDataOneway flightDataOneway;
    @SerializedName("flightDataReturn")
    @Expose
    private FlightDataReturn flightDataReturn;

    public Search(SearchData searchData, String flightId, FlightDataOneway flightDataOneway, FlightDataReturn flightDataReturn) {
        this.searchData = searchData;
        this.flightId = flightId;
        this.flightDataOneway = flightDataOneway;
        if (flightDataReturn != null) {
            this.flightDataReturn = flightDataReturn;
        }
    }

    public SearchData getSearchData() {
        return searchData;
    }

    public void setSearchData(SearchData searchData) {
        this.searchData = searchData;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public FlightDataOneway getFlightDataOneway() {
        return flightDataOneway;
    }

    public void setFlightDataOneway(FlightDataOneway flightDataOneway) {
        this.flightDataOneway = flightDataOneway;
    }

    public FlightDataReturn getFlightDataReturn() {
        return flightDataReturn;
    }

    public void setFlightDataReturn(FlightDataReturn flightDataReturn) {
        this.flightDataReturn = flightDataReturn;
    }
}
