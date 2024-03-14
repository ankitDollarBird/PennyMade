package com.example.pennymead.model.home;

import android.util.Log;

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
        Log.d("Last page is",totalPages+"  in Collectables Items data class");
        return lastPage;
    }

    public int getTotalPages() {
        Log.d("total page is",totalPages+"  in Collectables Items data class");
        return totalPages;
    }


    public int getPreviousPage() {

        Log.d("previous page is",previousPage+" in Collectables Items data class");
        return previousPage;
    }

    public int getNextPage() {
        Log.d("next page is",nextPage+" in Collectables Items data class");
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
