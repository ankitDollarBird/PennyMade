package com.example.pennymead.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectablesItems {
    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    CollectablesItemsData collectablesItemsData;

    public CollectablesItemsData getCollectablesItemsData() {
        return collectablesItemsData;
    }

    public void setCollectablesItemsData(CollectablesItemsData collectablesItemsData) {
        this.collectablesItemsData = collectablesItemsData;
    }
}
