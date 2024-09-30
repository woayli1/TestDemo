package com.enjoypartytime.testdemo.lazyLoad;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/30
 */
public class LazyChildFragment extends LazyFragment2 {

    private final String name;

    public LazyChildFragment(String name) {
        this.name = name;
    }

    @Override
    protected void initView(View rootView) {
        TextView tvLazyChild = rootView.findViewById(R.id.tv_lazy_child);
        tvLazyChild.setText(name);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_lazy_child;
    }

    @Override
    protected void printLog(String msg) {
        String mag = "fragment:" + name + " --> " + msg;
        Log.d(getClass().getName(), mag);
    }
}
