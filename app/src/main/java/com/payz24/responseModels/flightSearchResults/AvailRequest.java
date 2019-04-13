package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 2/7/2018.
 */

public class AvailRequest {

    @SerializedName("Trip")
    @Expose
    private String trip;
    @SerializedName("Origin")
    @Expose
    private String origin;
    @SerializedName("Destination")
    @Expose
    private String destination;
    @SerializedName("DepartDate")
    @Expose
    private String departDate;
    @SerializedName("ReturnDate")
    @Expose
    private String returnDate;
    @SerializedName("AdultPax")
    @Expose
    private String adultPax;
    @SerializedName("ChildPax")
    @Expose
    private String childPax;
    @SerializedName("InfantPax")
    @Expose
    private String infantPax;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("PreferredClass")
    @Expose
    private String preferredClass;
    @SerializedName("Eticket")
    @Expose
    private String eticket;
    @SerializedName("Clientid")
    @Expose
    private String clientid;
    @SerializedName("Clienttype")
    @Expose
    private String clienttype;
    @SerializedName("PreferredAirline")
    @Expose
    private List<Object> preferredAirline;

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getAdultPax() {
        return adultPax;
    }

    public void setAdultPax(String adultPax) {
        this.adultPax = adultPax;
    }

    public String getChildPax() {
        return childPax;
    }

    public void setChildPax(String childPax) {
        this.childPax = childPax;
    }

    public String getInfantPax() {
        return infantPax;
    }

    public void setInfantPax(String infantPax) {
        this.infantPax = infantPax;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPreferredClass() {
        return preferredClass;
    }

    public void setPreferredClass(String preferredClass) {
        this.preferredClass = preferredClass;
    }

    public String getEticket() {
        return eticket;
    }

    public void setEticket(String eticket) {
        this.eticket = eticket;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClienttype() {
        return clienttype;
    }

    public void setClienttype(String clienttype) {
        this.clienttype = clienttype;
    }

    public List<Object> getPreferredAirline() {
        return preferredAirline;
    }

    public void setPreferredAirline(List<Object> preferredAirline) {
        this.preferredAirline = preferredAirline;
    }
}
