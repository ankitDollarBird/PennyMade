package com.example.pennymead.page;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.pennymead.R;
import com.example.pennymead.databinding.ActivityBaseBinding;
import com.example.pennymead.page.aboutus.AboutUsActivity;
import com.example.pennymead.page.catalogue.CatalogueListActivity;
import com.example.pennymead.page.checkout.CheckOutPageActivity;
import com.example.pennymead.page.checkout.ContactUsActivity;
import com.example.pennymead.page.home.HomePageActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
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
    Context context;
    SharedPreferences storeSysId;
    ArrayList<String> sysIdList;
    TextView counter;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activity_container);
        container.addView(view);
        super.setContentView(drawerLayout);
        activity = getClass();

        counter = drawerLayout.findViewById(R.id.top_app_bar).findViewById(R.id.badge_notification);

        drawerLayout.findViewById(R.id.top_app_bar).findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != CheckOutPageActivity.class) {
                    intent = new Intent(view.getContext(), CheckOutPageActivity.class);
                    intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) categoriesName);
                    intent.putStringArrayListExtra("Categories Number", (ArrayList<String>) categoriesNumber);
                    startActivity(intent);
                    topActivity();
                }
            }
        });
        drawerLayout.findViewById(R.id.top_app_bar).findViewById(R.id.side_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNavigationDrawer();
            }
        });
        drawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        drawerLayout.findViewById(R.id.nav_drawer_view).findViewById(R.id.nav_bottom).findViewById(R.id.icon_follow_us_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUsOnPage();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

    }

    @Override
    protected void onResume() {
        super.onResume();
        storeCartItems("-1", 0);
    }

    public boolean isInternetConnected(Context context) {
        this.context = context;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);
        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        if (connected) {
            return true;
        }
        return false;
    }


    public void callNavigationDrawer() {

        DrawerLayout drawer = findViewById(R.id.navigation_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.topAppBar.openViewToolbar, R.string.nav_open, R.string.nav_close);

        drawer.openDrawer(GravityCompat.END);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = findViewById(R.id.nav_drawer_view);
        ConstraintLayout v = (ConstraintLayout) nav.getHeaderView(0);
        ImageButton btnNavBack = v.findViewById(R.id.btn_back_from_nav);
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
                    startActivity(new Intent(context, HomePageActivity.class));
                    previousItemId = itemId;
                } else if (itemId == R.id.nav_about_us) {
                    startActivity(new Intent(context, AboutUsActivity.class));
                    drawer.closeDrawer(GravityCompat.END);
                } else if (itemId == R.id.nav_track_order) {
                    drawer.closeDrawer(GravityCompat.END);
                } else if (itemId == R.id.nav_contact_us && activity != ContactUsActivity.class) {
                    drawer.closeDrawer(GravityCompat.END);
                    showProgressingView();
                    openContactUsPage();
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
            dataNotFound = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.try_again, null);
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

    public void openContactUsPage() {
        intent = new Intent(getApplicationContext(), ContactUsActivity.class);
        intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) categoriesName);
        intent.putStringArrayListExtra("Categories Number", (ArrayList<String>) categoriesNumber);
        startActivity(intent);
    }

    public void callCatalogueListActivity(Context context, String searchData, int position, int reference) {
        Intent intent = new Intent(context, CatalogueListActivity.class);
        intent.putExtra("Search Term", searchData);
        intent.putExtra("Reference", reference);
        intent.putExtra("Category Position", position);
        intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) categoriesName);
        intent.putStringArrayListExtra("Categories Number", (ArrayList<String>) categoriesNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        topActivity();

    }

    public void dataOfCategory(List<String> categoryName, List<String> categoryNumber) {
        this.categoriesName = categoryName;
        this.categoriesNumber = categoryNumber;
    }

    public void storeCartItems(String sysId, int saveDelete) {
        fetchCartItems();
        Gson gson = new Gson();

        if (!sysId.equals("-1")) {
            if (saveDelete == 0 && !sysIdList.contains(sysId)) {
                sysIdList.add(sysId);
                counter.setText(String.valueOf(sysIdList.size()));
                counter.setVisibility(View.VISIBLE);
            } else if (saveDelete == 1) {
                sysIdList.remove(sysId);
                if (sysIdList.size() != 0) {
                    counter.setText(String.valueOf(sysIdList.size()));
                    counter.setVisibility(View.VISIBLE);
                } else {
                    counter.setVisibility(View.GONE);
                }
            } else if (saveDelete ==-1) {
                sysIdList = new ArrayList<>();
                counter.setVisibility(View.GONE);
            }
        } else {
            if (sysIdList.size() != 0) {
                counter.setText(String.valueOf(sysIdList.size()));
                counter.setVisibility(View.VISIBLE);
            } else {
                counter.setVisibility(View.GONE);
            }
        }

        SharedPreferences.Editor editor = storeSysId.edit();
        String listData = gson.toJson(sysIdList);
        editor.putString("SysId", listData);
        editor.apply();
    }
    public void topActivity(){
        ActivityManager m = (ActivityManager) getApplicationContext().getSystemService( getApplicationContext().ACTIVITY_SERVICE );
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList =  m.getRunningTasks(10);
        Iterator<ActivityManager.RunningTaskInfo> itr = runningTaskInfoList.iterator();

        while(itr.hasNext()){
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo)itr.next();
            int id = runningTaskInfo.id;
            CharSequence desc= runningTaskInfo.description;
            int numOfActivities = runningTaskInfo.numActivities;
            String topActivity = runningTaskInfo.topActivity.getShortClassName();

            String bottomActivity = runningTaskInfo.baseActivity.getShortClassName();
            Log.d("Id------",id+ " desc " + desc +" number of activities " + numOfActivities +" top activity " + topActivity +" base Activity" + bottomActivity);
        }
    }

    public ArrayList<String> fetchCartItems() {
        storeSysId = getApplicationContext().getSharedPreferences("Products", MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        String fetchData = storeSysId.getString("SysId", null);
        ArrayList<String> list = gson.fromJson(fetchData, listType);
        if (list == null) {
            sysIdList = new ArrayList<>();
            return sysIdList;
        } else {
            sysIdList = list;
        }
        return list;
    }
    public void followUsOnPage(){
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.follow_us_on_link)));
        startActivity(intent);
    }
}