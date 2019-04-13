
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingClassFare {

    @SerializedName("adultFare")
    @Expose
    private String adultFare;
    @SerializedName("bookingclass")
    @Expose
    private String bookingclass;
    @SerializedName("childFare")
    @Expose
    private String childFare;
    @SerializedName("infantfare")
    @Expose
    private String infantfare;
    @SerializedName("classType")
    @Expose
    private String classType;
    @SerializedName("farebasiscode")
    @Expose
    private String farebasiscode;
    @SerializedName("Rule")
    @Expose
    private String rule;
    @SerializedName("adultCommission")
    @Expose
    private String adultCommission;
    @SerializedName("childCommission")
    @Expose
    private String childCommission;
    @SerializedName("commissionOnTCharge")
    @Expose
    private String commissionOnTCharge;

    public String getAdultFare() {
        return adultFare;
    }

    public void setAdultFare(String adultFare) {
        this.adultFare = adultFare;
    }

    public String getBookingclass() {
        return bookingclass;
    }

    public void setBookingclass(String bookingclass) {
        this.bookingclass = bookingclass;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getFarebasiscode() {
        return farebasiscode;
    }

    public void setFarebasiscode(String farebasiscode) {
        this.farebasiscode = farebasiscode;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getAdultCommission() {
        return adultCommission;
    }

    public void setAdultCommission(String adultCommission) {
        this.adultCommission = adultCommission;
    }

    public String getChildCommission() {
        return childCommission;
    }

    public void setChildCommission(String childCommission) {
        this.childCommission = childCommission;
    }

    public String getCommissionOnTCharge() {
        return commissionOnTCharge;
    }

    public void setCommissionOnTCharge(String commissionOnTCharge) {
        this.commissionOnTCharge = commissionOnTCharge;
    }

    public String getChildFare() {
        return childFare;
    }

    public void setChildFare(String childFare) {
        this.childFare = childFare;
    }

    public String getInfantfare() {
        return infantfare;
    }

    public void setInfantfare(String infantfare) {
        this.infantfare = infantfare;
    }
}
