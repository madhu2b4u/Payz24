package com.payz24.responseModels.busLayout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 1/27/2018.
 */

public class Seat {

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
    private Float fare;
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
    private Double totalFareWithTaxes;
    @SerializedName("serviceTaxAmount")
    @Expose
    private Double serviceTaxAmount;
    @SerializedName("serviceTaxPer")
    @Expose
    private Double serviceTaxPer;
    @SerializedName("childFare")
    @Expose
    private Double childFare;
    @SerializedName("operatorServiceChargeAbsolute")
    @Expose
    private Double operatorServiceChargeAbsolute;
    @SerializedName("operatorServiceChargePercent")
    @Expose
    private Double operatorServiceChargePercent;

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

    public Float getFare() {
        return fare;
    }

    public void setFare(Float fare) {
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

    public Double getTotalFareWithTaxes() {
        return totalFareWithTaxes;
    }

    public void setTotalFareWithTaxes(Double totalFareWithTaxes) {
        this.totalFareWithTaxes = totalFareWithTaxes;
    }

    public Double getServiceTaxAmount() {
        return serviceTaxAmount;
    }

    public void setServiceTaxAmount(Double serviceTaxAmount) {
        this.serviceTaxAmount = serviceTaxAmount;
    }

    public Double getServiceTaxPer() {
        return serviceTaxPer;
    }

    public void setServiceTaxPer(Double serviceTaxPer) {
        this.serviceTaxPer = serviceTaxPer;
    }

    public Double getChildFare() {
        return childFare;
    }

    public void setChildFare(Double childFare) {
        this.childFare = childFare;
    }

    public Double getOperatorServiceChargeAbsolute() {
        return operatorServiceChargeAbsolute;
    }

    public void setOperatorServiceChargeAbsolute(Double operatorServiceChargeAbsolute) {
        this.operatorServiceChargeAbsolute = operatorServiceChargeAbsolute;
    }

    public Double getOperatorServiceChargePercent() {
        return operatorServiceChargePercent;
    }

    public void setOperatorServiceChargePercent(Double operatorServiceChargePercent) {
        this.operatorServiceChargePercent = operatorServiceChargePercent;
    }
}
