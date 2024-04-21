package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectableItemsForCheckout {
    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;
    @SerializedName("collectableItems")
    List<CollectableItemsListData> collectableItemsListDataList;

    @SerializedName("result")
    List<CollectableItemsListData> collectableItemsForCart;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<CollectableItemsListData> getCollectableItemsForCart() {
        return collectableItemsForCart;
    }

    public List<CollectableItemsListData> getCollectableItemsListDataList() {
        return collectableItemsListDataList;
    }
}
