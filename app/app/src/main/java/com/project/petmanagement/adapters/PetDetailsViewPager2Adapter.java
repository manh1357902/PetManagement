package com.project.petmanagement.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.petmanagement.fragments.PetInfoFragment;
import com.project.petmanagement.fragments.PetActivityFragment;

public class PetDetailsViewPager2Adapter extends FragmentStateAdapter {

    private long idPet;
    private FloatingActionButton btnAdd;

    public PetDetailsViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setData(long idPet) {
        this.idPet = idPet;
    }

    public void setBtnAdd(FloatingActionButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PetInfoFragment(idPet, btnAdd);
            default:
                return new PetActivityFragment(idPet, btnAdd);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
