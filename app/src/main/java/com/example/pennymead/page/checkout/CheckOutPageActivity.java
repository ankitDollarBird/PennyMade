package com.example.pennymead.page.checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityCheckOutPageBinding;
import com.example.pennymead.model.CartItems;
import com.example.pennymead.model.CollectableItemsForCheckout;
import com.example.pennymead.model.CollectableItemsListData;
import com.example.pennymead.model.CountryList;
import com.example.pennymead.model.OrderCartItems;
import com.example.pennymead.model.OrderPlacedDetail;
import com.example.pennymead.model.OrderPlacing;
import com.example.pennymead.model.RegisteredUserDetails;
import com.example.pennymead.page.BaseActivity;
import com.example.pennymead.page.catalogue.CustomDropDownAdapter;
import com.example.pennymead.page.home.adapter.CollectableItemsAdapter;
import com.example.pennymead.page.ordersummary.OrderSummaryActivity;
import com.example.pennymead.page.product_detail.GetSystemIdOfCollectableItems;
import com.example.pennymead.viewmodel.CategoriesViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckOutPageActivity extends BaseActivity implements GetSystemIdOfCollectableItems {

    ActivityCheckOutPageBinding binding;
    CategoriesViewModel categoriesViewModel;
    CollectableItemsAdapter collectableItemsAdapter;
    CustomDropDownAdapter countryListAdapter;
    CustomDropDownAdapter categoryMenuAdapter;
    CartItemAdapter cartItemAdapter;
    List<String> countryList;
    List<OrderCartItems> itemsToPlaceOrder;
    int isCatClickedChangeIcon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        collectableItemsAdapter = new CollectableItemsAdapter();
        cartItemAdapter = new CartItemAdapter();

        dataOfCategory(getIntent().getStringArrayListExtra("Categories Name"), getIntent().getStringArrayListExtra("Categories Number"));
        categoryMenuAdapter = new CustomDropDownAdapter(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, getIntent().getStringArrayListExtra("Categories Name"));
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setAdapter(categoryMenuAdapter);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setText("Choose Collectables", false);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdown_menu_items_background));

        binding.checkoutSubCatMenu.tvForCategoriesMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callCatalogueListActivity(getApplicationContext(), null, position, -1);
            }
        });

        binding.checkoutSubCatMenu.tvForCategoriesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForMenu();
            }
        });
        binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndIconForMenu();
            }
        });

        String[] paymentMethods = getResources().getStringArray(R.array.payment_method);
        CustomDropDownAdapter paymentMethodAdapter = new CustomDropDownAdapter(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, Arrays.asList(paymentMethods));
        binding.menuPaymentMethod.setAdapter(paymentMethodAdapter);
        binding.menuPaymentMethod.setText("", false);
        binding.menuPaymentMethod.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdown_menu_items_background));
        binding.menuPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paymentMethodAdapter.setSelectedPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getCollectableItems();

        categoriesViewModel.getCountryList().observe(this, new Observer<List<CountryList>>() {
            @Override
            public void onChanged(List<CountryList> countryLists) {
                countryList = new ArrayList<>();
                for (int i = 0; i < countryLists.size(); i++) {
                    countryList.add(countryLists.get(i).getPrintableName());
                }
                countryListAdapter = new CustomDropDownAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countryList);
                binding.menuCountry.setAdapter(countryListAdapter);
                binding.menuCountry.setText("", false);
                binding.menuCountry.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdown_menu_items_background));
            }
        });
        binding.editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getRegisteredUserDetails();
                return false;
            }
        });
        binding.layoutCheckoutPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getRegisteredUserDetails();
                return false;
            }
        });

        binding.checkBoxCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (binding.checkBoxCondition.isChecked()) {
                    binding.checkBoxCondition.setButtonDrawable(getResources().getDrawable(R.drawable.checked_true));
                } else {
                    binding.checkBoxCondition.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));
                }
            }
        });
        binding.checkBoxCondition.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));
        binding.checkBoxTermsAndConditions.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));
        binding.checkBoxTermsAndConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (binding.checkBoxTermsAndConditions.isChecked()) {
                    binding.checkBoxTermsAndConditions.setButtonDrawable(getResources().getDrawable(R.drawable.checked_true));
                } else {
                    binding.checkBoxTermsAndConditions.setButtonDrawable(getResources().getDrawable(R.drawable.checked_false));
                }
            }
        });
        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.editTextEmail.getText().toString().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
                    binding.txtErrorEmail.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.editTextName.getText().toString().isEmpty()) {
                    binding.txtErrorName.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.editTextAddress1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.editTextAddress1.getText().toString().isEmpty()) {
                    binding.txtErrorAddress.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.editTextPhone.getText().toString().length() == 10) {
                    binding.txtErrorPhone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.editTextPostal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!binding.editTextPostal.getText().toString().isEmpty()) {
                    binding.txtErrorPostal.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.menuCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.txtErrorCountry.setVisibility(View.GONE);
            }
        });
        binding.menuPaymentMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.txtErrorPayments.setVisibility(View.GONE);
            }
        });
        binding.checkBoxTermsAndConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (binding.checkBoxTermsAndConditions.isChecked()) {
                    binding.tvErrorTermsAndCondition.setVisibility(View.GONE);
                }
            }
        });
        binding.constraintUserForm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getRegisteredUserDetails();
                hideSoftKeyboard(CheckOutPageActivity.this);
                return false;
            }
        });
        binding.constraintUserForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisteredUserDetails();
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(CheckOutPageActivity.this);
                if (!binding.editTextEmail.getText().toString().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") || binding.editTextName.getText().toString().isEmpty() || binding.editTextAddress1.getText().toString().isEmpty() || binding.editTextPhone.getText().toString().isEmpty() || binding.editTextPhone.getText().toString().length() != 10 || binding.menuPaymentMethod.getText().toString().equals("") || binding.editTextPostal.getText().toString().isEmpty() || binding.menuCountry.getText().toString().equals("")) {
                    getRegisteredUserDetails();
                    if (!binding.editTextEmail.getText().toString().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
                        binding.txtErrorEmail.setText("Enter Valid email");
                        binding.txtErrorEmail.setVisibility(View.VISIBLE);
                    } else {
                        binding.txtErrorEmail.setVisibility(View.GONE);
                    }
                    if (binding.editTextName.getText().toString().isEmpty()) {
                        binding.txtErrorName.setText("Enter name");
                        binding.txtErrorName.setVisibility(View.VISIBLE);
                    } else {
                        binding.txtErrorName.setVisibility(View.GONE);
                    }
                    if (binding.editTextAddress1.getText().toString().isEmpty()) {
                        binding.txtErrorAddress.setVisibility(View.VISIBLE);
                        binding.txtErrorAddress.setText("Enter address");
                    } else {
                        binding.txtErrorAddress.setVisibility(View.GONE);
                    }
                    if (binding.editTextPhone.getText().toString().isEmpty() || binding.editTextPhone.getText().toString().length() != 10) {
                        binding.txtErrorPhone.setVisibility(View.VISIBLE);
                        binding.txtErrorPhone.setText("Enter valid phone number");
                    } else {
                        binding.txtErrorPhone.setVisibility(View.GONE);
                    }
                    if (binding.menuCountry.getText().toString().equals("")) {
                        binding.txtErrorCountry.setVisibility(View.VISIBLE);
                        binding.txtErrorCountry.setText("select country");
                    } else {
                        binding.txtErrorCountry.setVisibility(View.GONE);
                    }
                    if (binding.menuPaymentMethod.getText().toString().equals("")) {
                        binding.txtErrorPayments.setVisibility(View.VISIBLE);
                        binding.txtErrorPayments.setText("select payment method");
                    } else {
                        binding.txtErrorPayments.setVisibility(View.GONE);
                    }
                    if (binding.editTextPostal.getText().toString().isEmpty()) {
                        binding.txtErrorPostal.setVisibility(View.VISIBLE);
                        binding.txtErrorPostal.setText("Enter valid zip");
                    } else {
                        binding.txtErrorPostal.setVisibility(View.GONE);
                    }
                    if (binding.checkBoxTermsAndConditions.isChecked()) {
                        binding.tvErrorTermsAndCondition.setVisibility(View.GONE);
                    } else {
                        binding.tvErrorTermsAndCondition.setText("Check the terms and condition");
                        binding.tvErrorTermsAndCondition.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.txtErrorEmail.setVisibility(View.GONE);
                    binding.txtErrorName.setVisibility(View.GONE);
                    binding.txtErrorAddress.setVisibility(View.GONE);
                    binding.txtErrorPhone.setVisibility(View.GONE);
                    binding.txtErrorPayments.setVisibility(View.GONE);
                    binding.txtErrorCountry.setVisibility(View.GONE);
                    binding.txtErrorPostal.setVisibility(View.GONE);
                    binding.tvErrorTermsAndCondition.setVisibility(View.GONE);

                    OrderPlacing orderPlacing = new OrderPlacing(binding.editTextName.getText().toString(), binding.editTextEmail.getText().toString(), binding.editTextAddress1.getText().toString(), binding.editTextAddress2.getText().toString(), binding.editTextPhone.getText().toString(), binding.editTextState.getText().toString(), binding.editTextPostal.getText().toString(), binding.menuCountry.getText().toString(), binding.editTextTown.getText().toString(), binding.menuPaymentMethod.getText().toString(), "", 0, itemsToPlaceOrder);

                    if (itemsToPlaceOrder != null && itemsToPlaceOrder.size() != 0) {
                        categoriesViewModel.getOrderPlacedDetail(orderPlacing).observe(CheckOutPageActivity.this, new Observer<OrderPlacedDetail>() {
                            @Override
                            public void onChanged(OrderPlacedDetail orderPlacedDetail) {
                                if (orderPlacedDetail != null) {
                                    storeCartItems("0", -1);

                                    Intent intent = new Intent(CheckOutPageActivity.this, OrderSummaryActivity.class);
                                    intent.putStringArrayListExtra("Categories Name", getIntent().getStringArrayListExtra("Categories Name"));
                                    intent.putStringArrayListExtra("Categories Number", getIntent().getStringArrayListExtra("Categories Number"));
                                    intent.putExtra("Order Number", orderPlacedDetail.getOrdernumber());
                                    intent.putExtra("Email", binding.editTextEmail.getText().toString());
                                    startActivity(intent);

                                    Log.d("Register------->", orderPlacedDetail.getOrdernumber() + " order number");
                                    Toast.makeText(getApplicationContext(), "Registered Successfully " + orderPlacedDetail.getOrdernumber(), Toast.LENGTH_SHORT).show();
                                } else {
                                    onDataNotFound(getApplicationContext());
                                }
                            }
                        });

                    }

                }

            }
        });

        binding.layoutCheckoutPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                hideSoftKeyboard(CheckOutPageActivity.this);
                return false;
            }
        });

        binding.collectablesScrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                hideSoftKeyboard(CheckOutPageActivity.this);
            }
        });

        SpannableString terms = new SpannableString(getResources().getString(R.string.checkout_terms_and_condition));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                openContactUsPage();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(getResources().getColor(R.color.text_link_color));
            }
        };
        binding.buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callCatalogueListActivity(getApplicationContext(), null, 0, -1);
                    }
                });
            }
        });
        binding.includeBottomApp.tvTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactUsPage();
            }
        });

        terms.setSpan(clickableSpan, terms.toString().indexOf("terms and condition"), terms.toString().length(), 0);
        binding.checkBoxTermsAndConditions.setText(terms);
        binding.checkBoxTermsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());


        binding.rvCheckoutCollectableItems.setNestedScrollingEnabled(false);
        binding.rvCheckoutCollectableItems.setHasFixedSize(true);
        binding.rvCheckoutCollectableItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCheckoutCollectableItems.setAdapter(collectableItemsAdapter);

        binding.rvCartItems.setNestedScrollingEnabled(false);
        binding.rvCartItems.setHasFixedSize(true);
        binding.rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCartItems.setAdapter(cartItemAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setEndIconForMenu() {
        if (isCatClickedChangeIcon == 0) {
            binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
            binding.checkoutSubCatMenu.tvForCategoriesMenu.showDropDown();
            isCatClickedChangeIcon = 1;
        } else {
            binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
            isCatClickedChangeIcon = 0;
        }
    }

    public void onClickForETFields(View v) {
        getRegisteredUserDetails();
    }

    private void getRegisteredUserDetails() {
        if (isInternetConnected(getApplicationContext())) {
            if (binding.editTextEmail.getText().toString().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {

                categoriesViewModel.getRegisteredUserDetails(binding.editTextEmail.getText().toString()).observe(this, new Observer<List<RegisteredUserDetails>>() {
                    @Override
                    public void onChanged(List<RegisteredUserDetails> registeredUserDetails) {
                        showProgressingView();
                        if (registeredUserDetails != null && registeredUserDetails.size() != 0) {
                            binding.editTextName.setText(registeredUserDetails.get(0).getName());
                            binding.editTextPhone.setText(registeredUserDetails.get(0).getHphone());
                            binding.editTextAddress1.setText(registeredUserDetails.get(0).getAddress1());
                            binding.editTextAddress2.setText(registeredUserDetails.get(0).getAddress2());
                            binding.editTextPostal.setText(registeredUserDetails.get(0).getPostcode());
                            binding.editTextState.setText(registeredUserDetails.get(0).getCounty());
                            binding.menuCountry.setText(registeredUserDetails.get(0).getCountry());
                            binding.editTextTown.setText(registeredUserDetails.get(0).getTown());
                            binding.checkBoxTermsAndConditions.setChecked(true);
                            countryListAdapter = new CustomDropDownAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countryList);
                            binding.menuCountry.setAdapter(countryListAdapter);
                            hideSoftKeyboard(CheckOutPageActivity.this);
                            binding.editTextName.clearFocus();
                            hideProgressingView();
                        } else {
                            hideProgressingView();
                        }
                    }
                });
            }
        }
    }

    private void getCollectableItems() {
        if (isInternetConnected(getApplicationContext())) {
            showProgressingView();
            ArrayList<String> cartItems = fetchCartItems();
            CartItems postCartItems = new CartItems(cartItems.toArray(new String[cartItems.size()]));
            categoriesViewModel.getCollectableCartItemsForCheckout(postCartItems).observe(this, new Observer<CollectableItemsForCheckout>() {
                @Override
                public void onChanged(CollectableItemsForCheckout collectableItemsForCheckout) {
                    if (collectableItemsForCheckout != null) {
                        if (collectableItemsForCheckout.getCollectableItemsForCart() != null) {
                            cartItemAdapter.setCartItemList((ArrayList<CollectableItemsListData>) collectableItemsForCheckout.getCollectableItemsForCart(), CheckOutPageActivity.this);
                            cartItemAdapter.notifyDataSetChanged();

                            collectableItemsAdapter.setCollectableItemsList((ArrayList<CollectableItemsListData>) collectableItemsForCheckout.getCollectableItemsListDataList(), null, null, CheckOutPageActivity.this);
                            collectableItemsAdapter.notifyDataSetChanged();
                            itemsToPlaceOrder = new ArrayList<>();
                            for (int i = 0; i < collectableItemsForCheckout.getCollectableItemsForCart().size(); i++) {
                                itemsToPlaceOrder.add(new OrderCartItems(collectableItemsForCheckout.getCollectableItemsForCart().get(i).getSysid(), collectableItemsForCheckout.getCollectableItemsForCart().get(i).getPrice()));
                            }
                            binding.constraintUserForm.setVisibility(View.VISIBLE);
                            binding.tvCollectablesItems.setVisibility(View.VISIBLE);
                            binding.rvCheckoutCollectableItems.setVisibility(View.VISIBLE);
                            binding.tvCheckOut.setVisibility(View.VISIBLE);
                            binding.rvCartItems.setVisibility(View.VISIBLE);
                            binding.imageViewEmptyBasket.setVisibility(View.GONE);
                            binding.tvEmptyBasket.setVisibility(View.GONE);
                            binding.buttonViewAll.setVisibility(View.VISIBLE);
                        }

                    } else {
                        binding.rvCartItems.setVisibility(View.GONE);
                        binding.constraintUserForm.setVisibility(View.GONE);
                        binding.tvCollectablesItems.setVisibility(View.GONE);
                        binding.rvCheckoutCollectableItems.setVisibility(View.GONE);
                        binding.tvCheckOut.setVisibility(View.GONE);
                        binding.imageViewEmptyBasket.setVisibility(View.VISIBLE);
                        binding.tvEmptyBasket.setVisibility(View.VISIBLE);
                        binding.buttonViewAll.setVisibility(View.GONE);
                    }
                    hideProgressingView();
                }

            });

        }
    }

    public static void hideSoftKeyboard(CheckOutPageActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText() && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void getSysId(String sysId, int saveDelete) {
        storeCartItems(sysId, saveDelete);
        getCollectableItems();
    }
}