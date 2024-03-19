package com.example.pennymead.page;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityBaseBinding;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    ViewGroup dataNotFound;
    public ActionBarDrawerToggle drawerToggle;
    ActivityBaseBinding binding;
    DrawerLayout drawerLayout;


    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activity_container);
        container.addView(view);
        super.setContentView(drawerLayout);

        drawerLayout.findViewById(R.id.top_app_bar).findViewById(R.id.side_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNavigationDrawer();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        ImageButton button = (ImageButton) v.findViewById(R.id.btn_back_from_nav);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BackButton-------------->", "is pressed");
                drawer.closeDrawer(GravityCompat.END);
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onDataNotFound() {
        dataNotFound = (ViewGroup) getLayoutInflater().inflate(R.layout.error, null);
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.addView(dataNotFound);
    }
}