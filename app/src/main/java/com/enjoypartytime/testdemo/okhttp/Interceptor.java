package com.enjoypartytime.testdemo.okhttp;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/21
 */
public class Interceptor {

    public void interceptor() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new okhttp3.Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                //前置处理
                Request request = chain.request().newBuilder().addHeader("os", "Android")
                        .addHeader("version", "1.0").build();
                Response response = chain.proceed(request);

                //后置处理

                return response;
            }
        }).addNetworkInterceptor(new okhttp3.Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {

                return null;
            }
        }).build();

        Request request = new Request.Builder().url("").build();
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
