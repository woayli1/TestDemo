package com.enjoypartytime.testdemo.utils;

import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/2/27
 */
public class OtherUtil {

    public static void getInstance() {
        getMaxMemory();
        isEmulator();
    }

    public static void getMaxMemory() {
        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();
        Log.d("OtherUtil getMaxMemory", Long.toString(maxMemory / (1024 * 1024)));
    }

    public static void isEmulator() {
        Log.d("OtherUtil isEmulator", DeviceUtils.isEmulator() + "");
    }
}
