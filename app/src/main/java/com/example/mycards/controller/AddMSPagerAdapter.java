package com.example.mycards.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mycards.fragments.addCardTab;
import com.example.mycards.fragments.addCouponTab;
import com.example.mycards.fragments.addSubTab;

public class AddMSPagerAdapter extends FragmentStateAdapter {


    public AddMSPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new addCardTab();
            case 1:
                return new addCouponTab();
            case 2:
                return new addSubTab();
            default:
                return new addCardTab();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
