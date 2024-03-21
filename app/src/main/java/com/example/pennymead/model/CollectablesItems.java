package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectablesItems {
    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    CollectablesItemsData collectablesItemsData;
    @SerializedName("categorydescription")
    List<CategoryDescription> categoryDescription;

    public CollectablesItemsData getCollectablesItemsData() {
        return collectablesItemsData;
    }

    public List<CategoryDescription> getCategoryDescription() {
        return categoryDescription;
    }

    public void setCollectablesItemsData(CollectablesItemsData collectablesItemsData) {
        this.collectablesItemsData = collectablesItemsData;
    }
}
