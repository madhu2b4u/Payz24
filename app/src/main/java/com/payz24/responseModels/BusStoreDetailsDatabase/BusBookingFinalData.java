package com.payz24.responseModels.BusStoreDetailsDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payz24.responseModels.availableBuses.ApiAvailableBus;
import com.payz24.responseModels.blockTicket.*;
import com.payz24.responseModels.blockTicket.BoardingPoint;

import java.util.Map;

/**
 * Created by mahesh on 3/2/2018.
 */

public class BusBookingFinalData {
    @SerializedName("totalFare")
    @Expose
    private Integer totalFare;
    @SerializedName("org_totalFare")
    @Expose
    private Integer orgTotalFare;
    @SerializedName("totalTax")
    @Expose
    private Integer totalTax;
    @SerializedName("commPCT")
    @Expose
    private Integer commPCT;
    @SerializedName("providerContact")
    @Expose
    private String providerContact;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("txn_id")
    @Expose
    private String txnId;
    @SerializedName("bus_m_type")
    @Expose
    private String busMtype;

    @SerializedName("bus_cfee")
    @Expose
    private String busCfee;

    @SerializedName("bus_markup")
    @Expose
    private String busMarkup;

    @SerializedName("tot_fare_cfee")
    @Expose
    private String totFareCfee;

    public BusBookingFinalData(Double totalFare, Double totalFeeWithTax , Double tax, Double commPCT, String providerContact, String bookingStatus, String paymentStatus, String txnId, String busMType, double conventionalFee, double markUpFee) {
        this.totalFare = totalFare.intValue();
        this.orgTotalFare = totalFeeWithTax.intValue();
        this.totalTax = tax.intValue();
        this.commPCT = commPCT.intValue();
        this.providerContact = providerContact;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.txnId = txnId;
        this.busMtype = busMType;
        this.busCfee = String.valueOf(conventionalFee);
        this.busMarkup = String.valueOf(markUpFee);
    }


    public Integer getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Integer totalFare) {
        this.totalFare = totalFare;
    }

    public Integer getOrgTotalFare() {
        return orgTotalFare;
    }

    public void setOrgTotalFare(Integer orgTotalFare) {
        this.orgTotalFare = orgTotalFare;
    }

    public Integer getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Integer totalTax) {
        this.totalTax = totalTax;
    }

    public Integer getCommPCT() {
        return commPCT;
    }

    public void setCommPCT(Integer commPCT) {
        this.commPCT = commPCT;
    }

    public String getProviderContact() {
        return providerContact;
    }

    public void setProviderContact(String providerContact) {
        this.providerContact = providerContact;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
}
