package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchCollectableItems {
    @SerializedName("searchresults")
    List<CollectableItemsListData> searchList;
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

    public int getTotalPages() {
        return totalPages;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public List<CollectableItemsListData> getSearchList() {
        return searchList;
    }
}
