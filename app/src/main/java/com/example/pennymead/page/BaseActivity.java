package com.example.pennymead.page;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityBaseBinding;
import com.example.pennymead.model.CollectableItemsListData;
import com.example.pennymead.page.checkout.CheckOutForPrivacyPolicy;
import com.example.pennymead.page.home.HomePageActivity;
import com.example.pennymead.page.product_detail.ProductDetailActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    ViewGroup progressView;
    boolean isProgressShowing = false;
    boolean isDataNotFoundShowing = false;
    ViewGroup dataNotFound;
    public ActionBarDrawerToggle drawerToggle;
    ActivityBaseBinding binding;
    DrawerLayout drawerLayout;
    int previousItemId = R.id.nav_home;
    Intent intent;
    List<String> categoriesName;
    List<String> categoriesNumber;
    Class activity;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activity_container);
        container.addView(view);
        super.setContentView(drawerLayout);
        activity = getClass();

        drawerLayout.findViewById(R.id.top_app_bar).findViewById(R.id.side_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNavigationDrawer();
            }
        });
    }

    public boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        if (connected) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }

    public void callNavigationDrawer() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.topAppBar.openSearchViewToolbar, R.string.nav_open, R.string.nav_close);

        drawer.openDrawer(GravityCompat.END);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.nav_drawer_view);
        ConstraintLayout v = (ConstraintLayout) nav.getHeaderView(0);
        ImageButton btnNavBack = (ImageButton) v.findViewById(R.id.btn_back_from_nav);
        btnNavBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.END);
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home && activity != HomePageActivity.class) {
                    drawer.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(BaseActivity.this, HomePageActivity.class));

                    previousItemId = itemId;
                } else if (itemId == R.id.nav_about_us) {
                    drawer.closeDrawer(GravityCompat.END);
                } else if (itemId == R.id.nav_track_order) {
                    drawer.closeDrawer(GravityCompat.END);
                } else if (itemId == R.id.nav_contact_us && activity != CheckOutForPrivacyPolicy.class) {
                    drawer.closeDrawer(GravityCompat.END);
                    showProgressingView();
                    openCheckoutPageOfTermsAndCondition();
                    hideProgressingView();
                }

                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgressingView() {
        if (!isProgressShowing) {
            isProgressShowing = true;
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.progress_bar, null);
            View v = this.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }


    public void onDataNotFound(Context context) {
        hideProgressingView();
        if (!isDataNotFoundShowing) {
            isDataNotFoundShowing = true;
            dataNotFound = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.error, null);
            View v = this.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(dataNotFound);
        }
        dataNotFound.findViewById(R.id.close_data_not_found).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDataNotFoundView();
            }
        });
    }

    public void hideDataNotFoundView() {
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(dataNotFound);
        isDataNotFoundShowing = false;
    }

    public void openCheckoutPageOfTermsAndCondition() {
         intent = new Intent(getApplicationContext(), CheckOutForPrivacyPolicy.class);
        intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) categoriesName);
        intent.putStringArrayListExtra("Categories Number", (ArrayList<String>) categoriesNumber);
        startActivity(intent);
    }

    public void dataOfCategory(List<String> categoryName, List<String> categoryNumber) {
        this.categoriesName = categoryName;
        this.categoriesNumber = categoryNumber;
    }
    public void callProductListPage(Context context, String sysId){
        intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("System Id",sysId);
        intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) categoriesName);
        intent.putStringArrayListExtra("Categories Number", (ArrayList<String>) categoriesNumber);
        startActivity(intent);
    }
}