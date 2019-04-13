package com.payz24.responseModels.domesticPreFare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahesh on 3/19/2018.
 */

public class DomesticPreFare {
    @SerializedName("search")
    @Expose
    private Search search;

    public DomesticPreFare(Search search) {
        this.search = search;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }
}
