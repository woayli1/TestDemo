package com.enjoypartytime.testdemo.shop.persenter;

import com.enjoypartytime.testdemo.shop.bean.UserBean;
import com.enjoypartytime.testdemo.shop.model.Callback;
import com.enjoypartytime.testdemo.shop.model.IModel;
import com.enjoypartytime.testdemo.shop.model.ShopModel;
import com.enjoypartytime.testdemo.shop.view.IView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class ShopPresenter implements IPresenter, Callback {

    private IView shopView;
    private IModel shopModel;

    public ShopPresenter(IView shopView) {
        shopModel = new ShopModel();
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
