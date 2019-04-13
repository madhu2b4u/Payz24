package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 2/21/2018.
 */

public class Onward {

    @SerializedName("flightId")
    @Expose
    private String flightId;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("tot_first_price")
    @Expose
    private Integer totFirstPrice;
    @SerializedName("tot_sec_price")
    @Expose
    private Integer totSecPrice;
    @SerializedName("fare")
    @Expose
    private Fare fare;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;

    public Onward(String flightId, String key, Integer totFirstPrice, Integer totSecPrice, Fare fare, List<Leg> listOfDomesticLegs) {
        this.flightId = flightId;
        this.key = key;
        if (totFirstPrice != -1)
            this.totFirstPrice = totFirstPrice;
        if (totSecPrice != -1)
            this.totSecPrice = totSecPrice;
        this.fare = fare;
        this.legs = listOfDomesticLegs;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getTotFirstPrice() {
        return totFirstPrice;
    }

    public void setTotFirstPrice(Integer totFirstPrice) {
        this.totFirstPrice = totFirstPrice;
    }

    public Integer getTotSecPrice() {
        return totSecPrice;
    }

    public void setTotSecPrice(Integer totSecPrice) {
        this.totSecPrice = totSecPrice;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
}
