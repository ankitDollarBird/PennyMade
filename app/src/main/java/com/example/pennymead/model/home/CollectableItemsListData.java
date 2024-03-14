package com.example.pennymead.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectableItemsListData {
    @SerializedName("title")
    String title;
    @SerializedName("price")
    String price;
    @SerializedName("image")
    List<String> image;
    @SerializedName("author")
    String author;
    @SerializedName("description")
    String description;

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public List<String> getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

}
