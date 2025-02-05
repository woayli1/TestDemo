package com.enjoypartytime.testdemo.mvp.model;

import android.os.Handler;
import android.os.Looper;

import com.blankj.utilcode.util.TimeUtils;
import com.enjoypartytime.testdemo.mvp.bean.UserBean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class MvpModel implements IModel {

    @Override
    public void login(final String username, String password, final Callback callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                long time = TimeUtils.getNowMills();
                if (time % 2 == 0) {
                    callback.onSuccess(new UserBean(1, "AAA"));
                } else {
                    callback.onFailure("今天运气差了一丢丢");
                }
            }
        }, 2000);
    }

}
