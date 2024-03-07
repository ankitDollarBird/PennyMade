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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
