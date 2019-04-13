package com.payz24.responseModels.flightTicketOverviewScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 3/9/2018.
 */

public class Return {

    @SerializedName("flight_name")
    @Expose
    private String flightName;
    @SerializedName("flight_code")
    @Expose
    private String flightCode;
    @SerializedName("flight_number")
    @Expose
    private String flightNumber;
    @SerializedName("Departue_name")
    @Expose
    private List<String> departueName = null;
    @SerializedName("Arivel_name")
    @Expose
    private List<String> arivelName = null;
    @SerializedName("Departure_Time")
    @Expose
    private String departureTime;
    @SerializedName("Arrivel_Time")
    @Expose
    private String arrivelTime;
    @SerializedName("class")
    @Expose
    private String _class;

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public List<String> getDepartueName() {
        return departueName;
    }

    public void setDepartueName(List<String> departueName) {
        this.departueName = departueName;
    }

    public List<String> getArivelName() {
        return arivelName;
    }

    public void setArivelName(List<String> arivelName) {
        this.arivelName = arivelName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivelTime() {
        return arrivelTime;
    }

    public void setArrivelTime(String arrivelTime) {
        this.arrivelTime = arrivelTime;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }
}
