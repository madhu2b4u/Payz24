
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChargeableFares {

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

}
