package com.example.pennymead.page.aboutus;


import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityAboutUsBinding;
import com.example.pennymead.model.AboutUs;
import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.page.BaseActivity;
import com.example.pennymead.page.home.adapter.CategoriesAdapter;
import com.example.pennymead.viewmodel.CategoriesViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AboutUsActivity extends BaseActivity {

    ActivityAboutUsBinding binding;
    CategoriesAdapter adapterListCategories;
    CategoriesViewModel listCategoriesViewModel;
    ArrayList<String> categoriesName;
    ArrayList<String> categoriesNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        listCategoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        adapterListCategories = new CategoriesAdapter();

        if (isInternetConnected(getApplicationContext())) {
            showProgressingView();
            listCategoriesViewModel.getCollectablesLiveData().observe(this, new Observer<List<CategoriesData>>() {
                @Override
                public void onChanged(List<CategoriesData> listCategoriesData) {
                    if (listCategoriesData != null) {
                        adapterListCategories.getListCategoriesList((ArrayList<CategoriesData>) listCategoriesData);
                        adapterListCategories.notifyDataSetChanged();
                        hideProgressingView();
//                        categoriesName = new ArrayList<>();
//                        categoriesNumber = new ArrayList<>();
//                        for (int j = 0; j < listCategoriesData.size(); j++) {
//                            categoriesName.add(listCategoriesData.get(j).getName());
//                            categoriesNumber.add(listCategoriesData.get(j).getCategory());
//                        }
//                        dataOfCategory(categoriesName, categoriesNumber);
                    } else {
                        onDataNotFound(getApplicationContext());
                    }
                }
            });
        } else {
            onDataNotFound(getApplicationContext());
        }

        if (isInternetConnected(getApplicationContext())) {
            showProgressingView();
            listCategoriesViewModel.getAboutUsContent().observe(this, new Observer<AboutUs>() {
                @Override
                public void onChanged(AboutUs aboutUs) {
                    binding.headerTextAboutUs.setText(Html.fromHtml(aboutUs.getData().get(0).getHeader()));
                    binding.aboutPennymead.setText(Html.fromHtml(aboutUs.getData().get(0).getAboutPennymead()));
                    binding.textAdminInfo.setText(Html.fromHtml(aboutUs.getData().get(0).getAdminInfo()));
                    binding.textAboutAdmin.setText(Html.fromHtml(aboutUs.getData().get(0).getAboutAdmin()));
                    if(aboutUs.getData().get(0).getAboutImage()!=null){
                        Picasso.get().load(aboutUs.getData().get(0).getAboutImage()).error(getResources().getDrawable(R.drawable.not_found_img)).into(binding.ivAboutImage);
                    }

                    if(aboutUs.getData().get(0).getAdminImage()!=null){
                        Picasso.get().load(aboutUs.getData().get(0).getAdminImage()).error(getResources().getDrawable(R.drawable.not_found_img)).into(binding.ivAdminImage);
                    }
                }
            });
        }
        binding.bottomAppBar.tvTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactUsPage();
            }
        });
        binding.bottomAppBar.iconFollowUsOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUsOnPage();
            }
        });
        binding.rvListCategories.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(binding.rvListCategories, false);
        binding.rvListCategories.setHasFixedSize(true);
        binding.rvListCategories.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvListCategories.setAdapter(adapterListCategories);

    }
}