package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectablesItemsData {
    @SerializedName("data")
    List<CollectableItemsListData> collectableItemsListData;
    @SerializedName("totalpages")
    int totalPages;
    @SerializedName("previouspage")
    int previousPage;
    @SerializedName("nextpage")
    int nextPage;
    @SerializedName("lastpage")
    int lastPage;
    @SerializedName("firstpage")
    int firstPage;

    public int getLastPage() {
        return lastPage;
    }

    public int getTotalPages() {
        return totalPages;
    }


    public int getPreviousPage() {
        return previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public List<CollectableItemsListData> getCollectableItemsListData() {
        return collectableItemsListData;
    }

    public void setCollectableItemsListData(List<CollectableItemsListData> collectableItemsListData) {
        this.collectableItemsListData = collectableItemsListData;
    }

}
