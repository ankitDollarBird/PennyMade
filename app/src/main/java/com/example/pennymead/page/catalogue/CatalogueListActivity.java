package com.example.pennymead.page.catalogue;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityCatalogueListBinding;
import com.example.pennymead.model.CollectableItemsListData;
import com.example.pennymead.model.CollectablesItems;
import com.example.pennymead.model.SearchCollectableItems;
import com.example.pennymead.model.SearchData;
import com.example.pennymead.model.SubCategoryDropdownListData;
import com.example.pennymead.page.BaseActivity;
import com.example.pennymead.page.home.adapter.CollectableItemsAdapter;
import com.example.pennymead.page.product_detail.GetSystemIdOfCollectableItems;
import com.example.pennymead.viewmodel.CategoriesViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogueListActivity extends BaseActivity implements ReferenceId, GetSystemIdOfCollectableItems {

    ActivityCatalogueListBinding catalogueListBinding;
    CategoriesViewModel categoriesViewModel;
    CollectableItemsAdapter collectableItemsAdapter;
    ArrayList<String> categoriesName;
    ArrayList<String> categoriesNumber;
    int selectedSubCategory = 1;
    int previousSelectedSubCategory = 0;
    SubCategoryAdapter subCategoryAdapter;
    String selectedFilter = "newest";
    String previousFilter = "newest";
    int totalPages;
    int previousPage;
    int selectedPage = 1;
    int previousSelectedPage = 0;
    int previousDropdownSelectedPage = 0;
    int selectedDropdownPage = 1;
    int firstPage;
    int reference;
    boolean isMenuClicked = false;
    boolean isSubCatClicked = false;
    boolean isMidClickable = false;
    int previousReference = 0;
    CustomDropDownAdapter adapterForFilters;
    CustomDropDownAdapter adapterForSubCategory;
    int searchDesc = 0;
    int categoryState = 0;
    int previousSelectedSearchedPage = 0;
    boolean isSearching = false;
    String term;
    int isFilterClicked;
    int isCatClickedChangeIcon;
    SearchData searchData;
    CollectablesItems collectablesItemsListTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        catalogueListBinding = ActivityCatalogueListBinding.inflate(getLayoutInflater());
        setContentView(catalogueListBinding.getRoot());

        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        collectableItemsAdapter = new CollectableItemsAdapter();
        categoriesNumber = getIntent().getStringArrayListExtra("Categories Number");
        subCategoryAdapter = new SubCategoryAdapter();

        selectedSubCategory = Integer.parseInt(categoriesNumber.get(getIntent().getIntExtra("Category Position", 0)));

        setSubCategoryItems();
        setFilterMenuItems();
        Gson gson = new Gson();
        String search = getIntent().getStringExtra("Search Term");
        searchData =gson.fromJson(search,SearchData.class);

        if(-1 != getIntent().getIntExtra("Reference",0)){
            reference = getIntent().getIntExtra("Reference",0);
            callViewModelForSubCategoryDropdownListData();
        } else if (searchData!=null) {
            callVMForCollectableItemsSearchData(searchData);
        } else {
            callViewModelForCollectableItems();
        }
        callViewModelForSubCategoryDropdownList(selectedSubCategory);
        scrollFromPaginationToTop();
        catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForMenu();
            }
        });
        catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForMenu();
            }
        });
        catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                selectedPage = 1;
                selectedSubCategory = Integer.parseInt(categoriesNumber.get(position));
                adapterForSubCategory.setSelectedPosition(position);
                catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setText(categoriesName.get(position), false);
                catalogueListBinding.tvCatCollectables.setText(categoriesName.get(position));
                if (selectedSubCategory != previousSelectedSubCategory) {
                    callViewModelForCollectableItems();
                    callViewModelForSubCategoryDropdownList(selectedSubCategory);
                    previousSelectedSubCategory = selectedSubCategory;
                    catalogueListBinding.cataloguePagination.getRoot().setVisibility(View.VISIBLE);
                    catalogueListBinding.rvCatalogueCollectablesItems.setVisibility(View.VISIBLE);
                    catalogueListBinding.includeCatalogueFilter.getRoot().setVisibility(View.VISIBLE);
                    catalogueListBinding.tvForDataNotFound.setVisibility(View.GONE);
                }
            }
        });

        catalogueListBinding.includeCatalogueFilter.atvFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForFilters();
            }
        });
        catalogueListBinding.includeCatalogueFilter.textLayoutForFilters.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForFilters();
            }
        });
        catalogueListBinding.includeCatalogueFilter.atvFilters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPage = 1;
                adapterForFilters.setSelectedPosition(position);
                Log.d("previous filter value is", previousFilter);
                catalogueListBinding.includeCatalogueFilter.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                selectedFilter = parent.getItemAtPosition(position).toString();

                if (selectedFilter.equals("Newest Items")) {
                    selectedFilter = "newest";
                } else if (selectedFilter.equals("Author") || selectedFilter.equals("Title")) {
                    selectedFilter = selectedFilter.toLowerCase();
                } else {
                    selectedFilter = selectedFilter.toLowerCase().replaceAll("-", "_");
                }
                if (!selectedFilter.equals(previousFilter)) {
                    if (isSubCatClicked) {
                        previousSelectedPage = 0;
                        callViewModelForCollectableItems();

                    } else if (isMenuClicked) {
                        previousDropdownSelectedPage = 0;
                        callViewModelForSubCategoryDropdownListData();
                    } else if (isSearching) {
                        previousSelectedSearchedPage = 0;
                        previousSelectedPage = 0;
                        searchCollectableItems();
                    }
                    previousFilter = selectedFilter;
                }
            }
        });


        //pagination
        catalogueListBinding.cataloguePagination.btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubCatClicked) {
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                    callViewModelForCollectableItems();
                } else if (isMenuClicked) {
                    selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                    callViewModelForSubCategoryDropdownListData();
                    btnFirstAlignment();
                } else if (isSearching) {
                    btnFirstAlignment();
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                    searchCollectableItems();
                }
            }
        });
        catalogueListBinding.cataloguePagination.btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubCatClicked) {
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnSecond.getText().toString());
                    callViewModelForCollectableItems();
                } else if (isMenuClicked) {
                    selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnSecond.getText().toString());
                    callViewModelForSubCategoryDropdownListData();
                    btnSecondAlignment();
                } else if (isSearching) {
                    btnSecondAlignment();
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnSecond.getText().toString());
                    searchCollectableItems();
                }
            }
        });
        catalogueListBinding.cataloguePagination.btnMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMidClickable) {
                    if (isMenuClicked) {
                        selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnMiddle.getText().toString());
                        callViewModelForSubCategoryDropdownListData();
                        btnThirdAlignment();
                    }
                } else if (isSearching) {
                    btnThirdAlignment();
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnMiddle.getText().toString());
                    searchCollectableItems();
                }
            }
        });
        catalogueListBinding.cataloguePagination.btnLastSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubCatClicked) {
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLastSecond.getText().toString());
                    callViewModelForCollectableItems();
                } else if (isMenuClicked) {
                    selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLastSecond.getText().toString());
                    callViewModelForSubCategoryDropdownListData();
                    btnFourAlignment();
                } else if (isSearching) {
                    btnFourAlignment();
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLastSecond.getText().toString());
                    searchCollectableItems();
                }
            }
        });

        catalogueListBinding.cataloguePagination.btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubCatClicked) {
                    selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                    callViewModelForCollectableItems();
                } else {
                    selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                    callViewModelForSubCategoryDropdownListData();
                }

            }
        });
        //Search
        catalogueListBinding.cataloguePagination.tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!catalogueListBinding.cataloguePagination.txtEtSearchPage.getText().toString().equals("")) {
                    if (isSubCatClicked) {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.txtEtSearchPage.getText().toString());
                        if (selectedPage <= totalPages && selectedPage >= firstPage) {
                            callViewModelForCollectableItems();
                        } else {
                            catalogueListBinding.cataloguePagination.pageNotFound.setText("Page number should be in allowed range");
                            catalogueListBinding.cataloguePagination.pageNotFound.setVisibility(View.VISIBLE);
                        }
                    } else if (isMenuClicked) {
                        selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.txtEtSearchPage.getText().toString());
                        if (selectedDropdownPage <= totalPages && selectedDropdownPage >= firstPage) {
                            callViewModelForSubCategoryDropdownListData();
                        } else {
                            catalogueListBinding.cataloguePagination.pageNotFound.setText("Page number should be in allowed range");
                            catalogueListBinding.cataloguePagination.pageNotFound.setVisibility(View.VISIBLE);
                        }
                    } else if (isSearching) {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.txtEtSearchPage.getText().toString());
                        if (selectedPage <= totalPages && selectedPage >= firstPage) {
                            searchCollectableItems();

                        } else {
                            catalogueListBinding.cataloguePagination.pageNotFound.setText(getResources().getString(R.string.allowed_range));
                            catalogueListBinding.cataloguePagination.pageNotFound.setVisibility(View.VISIBLE);
                        }
                    }
                    hideKeyboard(CatalogueListActivity.this);
                } else {
                    catalogueListBinding.cataloguePagination.pageNotFound.setText("Please enter the page number");
                    catalogueListBinding.cataloguePagination.pageNotFound.setVisibility(View.VISIBLE);
                }
            }
        });
        //Next Page Number
        catalogueListBinding.cataloguePagination.btnGreaterThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogueListBinding.cataloguePagination.pageNotFound.setVisibility(View.INVISIBLE);
                if (isSubCatClicked) {
                    if (selectedPage > totalPages / 2) {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                    } else {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                    }
                    ++selectedPage;
                    callViewModelForCollectableItems();
                } else if (isMenuClicked) {
                    if (selectedDropdownPage > totalPages / 2) {
                        selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                    } else {
                        selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                    }
                    ++selectedDropdownPage;
                    callViewModelForSubCategoryDropdownListData();
                } else if (isSearching) {
                    if (selectedPage > totalPages / 2) {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                    } else {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                    }
                    ++selectedPage;
                    searchCollectableItems();
                }
            }
        });
        //previous page Number
        catalogueListBinding.cataloguePagination.btnLessThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogueListBinding.cataloguePagination.pageNotFound.setVisibility(View.INVISIBLE);
                if (isSubCatClicked) {
                    if (selectedPage < totalPages / 2) {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                        --selectedPage;
                    } else {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                        selectedPage--;
                    }
                    callViewModelForCollectableItems();
                } else if (isMenuClicked) {
                    if (selectedDropdownPage < totalPages / 2) {
                        selectedDropdownPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                        --selectedDropdownPage;
                    } else {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                        selectedPage--;
                    }
                    callViewModelForSubCategoryDropdownListData();
                } else if (isSearching) {
                    if (selectedPage > totalPages / 2) {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnLast.getText().toString());
                    } else {
                        selectedPage = Integer.parseInt(catalogueListBinding.cataloguePagination.btnFirst.getText().toString());
                    }
                    --selectedPage;
                    searchCollectableItems();
                }
            }
        });
        catalogueListBinding.includeSubCatSearchCollectables.categoryState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (catalogueListBinding.includeSubCatSearchCollectables.wholeCatalogueRd.isChecked()) {
                    categoryState = 0;
                } else {
                    categoryState = selectedSubCategory;
                }
            }
        });
        catalogueListBinding.includeSubCatSearchCollectables.searchDescription.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));
        catalogueListBinding.includeSubCatSearchCollectables.searchDescription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (catalogueListBinding.includeSubCatSearchCollectables.searchDescription.isChecked()) {
                    searchDesc = 1;
                    catalogueListBinding.includeSubCatSearchCollectables.searchDescription.setButtonDrawable(getResources().getDrawable(R.drawable.checked_true));
                } else {
                    searchDesc = 0;
                    catalogueListBinding.includeSubCatSearchCollectables.searchDescription.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));

                }
            }
        });
        catalogueListBinding.includeSubCatSearchCollectables.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                term = catalogueListBinding.includeSubCatSearchCollectables.etSearch.getText().toString();
                if (!term.equals("")) {
                    selectedPage = 1;
                    previousSelectedSearchedPage = 0;
                    previousSelectedPage = 0;
                    searchCollectableItems();
                    hideKeyboard(CatalogueListActivity.this);
                }


            }
        });
        dataOfCategory(getIntent().getStringArrayListExtra("Categories Name"), getIntent().getStringArrayListExtra("Categories Number"));
        //terms and condition
        catalogueListBinding.catalogueBottomAppBar.tvTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactUsPage();
            }
        });

        catalogueListBinding.collectablesScrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                hideKeyboard(CatalogueListActivity.this);
                catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                catalogueListBinding.includeCatalogueFilter.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                isCatClickedChangeIcon = 0;
                isFilterClicked = 0;
            }
        });
        catalogueListBinding.layoutCatalogueListPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(CatalogueListActivity.this);
                catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                catalogueListBinding.includeCatalogueFilter.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                hideDataNotFoundView();
                isCatClickedChangeIcon = 0;
                isFilterClicked = 0;
                return true;
            }
        });

        catalogueListBinding.tvCatCollectables.setText(categoriesName.get(getIntent().getIntExtra("Category Position", 0)));
        catalogueListBinding.rvCatalogueCollectablesItems.setNestedScrollingEnabled(false);
        catalogueListBinding.rvCatalogueCollectablesItems.setHasFixedSize(true);
        catalogueListBinding.rvCatalogueCollectablesItems.setLayoutManager(new LinearLayoutManager(this));
        catalogueListBinding.rvCatalogueCollectablesItems.setAdapter(collectableItemsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        catalogueListBinding.includeSubCatSearchCollectables.rvExposedMenu.setHasFixedSize(true);
        catalogueListBinding.includeSubCatSearchCollectables.rvExposedMenu.setLayoutManager(linearLayoutManager);
        catalogueListBinding.includeSubCatSearchCollectables.rvExposedMenu.setAdapter(subCategoryAdapter);

    }

    public static void hideKeyboard(CatalogueListActivity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText() && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard(CatalogueListActivity.this);
    }

    private void btnFirstAlignment() {
        catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.button_shape));
        catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.text_white_color));
        catalogueListBinding.cataloguePagination.btnSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnSecond.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnMiddle.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnMiddle.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnLastSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnLastSecond.setTextColor(getResources().getColor(R.color.primary_color));
    }
    private void btnSecondAlignment() {
        catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnSecond.setBackground(getResources().getDrawable(R.drawable.button_shape));
        catalogueListBinding.cataloguePagination.btnSecond.setTextColor(getResources().getColor(R.color.text_white_color));
        catalogueListBinding.cataloguePagination.btnMiddle.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnMiddle.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnLastSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnLastSecond.setTextColor(getResources().getColor(R.color.primary_color));
    }
    private void btnThirdAlignment() {
        catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnSecond.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnMiddle.setBackground(getResources().getDrawable(R.drawable.button_shape));
        catalogueListBinding.cataloguePagination.btnMiddle.setTextColor(getResources().getColor(R.color.text_white_color));
        catalogueListBinding.cataloguePagination.btnLastSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnLastSecond.setTextColor(getResources().getColor(R.color.primary_color));
    }
    private void btnFourAlignment() {
        catalogueListBinding.cataloguePagination.btnLastSecond.setBackground(getResources().getDrawable(R.drawable.button_shape));
        catalogueListBinding.cataloguePagination.btnLastSecond.setTextColor(getResources().getColor(R.color.text_white_color));
        catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnSecond.setTextColor(getResources().getColor(R.color.primary_color));
        catalogueListBinding.cataloguePagination.btnMiddle.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
        catalogueListBinding.cataloguePagination.btnMiddle.setTextColor(getResources().getColor(R.color.primary_color));
    }

    private void searchCollectableItems() {
        searchData = new SearchData(term, searchDesc, categoryState, selectedFilter, selectedPage);
        callVMForCollectableItemsSearchData(searchData);
    }


    private void setSubCategoryItems() {
        categoriesName = getIntent().getStringArrayListExtra("Categories Name");
        adapterForSubCategory = new CustomDropDownAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoriesName);
        catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setAdapter(adapterForSubCategory);
        catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setText(categoriesName.get(getIntent().getIntExtra("Category Position", 0)), false);
        catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdown_menu_items_background));
        adapterForSubCategory.setSelectedPosition(getIntent().getIntExtra("Category Position", 0));
    }

    private void setFilterMenuItems() {
        String[] items = getResources().getStringArray(R.array.filters_of_collectable_items);
        adapterForFilters = new CustomDropDownAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(items));
        catalogueListBinding.includeCatalogueFilter.atvFilters.setAdapter(adapterForFilters);
        adapterForFilters.setSelectedPosition(0);
        catalogueListBinding.includeCatalogueFilter.atvFilters.setText(items[0], false);
        catalogueListBinding.includeCatalogueFilter.atvFilters.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdown_menu_items_background));

    }

    public void callVMForCollectableItemsSearchData(SearchData searchData) {

        if (isInternetConnected(getApplicationContext())) {
            if (selectedPage != previousSelectedSearchedPage) {
                isMenuClicked = false;
                isSearching = true;
                isSubCatClicked = false;
                showProgressingView();
                catalogueListBinding.includeSubCatSearchCollectables.etSearch.setText(searchData.getTerm());
                catalogueListBinding.tvCatCollectables.setText("Showing results for '"+searchData.getTerm()+"'");
                if(searchData.getAdesc()==0){
                    catalogueListBinding.includeSubCatSearchCollectables.searchDescription.setChecked(false);
                }
                else{
                    catalogueListBinding.includeSubCatSearchCollectables.searchDescription.setChecked(true);
                }
                if(searchData.getCategory_number()==0){
                    catalogueListBinding.includeSubCatSearchCollectables.wholeCatalogueRd.setChecked(false);
                }
                else{
                    catalogueListBinding.includeSubCatSearchCollectables.thisCategoryRd.setChecked(true);
                }
                categoriesViewModel.getCollectableItemsBySearch(searchData).observe(this, new Observer<SearchCollectableItems>() {
                    @Override
                    public void onChanged(SearchCollectableItems searchCollectableItems) {
                        if (searchCollectableItems != null) {
                            collectableItemsAdapter.setCollectableItemsList((ArrayList<CollectableItemsListData>) searchCollectableItems.getSearchList(), categoriesName, categoriesNumber, CatalogueListActivity.this);
                            collectableItemsAdapter.notifyDataSetChanged();
                            totalPages = searchCollectableItems.getTotalPages();
                            firstPage = searchCollectableItems.getFirstPage();
                            previousPage = searchCollectableItems.getPreviousPage();
                            isDropdownPagesEqual();
                            catalogueListBinding.cataloguePagination.getRoot().setVisibility(View.VISIBLE);
                            catalogueListBinding.rvCatalogueCollectablesItems.setVisibility(View.VISIBLE);
                            catalogueListBinding.includeCatalogueFilter.getRoot().setVisibility(View.VISIBLE);
                            catalogueListBinding.tvForDataNotFound.setVisibility(View.GONE);
                            hideProgressingView();
                        } else {
                            catalogueListBinding.cataloguePagination.getRoot().setVisibility(View.GONE);
                            catalogueListBinding.rvCatalogueCollectablesItems.setVisibility(View.GONE);
                            catalogueListBinding.includeCatalogueFilter.getRoot().setVisibility(View.GONE);
                            catalogueListBinding.tvForDataNotFound.setVisibility(View.VISIBLE);
                            hideProgressingView();
                        }
                        catalogueListBinding.cataloguePagination.btnFirst.setVisibility(View.VISIBLE);
                        previousSelectedSearchedPage = selectedPage;
                    }

                });

            }
        } else {
            onDataNotFound(getApplicationContext());
        }
    }

    public void callViewModelForCollectableItems() {
        if (isInternetConnected(getApplicationContext())) {
            isMenuClicked = false;
            isSubCatClicked = true;
            isSearching = false;
            catalogueListBinding.cataloguePagination.pageNotFound.setVisibility(View.INVISIBLE);
            catalogueListBinding.cataloguePagination.txtEtSearchPage.getText().clear();
            catalogueListBinding.includeSubCatSearchCollectables.etSearch.getText().clear();
            if (selectedPage != previousSelectedPage) {
                showProgressingView();
                categoriesViewModel.getCategoryCollectablesItemsLivedata(selectedSubCategory, selectedFilter, selectedPage).observe(this, new Observer<CollectablesItems>() {
                    @Override
                    public void onChanged(CollectablesItems collectablesItemsList) {
                        collectablesItemsListTemp = collectablesItemsList;
                        if (collectablesItemsList != null) {
                            if (collectablesItemsList.getCollectablesItemsData().getCollectableItemsListData() != null && collectablesItemsList.getCollectablesItemsData().getCollectableItemsListData().size() != 0 && collectablesItemsList.getStatus() != 404 && collectablesItemsList != null) {
                                collectableItemsAdapter.setCollectableItemsList((ArrayList<CollectableItemsListData>) collectablesItemsList.getCollectablesItemsData().getCollectableItemsListData(), categoriesName, categoriesNumber, CatalogueListActivity.this);
                                collectableItemsAdapter.notifyDataSetChanged();
                                catalogueListBinding.tvCategoryDescription.setText(Html.fromHtml(collectablesItemsList.getCategoryDescription().get(0).getCategoryDescription(), 0));
                                hideProgressingView();
                                totalPages = collectablesItemsList.getCollectablesItemsData().getTotalPages();
                                previousPage = collectablesItemsList.getCollectablesItemsData().getPreviousPage();
                                firstPage = collectablesItemsList.getCollectablesItemsData().getFirstPage();
                                catalogueListBinding.cataloguePagination.btnLast.setText(String.valueOf(totalPages));
                                catalogueListBinding.cataloguePagination.btnLastSecond.setText(String.valueOf(previousPage));
                                catalogueListBinding.rvCatalogueCollectablesItems.setVisibility(View.VISIBLE);
                                catalogueListBinding.includeCatalogueFilter.getRoot().setVisibility(View.VISIBLE);
                                catalogueListBinding.cataloguePagination.getRoot().setVisibility(View.VISIBLE);
                                catalogueListBinding.tvForDataNotFound.setVisibility(View.GONE);
                                scrollFromPaginationToTop();
                                isDropdownPagesEqual();
                            }
                        } else {
                            catalogueListBinding.rvCatalogueCollectablesItems.setVisibility(View.GONE);
                            catalogueListBinding.tvForDataNotFound.setVisibility(View.VISIBLE);
                            catalogueListBinding.includeCatalogueFilter.getRoot().setVisibility(View.GONE);
                            catalogueListBinding.cataloguePagination.getRoot().setVisibility(View.GONE);
                            hideProgressingView();
                            onDataNotFound(getApplicationContext());
                        }
                    }
                });
            }
        } else {
            onDataNotFound(getApplicationContext());
        }
    }


    public void callViewModelForSubCategoryDropdownList(int selectedSubCat) {
        if (isInternetConnected(getApplicationContext())) {
            categoriesViewModel.getSubCategoryLiveDropdownList(selectedSubCat).observe(this, new Observer<List<SubCategoryDropdownListData>>() {
                @Override
                public void onChanged(List<SubCategoryDropdownListData> subCategoryDropdownListData) {
                    if (subCategoryDropdownListData != null && subCategoryDropdownListData.size() != 0) {
                        subCategoryAdapter.setSubCategoriesList(subCategoryDropdownListData, getApplicationContext(), CatalogueListActivity.this);
                        subCategoryAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            onDataNotFound(getApplicationContext());
        }
    }

    public void callViewModelForSubCategoryDropdownListData() {
        if (isInternetConnected(getApplicationContext())) {
            isMenuClicked = true;
            isSubCatClicked = false;
            isSearching = false;
            catalogueListBinding.includeSubCatSearchCollectables.etSearch.getText().clear();
            if (selectedDropdownPage != previousDropdownSelectedPage) {
                showProgressingView();
                categoriesViewModel.getSubCategoryDropdownListData(selectedSubCategory, reference, selectedFilter, selectedDropdownPage).observe(this, new Observer<CollectablesItems>() {
                    @Override
                    public void onChanged(CollectablesItems collectablesItems) {
                        if (collectablesItems != null) {
                            collectableItemsAdapter.setCollectableItemsList((ArrayList<CollectableItemsListData>) collectablesItems.getCollectablesItemsData().getCollectableItemsListData(), categoriesName, categoriesNumber, CatalogueListActivity.this);
                            collectableItemsAdapter.notifyDataSetChanged();
                            totalPages = collectablesItems.getCollectablesItemsData().getTotalPages();
                            previousPage = collectablesItems.getCollectablesItemsData().getPreviousPage();
                            firstPage = collectablesItems.getCollectablesItemsData().getFirstPage();
                            isDropdownPagesEqual();
                            hideProgressingView();
                        }
                    }
                });
                previousDropdownSelectedPage = selectedDropdownPage;
            }
            catalogueListBinding.cataloguePagination.btnFirst.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.button_shape));
            catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.text_white_color));
        } else {
            onDataNotFound(getApplicationContext());
        }
    }

    public void isDropdownPagesEqual() {
        if (totalPages < 5) {
            isMidClickable = true;
            checkPage();
        } else {
            isMidClickable = false;
            isPagesEqual();
        }
    }

    public void isPagesEqual() {
        if (selectedDropdownPage != 1) {
            catalogueListBinding.cataloguePagination.btnSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
            catalogueListBinding.cataloguePagination.btnSecond.setTextColor(getResources().getColor(R.color.primary_color));
            catalogueListBinding.cataloguePagination.btnMiddle.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
            catalogueListBinding.cataloguePagination.btnMiddle.setTextColor(getResources().getColor(R.color.primary_color));
            catalogueListBinding.cataloguePagination.btnLastSecond.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
            catalogueListBinding.cataloguePagination.btnLastSecond.setTextColor(getResources().getColor(R.color.primary_color));
        }
        if (selectedPage == 1) {
            catalogueListBinding.cataloguePagination.btnLessThan.setVisibility(View.INVISIBLE);
            catalogueListBinding.cataloguePagination.btnGreaterThan.setVisibility(View.VISIBLE);
        } else if (selectedPage == totalPages) {
            catalogueListBinding.cataloguePagination.btnLessThan.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnGreaterThan.setVisibility(View.INVISIBLE);
        } else {
            catalogueListBinding.cataloguePagination.btnLessThan.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnGreaterThan.setVisibility(View.VISIBLE);
        }

        catalogueListBinding.cataloguePagination.btnMiddle.setVisibility(View.VISIBLE);
        catalogueListBinding.cataloguePagination.btnMiddle.setText("...");
        catalogueListBinding.cataloguePagination.btnSecond.setVisibility(View.VISIBLE);
        catalogueListBinding.cataloguePagination.btnLastSecond.setVisibility(View.VISIBLE);
        catalogueListBinding.cataloguePagination.btnLast.setVisibility(View.VISIBLE);
        catalogueListBinding.cataloguePagination.searchPageLayout.setVisibility(View.VISIBLE);
        if (isSubCatClicked) {
            if (selectedPage <= (totalPages / 2)) {
                catalogueListBinding.cataloguePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                catalogueListBinding.cataloguePagination.btnLast.setTextColor(getResources().getColor(R.color.primary_color));
                catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(selectedPage));
                catalogueListBinding.cataloguePagination.btnSecond.setText(String.valueOf(++selectedPage));
                catalogueListBinding.cataloguePagination.btnLast.setText(String.valueOf(totalPages));
                catalogueListBinding.cataloguePagination.btnLastSecond.setText(String.valueOf(--totalPages));
                catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.button_shape));
                catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.text_white_color));

            } else {

                catalogueListBinding.cataloguePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.button_shape));
                catalogueListBinding.cataloguePagination.btnLast.setTextColor(getResources().getColor(R.color.text_white_color));

                catalogueListBinding.cataloguePagination.btnLast.setText(String.valueOf(selectedPage));
                catalogueListBinding.cataloguePagination.btnLastSecond.setText(String.valueOf(--selectedPage));

                catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(firstPage));
                catalogueListBinding.cataloguePagination.btnSecond.setText(String.valueOf(++firstPage));

                catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.primary_color));
            }
        } else {
            if (selectedDropdownPage <= (totalPages / 2)) {
                catalogueListBinding.cataloguePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                catalogueListBinding.cataloguePagination.btnLast.setTextColor(getResources().getColor(R.color.primary_color));
                catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(selectedPage));
                catalogueListBinding.cataloguePagination.btnSecond.setText(String.valueOf(++selectedPage));
                catalogueListBinding.cataloguePagination.btnLast.setText(String.valueOf(totalPages));
                catalogueListBinding.cataloguePagination.btnLastSecond.setText(String.valueOf(--totalPages));
                catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.button_shape));
                catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.text_white_color));

            } else {

                catalogueListBinding.cataloguePagination.btnLast.setBackground(getResources().getDrawable(R.drawable.button_shape));
                catalogueListBinding.cataloguePagination.btnLast.setTextColor(getResources().getColor(R.color.text_white_color));

                catalogueListBinding.cataloguePagination.btnLast.setText(String.valueOf(selectedPage));
                catalogueListBinding.cataloguePagination.btnLastSecond.setText(String.valueOf(--selectedPage));

                catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(firstPage));
                catalogueListBinding.cataloguePagination.btnSecond.setText(String.valueOf(++firstPage));

                catalogueListBinding.cataloguePagination.btnFirst.setBackground(getResources().getDrawable(R.drawable.null_background_brown_border));
                catalogueListBinding.cataloguePagination.btnFirst.setTextColor(getResources().getColor(R.color.primary_color));
            }
            previousSelectedPage = selectedPage;
            previousDropdownSelectedPage = selectedPage;
        }
    }


    public void checkPage() {
        catalogueListBinding.cataloguePagination.btnLast.setVisibility(View.GONE);
        catalogueListBinding.cataloguePagination.btnLessThan.setVisibility(View.INVISIBLE);
        catalogueListBinding.cataloguePagination.btnGreaterThan.setVisibility(View.INVISIBLE);
        catalogueListBinding.cataloguePagination.searchPageLayout.setVisibility(View.GONE);
        if (totalPages == 1) {
            catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(firstPage));
            catalogueListBinding.cataloguePagination.btnMiddle.setVisibility(View.GONE);
            catalogueListBinding.cataloguePagination.btnSecond.setVisibility(View.GONE);
            catalogueListBinding.cataloguePagination.btnLastSecond.setVisibility(View.GONE);
            catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(firstPage));

        } else if (totalPages == 2) {
            catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(firstPage));
            catalogueListBinding.cataloguePagination.btnSecond.setText(String.valueOf(totalPages));
            catalogueListBinding.cataloguePagination.btnSecond.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnMiddle.setVisibility(View.GONE);
            catalogueListBinding.cataloguePagination.btnLastSecond.setVisibility(View.GONE);

        } else if (totalPages == 3) {
            catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(firstPage));
            catalogueListBinding.cataloguePagination.btnSecond.setText(String.valueOf(++firstPage));
            catalogueListBinding.cataloguePagination.btnMiddle.setText(String.valueOf(totalPages));
            catalogueListBinding.cataloguePagination.btnMiddle.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnSecond.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnLastSecond.setVisibility(View.GONE);


        } else if (totalPages == 4) {
            catalogueListBinding.cataloguePagination.btnFirst.setText(String.valueOf(firstPage));
            catalogueListBinding.cataloguePagination.btnSecond.setText(String.valueOf(++firstPage));
            catalogueListBinding.cataloguePagination.btnLastSecond.setText(String.valueOf(totalPages));
            catalogueListBinding.cataloguePagination.btnMiddle.setText(String.valueOf(--totalPages));
            catalogueListBinding.cataloguePagination.btnMiddle.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnSecond.setVisibility(View.VISIBLE);
            catalogueListBinding.cataloguePagination.btnLastSecond.setVisibility(View.VISIBLE);
        }
        scrollFromPaginationToTop();
    }

    @Override
    public void getReference(int reference) {
        this.reference = reference;
        if (previousReference != reference) {
            previousDropdownSelectedPage = 0;
            callViewModelForSubCategoryDropdownListData();
            previousReference = reference;
        }
    }
    public void scrollFromPaginationToTop() {
        catalogueListBinding.collectablesScrollview.post(new Runnable() {
            @Override
            public void run() {
                catalogueListBinding.collectablesScrollview.scrollTo(0, catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.getTop());
            }
        });
    }

    public void setEndIconForFilters() {
        if (isFilterClicked == 0) {
            catalogueListBinding.includeCatalogueFilter.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
            catalogueListBinding.includeCatalogueFilter.atvFilters.showDropDown();
            isFilterClicked = 1;
        } else {
            catalogueListBinding.includeCatalogueFilter.textLayoutForFilters.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
            isFilterClicked = 0;
        }
    }

    public void setEndIconForMenu() {
        if (isCatClickedChangeIcon == 0) {
            catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
            catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.showDropDown();
            isCatClickedChangeIcon = 1;
        } else {
            catalogueListBinding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
            isCatClickedChangeIcon = 0;
        }
    }


    @Override
    public void getSysId(String sysId, int saveDelete) {
        storeCartItems(sysId, saveDelete);
    }
}