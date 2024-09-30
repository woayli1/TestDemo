package com.enjoypartytime.testdemo.lazyLoad;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.enjoypartytime.testdemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/29
 */
public class LazyLoadActivity extends FragmentActivity {

    private ViewPager2 lazyViewPager;
    private BottomNavigationView bottomLazy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_load);

        lazyViewPager = findViewById(R.id.lazy_view_pager);
        bottomLazy = findViewById(R.id.bottom_lazy);

        bottomLazy.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.button_one) {
                lazyViewPager.setCurrentItem(0);
            } else if (itemId == R.id.button_two) {
                lazyViewPager.setCurrentItem(1);
            } else if (itemId == R.id.button_three) {
                lazyViewPager.setCurrentItem(2);
            } else if (itemId == R.id.button_four) {
                lazyViewPager.setCurrentItem(3);
            } else if (itemId == R.id.button_five) {
                lazyViewPager.setCurrentItem(4);
            }

            return true;
        });

        initFragment();
    }

    private void initFragment() {
        LazyAdapter lazyAdapter = new LazyAdapter(this);
        lazyAdapter.addFragment(new LazyChildFragment("1"));
        lazyAdapter.addFragment(new LazyChildFragment("2"));
        lazyAdapter.addFragment(new LazyChildFragment("3"));
        lazyAdapter.addFragment(new LazyChildFragment("4"));
        lazyAdapter.addFragment(new LazyChildFragment("5"));

        lazyViewPager.setAdapter(lazyAdapter);
        lazyViewPager.setCurrentItem(0);
        lazyViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomLazy.getMenu().getItem(position).setChecked(true);
            }
        });

    }
}
