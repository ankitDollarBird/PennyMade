package com.example.pennymead.page.checkout;

import android.os.Bundle;
import android.view.View;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityCheckOutForPrivacyPolicyBinding;
import com.example.pennymead.page.BaseActivity;

public class CheckOutForPrivacyPolicy extends BaseActivity {

    ActivityCheckOutForPrivacyPolicyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckOutForPrivacyPolicyBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
    }
}