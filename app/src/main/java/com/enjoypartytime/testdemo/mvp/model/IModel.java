package com.enjoypartytime.testdemo.mvp.model;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public interface IModel {

    void login(String username, String password, Callback callback);
}
