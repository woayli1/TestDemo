package com.enjoypartytime.testdemo.shop.bean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class UserBean {

    private int uid;
    private String nickName;

    public UserBean(int uid) {
        this.uid = uid;
    }

    public UserBean(int uid, String nickName) {
        this.uid = uid;
        this.nickName = nickName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
