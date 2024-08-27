package com.enjoypartytime.testdemo.shop;

import androidx.viewpager2.widget.ViewPager2;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.shop.adapter.ShopAdapter;
import com.enjoypartytime.testdemo.shop.base.BaseActivity;
import com.enjoypartytime.testdemo.shop.ui.cart.CartFragment;
import com.enjoypartytime.testdemo.shop.ui.home.HomeFragment;
import com.enjoypartytime.testdemo.shop.ui.mime.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class ShopActivity extends BaseActivity {

    private ViewPager2 shopViewPager;
    private BottomNavigationView bottomShop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initViews() {

        shopViewPager = find(R.id.shop_view_pager);
        bottomShop = find(R.id.bottom_shop);

        bottomShop.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.button_home) {
                shopViewPager.setCurrentItem(0);
            } else if (itemId == R.id.button_cart) {
                shopViewPager.setCurrentItem(1);
            } else if (itemId == R.id.button_mine) {
                shopViewPager.setCurrentItem(2);
            }

            return true;
        });

        initFragment();
    }

    private void initFragment() {
        ShopAdapter shopAdapter = new ShopAdapter(this);
        shopAdapter.addFragment(new HomeFragment());
        shopAdapter.addFragment(new CartFragment());
        shopAdapter.addFragment(new MineFragment());

        shopViewPager.setAdapter(shopAdapter);
        shopViewPager.setCurrentItem(0);
        shopViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomShop.getMenu().getItem(position).setChecked(true);

            }
        });
    }
}
