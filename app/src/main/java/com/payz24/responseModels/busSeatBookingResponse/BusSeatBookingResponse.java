package com.payz24.responseModels.busSeatBookingResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/15/2018.
 */

public class BusSeatBookingResponse {
    @SerializedName("apiStatus")
    @Expose
    private ApiStatus apiStatus;
    @SerializedName("inventoryType")
    @Expose
    private Integer inventoryType;
    @SerializedName("opPNR")
    @Expose
    private String opPNR;
    @SerializedName("commPCT")
    @Expose
    private Double commPCT;
    @SerializedName("totalFare")
    @Expose
    private Double totalFare;
    @SerializedName("cancellationPolicy")
    @Expose
    private String cancellationPolicy;
    @SerializedName("tripCode")
    @Expose
    private Object tripCode;
    @SerializedName("etstnumber")
    @Expose
    private String etstnumber;

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

    public String getOpPNR() {
        return opPNR;
    }

    public void setOpPNR(String opPNR) {
        this.opPNR = opPNR;
    }

    public Double getCommPCT() {
        return commPCT;
    }

    public void setCommPCT(Double commPCT) {
        this.commPCT = commPCT;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public Object getTripCode() {
        return tripCode;
    }

    public void setTripCode(Object tripCode) {
        this.tripCode = tripCode;
    }

    public String getEtstnumber() {
        return etstnumber;
    }

    public void setEtstnumber(String etstnumber) {
        this.etstnumber = etstnumber;
    }
}


