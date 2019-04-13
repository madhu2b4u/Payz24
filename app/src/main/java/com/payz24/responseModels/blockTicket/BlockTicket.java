package com.payz24.responseModels.blockTicket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mahesh on 1/31/2018.
 */

public class BlockTicket {

    @SerializedName("sourceCity")
    @Expose
    private String sourceCity;
    @SerializedName("destinationCity")
    @Expose
    private String destinationCity;
    @SerializedName("doj")
    @Expose
    private String doj;
    @SerializedName("routeScheduleId")
    @Expose
    private String routeScheduleId;
    @SerializedName("boardingPoint")
    @Expose
    private BoardingPoint boardingPoint;
    @SerializedName("droppingPoint")
    @Expose
    private DroppingPoint droppingPoint;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerLastName")
    @Expose
    private String customerLastName;
    @SerializedName("customerEmail")
    @Expose
    private String customerEmail;
    @SerializedName("customerPhone")
    @Expose
    private String customerPhone;
    @SerializedName("emergencyPhNumber")
    @Expose
    private String emergencyPhNumber;
    @SerializedName("customerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("blockSeatPaxDetails")
    @Expose
    private List<BlockSeatPaxDetail> blockSeatPaxDetails = null;
    @SerializedName("inventoryType")
    @Expose
    private Integer inventoryType;

    public BlockTicket(String sourceCity, String destinationCity, String dateOfJourney, String routeScheduleId, BoardingPoint boardingPoint, DroppingPoint droppingPoint, String customerName, String customerLastName, String customerEmail, String customerPhone, String emergencyPhNumber, String customerAddress, List<BlockSeatPaxDetail> listOfBlockSeatPaxDetails, int inventoryType) {
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.doj = dateOfJourney;
        this.routeScheduleId = routeScheduleId;
        this.boardingPoint = boardingPoint;
        this.droppingPoint = droppingPoint;
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.emergencyPhNumber = emergencyPhNumber;
        this.customerAddress = customerAddress;
        this.blockSeatPaxDetails = listOfBlockSeatPaxDetails;
        this.inventoryType = inventoryType;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getRouteScheduleId() {
        return routeScheduleId;
    }

    public void setRouteScheduleId(String routeScheduleId) {
        this.routeScheduleId = routeScheduleId;
    }

    public BoardingPoint getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(BoardingPoint boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public DroppingPoint getDroppingPoint() {
        return droppingPoint;
    }

    public void setDroppingPoint(DroppingPoint droppingPoint) {
        this.droppingPoint = droppingPoint;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getEmergencyPhNumber() {
        return emergencyPhNumber;
    }

    public void setEmergencyPhNumber(String emergencyPhNumber) {
        this.emergencyPhNumber = emergencyPhNumber;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public List<BlockSeatPaxDetail> getBlockSeatPaxDetails() {
        return blockSeatPaxDetails;
    }

    public void setBlockSeatPaxDetails(List<BlockSeatPaxDetail> blockSeatPaxDetails) {
        this.blockSeatPaxDetails = blockSeatPaxDetails;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }
}
