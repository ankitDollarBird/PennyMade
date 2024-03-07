package com.example.pennymead.remote.ApiModel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://stagingapi.pennymead.com//view/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
