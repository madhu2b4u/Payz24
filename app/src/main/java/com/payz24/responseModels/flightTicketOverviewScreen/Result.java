package com.payz24.responseModels.flightTicketOverviewScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 3/9/2018.
 */

public class Result {

    @SerializedName("fid")
    @Expose
    private String fid;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pnr")
    @Expose
    private String pnr;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("journeyType")
    @Expose
    private String journeyType;
    @SerializedName("stops")
    @Expose
    private String stops;
    @SerializedName("from_city")
    @Expose
    private String fromCity;
    @SerializedName("departDate")
    @Expose
    private String departDate;
    @SerializedName("to_city")
    @Expose
    private String toCity;
    @SerializedName("returnDate")
    @Expose
    private String returnDate;
    @SerializedName("Noadults")
    @Expose
    private String noadults;
    @SerializedName("Nochilds")
    @Expose
    private String nochilds;
    @SerializedName("Noinfants")
    @Expose
    private String noinfants;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("booked_date")
    @Expose
    private String bookedDate;
    @SerializedName("TotalPaid")
    @Expose
    private String totalPaid;
    @SerializedName("return")
    @Expose
    private List<Return> _return = null;
    @SerializedName("Onward")
    @Expose
    private List<Onward> onward = null;
    @SerializedName("Passenger_details")
    @Expose
    private List<PassengerDetail> passengerDetails = null;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getNoadults() {
        return noadults;
    }

    public void setNoadults(String noadults) {
        this.noadults = noadults;
    }

    public String getNochilds() {
        return nochilds;
    }

    public void setNochilds(String nochilds) {
        this.nochilds = nochilds;
    }

    public String getNoinfants() {
        return noinfants;
    }

    public void setNoinfants(String noinfants) {
        this.noinfants = noinfants;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public List<Return> getReturn() {
        return _return;
    }

    public void setReturn(List<Return> _return) {
        this._return = _return;
    }

    public List<Onward> getOnward() {
        return onward;
    }

    public void setOnward(List<Onward> onward) {
        this.onward = onward;
    }

    public List<PassengerDetail> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(List<PassengerDetail> passengerDetails) {
        this.passengerDetails = passengerDetails;
    }

}
