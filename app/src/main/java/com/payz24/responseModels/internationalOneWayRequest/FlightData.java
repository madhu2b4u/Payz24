package com.payz24.responseModels.internationalOneWayRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahesh on 2/19/2018.
 */

public class FlightData {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("fare_brk")
    @Expose
    private List<FareBrk> fareBrk = null;
    @SerializedName("stops")
    @Expose
    private Integer stops;
    @SerializedName("Rstops")
    @Expose
    private Integer rstops;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;
    @SerializedName("Rlegs")
    @Expose
    private List<Rleg> rlegs = null;

    public FlightData(Integer id, String flightId, String key, Integer totFirstPrice, Integer totSecPrice, Fare fare, List<FareBrk> listOfFareBrk, ArrayList<Leg> listOfLegs, ArrayList<Rleg> listOfRLegs, String numStops, String numReturnStops) {
        this.id = id;
        this.flightId = flightId;
        this.key = key;
        this.totFirstPrice = totFirstPrice;
        this.totSecPrice = totSecPrice;
        this.fare = fare;
        this.fareBrk = listOfFareBrk;
        this.legs = listOfLegs;
        this.stops = Integer.parseInt(numStops);
        if (listOfRLegs.size() != 0)
            this.rlegs = listOfRLegs;
        if (!numReturnStops.isEmpty())
            this.rstops = Integer.parseInt(numReturnStops);
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

    public List<FareBrk> getFareBrk() {
        return fareBrk;
    }

    public void setFareBrk(List<FareBrk> fareBrk) {
        this.fareBrk = fareBrk;
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

    public Integer getRstops() {
        return rstops;
    }

    public void setRstops(Integer rstops) {
        this.rstops = rstops;
    }
}
