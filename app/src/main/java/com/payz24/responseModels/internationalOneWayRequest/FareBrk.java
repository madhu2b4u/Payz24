package com.payz24.responseModels.internationalOneWayRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/19/2018.
 */

public class FareBrk {

    @SerializedName("PsgrType")
    @Expose
    private String psgrType;
    @SerializedName("BaseFare")
    @Expose
    private String baseFare;
    @SerializedName("Tax")
    @Expose
    private String tax;
    @SerializedName("Total")
    @Expose
    private Integer total;

    public FareBrk(String psgrType, String baseFare, String tax, Integer totalFareWithTax) {
        this.psgrType = psgrType;
        this.baseFare = baseFare;
        this.tax = tax;
        this.total = totalFareWithTax;
    }

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
