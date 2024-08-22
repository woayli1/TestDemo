package com.enjoypartytime.testdemo.okhttp;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/21
 */
public class Cookie {

    //接收cookie
    private List<okhttp3.Cookie> cookieList;

    public void cookie() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<okhttp3.Cookie> list) {
                cookieList = list;
            }

            @NonNull
            @Override
            public List<okhttp3.Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                if (httpUrl.host().equals("https://www.httpbin.org/")) {
                    return cookieList;
                }
                return Collections.emptyList();
            }
        }).build();

        Request request = new Request.Builder().url("https://www.httpbin.org/get").build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.body() != null) {
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
