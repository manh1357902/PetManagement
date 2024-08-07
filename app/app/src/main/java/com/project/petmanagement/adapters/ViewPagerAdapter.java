package com.project.petmanagement.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.project.petmanagement.fragments.CommunityFragment;
import com.project.petmanagement.fragments.HomeFragment;
import com.project.petmanagement.fragments.LibraryFragment;
import com.project.petmanagement.fragments.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new LibraryFragment();
            case 2:
                return new CommunityFragment();
            default:
                return new ProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
