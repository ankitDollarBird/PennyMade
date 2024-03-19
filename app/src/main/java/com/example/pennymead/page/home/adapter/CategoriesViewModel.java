package com.example.pennymead.page.home.adapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.CollectablesItemsData;
import com.example.pennymead.remote.repositories.Repository;

import java.util.List;

public class CategoriesViewModel extends ViewModel {

    Repository repo;

    public CategoriesViewModel() {
        repo = new Repository();
    }

    public MutableLiveData<List<CategoriesData>> getCollectablesLiveData() {
        return repo.getCollectablesLiveData();
    }

    public MutableLiveData<CollectablesItemsData> getCollectablesItemsLiveData(String filters, int pageNumber) {

        return repo.getCollectablesItemsLiveData(filters, pageNumber);
    }

    public MutableLiveData<CollectablesItems> getCategoryCollectablesItemsLivedata() {
        return repo.getCategoryCollectablesItemsLiveData();
    }

}
