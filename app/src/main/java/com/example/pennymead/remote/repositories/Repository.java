package com.example.pennymead.remote.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.CollectablesItemsData;
import com.example.pennymead.model.ListCategories;
import com.example.pennymead.model.ProductDetail;
import com.example.pennymead.model.SearchCollectableItems;
import com.example.pennymead.model.SearchData;
import com.example.pennymead.model.SubCategoryDropdownList;
import com.example.pennymead.model.SubCategoryDropdownListData;
import com.example.pennymead.page.BaseActivity;
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
    ApiInterface apiInterface;
    ListCategories listCategories;
    List<CategoriesData> listCategoriesDataList;

    //Collectables
    public MutableLiveData<List<CategoriesData>> getCollectablesLiveData() {

        liveDataCollectables = new MutableLiveData<>();

        apiInterface = ApiClient.getCollectables().create(ApiInterface.class);

        apiInterface.getListCategories().enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                if (response.isSuccessful()) {

                    listCategories = response.body();
                    listCategoriesDataList = listCategories.getDataList();
                    liveDataCollectables.postValue(listCategoriesDataList);
                }
            }
            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {

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
                }
            }
            @Override
            public void onFailure(Call<CollectablesItems> call, Throwable t) {

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
                }
            }
            @Override
            public void onFailure(Call<CollectablesItems> call, Throwable t) {
                Log.d("Data--------------->", "unable to fetch");
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
                    liveDataSubCategoryDropdownList.postValue(response.body().getSubCategoryDropdownListDataList());
                }
            }

            @Override
            public void onFailure(Call<SubCategoryDropdownList> call, Throwable t) {

            }
        });
        return liveDataSubCategoryDropdownList;
    }

    public MutableLiveData<SearchCollectableItems> getLiveDataCollectableItemsBySearch(SearchData searchData) {
        liveDataCollectableItemsBySearch = new MutableLiveData<>();
        apiInterface = ApiClient.getCollectableItemsBySearch().create(ApiInterface.class);
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
}
