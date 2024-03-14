package com.example.pennymead.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesData {
    @SerializedName("title")
    String title;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    List<String> image;
    @SerializedName("author")
    String author;
    @SerializedName("category")
    String category;

    public  String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public  List<String> getImage() {
        return image;
    }

}
