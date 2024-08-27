package com.enjoypartytime.testdemo.mvp.persenter;

import com.enjoypartytime.testdemo.mvp.bean.UserBean;
import com.enjoypartytime.testdemo.mvp.model.Callback;
import com.enjoypartytime.testdemo.mvp.model.IModel;
import com.enjoypartytime.testdemo.mvp.model.MvpModel;
import com.enjoypartytime.testdemo.mvp.view.IView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class MvpPresenter implements IPresenter, Callback {

    private IView shopView;
    private IModel shopModel;

    public MvpPresenter(IView shopView) {
        shopModel = new MvpModel();
        this.shopView = shopView;
    }

    @Override
    public void login(String username, String password) {
        shopView.showProgress();
        shopModel.login(username, password, this);
    }

    @Override
    public void onSuccess(UserBean userBean) {
        shopView.hideProgress();
        shopView.loginSuccess(userBean);
    }

    @Override
    public void onFailure(String msg) {
        shopView.hideProgress();
        shopView.loginFailure(msg);
    }
}
