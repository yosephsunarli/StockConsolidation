package com.beone_solution.stockconsolidation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResultModel {

    @SerializedName("conID")
    @Expose
    private String conID;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("statusDesc")
    @Expose
    private String statusDesc;

    /**
     * No args constructor for use in serialization
     *
     */
    public PostResultModel() {
    }

    /**
     *
     * @param statusCode
     * @param conID
     * @param statusDesc
     */
    public PostResultModel(String conID, String statusCode, String statusDesc) {
        super();
        this.conID = conID;
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    public String getConID() {
        return conID;
    }

    public void setConID(String conID) {
        this.conID = conID;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

}