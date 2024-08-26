package com.enjoypartytime.testdemo.shop.model;

import com.enjoypartytime.testdemo.shop.bean.UserBean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public interface Callback {

    void onSuccess(UserBean userBean);

    void onFailure(String msg);

}
