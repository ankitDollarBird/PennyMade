package com.example.pennymead.remote.ApiModel;

import com.example.pennymead.model.AboutUs;
import com.example.pennymead.model.CartItems;
import com.example.pennymead.model.CollectableItemsForCheckout;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.Country;
import com.example.pennymead.model.ListCategories;
import com.example.pennymead.model.OrderPlacedDetail;
import com.example.pennymead.model.OrderPlacing;
import com.example.pennymead.model.OrderSummaryDetails;
import com.example.pennymead.model.ProductDetail;
import com.example.pennymead.model.RegisteredUser;
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

    @GET("countries/")
    Call<Country> getCountryList();

    @POST("viewbasket/")
    Call<CollectableItemsForCheckout> getCollectableCartItemsForCheckout(@Body CartItems cartItems);

    @GET("{email}/")
    Call<RegisteredUser> getRegisteredUserDetails(@Path("email") String email);

    @POST("orderplacing/")
    Call<OrderPlacedDetail> getOrderPlacedDetail(@Body OrderPlacing orderPlacing);

    @GET("{orderNumber}/{email}/")
    Call<OrderSummaryDetails> getOrderSummaryDetails(@Path("orderNumber") Integer orderNumber, @Path("email") String email);
    @GET("getaboutpage_content/")
    Call<AboutUs> getAboutUsContent();

}
