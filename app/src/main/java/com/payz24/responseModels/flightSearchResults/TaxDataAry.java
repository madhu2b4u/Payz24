package com.payz24.responseModels.flightSearchResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 2/8/2018.
 */

public class TaxDataAry {

    @SerializedName("TaxData")
    @Expose
    private List<TaxDatum> taxData;

    public List<TaxDatum> getTaxData() {
        return taxData;
    }

    public void setTaxData(List<TaxDatum> taxData) {
        this.taxData = taxData;
    }
}
