package com.enjoypartytime.testdemo.mvp.model;

import com.enjoypartytime.testdemo.mvp.bean.UserBean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public interface Callback {

    void onSuccess(UserBean userBean);

    void onFailure(String msg);

}
