package com.enjoypartytime.testdemo.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.view.LoadingBottomPopupView;
import com.lxj.xpopup.XPopup;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public abstract class BaseActivity extends FragmentActivity {

    private LoadingBottomPopupView loadingBottomPopupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        loadingBottomPopupView = (LoadingBottomPopupView) new XPopup.Builder(this)
                .isLightStatusBar(true)
                .navigationBarColor(getResources().getColor(R.color.white, null)).offsetY(-100)
                .dismissOnTouchOutside(false).hasShadowBg(false)
                .asCustom(new LoadingBottomPopupView(this).setTitle("Loading...")
                        .setStyle(LoadingBottomPopupView.Style.ProgressBar));

        initViews();
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
        return findViewById(id);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ToastUtils.cancel();
    }

}
