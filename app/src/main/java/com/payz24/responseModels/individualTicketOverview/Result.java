package com.payz24.responseModels.individualTicketOverview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 3/9/2018.
 */

public class Result {

    @SerializedName("bid")
    @Expose
    private String bid;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pnr")
    @Expose
    private String pnr;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("sourceCity")
    @Expose
    private String sourceCity;
    @SerializedName("destinationCity")
    @Expose
    private String destinationCity;
    @SerializedName("journeyDate")
    @Expose
    private String journeyDate;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("arrivalTime")
    @Expose
    private String arrivalTime;
    @SerializedName("serviceProvider")
    @Expose
    private String serviceProvider;
    @SerializedName("bus_type")
    @Expose
    private String busType;
    @SerializedName("boardingPoint")
    @Expose
    private String boardingPoint;
    @SerializedName("droppingPoint")
    @Expose
    private String droppingPoint;
    @SerializedName("bookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("totalFare")
    @Expose
    private String totalFare;
    @SerializedName("booked_date")
    @Expose
    private String bookedDate;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_contact")
    @Expose
    private String userContact;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("etsTicketNo")
    @Expose
    private String etsTicketNo;
    @SerializedName("Passenger_details")
    @Expose
    private List<PassengerDetail> passengerDetails = null;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
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

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(String boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public String getDroppingPoint() {
        return droppingPoint;
    }

    public void setDroppingPoint(String droppingPoint) {
        this.droppingPoint = droppingPoint;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public List<PassengerDetail> getPassengerDetails() {
        return passengerDetails;
    }

    public void setPassengerDetails(List<PassengerDetail> passengerDetails) {
        this.passengerDetails = passengerDetails;
    }

    public String getEtsTicketNo() {
        return etsTicketNo;
    }

    public void setEtsTicketNo(String etsTicketNo) {
        this.etsTicketNo = etsTicketNo;
    }
}
