package com.example.pennymead.remote.ApiModel;

import com.example.pennymead.model.home.CollectablesItems;
import com.example.pennymead.model.home.ListCategories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("categories/")
    Call<ListCategories> getListCategories();

    @GET("{pageNumber}/")
    Call<CollectablesItems> getCollectableItems(@Path("pageNumber") int pageNumber);

}
