package com.example.pennymead.remote.ApiModel;

import com.example.pennymead.model.home.ListCategories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("categories/")
    Call<ListCategories> getListCategories();
}
