package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryDropdownList {

    @SerializedName("data")
    List<SubCategoryDropdownListData> subCategoryDropdownListDataList;

    public List<SubCategoryDropdownListData> getSubCategoryDropdownListDataList() {
        return subCategoryDropdownListDataList;
    }
}
