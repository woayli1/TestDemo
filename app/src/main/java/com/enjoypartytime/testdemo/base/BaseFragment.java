package com.enjoypartytime.testdemo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.view.LoadingBottomPopupView;
import com.lxj.xpopup.XPopup;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public abstract class BaseFragment extends Fragment {

    private View rootView;

    private LoadingBottomPopupView loadingBottomPopupView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);

        loadingBottomPopupView = (LoadingBottomPopupView) new XPopup.Builder(getContext())
                .isLightStatusBar(true)
                .navigationBarColor(getResources().getColor(R.color.white, null)).offsetY(-100)
                .dismissOnTouchOutside(false).hasShadowBg(false)
                .asCustom(new LoadingBottomPopupView(requireContext()).setTitle("Loading...")
                        .setStyle(LoadingBottomPopupView.Style.ProgressBar));

        initViews();
        return rootView;
    }

    public void showProgress() {
        loadingBottomPopupView.show();
    }

    public void hideProgress() {
        loadingBottomPopupView.dismiss();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected <T extends View> T find(@IdRes int id) {
        return rootView.findViewById(id);
    }
}
