package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

public class CategoryDescription {

    @SerializedName("category_description")
    String categoryDescription;

    public String getCategoryDescription() {
        return categoryDescription;
    }
}
