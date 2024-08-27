package com.enjoypartytime.testdemo.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.mvp.bean.UserBean;
import com.enjoypartytime.testdemo.mvp.persenter.IPresenter;
import com.enjoypartytime.testdemo.mvp.persenter.MvpPresenter;
import com.enjoypartytime.testdemo.mvp.view.IView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class MvpActivity extends Activity implements IView {

    private LoadingPopupView loadingPopup;
    private IPresenter mvpPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        TextView tvShopLogin = findViewById(R.id.tv_mvp_login);

        tvShopLogin.setOnClickListener(view -> login());

        mvpPresenter = new MvpPresenter(this);
        loadingPopup = new XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .isLightNavigationBar(true)
                .asLoading(null, LoadingPopupView.Style.ProgressBar);

    }

    private void login() {
        mvpPresenter.login("111", "222");
    }

    @Override
    public void showProgress() {
        loadingPopup.show();
    }

    @Override
    public void hideProgress() {
        loadingPopup.dismiss();
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        ToastUtils.showShort("登录成功");
    }

    @Override
    public void loginFailure(String msg) {
        ToastUtils.showShort("登录失败：" + msg);
    }
}
