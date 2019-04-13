
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonchargeableFares {

    @SerializedName("TCharge")
    @Expose
    private String tCharge;
    @SerializedName("TMarkup")
    @Expose
    private String tMarkup;
    @SerializedName("TSdiscount")
    @Expose
    private String tSdiscount;

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
