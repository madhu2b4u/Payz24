package com.payz24.responseModels.BusStoreDetailsDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.payz24.responseModels.availableBuses.*;

import java.util.List;

/**
 * Created by mahesh on 3/2/2018.
 */

public class BusData {

    @SerializedName("pnr")
    @Expose
    private String pnr;
    @SerializedName("etstnumber")
    @Expose
    private String etstnumber;
    @SerializedName("inventoryType")
    @Expose
    private Integer inventoryType;
    @SerializedName("routeScheduleId")
    @Expose
    private String routeScheduleId;
    @SerializedName("serviceId")
    @Expose
    private String serviceId;
    @SerializedName("fare")
    @Expose
    private String fare;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("arrivalTime")
    @Expose
    private String arrivalTime;
    @SerializedName("availableSeats")
    @Expose
    private Integer availableSeats;
    @SerializedName("operatorName")
    @Expose
    private String operatorName;
    @SerializedName("cancellationPolicy")
    @Expose
    private String cancellationPolicy;
    @SerializedName("boardingPoints")
    @Expose
    private List<com.payz24.responseModels.availableBuses.BoardingPoint> boardingPoints = null;
    @SerializedName("droppingPoints")
    @Expose
    private Object droppingPoints;
    @SerializedName("busType")
    @Expose
    private String busType;
    @SerializedName("partialCancellationAllowed")
    @Expose
    private Boolean partialCancellationAllowed;
    @SerializedName("idProofRequired")
    @Expose
    private Boolean idProofRequired;
    @SerializedName("operatorId")
    @Expose
    private Integer operatorId;
    @SerializedName("commPCT")
    @Expose
    private Integer commPCT;
    @SerializedName("mTicketAllowed")
    @Expose
    private Boolean mTicketAllowed;
    @SerializedName("isRTC")
    @Expose
    private Boolean isRTC;
    @SerializedName("isOpTicketTemplateRequired")
    @Expose
    private Boolean isOpTicketTemplateRequired;
    @SerializedName("isOpLogoRequired")
    @Expose
    private Boolean isOpLogoRequired;
    @SerializedName("isFareUpdateRequired")
    @Expose
    private Boolean isFareUpdateRequired;
    @SerializedName("is_child_concession")
    @Expose
    private Boolean isChildConcession;
    @SerializedName("isGetLayoutByBPDP")
    @Expose
    private Boolean isGetLayoutByBPDP;

    public BusData(String opPNR, String blockTicketKey, int inventoryType, String routeScheduleId, String serviceId, String fare, String departureTime, String arrivalTime, Integer availableSeats,
                   String operatorName, String cancellationPolicy, List<com.payz24.responseModels.availableBuses.BoardingPoint> boardingPoint, Object droppingPoint, String busType,
                   Boolean partialCancellationAllowed, Boolean idProofRequired, Integer operatorId, Double commPCT, Boolean mTicketAllowed, Boolean isRTC,
                   Boolean isOpTicketTemplateRequired, Boolean isOpLogoRequired, Boolean isFareUpdateRequired, Boolean isChildConcession, Boolean isGetLayoutByBPDP) {
        this.pnr = opPNR;
        this.etstnumber = blockTicketKey;
        this.inventoryType = inventoryType;
        this.routeScheduleId = routeScheduleId;
        this.serviceId = serviceId;
        this.fare = fare;
        this.departureTime =departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.operatorName = operatorName;
        this.cancellationPolicy = cancellationPolicy;
        this.boardingPoints = boardingPoint;
        this.droppingPoints = droppingPoint;
        this.busType = busType;
        this.partialCancellationAllowed = partialCancellationAllowed;
        this.idProofRequired = idProofRequired;
        this.operatorId = operatorId;
        this.commPCT = commPCT.intValue();
        this.mTicketAllowed = mTicketAllowed;
        this.isRTC = isRTC;
        this.isOpTicketTemplateRequired = isOpTicketTemplateRequired;
        this.isOpLogoRequired = isOpLogoRequired;
        this.isFareUpdateRequired = isFareUpdateRequired;
        this.isChildConcession = isChildConcession;
        this.isGetLayoutByBPDP = isGetLayoutByBPDP;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(String routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public List<com.payz24.responseModels.availableBuses.BoardingPoint> getBoardingPoints() {
        return boardingPoints;
    }

    public void setBoardingPoints(List<com.payz24.responseModels.availableBuses.BoardingPoint> boardingPoints) {
        this.boardingPoints = boardingPoints;
    }

    public Object getDroppingPoints() {
        return droppingPoints;
    }

    public void setDroppingPoints(Object droppingPoints) {
        this.droppingPoints = droppingPoints;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public Boolean getPartialCancellationAllowed() {
        return partialCancellationAllowed;
    }

    public void setPartialCancellationAllowed(Boolean partialCancellationAllowed) {
        this.partialCancellationAllowed = partialCancellationAllowed;
    }

    public Boolean getIdProofRequired() {
        return idProofRequired;
    }

    public void setIdProofRequired(Boolean idProofRequired) {
        this.idProofRequired = idProofRequired;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getCommPCT() {
        return commPCT;
    }

    public void setCommPCT(Integer commPCT) {
        this.commPCT = commPCT;
    }

    public Boolean getMTicketAllowed() {
        return mTicketAllowed;
    }

    public void setMTicketAllowed(Boolean mTicketAllowed) {
        this.mTicketAllowed = mTicketAllowed;
    }

    public Boolean getIsRTC() {
        return isRTC;
    }

    public void setIsRTC(Boolean isRTC) {
        this.isRTC = isRTC;
    }

    public Boolean getIsOpTicketTemplateRequired() {
        return isOpTicketTemplateRequired;
    }

    public void setIsOpTicketTemplateRequired(Boolean isOpTicketTemplateRequired) {
        this.isOpTicketTemplateRequired = isOpTicketTemplateRequired;
    }

    public Boolean getIsOpLogoRequired() {
        return isOpLogoRequired;
    }

    public void setIsOpLogoRequired(Boolean isOpLogoRequired) {
        this.isOpLogoRequired = isOpLogoRequired;
    }

    public Boolean getIsFareUpdateRequired() {
        return isFareUpdateRequired;
    }

    public void setIsFareUpdateRequired(Boolean isFareUpdateRequired) {
        this.isFareUpdateRequired = isFareUpdateRequired;
    }

    public Boolean getIsChildConcession() {
        return isChildConcession;
    }

    public void setIsChildConcession(Boolean isChildConcession) {
        this.isChildConcession = isChildConcession;
    }

    public Boolean getIsGetLayoutByBPDP() {
        return isGetLayoutByBPDP;
    }

    public void setIsGetLayoutByBPDP(Boolean isGetLayoutByBPDP) {
        this.isGetLayoutByBPDP = isGetLayoutByBPDP;
    }
}
