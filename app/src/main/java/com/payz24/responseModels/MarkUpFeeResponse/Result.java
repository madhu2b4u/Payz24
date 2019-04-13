package com.payz24.responseModels.MarkUpFeeResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/31/2018.
 */

public class Result {
    @SerializedName("flight_markup")
    @Expose
    private String flightMarkup;
    @SerializedName("flight_conv_fee")
    @Expose
    private String flightConvFee;
    @SerializedName("flight_m_type")
    @Expose
    private String flightMType;
    @SerializedName("bus_markup")
    @Expose
    private String busMarkup;
    @SerializedName("bus_conv_fee")
    @Expose
    private String busConvFee;
    @SerializedName("bus_m_type")
    @Expose
    private String busMType;
    @SerializedName("hotel_markup")
    @Expose
    private String hotelMarkup;
    @SerializedName("hotel_conv_fee")
    @Expose
    private String hotelConvFee;
    @SerializedName("hotel_m_type")
    @Expose
    private String hotelMType;

    public String getFlightMarkup() {
        return flightMarkup;
    }

    public void setFlightMarkup(String flightMarkup) {
        this.flightMarkup = flightMarkup;
    }

    public String getFlightConvFee() {
        return flightConvFee;
    }

    public void setFlightConvFee(String flightConvFee) {
        this.flightConvFee = flightConvFee;
    }

    public String getFlightMType() {
        return flightMType;
    }

    public void setFlightMType(String flightMType) {
        this.flightMType = flightMType;
    }

    public String getBusMarkup() {
        return busMarkup;
    }

    public void setBusMarkup(String busMarkup) {
        this.busMarkup = busMarkup;
    }

    public String getBusConvFee() {
        return busConvFee;
    }

    public void setBusConvFee(String busConvFee) {
        this.busConvFee = busConvFee;
    }

    public String getBusMType() {
        return busMType;
    }

    public void setBusMType(String busMType) {
        this.busMType = busMType;
    }

    public String getHotelMarkup() {
        return hotelMarkup;
    }

    public void setHotelMarkup(String hotelMarkup) {
        this.hotelMarkup = hotelMarkup;
    }

    public String getHotelConvFee() {
        return hotelConvFee;
    }

    public void setHotelConvFee(String hotelConvFee) {
        this.hotelConvFee = hotelConvFee;
    }

    public String getHotelMType() {
        return hotelMType;
    }

    public void setHotelMType(String hotelMType) {
        this.hotelMType = hotelMType;
    }

}
