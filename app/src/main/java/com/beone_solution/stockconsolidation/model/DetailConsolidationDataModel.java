package com.beone_solution.stockconsolidation.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailConsolidationDataModel
{
    @SerializedName("conID")
    @Expose
    private String conID;

    /**
     * No args constructor for use in serialization
     *
     */
    public DetailConsolidationDataModel() {
    }

    /**
     *
     * @param conID
     */
    public DetailConsolidationDataModel(String conID) {
        super();
        this.conID = conID;
    }

    public String getConID() {
        return conID;
    }

    public void setConID(String conID) {
        this.conID = conID;
    }
}
