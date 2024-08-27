package com.enjoypartytime.testdemo.shop.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        initViews();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected <T extends View> T find(@IdRes int id) {
        return rootView.findViewById(id);
    }
}
