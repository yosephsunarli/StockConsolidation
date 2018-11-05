package com.beone_solution.stockconsolidation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailConsolidationModel
{
    @SerializedName("conID")
    @Expose
    private String conID;
    @SerializedName("conImage")
    @Expose
    private String conImage;
    @SerializedName("artNo")
    @Expose
    private String artNo;
    @SerializedName("artName")
    @Expose
    private String artName;
    @SerializedName("collection")
    @Expose
    private String collection;
    @SerializedName("ofWeeks")
    @Expose
    private Integer ofWeeks;
    @SerializedName("sellThru")
    @Expose
    private Double sellThru;
    @SerializedName("retailPrice")
    @Expose
    private Double retailPrice;
    @SerializedName("disc")
    @Expose
    private Double disc;
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
    @SerializedName("itemCode")
    @Expose
    private String itemCode;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("fromBQty")
    @Expose
    private Double fromBQty;
    @SerializedName("toBQty")
    @Expose
    private Double toBQty;
    @SerializedName("suggestedQty")
    @Expose
    private Double suggestedQty;

    /**
     * No args constructor for use in serialization
     *
     */
    public DetailConsolidationModel() {
    }

    /**
     *
     * @param toWhsCode
     * @param fromWhsCode
     * @param ofWeeks
     * @param disc
     * @param itemCode
     * @param size
     * @param fromWhsName
     * @param fromBQty
     * @param sellThru
     * @param retailPrice
     * @param toWhsName
     * @param artNo
     * @param artName
     * @param collection
     * @param toBQty
     * @param suggestedQty
     */
    public DetailConsolidationModel(String conID, String conImage, String artNo, String artName, String collection, Integer ofWeeks, Double sellThru, Double retailPrice, Double disc, String fromWhsCode, String fromWhsName, String toWhsCode, String toWhsName, String itemCode, String size, Double fromBQty, Double toBQty, Double suggestedQty) {
        super();
        this.conID = conID;
        this.conImage = conImage;
        this.artNo = artNo;
        this.artName = artName;
        this.collection = collection;
        this.ofWeeks = ofWeeks;
        this.sellThru = sellThru;
        this.retailPrice = retailPrice;
        this.disc = disc;
        this.fromWhsCode = fromWhsCode;
        this.fromWhsName = fromWhsName;
        this.toWhsCode = toWhsCode;
        this.toWhsName = toWhsName;
        this.itemCode = itemCode;
        this.size = size;
        this.fromBQty = fromBQty;
        this.toBQty = toBQty;
        this.suggestedQty = suggestedQty;
    }

    public String getConID() {
        return conID;
    }

    public void setConID(String conID) {
        this.conID = conID;
    }

    public String getConImage() {
        return conImage;
    }

    public void setConImage(String conImage) {
        this.conImage = conImage;
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

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Integer getOfWeeks() {
        return ofWeeks;
    }

    public void setOfWeeks(Integer ofWeeks) {
        this.ofWeeks = ofWeeks;
    }

    public Double getSellThru() {
        return sellThru;
    }

    public void setSellThru(Double sellThru) {
        this.sellThru = sellThru;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getDisc() {
        return disc;
    }

    public void setDisc(Double disc) {
        this.disc = disc;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setitemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getFromBQty() {
        return fromBQty;
    }

    public void setFromBQty(Double fromBQty) {
        this.fromBQty = fromBQty;
    }

    public Double getToBQty() {
        return toBQty;
    }

    public void setToBQty(Double toBQty) {
        this.toBQty = toBQty;
    }

    public Double getSuggestedQty() {
        return suggestedQty;
    }

    public void setSuggestedQty(Double suggestedQty) {
        this.suggestedQty = suggestedQty;
    }
}
