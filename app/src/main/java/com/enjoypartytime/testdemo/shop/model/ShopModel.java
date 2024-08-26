package com.enjoypartytime.testdemo.shop.model;

import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.util.TimeUtils;
import com.enjoypartytime.testdemo.shop.bean.UserBean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class ShopModel implements IModel {

    @Override
    public void login(final String username, String password, final Callback callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                long time = TimeUtils.getNowMills();
                if (time % 2 == 0) {
                    callback.onSuccess(new UserBean(1, "AAA"));
                } else {
                    callback.onFailure("连接服务器超时");
                }
            }
        }, 2000);
    }

}
