package com.example.pennymead.remote.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pennymead.model.home.ListCategories;
import com.example.pennymead.model.home.CategoriesData;
import com.example.pennymead.remote.ApiModel.ApiClient;
import com.example.pennymead.remote.ApiModel.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoriesRepo {
    MutableLiveData<List<CategoriesData>> liveData;
    ApiInterface apiInterface;
    ListCategories listCategories;
    List<CategoriesData> listCategoriesDataList;

    public MutableLiveData<List<CategoriesData>> getLiveData() {
        liveData = new MutableLiveData<>();

        Log.d("My Compiler and interpreter","is repositories");

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        apiInterface.getListCategories().enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                Log.d("My Compiler and interpreter","is repositories in onResponse");
                if(response.isSuccessful()) {

                    listCategories = response.body();
                    if(listCategories!=null) {
                        Log.d("My Compiler and interpreter","is repositories it is not null");
                    }
                    else{
                        Log.d("My Compiler and interpreter","is repositories it is null");
                    }
                    listCategoriesDataList = listCategories.getDataList();
                    liveData.postValue(listCategoriesDataList);
                }
            }

            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
                Log.d("Unable to fetch the data","in repositories");
            }
        });

        return liveData;
    }
}
