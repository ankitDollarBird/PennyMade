package com.example.pennymead.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pennymead.R;
import com.example.pennymead.page.catalogue.CustomDropDownAdapter;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CustomDropdownMenu extends LinearLayout {

    CustomDropDownAdapter customDropDownAdapter;
    MaterialAutoCompleteTextView setDropdownListItems;
    TextInputLayout inputLayout;
    int isClicked = 0;
    int isEndIconCLicked = 0;

    public CustomDropdownMenu(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomDropdownMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDropdownMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflater();
    }

    public void inflater() {
        inflate(getContext(), R.layout.custom_dropdown_item, this);
        setDropdownListItems = findViewById(R.id.custom_dropdown);
        inputLayout = findViewById(R.id.textInputLayoutForDropdown);
    }

    public void setText(String name) {

        setDropdownListItems.setText(name);
    }

    public void setDropDownBackgroundDrawable(Drawable drawable) {
        setDropdownListItems.setDropDownBackgroundDrawable(drawable);
    }

    public void getDropdownList(ArrayList<String> subCategoriesDropdownList, GetPosition getPosition, int recyclerViewPosition) {
        customDropDownAdapter = new CustomDropDownAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, subCategoriesDropdownList);
        setDropdownListItems.setAdapter(customDropDownAdapter);
        setDropdownListItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                customDropDownAdapter.setSelectedPosition(position);
                getPosition.getPosition(position, recyclerViewPosition);
            }
        });

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        setDropdownListItems.setOnClickListener(onClickListener);
        inputLayout.setEndIconOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEndIconCLicked == 0) {
                    inputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
                    setDropdownListItems.showDropDown();
                    isEndIconCLicked = 1;
                } else {
                    inputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
                    isEndIconCLicked = 0;
                }
            }
        });
    }

    public void onClick(View v) {
        if (isClicked == 0) {
            inputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_up_arrow));
            isClicked = 1;
        } else {
            inputLayout.setEndIconDrawable(getResources().getDrawable(R.drawable.icon_spinner_down_arrow));
            isClicked = 0;
        }
    }

}
