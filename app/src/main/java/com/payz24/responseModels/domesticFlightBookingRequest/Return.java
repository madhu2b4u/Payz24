package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 3/3/2018.
 */

public class Return {

    @SerializedName("flightId")
    @Expose
    private String flightId;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("fare")
    @Expose
    private Fare fare;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;

    public Return(String fromFlightId, String fromKey, Fare fromFare, List<Leg> listOfFromLegs) {
        this.flightId = fromFlightId;
        this.key = fromKey;
        this.fare = fromFare;
        this.legs = listOfFromLegs;
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
