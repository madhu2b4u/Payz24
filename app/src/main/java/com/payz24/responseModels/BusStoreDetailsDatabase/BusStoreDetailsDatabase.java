package com.payz24.responseModels.BusStoreDetailsDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 3/2/2018.
 */

public class BusStoreDetailsDatabase {

    @SerializedName("user_data")
    @Expose
    private UserData userData;
    @SerializedName("searchData")
    @Expose
    private SearchData searchData;
    @SerializedName("seatVals")
    @Expose
    private String seatVals;
    @SerializedName("busval")
    @Expose
    private String busval;
    @SerializedName("finalSeats")
    @Expose
    private List<FinalSeat> finalSeats = null;
    @SerializedName("busBookingFinalData")
    @Expose
    private BusBookingFinalData busBookingFinalData;
    @SerializedName("busData")
    @Expose
    private BusData busData;

    public BusStoreDetailsDatabase(UserData userData, SearchData searchData, String seatValues, String busval, List<FinalSeat> listOfFinalSeats, BusBookingFinalData busBookingFinalData, BusData busData) {
        this.userData = userData;
        this.searchData = searchData;
        this.seatVals = seatValues;
        this.busval = busval;
        this.finalSeats = listOfFinalSeats;
        this.busBookingFinalData = busBookingFinalData;
        this.busData = busData;
    }


    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public SearchData getSearchData() {
        return searchData;
    }

    public void setSearchData(SearchData searchData) {
        this.searchData = searchData;
    }

    public String getSeatVals() {
        return seatVals;
    }

    public void setSeatVals(String seatVals) {
        this.seatVals = seatVals;
    }

    public String getBusval() {
        return busval;
    }

    public void setBusval(String busval) {
        this.busval = busval;
    }

    public List<FinalSeat> getFinalSeats() {
        return finalSeats;
    }

    public void setFinalSeats(List<FinalSeat> finalSeats) {
        this.finalSeats = finalSeats;
    }

    public BusBookingFinalData getBusBookingFinalData() {
        return busBookingFinalData;
    }

    public void setBusBookingFinalData(BusBookingFinalData busBookingFinalData) {
        this.busBookingFinalData = busBookingFinalData;
    }

    public BusData getBusData() {
        return busData;
    }

    public void setBusData(BusData busData) {
        this.busData = busData;
    }
}
