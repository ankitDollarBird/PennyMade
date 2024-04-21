package com.example.pennymead.remote.ApiModel;

import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.ListCategories;
import com.example.pennymead.model.ProductDetail;
import com.example.pennymead.model.SearchCollectableItems;
import com.example.pennymead.model.SearchData;
import com.example.pennymead.model.SubCategoryDropdownList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("categories/")
    Call<ListCategories> getListCategories();

    @GET("{pageNumber}/")
    Call<CollectablesItems> getCollectableItems(@Path("pageNumber") int pageNumber);

    @GET("{selectedPage}/")
    Call<CollectablesItems> getCategoryCollectablesItems(@Path("selectedPage") int selectedPage);

    @GET("{category}/")
    Call<SubCategoryDropdownList> getSubCategoryDropdownList(@Path("category") int category);

    @GET("{reference}/{filter}/{page}/")
    Call<CollectablesItems> getSubCategoryDropdownListData(@Path("reference") int reference, @Path("filter") String filter, @Path("page") int page);

    @POST("search-keyword/")
    Call<SearchCollectableItems> getCollectableItemsBySearch(@Body SearchData searchData);

    @GET("{sysid}/")
    Call<ProductDetail> getCollectableRelatedItems(@Path("sysid") String sysid);

}
