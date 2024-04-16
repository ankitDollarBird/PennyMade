package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutUs {

    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<AboutUsContent> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AboutUsContent> getData() {
        return data;
    }

    public void setData(List<AboutUsContent> data) {
        this.data = data;
    }

}
