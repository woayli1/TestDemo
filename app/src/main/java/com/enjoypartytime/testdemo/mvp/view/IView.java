package com.enjoypartytime.testdemo.mvp.view;

import com.enjoypartytime.testdemo.mvp.bean.UserBean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public interface IView {

    void showProgress();

    void hideProgress();

    void loginSuccess(UserBean userBean);

    void loginFailure(String msg);
}