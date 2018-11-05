package com.beone_solution.stockconsolidation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDataModel {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("conID")
    @Expose
    private String conID;
    @SerializedName("itemStyle")
    @Expose
    private String itemStyle;
    @SerializedName("itemCode")
    @Expose
    private String itemCode;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("suggestion")
    @Expose
    private Double suggestion;
    @SerializedName("fromEQty")
    @Expose
    private Double fromEQty;
    @SerializedName("toEQty")
    @Expose
    private Double toEQty;
    @SerializedName("totalFromEQty")
    @Expose
    private Double totalFromEQty;
    @SerializedName("totalToEQty")
    @Expose
    private Double totalToEQty;
    @SerializedName("allocationID")
    @Expose
    private String allocationID;
    @SerializedName("approveDate")
    @Expose
    private String approveDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public PostDataModel() {
    }

    /**
     *
     * @param totalFromEQty
     * @param toEQty
     * @param itemStyle
     * @param conID
     * @param fromEQty
     * @param suggestion
     * @param totalToEQty
     * @param type
     * @param size
     * @param itemCode
     */
    public PostDataModel(String type, String conID, String itemStyle, String itemCode, String size, Double suggestion, Double fromEQty, Double toEQty, Double totalFromEQty, Double totalToEQty, String allocationID, String approveDate) {
        super();
        this.type = type;
        this.conID = conID;
        this.itemStyle = itemStyle;
        this.itemCode = itemCode;
        this.size = size;
        this.suggestion = suggestion;
        this.fromEQty = fromEQty;
        this.toEQty = toEQty;
        this.totalFromEQty = totalFromEQty;
        this.totalToEQty = totalToEQty;
        this.allocationID = allocationID;
        this.approveDate = approveDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConID() {
        return conID;
    }

    public void setConID(String conID) {
        this.conID = conID;
    }

    public String getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(String itemStyle) {
        this.itemStyle = itemStyle;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Double suggestion) {
        this.suggestion = suggestion;
    }

    public Double getFromEQty() {
        return fromEQty;
    }

    public void setFromEQty(Double fromEQty) {
        this.fromEQty = fromEQty;
    }

    public Double getToEQty() {
        return toEQty;
    }

    public void setToEQty(Double toEQty) {
        this.toEQty = toEQty;
    }

    public Double getTotalFromEQty() {
        return totalFromEQty;
    }

    public void setTotalFromEQty(Double totalFromEQty) {
        this.totalFromEQty = totalFromEQty;
    }

    public Double getTotalToEQty() {
        return totalToEQty;
    }

    public void setTotalToEQty(Double totalToEQty) {
        this.totalToEQty = totalToEQty;
    }

    public String getAllocationID() {
        return allocationID;
    }

    public void setAllocationID(String allocationID) {
        this.allocationID = allocationID;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

}