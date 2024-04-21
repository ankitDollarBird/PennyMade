package com.example.pennymead.remote.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pennymead.model.AboutUs;
import com.example.pennymead.model.CartItems;
import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.model.CollectableItemsForCheckout;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.CollectablesItemsData;
import com.example.pennymead.model.Country;
import com.example.pennymead.model.CountryList;
import com.example.pennymead.model.ListCategories;
import com.example.pennymead.model.OrderPlacedDetail;
import com.example.pennymead.model.OrderPlacing;
import com.example.pennymead.model.OrderSummaryDetails;
import com.example.pennymead.model.ProductDetail;
import com.example.pennymead.model.RegisteredUser;
import com.example.pennymead.model.RegisteredUserDetails;
import com.example.pennymead.model.SearchCollectableItems;
import com.example.pennymead.model.SearchData;
import com.example.pennymead.model.SubCategoryDropdownList;
import com.example.pennymead.model.SubCategoryDropdownListData;
import com.example.pennymead.remote.ApiModel.ApiClient;
import com.example.pennymead.remote.ApiModel.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    MutableLiveData<List<CategoriesData>> liveDataCollectables;
    MutableLiveData<CollectablesItemsData> liveDataCollectableItems;
    MutableLiveData<CollectablesItems> liveDataCategoryCollectableItems;
    MutableLiveData<List<SubCategoryDropdownListData>> liveDataSubCategoryDropdownList;
    MutableLiveData<SearchCollectableItems> liveDataCollectableItemsBySearch;
    MutableLiveData<ProductDetail> liveDataCollectablesRelatedItems;
    MutableLiveData<List<CountryList>> liveDataCountryList;
    MutableLiveData<CollectableItemsForCheckout> liveDataCollectableItemsForCheckout;
    MutableLiveData<List<RegisteredUserDetails>> registeredUserDetailsMutableLiveData;
    MutableLiveData<OrderPlacedDetail> liveDataOfOrderedPlacedDetail;
    MutableLiveData<OrderSummaryDetails> liveDataOfOrderSummary;
    MutableLiveData<AboutUs> liveDataOfAboutUs;
    ApiInterface apiInterface;
    ListCategories listCategories;
    List<CategoriesData> listCategoriesDataList;
    OrderPlacedDetail orderPlacedDetail;

    //Collectables
    public MutableLiveData<List<CategoriesData>> getCollectablesLiveData() {

        liveDataCollectables = new MutableLiveData<>();

        apiInterface = ApiClient.baseUrl().create(ApiInterface.class);

        apiInterface.getListCategories().enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                if (response.isSuccessful()) {
                    listCategories = response.body();
                    listCategoriesDataList = listCategories.getDataList();
                    liveDataCollectables.postValue(listCategoriesDataList);
                } else {
                    liveDataCollectables.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
                liveDataCollectables.postValue(null);
            }
        });
        return liveDataCollectables;
    }

    //collectables items
    public MutableLiveData<CollectablesItemsData> getCollectablesItemsLiveData(String filters, int pageNumber) {

        liveDataCollectableItems = new MutableLiveData<>();

        apiInterface = ApiClient.getCollectableItems(filters).create(ApiInterface.class);

        apiInterface.getCollectableItems(pageNumber).enqueue(new Callback<CollectablesItems>() {
            @Override
            public void onResponse(Call<CollectablesItems> call, Response<CollectablesItems> response) {

                if (response.isSuccessful()) {
                    CollectablesItems collectablesItems = response.body();
                    if (collectablesItems != null) {
                        CollectablesItemsData collectablesItemsData = collectablesItems.getCollectablesItemsData();
                        if (collectablesItemsData != null) {
                            liveDataCollectableItems.postValue(collectablesItemsData);
                        }
                    }
                } else {
                    liveDataCollectableItems.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<CollectablesItems> call, Throwable t) {
                liveDataCollectableItems.postValue(null);
                Log.d("Unable to fetch the data", "in collectables items repositories");
            }
        });

        return liveDataCollectableItems;
    }

    //subcategory data
    public MutableLiveData<CollectablesItems> getCategoryCollectablesItemsLiveData(int subCategory, String selectedFilter, int selectedPage) {
        liveDataCategoryCollectableItems = new MutableLiveData<>();
        apiInterface = ApiClient.getCategoryCollectableItems(subCategory, selectedFilter).create(ApiInterface.class);
        apiInterface.getCategoryCollectablesItems(selectedPage).enqueue(new Callback<CollectablesItems>() {
            @Override
            public void onResponse(Call<CollectablesItems> call, Response<CollectablesItems> response) {

                if (response.isSuccessful()) {
                    CollectablesItems collectablesItems = response.body();
                    if (collectablesItems != null) {
                        liveDataCategoryCollectableItems.postValue(collectablesItems);
                    }
                } else {
                    liveDataCategoryCollectableItems.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<CollectablesItems> call, Throwable t) {
                liveDataCategoryCollectableItems.postValue(null);
            }
        });
        return liveDataCategoryCollectableItems;
    }

    //subcategory data with dropdown items data
    public MutableLiveData<CollectablesItems> getSubCategoryDropdownListData(int subCategory, int reference, String selectedFilter, int selectedPage) {
        liveDataCategoryCollectableItems = new MutableLiveData<>();
        apiInterface = ApiClient.getSubCategoryDropdownListData(subCategory).create(ApiInterface.class);
        apiInterface.getSubCategoryDropdownListData(reference, selectedFilter, selectedPage).enqueue(new Callback<CollectablesItems>() {
            @Override
            public void onResponse(Call<CollectablesItems> call, Response<CollectablesItems> response) {

                if (response.isSuccessful()) {
                    CollectablesItems collectablesItems = response.body();
                    if (collectablesItems != null) {
                        liveDataCategoryCollectableItems.postValue(collectablesItems);
                    }
                } else {
                    liveDataCategoryCollectableItems.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CollectablesItems> call, Throwable t) {
                Log.d("Data--------------->", "unable to fetch");
            }
        });
        return liveDataCategoryCollectableItems;
    }

    //sub category dropdown items
    public MutableLiveData<List<SubCategoryDropdownListData>> getLiveDataSubCategoryDropdownList(int category) {
        liveDataSubCategoryDropdownList = new MutableLiveData<>();
        apiInterface = ApiClient.getSubCategoryDropdownList().create(ApiInterface.class);
        apiInterface.getSubCategoryDropdownList(category).enqueue(new Callback<SubCategoryDropdownList>() {
            @Override
            public void onResponse(Call<SubCategoryDropdownList> call, Response<SubCategoryDropdownList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSubCategoryDropdownListDataList() != null) {
                        liveDataSubCategoryDropdownList.postValue(response.body().getSubCategoryDropdownListDataList());
                    } else {
                        liveDataSubCategoryDropdownList.postValue(null);
                    }
                } else {
                    liveDataSubCategoryDropdownList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SubCategoryDropdownList> call, Throwable t) {
                liveDataSubCategoryDropdownList.postValue(null);
            }
        });
        return liveDataSubCategoryDropdownList;
    }

    public MutableLiveData<SearchCollectableItems> getLiveDataCollectableItemsBySearch(SearchData searchData) {
        liveDataCollectableItemsBySearch = new MutableLiveData<>();
        apiInterface = ApiClient.baseUrl().create(ApiInterface.class);
        apiInterface.getCollectableItemsBySearch(searchData).enqueue(new Callback<SearchCollectableItems>() {
            @Override
            public void onResponse(Call<SearchCollectableItems> call, Response<SearchCollectableItems> response) {
                if (response.isSuccessful()) {
                    liveDataCollectableItemsBySearch.postValue(response.body());
                } else {
                    liveDataCollectableItemsBySearch.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchCollectableItems> call, Throwable t) {
                Log.d("term data----------->", searchData.getTerm() + "------------ in response Failed");
            }
        });
        return liveDataCollectableItemsBySearch;
    }
    public MutableLiveData<ProductDetail> getLiveDataCollectableRelatedItems(String sysId) {
        liveDataCollectablesRelatedItems = new MutableLiveData<>();
        apiInterface = ApiClient.getCollectableRelatedItems().create(ApiInterface.class);
        apiInterface.getCollectableRelatedItems(sysId).enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if(response.isSuccessful()){
                    liveDataCollectablesRelatedItems.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        return liveDataCollectablesRelatedItems;
    }

    public MutableLiveData<List<CountryList>> getCountryList() {

        liveDataCountryList = new MutableLiveData<>();
        apiInterface = ApiClient.baseUrl().create(ApiInterface.class);
        apiInterface.getCountryList().enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                if (response.isSuccessful()) {
                    liveDataCountryList.postValue((List<CountryList>) response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {

            }
        });
        return liveDataCountryList;
    }

    public MutableLiveData<CollectableItemsForCheckout> getLiveDataCollectableCartItemsForCheckout(CartItems cartItems) {

        liveDataCollectableItemsForCheckout = new MutableLiveData<>();

        apiInterface = ApiClient.baseUrl().create(ApiInterface.class);
        apiInterface.getCollectableCartItemsForCheckout(cartItems).enqueue(new Callback<CollectableItemsForCheckout>() {
            @Override
            public void onResponse(Call<CollectableItemsForCheckout> call, Response<CollectableItemsForCheckout> response) {
                if (response.isSuccessful()) {
                    liveDataCollectableItemsForCheckout.postValue(response.body());
                } else {
                    liveDataCollectableItemsForCheckout.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CollectableItemsForCheckout> call, Throwable t) {

            }
        });
        return liveDataCollectableItemsForCheckout;
    }

    public MutableLiveData<List<RegisteredUserDetails>> getRegisteredUserDetailsLiveData(String email) {
        registeredUserDetailsMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getRegisteredUSerDetail().create(ApiInterface.class);
        apiInterface.getRegisteredUserDetails(email).enqueue(new Callback<RegisteredUser>() {
            @Override
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    registeredUserDetailsMutableLiveData.postValue(response.body().getData());
                } else {
                    registeredUserDetailsMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {

            }
        });
        return registeredUserDetailsMutableLiveData;
    }

    public MutableLiveData<OrderPlacedDetail> getLiveDataOfOrderedPlacedDetail(OrderPlacing orderPlacing) {

        liveDataOfOrderedPlacedDetail = new MutableLiveData<>();

        apiInterface = ApiClient.baseUrl().create(ApiInterface.class);
        apiInterface.getOrderPlacedDetail(orderPlacing).enqueue(new Callback<OrderPlacedDetail>() {
            @Override
            public void onResponse(Call<OrderPlacedDetail> call, Response<OrderPlacedDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                        orderPlacedDetail = response.body();
                        liveDataOfOrderedPlacedDetail.postValue(orderPlacedDetail);
                } else {
                    liveDataOfOrderedPlacedDetail.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<OrderPlacedDetail> call, Throwable t) {

            }
        });
        return liveDataOfOrderedPlacedDetail;
    }

    public MutableLiveData<OrderSummaryDetails> getLiveDataOfOrderSummary(Integer orderNumber, String email) {

        liveDataOfOrderSummary = new MutableLiveData<>();

        apiInterface = ApiClient.getOrderSummary().create(ApiInterface.class);
        apiInterface.getOrderSummaryDetails(orderNumber, email).enqueue(new Callback<OrderSummaryDetails>() {
            @Override
            public void onResponse(Call<OrderSummaryDetails> call, Response<OrderSummaryDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                        liveDataOfOrderSummary.postValue(response.body());
                } else {
                    liveDataOfOrderSummary.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<OrderSummaryDetails> call, Throwable t) {
                liveDataOfOrderSummary.postValue(null);
            }
        });
        return liveDataOfOrderSummary;
    }
    public MutableLiveData<AboutUs> getLiveDataOfAboutUs() {

        liveDataOfAboutUs = new MutableLiveData<>();

        apiInterface = ApiClient.getAboutUsData().create(ApiInterface.class);
        apiInterface.getAboutUsContent().enqueue(new Callback<AboutUs>() {
            @Override
            public void onResponse(Call<AboutUs> call, Response<AboutUs> response) {
                if(response.isSuccessful() && response.body()!=null){
                  liveDataOfAboutUs.postValue(response.body());
                }
                else{
                    liveDataOfAboutUs.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AboutUs> call, Throwable t) {
                liveDataOfAboutUs.postValue(null);
            }
        });
        return liveDataOfAboutUs;
    }

}
