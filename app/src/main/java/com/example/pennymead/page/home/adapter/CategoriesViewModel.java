package com.example.pennymead.page.home.adapter;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pennymead.model.home.CategoriesData;
import com.example.pennymead.model.home.CollectableItemsListData;
import com.example.pennymead.model.home.CollectablesItemsData;
import com.example.pennymead.remote.repositories.ListCategoriesRepo;

import java.util.List;

public class CategoriesViewModel extends ViewModel {

        ListCategoriesRepo repo;


    public CategoriesViewModel() {
        repo = new ListCategoriesRepo();
    }

    public MutableLiveData<List<CategoriesData>> getCollectablesLiveData() {
        return  repo.getCollectablesLiveData();
    }


    public MutableLiveData<CollectablesItemsData> getCollectablesItemsLiveData(String filters, int pageNumber) {

        return  repo.getCollectablesItemsLiveData(filters,pageNumber);
    }


}
