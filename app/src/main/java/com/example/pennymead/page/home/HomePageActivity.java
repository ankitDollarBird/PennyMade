package com.example.pennymead.page.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennymead.R;
import com.example.pennymead.page.BaseActivity;

import com.example.pennymead.model.home.CategoriesData;
import com.example.pennymead.page.home.adapter.CategoriesAdapter;
import com.example.pennymead.page.home.viewmodel.CategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity {

    CategoriesViewModel listCategoriesViewModel;
    RecyclerView rvListCategories;
   CategoriesAdapter adapterListCategories;
   NestedScrollView svCategories;
//    ScrollView svCategories;
    ImageButton sideMenu;
    ConstraintLayout topBar,filters;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listCategoriesViewModel =  ViewModelProviders.of(this).get(CategoriesViewModel.class);
        rvListCategories = findViewById(R.id.rv_list_categories);
        svCategories = findViewById(R.id.collectables_scrollview);
       topBar  = findViewById(R.id.home_page_header);
       filters = findViewById(R.id.collectable_items_filters);
       spinner = filters.findViewById(R.id.collectable_items_spinner);
        sideMenu = topBar.findViewById(R.id.side_menu);

        Log.d("My Compiler and interpreter","is n View");

        listCategoriesViewModel.getLiveData().observe(HomePageActivity.this, new Observer<List<CategoriesData>>() {

            @Override
            public void onChanged(List<CategoriesData> hListCategoriesData) {
                adapterListCategories.getListCategoriesList((ArrayList<CategoriesData>) hListCategoriesData);
                adapterListCategories.notifyDataSetChanged();
                Log.d("My Compiler and interpreter","is entered to adapter");
            }
        });

        ArrayAdapter<CharSequence> spinnerArray = ArrayAdapter.createFromResource(HomePageActivity.this, R.array.filters, android.R.layout.simple_spinner_item);
        spinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArray);

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("The side menu is clicked","in home page");

            }
        });


        rvListCategories.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(rvListCategories,false);
        rvListCategories.setHasFixedSize(true);
        rvListCategories.setLayoutManager(new GridLayoutManager(this,2));
        rvListCategories.setFocusable(false);
        adapterListCategories =new CategoriesAdapter();
        rvListCategories.setAdapter(adapterListCategories);

        toastMessage("Toast Message");

    }
}