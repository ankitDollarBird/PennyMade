package com.example.pennymead.page.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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
    ConstraintLayout homeHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listCategoriesViewModel =  ViewModelProviders.of(this).get(CategoriesViewModel.class);
        rvListCategories = findViewById(R.id.rv_list_categories);
        svCategories = findViewById(R.id.scrollView);
        homeHeader = findViewById(R.id.home_page_header);
        sideMenu = homeHeader.findViewById(R.id.side_menu);

        Log.d("My Compiler and interpreter","is n View");

        listCategoriesViewModel.getLiveData().observe(HomePageActivity.this, new Observer<List<CategoriesData>>() {

            @Override
            public void onChanged(List<CategoriesData> hListCategoriesData) {
                adapterListCategories.getListCategoriesList((ArrayList<CategoriesData>) hListCategoriesData);
                adapterListCategories.notifyDataSetChanged();
                Log.d("My Compiler and interpreter","is entered to adapter");
            }
        });

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("The side menu is clikced","in home page");
            }
        });

//        rvListCategories.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                findViewById(R.id.scrollView).getParent().requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//        });
//        svCategories.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });

//        rvListCategories.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(rvListCategories,false);
        rvListCategories.setHasFixedSize(true);
        rvListCategories.setLayoutManager(new GridLayoutManager(this,2));
        adapterListCategories =new CategoriesAdapter();
        rvListCategories.setAdapter(adapterListCategories);

        toastMessage("Toast Message");

    }
}