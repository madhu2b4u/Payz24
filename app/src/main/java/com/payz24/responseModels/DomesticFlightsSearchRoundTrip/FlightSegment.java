
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlightSegment {

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
    private Object rPH = null;
    @SerializedName("StopQuantity")
    @Expose
    private String stopQuantity;
    @SerializedName("airLineName")
    @Expose
    private String airLineName;
    @SerializedName("airportTax")
    @Expose
    private String airportTax;
    @SerializedName("imageFileName")
    @Expose
    private String imageFileName;
    //@SerializedName("viaFlight")
    //@Expose
    //private List<Object> viaFlight = null;
    @SerializedName("BookingClass")
    @Expose
    private BookingClass bookingClass;
    @SerializedName("BookingClassFare")
    @Expose
    private BookingClassFare bookingClassFare;
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

    public Object getRPH() {
        return rPH;
    }

    public void setRPH(Object rPH) {
        this.rPH = rPH;
    }

    public String getStopQuantity() {
        return stopQuantity;
    }

    public void setStopQuantity(String stopQuantity) {
        this.stopQuantity = stopQuantity;
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

    /*public List<Object> getViaFlight() {
        return viaFlight;
    }

    public void setViaFlight(List<Object> viaFlight) {
        this.viaFlight = viaFlight;
    }*/

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
