package com.example.pennymead.page.checkout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityContactUsBinding;
import com.example.pennymead.page.BaseActivity;
import com.example.pennymead.page.catalogue.CustomDropDownAdapter;

import java.util.List;

public class ContactUsActivity extends BaseActivity {

    ActivityContactUsBinding binding;
    int setEndIcon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        List<String> items = getIntent().getStringArrayListExtra("Categories Name");
        dataOfCategory(items, getIntent().getStringArrayListExtra("Categories Number"));
        CustomDropDownAdapter adapter = new CustomDropDownAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setAdapter(adapter);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setText("Choose Collectables", false);
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdown_menu_items_background));


        binding.checkoutSubCatMenu.tvForCategoriesMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (setEndIcon == 0) {
                    binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
                    binding.checkoutSubCatMenu.tvForCategoriesMenu.showDropDown();
                    setEndIcon = 1;
                } else {
                    binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                    setEndIcon = 0;
                }
                return false;
            }
        });
        binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setEndIcon == 0) {
                    binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
                    binding.checkoutSubCatMenu.tvForCategoriesMenu.showDropDown();
                    setEndIcon = 1;
                } else {
                    binding.checkoutSubCatMenu.textInputLayoutForCategory.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                    setEndIcon = 0;
                }
            }
        });

        binding.bottomAppBar.tvTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.collectablesScrollview.scrollTo(0, binding.tvTermsAndConditions.getTop());
            }
        });
        binding.checkoutSubCatMenu.tvForCategoriesMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callCatalogueListActivity(getApplicationContext(), null, position, -1);
            }
        });
    }

}