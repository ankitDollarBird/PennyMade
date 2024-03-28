package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryDropdownListData {
    @SerializedName("name")
    String name;
    @SerializedName("dropdownlist")
    List<DropdownList> dropdownLists;

    public String getName() {
        return name;
    }

    public List<DropdownList> getDropdownLists() {
        return dropdownLists;
    }
}
