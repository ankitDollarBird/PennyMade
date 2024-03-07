package com.example.pennymead.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCategories {
    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<CategoriesData> dataList;

    public List<CategoriesData> getDataList() {
        return dataList;
    }

    public void setDataList(List<CategoriesData> dataList) {
        this.dataList = dataList;
    }
}
