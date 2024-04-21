package com.example.pennymead.page.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;

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
import com.example.pennymead.page.catalogue.CustomDropDownAdapter;
import com.example.pennymead.page.home.adapter.CategoriesAdapter;
import com.example.pennymead.page.home.adapter.CollectableItemsAdapter;
import com.example.pennymead.page.product_detail.GetSystemIdOfCollectableItems;
import com.example.pennymead.viewmodel.CategoriesViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends BaseActivity implements GetSystemIdOfCollectableItems {

    CategoriesViewModel listCategoriesViewModel;
    CategoriesAdapter adapterListCategories;
    CollectableItemsAdapter collectableItemsAdapter;
    int totalPages;
    String previousFilter = "newest";
    String selectedFilter = "newest";
    ActivityHomePageBinding homePageBinding;
    View view;
    int selectedPage = 1;
    int previousSelectedPage = 0;
    int firstPage;
    ArrayList<String> categories;
    ArrayList<String> categoriesNumber;
    int isFilterClicked = 0;
    int isFilterTouched = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        view = homePageBinding.getRoot();
        setContentView(view);

        listCategoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        adapterListCategories = new CategoriesAdapter();
        collectableItemsAdapter = new CollectableItemsAdapter();



        //Collectables
        if (isInternetConnected(getApplicationContext())) {
            showProgressingView();
            homePageBinding.includePagination.getRoot().setVisibility(View.VISIBLE);
            homePageBinding.collectableItemsFilters.getRoot().setVisibility(View.VISIBLE);
            listCategoriesViewModel.getCollectablesLiveData().observe(HomePageActivity.this, new Observer<List<CategoriesData>>() {
                @Override
                public void onChanged(List<CategoriesData> listCategoriesData) {
                    adapterListCategories.getListCategoriesList((ArrayList<CategoriesData>) listCategoriesData);
                    adapterListCategories.notifyDataSetChanged();
                    hideProgressingView();
                    categories = new ArrayList<>();
                    categoriesNumber = new ArrayList<>();
                    for (int j = 0; j < listCategoriesData.size(); j++) {
                        categories.add(listCategoriesData.get(j).getName());
                        categoriesNumber.add(listCategoriesData.get(j).getCategory());
                    }
                    dataOfCategory(categories, categoriesNumber);
                    homePageBinding.collectablesScrollview.scrollTo(0, homePageBinding.tvCollectables.getTop());
                }
            });
        } else {
            onDataNotFound(getApplicationContext());
        }

        //Exposed Dropdown menu

        String[] items = getResources().getStringArray(R.array.collectables_items);
        CustomDropDownAdapter adapter = new CustomDropDownAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(items));
        homePageBinding.collectableItemsFilters.atvFilters.setAdapter(adapter);
        homePageBinding.collectableItemsFilters.atvFilters.setText(items[0], false);
        adapter.setSelectedPosition(0);
        homePageBinding.collectableItemsFilters.atvFilters.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.filters_items_background));

        homePageBinding.collectableItemsFilters.atvFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilterTouched == 0) {
                    homePageBinding.collectableItemsFilters.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
                    homePageBinding.collectableItemsFilters.atvFilters.showDropDown();
                    isFilterTouched = 1;
                    Log.d("Is Touched-------->", isFilterTouched + "");
                } else {
                    homePageBinding.collectableItemsFilters.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                    isFilterTouched = 0;
                    Log.d("Is Touched-------->", isFilterTouched + "");
                }
            }
        });

        homePageBinding.collectableItemsFilters.textLayoutForFilters.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFilterClicked == 0) {
                    homePageBinding.collectableItemsFilters.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
                    homePageBinding.collectableItemsFilters.atvFilters.showDropDown();
                    isFilterClicked = 1;
                } else {
                    homePageBinding.collectableItemsFilters.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                    isFilterClicked = 0;
                }
            }
        });

        //Collectables Items
        callViewModel(selectedFilter, selectedPage);
        //filters
        homePageBinding.collectableItemsFilters.atvFilters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homePageBinding.collectableItemsFilters.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                adapter.setSelectedPosition(position);
                selectedFilter = parent.getItemAtPosition(position).toString();

                if (selectedFilter.equals("Newest Items")) {
                    selectedFilter = "newest";
                } else if (selectedFilter.equals("Author") || selectedFilter.equals("Title")) {
                    selectedFilter = selectedFilter.toLowerCase();
                } else {
                    selectedFilter = selectedFilter.toLowerCase().replaceAll("-", "_");
                }
                if (!selectedFilter.equals(previousFilter)) {
                    selectedPage = 1;
                    previousSelectedPage = 0;
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
                Log.d(("Selected Filter---------->"), selectedFilter);
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
                if (!homePageBinding.includePagination.txtEtSearchPage.getText().toString().equals("")) {
                    selectedPage = Integer.parseInt(homePageBinding.includePagination.txtEtSearchPage.getText().toString());
                    if (selectedPage <= totalPages && selectedPage >= firstPage) {
                        callViewModel(selectedFilter, selectedPage);
                        hideKeyboard();
                    } else {
                        homePageBinding.includePagination.pageNotFound.setText("Page number should be in allowed range");
                        homePageBinding.includePagination.pageNotFound.setVisibility(View.VISIBLE);
                    }
                } else
                    homePageBinding.includePagination.pageNotFound.setText("Please enter page number");
                homePageBinding.includePagination.pageNotFound.setVisibility(View.VISIBLE);
            }
        });
        //Next Page Number
        homePageBinding.includePagination.btnGreaterThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageBinding.includePagination.pageNotFound.setVisibility(View.INVISIBLE);

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
                homePageBinding.includePagination.pageNotFound.setVisibility(View.INVISIBLE);
                if (selectedPage < totalPages / 2) {
                    selectedPage = Integer.parseInt(homePageBinding.includePagination.btnFirst.getText().toString());
                    callViewModel(selectedFilter, --selectedPage);
                } else {
                    selectedPage = Integer.parseInt(homePageBinding.includePagination.btnLast.getText().toString());
                    callViewModel(selectedFilter, --selectedPage);
                }
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
                openCheckoutPageOfTermsAndCondition();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        homePageBinding.collectablesScrollview.scrollTo(0, homePageBinding.tvCollectables.getTop());
    }

    public void callViewModel(String filters, int pageNumber) {
        homePageBinding.includePagination.pageNotFound.setVisibility(View.INVISIBLE);
        if (homePageBinding.includePagination.txtEtSearchPage.getText().toString() != null) {
            homePageBinding.includePagination.txtEtSearchPage.getText().clear();
        }
        if (isInternetConnected(getApplicationContext())) {
            if (selectedPage != previousSelectedPage) {
                focusOnView();
                showProgressingView();
                listCategoriesViewModel.getCollectablesItemsLiveData(filters, pageNumber).observe(this, new Observer<CollectablesItemsData>() {
                    @Override
                    public void onChanged(CollectablesItemsData collectableItemsList) {
                        collectableItemsAdapter.setCollectableItemsList((ArrayList<CollectableItemsListData>) collectableItemsList.getCollectableItemsListData(),HomePageActivity.this::getSysId);
                        collectableItemsAdapter.notifyDataSetChanged();
                        hideProgressingView();
                        totalPages = collectableItemsList.getTotalPages();
                        firstPage = collectableItemsList.getFirstPage();
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
        }
    }

    public boolean isPagesEqual() {
        if (selectedPage != previousSelectedPage) {
            if (selectedPage <= (totalPages / 2)) {
                homePageBinding.includePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                homePageBinding.includePagination.btnLast.setTextColor(getResources().getColor(R.color.primary_color));


                homePageBinding.includePagination.btnFirst.setText(selectedPage + "");
                homePageBinding.includePagination.btnSecond.setText(++selectedPage + "");
                homePageBinding.includePagination.btnLast.setText(totalPages + "");
                homePageBinding.includePagination.btnLastSecond.setText(--totalPages + "");

                homePageBinding.includePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.button_shape));
                homePageBinding.includePagination.btnFirst.setTextColor(getResources().getColor(R.color.text_white_color));
            } else {

                homePageBinding.includePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.button_shape));
                homePageBinding.includePagination.btnLast.setTextColor(getResources().getColor(R.color.text_white_color));

                homePageBinding.includePagination.btnLast.setText(selectedPage + "");
                homePageBinding.includePagination.btnLastSecond.setText(--selectedPage + "");

                homePageBinding.includePagination.btnFirst.setText(firstPage + "");
                homePageBinding.includePagination.btnSecond.setText((++firstPage) + "");

                homePageBinding.includePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                homePageBinding.includePagination.btnFirst.setTextColor(getResources().getColor(R.color.primary_color));
            }
            previousSelectedPage = selectedPage;
            return true;
        }
        return false;
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

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(homePageBinding.includePagination.txtEtSearchPage.getWindowToken(), 0);
        }
    }

    @Override
    public void getSysId(String sysId) {
        callProductListPage(HomePageActivity.this,sysId);
    }
}