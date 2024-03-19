package com.example.pennymead.remote.ApiModel;

import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.ListCategories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("categories/")
    Call<ListCategories> getListCategories();

    @GET("{pageNumber}/")
    Call<CollectablesItems> getCollectableItems(@Path("pageNumber") int pageNumber);

    @GET("1/newest/1/")
    Call<CollectablesItems> getCategoryCollectablesItems();

}
