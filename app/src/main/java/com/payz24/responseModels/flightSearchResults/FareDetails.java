package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class FareDetails {

    @SerializedName("ActualBaseFare")
    @Expose
    private String actualBaseFare;
    @SerializedName("Tax")
    @Expose
    private String tax;
    @SerializedName("STax")
    @Expose
    private String sTax;
    @SerializedName("TCharge")
    @Expose
    private String tCharge;
    @SerializedName("SCharge")
    @Expose
    private String sCharge;
    @SerializedName("TDiscount")
    @Expose
    private String tDiscount;
    @SerializedName("TMarkup")
    @Expose
    private String tMarkup;
    @SerializedName("TPartnerCommission")
    @Expose
    private String tPartnerCommission;
    @SerializedName("TSdiscount")
    @Expose
    private String tSdiscount;
    @SerializedName("FareBreakup")
    @Expose
    private FareBreakup fareBreakup;
    @SerializedName("ocTax")
    @Expose
    private String ocTax;

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

    public String getTCharge() {
        return tCharge;
    }

    public void setTCharge(String tCharge) {
        this.tCharge = tCharge;
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

    public String getTMarkup() {
        return tMarkup;
    }

    public void setTMarkup(String tMarkup) {
        this.tMarkup = tMarkup;
    }

    public String getTPartnerCommission() {
        return tPartnerCommission;
    }

    public void setTPartnerCommission(String tPartnerCommission) {
        this.tPartnerCommission = tPartnerCommission;
    }

    public String getTSdiscount() {
        return tSdiscount;
    }

    public void setTSdiscount(String tSdiscount) {
        this.tSdiscount = tSdiscount;
    }

    public FareBreakup getFareBreakup() {
        return fareBreakup;
    }

    public void setFareBreakup(FareBreakup fareBreakup) {
        this.fareBreakup = fareBreakup;
    }

    public String getOcTax() {
        return ocTax;
    }

    public void setOcTax(String ocTax) {
        this.ocTax = ocTax;
    }
}
