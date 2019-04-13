package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/8/2018.
 */

public class Fare {
    @SerializedName("PsgrType")
    @Expose
    private String psgrType;
    @SerializedName("BaseFare")
    @Expose
    private String baseFare;
    @SerializedName("Tax")
    @Expose
    private String tax;
    @SerializedName("TaxDataAry")
    @Expose
    private TaxDataAry taxDataAry;

    public String getPsgrType() {
        return psgrType;
    }

    public void setPsgrType(String psgrType) {
        this.psgrType = psgrType;
    }

    public String getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(String baseFare) {
        this.baseFare = baseFare;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public TaxDataAry getTaxDataAry() {
        return taxDataAry;
    }

    public void setTaxDataAry(TaxDataAry taxDataAry) {
        this.taxDataAry = taxDataAry;
    }
}
