package com.project.petmanagement.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.petmanagement.MyApplication;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.login.LoginActivity;
import com.project.petmanagement.adapters.ViewPagerAdapter;
import com.project.petmanagement.services.StorageService;

public class MainActivity extends AppCompatActivity {

    private final StorageService storageService = MyApplication.getStorageService();
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = storageService.getString("token");
        if (token == null || token.isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        viewPager2 = findViewById(R.id.view_pager_2);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_library).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_community).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_profile).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                viewPager2.setCurrentItem(0, false);
            } else if (item.getItemId() == R.id.navigation_library) {
                viewPager2.setCurrentItem(1, false);
            } else if (item.getItemId() == R.id.navigation_community) {
                viewPager2.setCurrentItem(2, false);
            } else {
                viewPager2.setCurrentItem(3, false);
            }
            return true;
        });
        String fragmentZero = getIntent().getStringExtra("fragmentIndex");
        if (fragmentZero != null) {
            viewPager2.setCurrentItem(0);
            bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
        }
    }

}