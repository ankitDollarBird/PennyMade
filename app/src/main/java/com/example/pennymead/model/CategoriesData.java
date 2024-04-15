package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesData {
    @SerializedName("title")
    String title;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    List<String> image;
    @SerializedName("category")
    String category;

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public List<String> getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }
}
