
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDepart {

    @SerializedName("OriginDestinationOptions")
    @Expose
    private OriginDestinationOptions originDestinationOptions;

    public OriginDestinationOptions getOriginDestinationOptions() {
        return originDestinationOptions;
    }

    public void setOriginDestinationOptions(OriginDestinationOptions originDestinationOptions) {
        this.originDestinationOptions = originDestinationOptions;
    }

}
