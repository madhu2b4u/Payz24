package com.payz24.responseModels.domesticPreFare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 3/19/2018.
 */

public class FlightDataOneway {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("flightId")
    @Expose
    private String flightId;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("fare")
    @Expose
    private Fare fare;
    @SerializedName("total_fare")
    @Expose
    private Integer totalFare;
    @SerializedName("stops")
    @Expose
    private Integer stops;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;

    public FlightDataOneway(int id, String flightId, String key, Fare fare, int totalFare, String numberOfStops, List<Leg> listOfLegs) {
        this.id = id;
        this.flightId = flightId;
        this.key = key;
        this.fare = fare;
        this.totalFare = totalFare;
        this.stops = Integer.parseInt(numberOfStops);
        this.legs = listOfLegs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Integer totalFare) {
        this.totalFare = totalFare;
    }

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

}
