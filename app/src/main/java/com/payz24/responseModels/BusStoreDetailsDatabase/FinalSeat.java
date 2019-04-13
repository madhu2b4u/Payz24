package com.payz24.responseModels.BusStoreDetailsDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/2/2018.
 */

public class FinalSeat {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("row")
    @Expose
    private Integer row;
    @SerializedName("column")
    @Expose
    private Integer column;
    @SerializedName("zIndex")
    @Expose
    private Integer zIndex;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("fare")
    @Expose
    private Integer fare;
    @SerializedName("commission")
    @Expose
    private Object commission;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("ladiesSeat")
    @Expose
    private Boolean ladiesSeat;
    @SerializedName("bookedBy")
    @Expose
    private Object bookedBy;
    @SerializedName("ac")
    @Expose
    private Boolean ac;
    @SerializedName("sleeper")
    @Expose
    private Boolean sleeper;
    @SerializedName("totalFareWithTaxes")
    @Expose
    private Integer totalFareWithTaxes;
    @SerializedName("serviceTaxAmount")
    @Expose
    private Integer serviceTaxAmount;
    @SerializedName("serviceTaxPer")
    @Expose
    private Integer serviceTaxPer;
    @SerializedName("childFare")
    @Expose
    private Integer childFare;
    @SerializedName("operatorServiceChargeAbsolute")
    @Expose
    private Integer operatorServiceChargeAbsolute;
    @SerializedName("operatorServiceChargePercent")
    @Expose
    private Integer operatorServiceChargePercent;

    public FinalSeat(String id, Integer row, Integer column, Integer zIndex, Integer length, Integer width, Float fare, Object commission, Boolean available,
                     Boolean ladiesSeat, Object bookedBy, Boolean ac, Boolean sleeper, Double totalFareWithTaxes, Double serviceTaxAmount, Double serviceTaxPer,
                     Double childFare, Double operatorServiceChargeAbsolute, Double operatorServiceChargePercent) {
        this.id = id;
        this.row = row;
        this.zIndex = zIndex;
        this.length = length;
        this.width = width;
        this.fare = fare.intValue();
        this.commission = commission;
        this.available = available;
        this.ladiesSeat = ladiesSeat;
        this.bookedBy = bookedBy;
        this.ac = ac;
        this.sleeper = sleeper;
        this.totalFareWithTaxes = totalFareWithTaxes.intValue();
        this.serviceTaxAmount = serviceTaxAmount.intValue();
        this.serviceTaxPer = serviceTaxPer.intValue();
        this.childFare = childFare.intValue();
        this.operatorServiceChargeAbsolute = operatorServiceChargeAbsolute.intValue();
        this.operatorServiceChargePercent = operatorServiceChargePercent.intValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getZIndex() {
        return zIndex;
    }

    public void setZIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public Object getCommission() {
        return commission;
    }

    public void setCommission(Object commission) {
        this.commission = commission;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getLadiesSeat() {
        return ladiesSeat;
    }

    public void setLadiesSeat(Boolean ladiesSeat) {
        this.ladiesSeat = ladiesSeat;
    }

    public Object getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(Object bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Boolean getAc() {
        return ac;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public Boolean getSleeper() {
        return sleeper;
    }

    public void setSleeper(Boolean sleeper) {
        this.sleeper = sleeper;
    }

    public Integer getTotalFareWithTaxes() {
        return totalFareWithTaxes;
    }

    public void setTotalFareWithTaxes(Integer totalFareWithTaxes) {
        this.totalFareWithTaxes = totalFareWithTaxes;
    }

    public Integer getServiceTaxAmount() {
        return serviceTaxAmount;
    }

    public void setServiceTaxAmount(Integer serviceTaxAmount) {
        this.serviceTaxAmount = serviceTaxAmount;
    }

    public Integer getServiceTaxPer() {
        return serviceTaxPer;
    }

    public void setServiceTaxPer(Integer serviceTaxPer) {
        this.serviceTaxPer = serviceTaxPer;
    }

    public Integer getChildFare() {
        return childFare;
    }

    public void setChildFare(Integer childFare) {
        this.childFare = childFare;
    }

    public Integer getOperatorServiceChargeAbsolute() {
        return operatorServiceChargeAbsolute;
    }

    public void setOperatorServiceChargeAbsolute(Integer operatorServiceChargeAbsolute) {
        this.operatorServiceChargeAbsolute = operatorServiceChargeAbsolute;
    }

    public Integer getOperatorServiceChargePercent() {
        return operatorServiceChargePercent;
    }

    public void setOperatorServiceChargePercent(Integer operatorServiceChargePercent) {
        this.operatorServiceChargePercent = operatorServiceChargePercent;
    }
}
