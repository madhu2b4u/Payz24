package com.payz24.responseModels.storeBusDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/4/2018.
 */

public class Result {

    @SerializedName("master_id")
    @Expose
    private Integer masterId;

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }
}
