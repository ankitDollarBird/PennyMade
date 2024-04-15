package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {

    @SerializedName("data")
    private List<CountryList> data;

    public List<CountryList> getData() {
        return data;
    }

    public void setData(List<CountryList> data) {
        this.data = data;
    }

}
