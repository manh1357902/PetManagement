package com.project.petmanagement.activity.shop;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.petmanagement.R;
import com.project.petmanagement.adapters.ShopViewpagerAdapter;

public class ShopActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        viewPager2 = findViewById(R.id.viewpager2);
        bottomNavigationView = findViewById(R.id.bottom_navigation_shop);
        ShopViewpagerAdapter adapter = new ShopViewpagerAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.shop_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.cart).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.more).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.shop_home) {
                    viewPager2.setCurrentItem(0);
                } else if (item.getItemId() == R.id.cart) {
                    viewPager2.setCurrentItem(1);
                } else {
                    viewPager2.setCurrentItem(2);
                }
                return true;
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String key = extras.getString("key");
            if (key != null) {
                viewPager2.setCurrentItem(1);
                bottomNavigationView.getMenu().findItem(R.id.cart).setChecked(true);
            }
        }
    }

    public void getHomePage() {
        viewPager2.setCurrentItem(0);
        bottomNavigationView.getMenu().findItem(R.id.shop_home).setChecked(true);
    }
}