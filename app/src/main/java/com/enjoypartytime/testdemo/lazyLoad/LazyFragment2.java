package com.enjoypartytime.testdemo.lazyLoad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/30
 */
public abstract class LazyFragment2 extends Fragment {

    private View rootView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        printLog("onCreateView");
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        initView(rootView);
        return rootView;
    }

    protected abstract void initView(View rootView);

    protected abstract int getLayoutRes();

    //自定义函数，用于分发可见事件
    private void dispatchUserVisibleHint(boolean visibleState) {
        printLog("dispatchUserVisibleHint");
        if (visibleState) {
            onFragmentLoad();
        } else {
            onFragmentLoadStop();
        }
    }

    //停止网络数据请求
    private void onFragmentLoadStop() {
        printLog("onFragmentLoadStop");
    }

    //加载网络数据请求
    private void onFragmentLoad() {
        printLog("onFragmentLoad");
    }

    @Override
    public void onResume() {
        super.onResume();
        printLog("onResume");
        dispatchUserVisibleHint(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        printLog("onPause");
        dispatchUserVisibleHint(false);
    }

    protected abstract void printLog(String msg);
}
