package com.example.pennymead.page.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityCheckOutForPrivacyPolicyBinding;
import com.example.pennymead.page.BaseActivity;
import com.example.pennymead.page.catalogue.CatalogueListActivity;
import com.example.pennymead.page.catalogue.CustomDropDownAdapter;

import java.util.ArrayList;
import java.util.List;

public class CheckOutForPrivacyPolicy extends BaseActivity {

    ActivityCheckOutForPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckOutForPrivacyPolicyBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        List<String> items = getIntent().getStringArrayListExtra("Categories Name");
        CustomDropDownAdapter adapter = new CustomDropDownAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setAdapter(adapter);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setText("Choose Collectables", false);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.filters_items_background));

        binding.checkoutSubCatMenu.tvForCategoriesMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
                binding.checkoutSubCatMenu.tvForCategoriesMenu.showDropDown();
                return false;
            }
        });
        binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
                binding.checkoutSubCatMenu.tvForCategoriesMenu.showDropDown();
            }
        });
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CheckOutForPrivacyPolicy.this, CatalogueListActivity.class);
                intent.putExtra("Category Position", position);
                intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) items);
                intent.putStringArrayListExtra("Categories Number", getIntent().getStringArrayListExtra("Categories Number"));
                startActivity(intent);
                onBackPressed();
            }
        });
    }

}