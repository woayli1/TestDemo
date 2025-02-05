package com.enjoypartytime.testdemo.mvp;

import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.base.BaseActivity;
import com.enjoypartytime.testdemo.mvp.bean.UserBean;
import com.enjoypartytime.testdemo.mvp.persenter.IPresenter;
import com.enjoypartytime.testdemo.mvp.persenter.MvpPresenter;
import com.enjoypartytime.testdemo.mvp.view.IView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class MvpActivity extends BaseActivity implements IView {

    private IPresenter mvpPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initViews() {
        TextView tvShopLogin = findViewById(R.id.tv_mvp_login);

        tvShopLogin.setOnClickListener(view -> login());

        mvpPresenter = new MvpPresenter(this);
    }

    private void login() {
        mvpPresenter.login("111", "222");
    }

    @Override
    public void showViewProgress() {
        showProgress();
    }

    @Override
    public void hideViewProgress() {
        hideProgress();
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        ToastUtils.showShort("登录成功：运气不错哦~");
    }

    @Override
    public void loginFailure(String msg) {
        ToastUtils.showShort("登录失败：" + msg);
    }
}
