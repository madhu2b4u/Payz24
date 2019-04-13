package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/21/2018.
 */

public class SearchData {

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("fromCode")
    @Expose
    private String fromCode;
    @SerializedName("toCode")
    @Expose
    private String toCode;
    @SerializedName("sd")
    @Expose
    private String sd;
    @SerializedName("ed")
    @Expose
    private String ed;
    @SerializedName("adult")
    @Expose
    private String adult;
    @SerializedName("child")
    @Expose
    private String child;
    @SerializedName("infant")
    @Expose
    private String infant;
    @SerializedName("travelClass")
    @Expose
    private String travelClass;
    @SerializedName("nonStopOnly")
    @Expose
    private Integer nonStopOnly;
    @SerializedName("journey_type")
    @Expose
    private String journeyType;
    @SerializedName("fromCountry")
    @Expose
    private String fromCountry;
    @SerializedName("toCountry")
    @Expose
    private String toCountry;
    @SerializedName("fromAirport")
    @Expose
    private String fromAirport;
    @SerializedName("toAirport")
    @Expose
    private String toAirport;
    @SerializedName("fromCity")
    @Expose
    private String fromCity;
    @SerializedName("toCity")
    @Expose
    private String toCity;
    @SerializedName("searchType")
    @Expose
    private String searchType;
    @SerializedName("org_from")
    @Expose
    private String orgFrom;
    @SerializedName("org_to")
    @Expose
    private String orgTo;

    public SearchData(String from, String to, String fromCode, String toCode, String sd, String ed, String adult, String child, String infant, String travelClass, Integer nonStopOnly,
                      String journeyType, String fromCountry, String toCountry, String fromAirport, String toAirport, String fromCity, String toCity, String searchType, String sourceFullName, String destinationFullName) {
        this.from = from;
        this.to = to;
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.sd = sd;
        this.ed = ed;
        this.adult = adult;
        this.child = child;
        this.infant = infant;
        this.travelClass = travelClass;
        this.nonStopOnly = nonStopOnly;
        this.journeyType = journeyType;
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.searchType = searchType;
        this.orgFrom = sourceFullName;
        this.orgTo = destinationFullName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getInfant() {
        return infant;
    }

    public void setInfant(String infant) {
        this.infant = infant;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public Integer getNonStopOnly() {
        return nonStopOnly;
    }

    public void setNonStopOnly(Integer nonStopOnly) {
        this.nonStopOnly = nonStopOnly;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
