package com.enjoypartytime.testdemo.diyView.coordinatorLayout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.diyView.coordinatorLayout.adapter.CoordinatorLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/17
 * 协调者布局
 */
public class CoordinatorLayoutActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 coordinatorViewPager = findViewById(R.id.coordinator_view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("PAGE1"), 0);
        tabLayout.addTab(tabLayout.newTab().setText("PAGE2"), 1);
        tabLayout.addTab(tabLayout.newTab().setText("PAGE3"), 2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                coordinatorViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        CoordinatorLayoutAdapter adapter = new CoordinatorLayoutAdapter(this);
        adapter.addFragment(new CoordinatorLayoutChildFragment());
        adapter.addFragment(new CoordinatorLayoutChildFragment());
        adapter.addFragment(new CoordinatorLayoutChildFragment());

        coordinatorViewPager.setAdapter(adapter);
        coordinatorViewPager.setCurrentItem(0);
        coordinatorViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (tabLayout.getSelectedTabPosition() != position) {
                    Objects.requireNonNull(tabLayout.getTabAt(position)).select();
                }
            }
        });
    }
}
