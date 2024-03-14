package com.example.pennymead.remote.ApiModel;

import android.database.Observable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    static Retrofit retrofit;

    public static Retrofit getCollectables() {
        if(retrofit==null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

        }
        retrofit = new Retrofit.Builder()
                .baseUrl("https://stagingapi.pennymead.com//view/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


    public static Retrofit getCollectableItems(String filters) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://stagingapi.pennymead.com//view/allCategoryData/"+filters+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


}
