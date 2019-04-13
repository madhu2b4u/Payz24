package com.payz24.responseModels.blockTicketResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 2/15/2018.
 */

public class BlockTicketResponse {

    @SerializedName("apiStatus")
    @Expose
    private ApiStatus apiStatus;
    @SerializedName("inventoryType")
    @Expose
    private Integer inventoryType;
    @SerializedName("blockTicketKey")
    @Expose
    private String blockTicketKey;

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getBlockTicketKey() {
        return blockTicketKey;
    }

    public void setBlockTicketKey(String blockTicketKey) {
        this.blockTicketKey = blockTicketKey;
    }
}
