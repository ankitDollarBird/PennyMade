package com.example.pennymead.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pennymead.model.AboutUs;
import com.example.pennymead.model.CartItems;
import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.model.CollectableItemsForCheckout;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.CollectablesItemsData;
import com.example.pennymead.model.CountryList;
import com.example.pennymead.model.OrderPlacedDetail;
import com.example.pennymead.model.OrderPlacing;
import com.example.pennymead.model.OrderSummaryDetails;
import com.example.pennymead.model.ProductDetail;
import com.example.pennymead.model.RegisteredUserDetails;
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

    public MutableLiveData<List<CountryList>> getCountryList() {
        return repo.getCountryList();
    }

    public MutableLiveData<CollectableItemsForCheckout> getCollectableCartItemsForCheckout(CartItems cartItems) {
        return repo.getLiveDataCollectableCartItemsForCheckout(cartItems);
    }

    public MutableLiveData<List<RegisteredUserDetails>> getRegisteredUserDetails(String email) {
        return repo.getRegisteredUserDetailsLiveData(email);
    }

    public MutableLiveData<OrderPlacedDetail> getOrderPlacedDetail(OrderPlacing orderPlacing) {
        return repo.getLiveDataOfOrderedPlacedDetail(orderPlacing);
    }

    public MutableLiveData<OrderSummaryDetails> getOrderSummary(Integer orderNumber, String email) {
        return repo.getLiveDataOfOrderSummary(orderNumber, email);
    }
    public MutableLiveData<AboutUs> getAboutUsContent() {
        return repo.getLiveDataOfAboutUs();
    }
}
