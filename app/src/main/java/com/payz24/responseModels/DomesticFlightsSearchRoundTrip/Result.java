
package com.payz24.responseModels.DomesticFlightsSearchRoundTrip;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("Request")
    @Expose
    private Request request;
    @SerializedName("Response__Depart")
    @Expose
    private ResponseDepart responseDepart;
    @SerializedName("Response__Return")
    @Expose
    private ResponseReturn responseReturn;
    @SerializedName("error__tag")
    @Expose
    private List<Object> errorTag = null;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public ResponseDepart getResponseDepart() {
        return responseDepart;
    }

    public void setResponseDepart(ResponseDepart responseDepart) {
        this.responseDepart = responseDepart;
    }

    public ResponseReturn getResponseReturn() {
        return responseReturn;
    }

    public void setResponseReturn(ResponseReturn responseReturn) {
        this.responseReturn = responseReturn;
    }

    public List<Object> getErrorTag() {
        return errorTag;
    }

    public void setErrorTag(List<Object> errorTag) {
        this.errorTag = errorTag;
    }

}
