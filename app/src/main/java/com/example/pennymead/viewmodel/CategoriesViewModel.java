package com.example.pennymead.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.CollectablesItemsData;
import com.example.pennymead.model.ProductDetail;
import com.example.pennymead.model.SearchCollectableItems;
import com.example.pennymead.model.SearchData;
import com.example.pennymead.model.SubCategoryDropdownListData;
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

    public MutableLiveData<CollectablesItems> getCategoryCollectablesItemsLivedata(int subCategory, String selectedFilter, int selectedPage) {
        return repo.getCategoryCollectablesItemsLiveData(subCategory, selectedFilter, selectedPage);
    }


    public MutableLiveData<List<SubCategoryDropdownListData>> getSubCategoryLiveDropdownList(int category) {
        Log.d("Data---------->","ProductList 2");
        return repo.getLiveDataSubCategoryDropdownList(category);
    }

    public MutableLiveData<CollectablesItems> getSubCategoryDropdownListData(int subCategory, int reference, String selectedFilter, int selectedPage) {
        return repo.getSubCategoryDropdownListData(subCategory, reference, selectedFilter, selectedPage);
    }

    public MutableLiveData<SearchCollectableItems> getCollectableItemsBySearch(SearchData searchData) {
        return repo.getLiveDataCollectableItemsBySearch(searchData);
    }
    public MutableLiveData<ProductDetail> getCollectableRelatedItems(String sysId) {
        return repo.getLiveDataCollectableRelatedItems(sysId);
    }

}
