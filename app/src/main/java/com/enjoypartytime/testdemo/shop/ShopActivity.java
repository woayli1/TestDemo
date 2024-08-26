package com.enjoypartytime.testdemo.shop;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.shop.bean.UserBean;
import com.enjoypartytime.testdemo.shop.persenter.IPresenter;
import com.enjoypartytime.testdemo.shop.persenter.ShopPresenter;
import com.enjoypartytime.testdemo.shop.view.IView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class ShopActivity extends Activity implements IView {

    private LoadingPopupView loadingPopup;
    private IPresenter shopPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        TextView tvShopLogin = findViewById(R.id.tv_shop_login);

        tvShopLogin.setOnClickListener(view -> login());

        shopPresenter = new ShopPresenter(this);
        loadingPopup = new XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .isLightNavigationBar(true)
                .asLoading(null, LoadingPopupView.Style.ProgressBar);

    }

    private void login() {
        shopPresenter.login("111", "222");
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
