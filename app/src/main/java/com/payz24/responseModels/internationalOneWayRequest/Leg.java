package com.payz24.responseModels.internationalOneWayRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/19/2018.
 */

public class Leg {

    @SerializedName("AirEquipType")
    @Expose
    private String airEquipType;
    @SerializedName("ArrivalAirportCode")
    @Expose
    private String arrivalAirportCode;
    @SerializedName("ArrivalAirportName")
    @Expose
    private String arrivalAirportName;
    @SerializedName("ArrivalDateTime")
    @Expose
    private String arrivalDateTime;
    @SerializedName("DepartureAirportCode")
    @Expose
    private String departureAirportCode;
    @SerializedName("DepartureAirportName")
    @Expose
    private String departureAirportName;
    @SerializedName("DepartureDateTime")
    @Expose
    private String departureDateTime;
    @SerializedName("FlightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("MarketingAirlineCode")
    @Expose
    private String marketingAirlineCode;
    @SerializedName("OperatingAirlineCode")
    @Expose
    private String operatingAirlineCode;
    @SerializedName("OperatingAirlineName")
    @Expose
    private String operatingAirlineName;
    @SerializedName("OperatingAirlineFlightNumber")
    @Expose
    private String operatingAirlineFlightNumber;
    @SerializedName("NumStops")
    @Expose
    private String numStops;
    @SerializedName("LinkSellAgrmnt")
    @Expose
    private String linkSellAgrmnt;
    @SerializedName("Conx")
    @Expose
    private String conx;
    @SerializedName("AirpChg")
    @Expose
    private String airpChg;
    @SerializedName("InsideAvailOption")
    @Expose
    private String insideAvailOption;
    @SerializedName("GenTrafRestriction")
    @Expose
    private String genTrafRestriction;
    @SerializedName("DaysOperates")
    @Expose
    private String daysOperates;
    @SerializedName("JrnyTm")
    @Expose
    private String jrnyTm;
    @SerializedName("EndDt")
    @Expose
    private String endDt;
    @SerializedName("StartTerminal")
    @Expose
    private String startTerminal;
    @SerializedName("EndTerminal")
    @Expose
    private String endTerminal;
    @SerializedName("FltTm")
    @Expose
    private String fltTm;
    @SerializedName("LSAInd")
    @Expose
    private String lSAInd;
    @SerializedName("Mile")
    @Expose
    private String mile;
    @SerializedName("Availability")
    @Expose
    private String availability;
    @SerializedName("BIC")
    @Expose
    private String bIC;
    @SerializedName("bookingclass")
    @Expose
    private String bookingclass;
    @SerializedName("classType")
    @Expose
    private String classType;
    @SerializedName("farebasiscode")
    @Expose
    private String farebasiscode;

    public Leg(String airEquipType, String arrivalAirportCode, String arrivalAirportName, String arrivalDateTime, String departureAirportCode, String departureAirportName, String departureDateTime, String flightNumber, String marketingAirlineCode, String operatingAirlineCode,
               String operatingAirlineName, String operatingAirlineFlightNumber, String numStops, String linkSellAgrmnt, String conx, String airpChg, String insideAvailOption, String genTrafRestriction, String daysOperates, String jrnyTm, String endDt, String startTerminal, String endTerminal,
               String fltTm, String lSAInd, String mile, String availability, String bIC, String bookingclass, String classType, String farebasiscode) {
        this.airEquipType = airEquipType;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalAirportName = arrivalAirportName;
        this.arrivalDateTime = arrivalDateTime;
        this.departureAirportCode = departureAirportCode;
        this.departureAirportName = departureAirportName;
        this.departureDateTime = departureDateTime;
        this.flightNumber = flightNumber;
        this.marketingAirlineCode = marketingAirlineCode;
        this.operatingAirlineCode = operatingAirlineCode;
        this.operatingAirlineName = operatingAirlineName;
        this.operatingAirlineFlightNumber = operatingAirlineFlightNumber;
        this.numStops = numStops;
        this.linkSellAgrmnt = linkSellAgrmnt;
        this.conx = conx;
        this.airpChg = airpChg;
        this.insideAvailOption = insideAvailOption;
        this.genTrafRestriction = genTrafRestriction;
        this.daysOperates = daysOperates;
        this.jrnyTm = jrnyTm;
        this.endDt = endDt;
        this.startTerminal = startTerminal;
        this.endTerminal = endTerminal;
        this.fltTm = fltTm;
        this.lSAInd = lSAInd;
        this.mile = mile;
        this.availability = availability;
        this.bIC = bIC;
        this.bookingclass = bookingclass;
        this.classType = classType;
        this.farebasiscode = farebasiscode;
    }

    public String getAirEquipType() {
        return airEquipType;
    }

    public void setAirEquipType(String airEquipType) {
        this.airEquipType = airEquipType;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getMarketingAirlineCode() {
        return marketingAirlineCode;
    }

    public void setMarketingAirlineCode(String marketingAirlineCode) {
        this.marketingAirlineCode = marketingAirlineCode;
    }

    public String getOperatingAirlineCode() {
        return operatingAirlineCode;
    }

    public void setOperatingAirlineCode(String operatingAirlineCode) {
        this.operatingAirlineCode = operatingAirlineCode;
    }

    public String getOperatingAirlineName() {
        return operatingAirlineName;
    }

    public void setOperatingAirlineName(String operatingAirlineName) {
        this.operatingAirlineName = operatingAirlineName;
    }

    public String getOperatingAirlineFlightNumber() {
        return operatingAirlineFlightNumber;
    }

    public void setOperatingAirlineFlightNumber(String operatingAirlineFlightNumber) {
        this.operatingAirlineFlightNumber = operatingAirlineFlightNumber;
    }

    public String getNumStops() {
        return numStops;
    }

    public void setNumStops(String numStops) {
        this.numStops = numStops;
    }

    public String getLinkSellAgrmnt() {
        return linkSellAgrmnt;
    }

    public void setLinkSellAgrmnt(String linkSellAgrmnt) {
        this.linkSellAgrmnt = linkSellAgrmnt;
    }

    public String getConx() {
        return conx;
    }

    public void setConx(String conx) {
        this.conx = conx;
    }

    public String getAirpChg() {
        return airpChg;
    }

    public void setAirpChg(String airpChg) {
        this.airpChg = airpChg;
    }

    public String getInsideAvailOption() {
        return insideAvailOption;
    }

    public void setInsideAvailOption(String insideAvailOption) {
        this.insideAvailOption = insideAvailOption;
    }

    public String getGenTrafRestriction() {
        return genTrafRestriction;
    }

    public void setGenTrafRestriction(String genTrafRestriction) {
        this.genTrafRestriction = genTrafRestriction;
    }

    public String getDaysOperates() {
        return daysOperates;
    }

    public void setDaysOperates(String daysOperates) {
        this.daysOperates = daysOperates;
    }

    public String getJrnyTm() {
        return jrnyTm;
    }

    public void setJrnyTm(String jrnyTm) {
        this.jrnyTm = jrnyTm;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getStartTerminal() {
        return startTerminal;
    }

    public void setStartTerminal(String startTerminal) {
        this.startTerminal = startTerminal;
    }

    public String getEndTerminal() {
        return endTerminal;
    }

    public void setEndTerminal(String endTerminal) {
        this.endTerminal = endTerminal;
    }

    public String getFltTm() {
        return fltTm;
    }

    public void setFltTm(String fltTm) {
        this.fltTm = fltTm;
    }

    public String getLSAInd() {
        return lSAInd;
    }

    public void setLSAInd(String lSAInd) {
        this.lSAInd = lSAInd;
    }

    public String getMile() {
        return mile;
    }

    public void setMile(String mile) {
        this.mile = mile;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBIC() {
        return bIC;
    }

    public void setBIC(String bIC) {
        this.bIC = bIC;
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
}
