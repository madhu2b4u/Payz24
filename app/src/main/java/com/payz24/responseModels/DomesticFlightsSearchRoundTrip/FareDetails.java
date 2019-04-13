
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FareDetails {

    @SerializedName("ChargeableFares")
    @Expose
    private ChargeableFares chargeableFares;
    @SerializedName("NonchargeableFares")
    @Expose
    private NonchargeableFares nonchargeableFares;

    public ChargeableFares getChargeableFares() {
        return chargeableFares;
    }

    public void setChargeableFares(ChargeableFares chargeableFares) {
        this.chargeableFares = chargeableFares;
    }

    public NonchargeableFares getNonchargeableFares() {
        return nonchargeableFares;
    }

    public void setNonchargeableFares(NonchargeableFares nonchargeableFares) {
        this.nonchargeableFares = nonchargeableFares;
    }

}
