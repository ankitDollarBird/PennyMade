package com.example.pennymead.remote.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.CollectablesItemsData;
import com.example.pennymead.model.ListCategories;
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
    ApiInterface apiInterface;
    ListCategories listCategories;
    List<CategoriesData> listCategoriesDataList;
    BaseActivity baseActivity;

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
                baseActivity = new BaseActivity();
                baseActivity.onDataNotFound();
            }
        });
        return liveDataCollectables;
    }

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
                baseActivity = new BaseActivity();
                baseActivity.onDataNotFound();
                Log.d("Unable to fetch the data", "in collectables items repositories");
            }
        });

        return liveDataCollectableItems;
    }

    public MutableLiveData<CollectablesItems> getCategoryCollectablesItemsLiveData() {
        liveDataCategoryCollectableItems = new MutableLiveData<>();
        apiInterface = ApiClient.getCategoryCollectableItems().create(ApiInterface.class);
        apiInterface.getCategoryCollectablesItems().enqueue(new Callback<CollectablesItems>() {
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

            }
        });
        return liveDataCategoryCollectableItems;
    }
}
