package com.example.pennymead.page.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityHomePageBinding;
import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.model.CollectableItemsListData;
import com.example.pennymead.model.CollectablesItemsData;
import com.example.pennymead.page.BaseActivity;
import com.example.pennymead.page.checkout.CheckOutForPrivacyPolicy;
import com.example.pennymead.page.home.adapter.CategoriesViewModel;
import com.example.pennymead.page.home.adapter.CollectableItemsAdapter;
import com.example.pennymead.page.home.adapter.CustomDropDownAdapter;
import com.example.pennymead.page.home.viewmodel.CategoriesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends BaseActivity {

    CategoriesViewModel listCategoriesViewModel;
    CategoriesAdapter adapterListCategories;
    CollectableItemsAdapter collectableItemsAdapter;
    CollectablesItemsData collectablesItemsData;
    int totalPages;
    Intent intent;
    String previousFilter = "newest";
    String selectedFilter = "newest";
    ActivityHomePageBinding homePageBinding;
    View view;
    int previousPage;
    int nextPage;
    int selectedPage = 1;
    int previousSelectedPage = 1;
    int firstPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        view = homePageBinding.getRoot();
        setContentView(view);

        listCategoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        adapterListCategories = new CategoriesAdapter();
        collectableItemsAdapter = new CollectableItemsAdapter();
        collectablesItemsData = new CollectablesItemsData();


        //Collectables
        listCategoriesViewModel.getCollectablesLiveData().observe(HomePageActivity.this, new Observer<List<CategoriesData>>() {
            @Override
            public void onChanged(List<CategoriesData> listCategoriesData) {
                adapterListCategories.getListCategoriesList((ArrayList<CategoriesData>) listCategoriesData);
                adapterListCategories.notifyDataSetChanged();
                hideProgressingView();
                homePageBinding.collectablesScrollview.scrollTo(0, homePageBinding.tvCollectables.getTop());

            }
        });

        //Exposed Dropdown menu
        String[] items = getResources().getStringArray(R.array.collectables_items);
        CustomDropDownAdapter adapter = new CustomDropDownAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(items));
        homePageBinding.collectableItemsFilters.atvFilters.setAdapter(adapter);
        homePageBinding.collectableItemsFilters.atvFilters.setText(items[0], false);
        adapter.setSelectedPosition(0);
        homePageBinding.collectableItemsFilters.atvFilters.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.filters_items_background));
        showProgressingView();


        //Collectables Items
        callViewModel(selectedFilter, 1);
        //filters
        homePageBinding.collectableItemsFilters.atvFilters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);
                Log.d("previous filter value is", previousFilter);
                selectedFilter = parent.getItemAtPosition(position).toString();

                if (selectedFilter.equals("Newest Items")) {
                    selectedFilter = "newest";
                } else if (selectedFilter.equals("Author") || selectedFilter.equals("Title")) {
                    selectedFilter = selectedFilter.toLowerCase();
                } else {
                    selectedFilter = selectedFilter.toLowerCase().replaceAll("//s", "");
                }
                if (!selectedFilter.equals(previousFilter)) {
                    callViewModel(selectedFilter, 1);
                    previousFilter = selectedFilter;
                }
            }
        });


        //pagination
        homePageBinding.includePagination.btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = Integer.parseInt(homePageBinding.includePagination.btnFirst.getText().toString());
                callViewModel(selectedFilter, selectedPage);
            }
        });
        homePageBinding.includePagination.btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = Integer.parseInt(homePageBinding.includePagination.btnSecond.getText().toString());
                callViewModel(selectedFilter, selectedPage);
            }
        });
        homePageBinding.includePagination.btnLastSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = Integer.parseInt(homePageBinding.includePagination.btnLastSecond.getText().toString());
                callViewModel(selectedFilter, selectedPage);
            }
        });

        homePageBinding.includePagination.btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = Integer.parseInt(homePageBinding.includePagination.btnLast.getText().toString());
                callViewModel(selectedFilter, selectedPage);

            }
        });
        //Search
        homePageBinding.includePagination.tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPage = Integer.parseInt(homePageBinding.includePagination.txtEtSearchPage.getText().toString());
                callViewModel(selectedFilter, selectedPage);
            }
        });
        //Next Page Number
        homePageBinding.includePagination.btnGreaterThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPage > totalPages / 2) {
                    selectedPage = Integer.parseInt(homePageBinding.includePagination.btnLast.getText().toString());
                    callViewModel(selectedFilter, ++selectedPage);
                } else {
                    selectedPage = Integer.parseInt(homePageBinding.includePagination.btnFirst.getText().toString());
                    callViewModel(selectedFilter, ++selectedPage);
                }
            }
        });
        //previous page Number
        homePageBinding.includePagination.btnLessThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPage < totalPages / 2) {
                    selectedPage = Integer.parseInt(homePageBinding.includePagination.btnFirst.getText().toString());
                    callViewModel(selectedFilter, --selectedPage);
                } else {
                    selectedPage = Integer.parseInt(homePageBinding.includePagination.btnLast.getText().toString());
                    callViewModel(selectedFilter, --selectedPage);
                }
                Log.d("selected page is", selectedPage + "");
            }
        });


        //Collectables adapter calling
        homePageBinding.rvListCategories.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(homePageBinding.rvListCategories, false);
        homePageBinding.rvListCategories.setHasFixedSize(true);
        homePageBinding.rvListCategories.setLayoutManager(new GridLayoutManager(this, 2));
        homePageBinding.rvListCategories.setAdapter(adapterListCategories);

        homePageBinding.rvCollectableItems.setNestedScrollingEnabled(false);
        homePageBinding.rvCollectableItems.setHasFixedSize(true);
        homePageBinding.rvCollectableItems.setLayoutManager(new LinearLayoutManager(this));
        homePageBinding.rvCollectableItems.setAdapter(collectableItemsAdapter);

        //terms and condition
        homePageBinding.bottomAppBar.tvTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CheckOutForPrivacyPolicy.class);
                startActivity(intent);
            }
        });
    }

    public void callViewModel(String filters, int pageNumber) {
        focusOnView();
        showProgressingView();
        listCategoriesViewModel.getCollectablesItemsLiveData(filters, pageNumber).observe(this, new Observer<CollectablesItemsData>() {
            @Override
            public void onChanged(CollectablesItemsData collectableItemsListData) {
                collectablesItemsData = new CollectablesItemsData();
                collectableItemsAdapter.setCollectableItemsList((ArrayList<CollectableItemsListData>) collectableItemsListData.getCollectableItemsListData());
                collectableItemsAdapter.notifyDataSetChanged();
                hideProgressingView();
                collectablesItemsData = collectableItemsListData;
                totalPages = collectableItemsListData.getTotalPages();
                Log.d("collectableItemsListData--------->", "total page is " + totalPages + "in Base activity");
                previousPage = collectableItemsListData.getPreviousPage();
                Log.d("collectableItemsListData--------->", "previous page is " + previousPage + "in Base activity");
                nextPage = collectableItemsListData.getNextPage();

                Log.d("collectableItemsListData--------->", "next page is " + nextPage + "in Base activity");
                firstPage = collectableItemsListData.getFirstPage();
                homePageBinding.includePagination.btnLast.setText(totalPages + "");
                homePageBinding.includePagination.btnLastSecond.setText(--totalPages + "");
                if (pageNumber == firstPage) {
                    homePageBinding.includePagination.btnLessThan.setVisibility(View.INVISIBLE);
                } else if (pageNumber == totalPages) {
                    homePageBinding.includePagination.btnGreaterThan.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnLessThan.setVisibility(View.VISIBLE);
                } else {
                    homePageBinding.includePagination.btnLessThan.setVisibility(View.VISIBLE);
                    homePageBinding.includePagination.btnGreaterThan.setVisibility(View.VISIBLE);
                }

                isPagesEqual();

                if (totalPages == 1) {
                    onPageLessThanFive();
                    homePageBinding.includePagination.btnFirst.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnSecond.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnLast.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnLastSecond.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnMiddle.setText("1");
                } else if (totalPages == 2) {
                    homePageBinding.includePagination.btnLast.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnLastSecond.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnMiddle.setVisibility(View.INVISIBLE);
                    onPageLessThanFive();
                } else if (totalPages == 3) {
                    homePageBinding.includePagination.btnLast.setVisibility(View.INVISIBLE);
                    homePageBinding.includePagination.btnLastSecond.setVisibility(View.INVISIBLE);
                    onPageLessThanFive();

                } else if (totalPages == 4) {
                    homePageBinding.includePagination.btnLast.setVisibility(View.INVISIBLE);

                } else if (totalPages == 5) {
                    onPageLessThanFive();
                    homePageBinding.includePagination.btnMiddle.setText("3");
                }
            }
        });
    }

    public void isPagesEqual() {
        if (selectedPage != previousSelectedPage) {

            if (selectedPage <= (totalPages / 2)) {
                homePageBinding.includePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                homePageBinding.includePagination.btnLast.setTextColor(getResources().getColor(R.color.primary_color));


                homePageBinding.includePagination.btnFirst.setText(selectedPage + "");
                homePageBinding.includePagination.btnSecond.setText(nextPage + "");
                homePageBinding.includePagination.btnLast.setText(totalPages + "");
                homePageBinding.includePagination.btnLastSecond.setText(--totalPages + "");

                homePageBinding.includePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.button_shape));
                homePageBinding.includePagination.btnFirst.setTextColor(getResources().getColor(R.color.text_white_color));
            } else {

                homePageBinding.includePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.button_shape));
                homePageBinding.includePagination.btnLast.setTextColor(getResources().getColor(R.color.text_white_color));

                homePageBinding.includePagination.btnLast.setText(selectedPage + "");
                homePageBinding.includePagination.btnLastSecond.setText(previousPage + "");

                homePageBinding.includePagination.btnFirst.setText(firstPage + "");
                homePageBinding.includePagination.btnSecond.setText((++firstPage) + "");

                homePageBinding.includePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                homePageBinding.includePagination.btnFirst.setTextColor(getResources().getColor(R.color.primary_color));
            }
            previousSelectedPage = selectedPage;
        }
        hideKeyboard(HomePageActivity.this);
    }


    public void focusOnView() {
        homePageBinding.collectablesScrollview.post(new Runnable() {
            @Override
            public void run() {
                homePageBinding.collectablesScrollview.scrollTo(0, homePageBinding.tvCollectableItems.getTop());
            }
        });
    }

    public void onPageLessThanFive() {

        homePageBinding.includePagination.btnLessThan.setVisibility(View.INVISIBLE);
        homePageBinding.includePagination.btnGreaterThan.setVisibility(View.INVISIBLE);
        homePageBinding.includePagination.searchPageLayout.setVisibility(View.INVISIBLE);

    }

}