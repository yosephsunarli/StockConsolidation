package com.beone_solution.stockconsolidation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListConsolidationModel {
    @SerializedName("conID")
    @Expose
    private String conID;
    @SerializedName("artNo")
    @Expose
    private String artNo;
    @SerializedName("artName")
    @Expose
    private String artName;
    @SerializedName("fromWhsCode")
    @Expose
    private String fromWhsCode;
    @SerializedName("fromWhsName")
    @Expose
    private String fromWhsName;
    @SerializedName("toWhsCode")
    @Expose
    private String toWhsCode;
    @SerializedName("toWhsName")
    @Expose
    private String toWhsName;
    @SerializedName("fromBQty")
    @Expose
    private Double fromBQty;
    @SerializedName("fromEQty")
    @Expose
    private Double fromEQty;
    @SerializedName("toBQty")
    @Expose
    private Double toBQty;
    @SerializedName("toEQty")
    @Expose
    private Double toEQty;

    private boolean isChecked;

    /**
     * No args constructor for use in serialization
     *
     */
    public ListConsolidationModel() {
    }

    /**
     *
     * @param fromWhsName
     * @param fromBQty
     * @param toWhsName
     * @param artNo
     * @param toEQty
     * @param toWhsCode
     * @param toBQty
     * @param fromEQty
     * @param fromWhsCode
     */
    public ListConsolidationModel(String conID, String artNo, String artName, String fromWhsCode, String fromWhsName, String toWhsCode, String toWhsName, Double fromBQty, Double fromEQty, Double toBQty, Double toEQty) {
        super();
        this.conID = conID;
        this.artNo = artNo;
        this.artName = artName;
        this.fromWhsCode = fromWhsCode;
        this.fromWhsName = fromWhsName;
        this.toWhsCode = toWhsCode;
        this.toWhsName = toWhsName;
        this.fromBQty = fromBQty;
        this.fromEQty = fromEQty;
        this.toBQty = toBQty;
        this.toEQty = toEQty;
    }

    public String getConID() {
        return conID;
    }

    public void setConID(String conID) {
        this.conID = conID;
    }

    public String getArtNo() {
        return artNo;
    }

    public void setArtNo(String artNo) {
        this.artNo = artNo;
    }

    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }

    public String getFromWhsCode() {
        return fromWhsCode;
    }

    public void setFromWhsCode(String fromWhsCode) {
        this.fromWhsCode = fromWhsCode;
    }

    public String getFromWhsName() {
        return fromWhsName;
    }

    public void setFromWhsName(String fromWhsName) {
        this.fromWhsName = fromWhsName;
    }

    public String getToWhsCode() {
        return toWhsCode;
    }

    public void setToWhsCode(String toWhsCode) {
        this.toWhsCode = toWhsCode;
    }

    public String getToWhsName() {
        return toWhsName;
    }

    public void setToWhsName(String toWhsName) {
        this.toWhsName = toWhsName;
    }

    public Double getFromBQty() {
        return fromBQty;
    }

    public void setFromBQty(Double fromBQty) {
        this.fromBQty = fromBQty;
    }

    public Double getFromEQty() {
        return fromEQty;
    }

    public void setFromEQty(Double fromEQty) {
        this.fromEQty = fromEQty;
    }

    public Double getToBQty() {
        return toBQty;
    }

    public void setToBQty(Double toBQty) {
        this.toBQty = toBQty;
    }

    public Double getToEQty() {
        return toEQty;
    }

    public void setToEQty(Double toEQty) {
        this.toEQty = toEQty;
    }

    public boolean getChecked() { return isChecked; }

    public void setChecked(boolean isChecked) { this.isChecked = isChecked; }
}
