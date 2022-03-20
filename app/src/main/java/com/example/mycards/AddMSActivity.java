package com.example.mycards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.mycards.controller.AddMSPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AddMSActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager2 mViewPager;
    private AddMSPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_membership);

        mTabLayout = (TabLayout) findViewById(R.id.addMSTabLayout);
        mViewPager = (ViewPager2) findViewById(R.id.addMSViewPager);
        mPagerAdapter = new AddMSPagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setText("Card");
                        break;
                    case 1:
                        tab.setText("Coupon");
                        break;
                    case 2:
                        tab.setText("Subscription");
                        break;
                }
            }
        }).attach();
    }
}