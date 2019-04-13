package com.payz24.responseModels.busLayout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 1/27/2018.
 */

public class BusLayout {

    @SerializedName("apiStatus")
    @Expose
    private ApiStatus apiStatus;
    @SerializedName("inventoryType")
    @Expose
    private Integer inventoryType;
    @SerializedName("boardingPoints")
    @Expose
    private Object boardingPoints;
    @SerializedName("droppingPoints")
    @Expose
    private Object droppingPoints;
    @SerializedName("seats")
    @Expose
    private List<Seat> seats = null;
    @SerializedName("serviceTaxApplicable")
    @Expose
    private Boolean serviceTaxApplicable;
    @SerializedName("etsServiceChargePer")
    @Expose
    private Double etsServiceChargePer;

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Object getBoardingPoints() {
        return boardingPoints;
    }

    public void setBoardingPoints(Object boardingPoints) {
        this.boardingPoints = boardingPoints;
    }

    public Object getDroppingPoints() {
        return droppingPoints;
    }

    public void setDroppingPoints(Object droppingPoints) {
        this.droppingPoints = droppingPoints;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Boolean getServiceTaxApplicable() {
        return serviceTaxApplicable;
    }

    public void setServiceTaxApplicable(Boolean serviceTaxApplicable) {
        this.serviceTaxApplicable = serviceTaxApplicable;
    }

    public Double getEtsServiceChargePer() {
        return etsServiceChargePer;
    }

    public void setEtsServiceChargePer(Double etsServiceChargePer) {
        this.etsServiceChargePer = etsServiceChargePer;
    }
}
