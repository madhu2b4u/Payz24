package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class FlightSegment {

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
    private Object operatingAirlineName;
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
    @SerializedName("BookingClass")
    @Expose
    private BookingClass bookingClass;
    @SerializedName("BookingClassFare")
    @Expose
    private BookingClassFare bookingClassFare;

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

    public Object getOperatingAirlineName() {
        return operatingAirlineName;
    }

    public void setOperatingAirlineName(Object operatingAirlineName) {
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

    public BookingClass getBookingClass() {
        return bookingClass;
    }

    public void setBookingClass(BookingClass bookingClass) {
        this.bookingClass = bookingClass;
    }

    public BookingClassFare getBookingClassFare() {
        return bookingClassFare;
    }

    public void setBookingClassFare(BookingClassFare bookingClassFare) {
        this.bookingClassFare = bookingClassFare;
    }
}
