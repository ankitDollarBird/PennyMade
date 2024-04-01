package com.example.pennymead.page.product_detail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityProductDetailBinding;
import com.example.pennymead.model.CollectableItemsListData;
import com.example.pennymead.model.ProductDetail;
import com.example.pennymead.model.SearchData;
import com.example.pennymead.model.SubCategoryDropdownListData;
import com.example.pennymead.page.BaseActivity;
import com.example.pennymead.page.catalogue.CatalogueListActivity;
import com.example.pennymead.page.catalogue.CustomDropDownAdapter;
import com.example.pennymead.page.catalogue.ReferenceId;
import com.example.pennymead.page.catalogue.SubCategoryAdapter;
import com.example.pennymead.page.home.adapter.CollectableItemsAdapter;
import com.example.pennymead.viewmodel.CategoriesViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity implements GetSystemIdOfCollectableItems, ReferenceId {
    ActivityProductDetailBinding binding;
    int isCatClickedChangeIcon;
    int subCategoryPosition;
    int searchDesc = 0;
    ArrayList<String> categoriesNumber;
    ArrayList<String> categoriesName;
    String term;
    CollectableItemsListData collectableItemsListData;
    String setCategory;
    int n = 0;
    CategoriesViewModel viewModel;
    CollectableItemsAdapter collectableItemsAdapter;
    SubCategoryAdapter subCategoryAdapter;
    int reference =-1;
    int categoryState=0;
    SearchData searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        categoriesNumber = getIntent().getStringArrayListExtra("Categories Number");
        categoriesName = getIntent().getStringArrayListExtra("Categories Name");
        collectableItemsAdapter = new CollectableItemsAdapter();
        subCategoryAdapter = new SubCategoryAdapter();

        getLiveProductDetail();
        dataOfCategory(categoriesName, categoriesNumber);

        binding.btnGreaterThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnLessThan.setVisibility(View.VISIBLE);
                setImages(v.getId());
            }
        });
        binding.btnLessThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImages(v.getId());
            }
        });
        binding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForMenu();
            }
        });
        binding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForMenu();
            }
        });
        binding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                callCatalogueList(position);

            }
        });
        binding.buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCatalogueList(subCategoryPosition);
            }
        });

        binding.includeSubCatSearchCollectables.searchDescription.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));
        binding.includeSubCatSearchCollectables.searchDescription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (binding.includeSubCatSearchCollectables.searchDescription.isChecked()) {
                    searchDesc = 1;
                    binding.includeSubCatSearchCollectables.searchDescription.setButtonDrawable(getResources().getDrawable(R.drawable.checked_true));
                } else {
                    searchDesc = 0;
                    binding.includeSubCatSearchCollectables.searchDescription.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));

                }
            }
        });
      binding.includeSubCatSearchCollectables.categoryState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (binding.includeSubCatSearchCollectables.wholeCatalogueRd.isChecked()) {
                    categoryState = 0;
                } else {
                    categoryState = Integer.parseInt(collectableItemsListData.getCategory());
                }
            }
        });
        binding.includeSubCatSearchCollectables.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                term = binding.includeSubCatSearchCollectables.etSearch.getText().toString();
                if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.includeSubCatSearchCollectables.etSearch.getWindowToken(), 0);
                }
                 searchData = new SearchData(term,searchDesc,categoryState,"new",1);
                callCatalogueList(subCategoryPosition);
                binding.includeSubCatSearchCollectables.etSearch.getText().clear();
            }
        });
        binding.includeBottomApp.tvTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCheckoutPageOfTermsAndCondition();
            }
        });


        binding.rvProductCollectableItems.setHasFixedSize(true);
        binding.rvProductCollectableItems.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.includeSubCatSearchCollectables.rvExposedMenu.setHasFixedSize(true);
        binding.includeSubCatSearchCollectables.rvExposedMenu.setLayoutManager(linearLayoutManager);
        binding.includeSubCatSearchCollectables.rvExposedMenu.setAdapter(subCategoryAdapter);
    }

    private void callCatalogueList(int s) {
        Intent intent = new Intent(this, CatalogueListActivity.class);
        intent.putExtra("Search Term",new Gson().toJson(searchData));
        intent.putExtra("Reference",reference);
        intent.putExtra("Category Position", s);
        intent.putStringArrayListExtra("Categories Number", categoriesNumber);
        intent.putStringArrayListExtra("Categories Name", categoriesName);
        startActivity(intent);
        finish();
    }

    private void getLiveProductDetail() {
        showProgressingView();
        viewModel.getCollectableRelatedItems(getIntent().getStringExtra("System Id")).observe(this, new Observer<ProductDetail>() {
            @Override
            public void onChanged(ProductDetail productDetail) {
                collectableItemsListData = productDetail.getProductDetail();
                setCollectablesMenu();
                if (productDetail.getCollectableItemsListData().size() != 0) {
                    binding.rvProductCollectableItems.setAdapter(collectableItemsAdapter);
                    collectableItemsAdapter.setCollectableItemsList((ArrayList<CollectableItemsListData>) productDetail.getCollectableItemsListData(), ProductDetailActivity.this::getSysId);
                    collectableItemsAdapter.notifyDataSetChanged();
                }
                callVmForSubCategoryDropdownList(Integer.parseInt(productDetail.getProductDetail().getCategory()));
                hideProgressingView();
            }
        });
    }

    private void setImages(int id) {

        if (id == R.id.btn_greater_than) {
            n++;
            if (n < collectableItemsListData.getImage().size() - 1) {
                Picasso.get().load(collectableItemsListData.getImage().get(n)).resize(200, 250).into(binding.imageProduct);
            } else {
                Picasso.get().load(collectableItemsListData.getImage().get(n)).resize(200, 250).into(binding.imageProduct);
                binding.btnGreaterThan.setVisibility(View.INVISIBLE);
                binding.btnLessThan.setVisibility(View.VISIBLE);
            }
        } else {
            n--;
            if (n > 0) {
                Picasso.get().load(collectableItemsListData.getImage().get(n)).resize(200, 250).into(binding.imageProduct);
                binding.btnGreaterThan.setVisibility(View.VISIBLE);
            } else {
                Picasso.get().load(collectableItemsListData.getImage().get(0)).resize(200, 250).into(binding.imageProduct);
                binding.btnLessThan.setVisibility(View.INVISIBLE);
                binding.btnGreaterThan.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setCollectablesMenu() {

        CustomDropDownAdapter adapterForSubCategory = new CustomDropDownAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoriesName);
        binding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setAdapter(adapterForSubCategory);
        for (int i = 0; i < categoriesNumber.size(); i++) {
            if (collectableItemsListData.getCategory().equals(categoriesNumber.get(i))) {
                subCategoryPosition = i;
                setCategory = categoriesName.get(i);
                adapterForSubCategory.setSelectedPosition(i);
            }
        }
        binding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setText(setCategory, false);
        binding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.filters_items_background));
        binding.tvCollectablesItemsAuthor.setText(collectableItemsListData.getAuthor());
        binding.tvCollectablesItemsDescription.setText(collectableItemsListData.getDescription());
        binding.tvCollectablesItemsTitle.setText(collectableItemsListData.getTitle());
        binding.tvCollectablesItemsPrice.setText("$" + collectableItemsListData.getPrice());
        Picasso.get().load(collectableItemsListData.getImage().get(0)).resize(200, 250).into(binding.imageProduct);
        if (collectableItemsListData.getImage().size() > 1) {
            binding.btnGreaterThan.setVisibility(View.VISIBLE);
        }
    }

    public void callVmForSubCategoryDropdownList(int selectedSubCat) {
        if (isInternetConnected(getApplicationContext())) {
            viewModel.getSubCategoryLiveDropdownList(selectedSubCat).observe(this, new Observer<List<SubCategoryDropdownListData>>() {
                @Override
                public void onChanged(List<SubCategoryDropdownListData> subCategoryDropdownListData) {
                    subCategoryAdapter.setSubCategoriesList(subCategoryDropdownListData, getApplicationContext(), ProductDetailActivity.this);
                    subCategoryAdapter.notifyDataSetChanged();
                }
            });
        } else {
            onDataNotFound(getApplicationContext());
        }
    }

    public void setEndIconForMenu() {
        if (isCatClickedChangeIcon == 0) {
            binding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
            binding.includeSubCatSearchCollectables.dmSubCategories.tvForCategoriesMenu.showDropDown();
            isCatClickedChangeIcon = 1;
        } else {
            binding.includeSubCatSearchCollectables.dmSubCategories.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
            isCatClickedChangeIcon = 0;
        }
    }


    @Override
    public void getSysId(String sysId) {
        callProductListPage(getApplicationContext(), sysId);
    }

    @Override
    public void getReference(int reference) {
            this.reference = reference;
            callCatalogueList(subCategoryPosition);
    }
}