package com.project.petmanagement.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.petmanagement.fragments.ShopCartFragment;
import com.project.petmanagement.fragments.ShopHomeFragment;
import com.project.petmanagement.fragments.ShopMoreFragment;

public class ShopViewpagerAdapter extends FragmentStateAdapter {
    public ShopViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ShopHomeFragment();
            case 1:
                return new ShopCartFragment();
            default:
                return new ShopMoreFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
