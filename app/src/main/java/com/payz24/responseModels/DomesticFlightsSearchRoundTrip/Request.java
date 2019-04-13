
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

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
    @SerializedName("Clientid")
    @Expose
    private String clientid;
    @SerializedName("Clienttype")
    @Expose
    private String clienttype;
    @SerializedName("Preferredclass")
    @Expose
    private String preferredclass;
    @SerializedName("mode")
    @Expose
    private String mode;

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

    public String getPreferredclass() {
        return preferredclass;
    }

    public void setPreferredclass(String preferredclass) {
        this.preferredclass = preferredclass;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
