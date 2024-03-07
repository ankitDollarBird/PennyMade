package com.example.pennymead.page.home.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pennymead.model.home.CategoriesData;
import com.example.pennymead.remote.repositories.ListCategoriesRepo;

import java.util.List;

public class CategoriesViewModel extends ViewModel {
        MutableLiveData<List<CategoriesData>> liveData;
        ListCategoriesRepo repo;

    public CategoriesViewModel() {
        this.liveData = new MutableLiveData<>();
        repo = new ListCategoriesRepo();
    }

    public MutableLiveData<List<CategoriesData>> getLiveData() {
        Log.d("My Compiler and interpreter","is in ViewModel");
        liveData = repo.getLiveData();
            Log.d("My Compiler and interpreter", "back to ViewModel");
        return liveData;
    }
}
