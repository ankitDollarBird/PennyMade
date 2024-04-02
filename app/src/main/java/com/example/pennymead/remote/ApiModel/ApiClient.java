package com.example.pennymead.remote.ApiModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    static Retrofit retrofit;
    static OkHttpClient client;

    public static void setRetrofit(String url) {

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static Retrofit getCollectables() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(logging)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS).build();

        setRetrofit("https://stagingapi.pennymead.com//view/");

        return retrofit;
    }


    public static Retrofit getCollectableItems(String filters) {
        setRetrofit("https://stagingapi.pennymead.com//view/allCategoryData/" + filters + "/");
        return retrofit;
    }

    public static Retrofit getCategoryCollectableItems(int subCat, String selectedFilter) {

        setRetrofit("https://stagingapi.pennymead.com/view/category/" + subCat + "/" + selectedFilter + "/");
        return retrofit;
    }

    public static Retrofit getSubCategoryDropdownList() {
        setRetrofit("https://stagingapi.pennymead.com/view/getsubcat_dropdownlist/");

        return retrofit;
    }

    public static Retrofit getSubCategoryDropdownListData(int category) {
        setRetrofit("https://stagingapi.pennymead.com/view/search-by-subcat/" + category + "/");
        return retrofit;
    }

    public static Retrofit getCollectableItemsBySearch() {
        setRetrofit("https://stagingapi.pennymead.com/view/");
        return retrofit;
    }
}
