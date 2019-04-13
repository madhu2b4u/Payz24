package com.payz24.responseModels.domesticFlightBookingRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/21/2018.
 */

public class Leg {

    @SerializedName("AirEquipType")
    @Expose
    private String airEquipType;
    @SerializedName("ArrivalAirportCode")
    @Expose
    private String arrivalAirportCode;
    @SerializedName("ArrivalDateTime")
    @Expose
    private String arrivalDateTime;
    @SerializedName("DepartureAirportCode")
    @Expose
    private String departureAirportCode;
    @SerializedName("DepartureDateTime")
    @Expose
    private String departureDateTime;
    @SerializedName("FlightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("OperatingAirlineCode")
    @Expose
    private String operatingAirlineCode;
    @SerializedName("OperatingAirlineFlightNumber")
    @Expose
    private String operatingAirlineFlightNumber;
    @SerializedName("RPH")
    @Expose
    private String rPH;
    @SerializedName("airLineName")
    @Expose
    private String airLineName;
    @SerializedName("airportTax")
    @Expose
    private String airportTax;
    @SerializedName("imageFileName")
    @Expose
    private String imageFileName;
    @SerializedName("viaFlight")
    @Expose
    private String viaFlight;
    @SerializedName("StopQuantity")
    @Expose
    private String stopQuantity;
    @SerializedName("Availability")
    @Expose
    private String availability;
    @SerializedName("ResBookDesigCode")
    @Expose
    private String resBookDesigCode;
    @SerializedName("adultFare")
    @Expose
    private String adultFare;
    @SerializedName("bookingclass")
    @Expose
    private String bookingclass;
    @SerializedName("classType")
    @Expose
    private String classType;
    @SerializedName("farebasiscode")
    @Expose
    private String farebasiscode;
    @SerializedName("Rule")
    @Expose
    private String rule;
    @SerializedName("infantfare")
    @Expose
    private Integer infantfare;
    @SerializedName("childFare")
    @Expose
    private Integer childFare;
    @SerializedName("adultCommission")
    @Expose
    private String adultCommission;
    @SerializedName("childCommission")
    @Expose
    private String childCommission;
    @SerializedName("commissionOnTCharge")
    @Expose
    private String commissionOnTCharge;
    @SerializedName("Discount")
    @Expose
    private String discount;
    @SerializedName("airportTaxChild")
    @Expose
    private String airportTaxChild;
    @SerializedName("airportTaxInfant")
    @Expose
    private String airportTaxInfant;
    @SerializedName("adultTaxBreakup")
    @Expose
    private String adultTaxBreakup;
    @SerializedName("childTaxBreakup")
    @Expose
    private String childTaxBreakup;
    @SerializedName("infantTaxBreakup")
    @Expose
    private String infantTaxBreakup;
    @SerializedName("octax")
    @Expose
    private String octax;

    public Leg(String airEquipType, String arrivalAirportCode, String arrivalDateTime, String departureAirportCode, String departureDateTime, String flightNumber,
               String operatingAirlineCode, String operatingAirlineFlightNumber, String rPH, String airLineName, String airportTax, String imageFileName, String viaFlight, String stopQuantity,
               String availability, String resBookDesigCode, String adultFare, String bookingclass, String classType, String farebasiscode, String rule, String infantfare,
               String childFare, String adultCommission, String childCommission, String commissionOnTCharge, String discount, String airportTaxChild, String airportTaxInfant,
               String adultTaxBreakup, String childTaxBreakup, String infantTaxBreakup, String octax) {

        this.airEquipType = airEquipType;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalDateTime = arrivalDateTime;
        this.departureAirportCode = departureAirportCode;
        this.departureDateTime = departureDateTime;
        this.flightNumber = flightNumber;
        this.operatingAirlineCode = operatingAirlineCode;
        this.operatingAirlineFlightNumber = operatingAirlineFlightNumber;
        this.rPH = rPH;
        this.airLineName = airLineName;
        this.airportTax = airportTax;
        this.imageFileName = imageFileName;
        this.viaFlight = viaFlight;
        this.stopQuantity = stopQuantity;
        this.availability = availability;
        this.resBookDesigCode = resBookDesigCode;
        this.adultFare = adultFare;
        this.bookingclass = bookingclass;
        this.classType = classType;
        this.farebasiscode = farebasiscode;
        this.rule = rule;
        this.infantfare = Integer.parseInt(infantfare);
        this.childFare = Integer.parseInt(childFare);
        this.adultCommission = adultCommission;
        this.childCommission = childCommission;
        this.commissionOnTCharge = commissionOnTCharge;
        this.discount = discount;
        this.airportTaxChild = airportTaxChild;
        this.airportTaxInfant  = airportTaxInfant;
        this.adultTaxBreakup = adultTaxBreakup;
        this.childTaxBreakup = childTaxBreakup;
        this.infantTaxBreakup = infantTaxBreakup;
        this.octax = octax;
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

    public String getOperatingAirlineCode() {
        return operatingAirlineCode;
    }

    public void setOperatingAirlineCode(String operatingAirlineCode) {
        this.operatingAirlineCode = operatingAirlineCode;
    }

    public String getOperatingAirlineFlightNumber() {
        return operatingAirlineFlightNumber;
    }

    public void setOperatingAirlineFlightNumber(String operatingAirlineFlightNumber) {
        this.operatingAirlineFlightNumber = operatingAirlineFlightNumber;
    }

    public String getRPH() {
        return rPH;
    }

    public void setRPH(String rPH) {
        this.rPH = rPH;
    }

    public String getAirLineName() {
        return airLineName;
    }

    public void setAirLineName(String airLineName) {
        this.airLineName = airLineName;
    }

    public String getAirportTax() {
        return airportTax;
    }

    public void setAirportTax(String airportTax) {
        this.airportTax = airportTax;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getViaFlight() {
        return viaFlight;
    }

    public void setViaFlight(String viaFlight) {
        this.viaFlight = viaFlight;
    }

    public String getStopQuantity() {
        return stopQuantity;
    }

    public void setStopQuantity(String stopQuantity) {
        this.stopQuantity = stopQuantity;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getResBookDesigCode() {
        return resBookDesigCode;
    }

    public void setResBookDesigCode(String resBookDesigCode) {
        this.resBookDesigCode = resBookDesigCode;
    }

    public String getAdultFare() {
        return adultFare;
    }

    public void setAdultFare(String adultFare) {
        this.adultFare = adultFare;
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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getInfantfare() {
        return infantfare;
    }

    public void setInfantfare(Integer infantfare) {
        this.infantfare = infantfare;
    }

    public Integer getChildFare() {
        return childFare;
    }

    public void setChildFare(Integer childFare) {
        this.childFare = childFare;
    }

    public String getAdultCommission() {
        return adultCommission;
    }

    public void setAdultCommission(String adultCommission) {
        this.adultCommission = adultCommission;
    }

    public String getChildCommission() {
        return childCommission;
    }

    public void setChildCommission(String childCommission) {
        this.childCommission = childCommission;
    }

    public String getCommissionOnTCharge() {
        return commissionOnTCharge;
    }

    public void setCommissionOnTCharge(String commissionOnTCharge) {
        this.commissionOnTCharge = commissionOnTCharge;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAirportTaxChild() {
        return airportTaxChild;
    }

    public void setAirportTaxChild(String airportTaxChild) {
        this.airportTaxChild = airportTaxChild;
    }

    public String getAirportTaxInfant() {
        return airportTaxInfant;
    }

    public void setAirportTaxInfant(String airportTaxInfant) {
        this.airportTaxInfant = airportTaxInfant;
    }

    public String getAdultTaxBreakup() {
        return adultTaxBreakup;
    }

    public void setAdultTaxBreakup(String adultTaxBreakup) {
        this.adultTaxBreakup = adultTaxBreakup;
    }

    public String getChildTaxBreakup() {
        return childTaxBreakup;
    }

    public void setChildTaxBreakup(String childTaxBreakup) {
        this.childTaxBreakup = childTaxBreakup;
    }

    public String getInfantTaxBreakup() {
        return infantTaxBreakup;
    }

    public void setInfantTaxBreakup(String infantTaxBreakup) {
        this.infantTaxBreakup = infantTaxBreakup;
    }

    public String getOctax() {
        return octax;
    }

    public void setOctax(String octax) {
        this.octax = octax;
    }

}
