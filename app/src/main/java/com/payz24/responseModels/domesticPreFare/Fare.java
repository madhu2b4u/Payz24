package com.payz24.responseModels.domesticPreFare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/19/2018.
 */

public class Fare {

    @SerializedName("ActualBaseFare")
    @Expose
    private String actualBaseFare;
    @SerializedName("Tax")
    @Expose
    private String tax;
    @SerializedName("STax")
    @Expose
    private String sTax;
    @SerializedName("SCharge")
    @Expose
    private String sCharge;
    @SerializedName("TDiscount")
    @Expose
    private String tDiscount;
    @SerializedName("TPartnerCommission")
    @Expose
    private String tPartnerCommission;
    @SerializedName("TCharge")
    @Expose
    private String tCharge;
    @SerializedName("TMarkup")
    @Expose
    private String tMarkup;
    @SerializedName("TSdiscount")
    @Expose
    private String tSdiscount;

    public Fare(String actualBaseFare, String tax, String sTax, String sCharge, String tDiscount, String tPartnerCommission, String tCharge, String tMarkup, String tSdiscount) {
        this.actualBaseFare = actualBaseFare;
        this.tax = tax;
        this.sTax = sTax;
        this.sCharge = sCharge;
        this.tDiscount = tDiscount;
        this.tPartnerCommission = tPartnerCommission;
        this.tCharge = tCharge;
        this.tMarkup = tMarkup;
        this.tSdiscount = tSdiscount;
    }

    public String getActualBaseFare() {
        return actualBaseFare;
    }

    public void setActualBaseFare(String actualBaseFare) {
        this.actualBaseFare = actualBaseFare;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSTax() {
        return sTax;
    }

    public void setSTax(String sTax) {
        this.sTax = sTax;
    }

    public String getSCharge() {
        return sCharge;
    }

    public void setSCharge(String sCharge) {
        this.sCharge = sCharge;
    }

    public String getTDiscount() {
        return tDiscount;
    }

    public void setTDiscount(String tDiscount) {
        this.tDiscount = tDiscount;
    }

    public String getTPartnerCommission() {
        return tPartnerCommission;
    }

    public void setTPartnerCommission(String tPartnerCommission) {
        this.tPartnerCommission = tPartnerCommission;
    }

    public String getTCharge() {
        return tCharge;
    }

    public void setTCharge(String tCharge) {
        this.tCharge = tCharge;
    }

    public String getTMarkup() {
        return tMarkup;
    }

    public void setTMarkup(String tMarkup) {
        this.tMarkup = tMarkup;
    }

    public String getTSdiscount() {
        return tSdiscount;
    }

    public void setTSdiscount(String tSdiscount) {
        this.tSdiscount = tSdiscount;
    }
}
