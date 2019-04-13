package com.payz24.responseModels.CancelBusTicketResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/30/2018.
 */

public class CancelBusTicketResponse {

    @SerializedName("apiStatus")
    @Expose
    private ApiStatus apiStatus;
    @SerializedName("cancellable")
    @Expose
    private Boolean cancellable;
    @SerializedName("partiallyCancellable")
    @Expose
    private Boolean partiallyCancellable;
    @SerializedName("totalTicketFare")
    @Expose
    private String totalTicketFare;
    @SerializedName("totalRefundAmount")
    @Expose
    private String totalRefundAmount;
    @SerializedName("cancelChargesPercentage")
    @Expose
    private String cancelChargesPercentage;
    @SerializedName("cancellationCharges")
    @Expose
    private Double cancellationCharges;

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }

    public Boolean getCancellable() {
        return cancellable;
    }

    public void setCancellable(Boolean cancellable) {
        this.cancellable = cancellable;
    }

    public Boolean getPartiallyCancellable() {
        return partiallyCancellable;
    }

    public void setPartiallyCancellable(Boolean partiallyCancellable) {
        this.partiallyCancellable = partiallyCancellable;
    }

    public String getTotalTicketFare() {
        return totalTicketFare;
    }

    public void setTotalTicketFare(String totalTicketFare) {
        this.totalTicketFare = totalTicketFare;
    }

    public String getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(String totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getCancelChargesPercentage() {
        return cancelChargesPercentage;
    }

    public void setCancelChargesPercentage(String cancelChargesPercentage) {
        this.cancelChargesPercentage = cancelChargesPercentage;
    }

    public Double getCancellationCharges() {
        return cancellationCharges;
    }

    public void setCancellationCharges(Double cancellationCharges) {
        this.cancellationCharges = cancellationCharges;
    }

}
