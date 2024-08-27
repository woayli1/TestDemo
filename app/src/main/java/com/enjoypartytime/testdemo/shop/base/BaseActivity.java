package com.enjoypartytime.testdemo.shop.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected <T extends View> T find(@IdRes int id) {
        return findViewById(id);
    }

}
