package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisteredUser {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<RegisteredUserDetails> data;

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

    public List<RegisteredUserDetails> getData() {
        return data;
    }

    public void setData(List<RegisteredUserDetails> data) {
        this.data = data;
    }

}
