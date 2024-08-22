package com.enjoypartytime.testdemo.okhttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.okhttp.retrofit.RetrofitActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/21
 */
public class OkhttpActivity extends Activity {

    protected OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        TextView tvGetSync = findViewById(R.id.tv_get_sync);
        TextView tvGetAsync = findViewById(R.id.tv_get_async);
        TextView tvPostSync = findViewById(R.id.tv_post_sync);
        TextView tvPostAsync = findViewById(R.id.tv_post_async);
        TextView tvOkHttpMore = findViewById(R.id.tv_ok_http_more);
        TextView tvRetrofit = findViewById(R.id.tv_retrofit);

        tvGetSync.setOnClickListener(view -> getSync());
        tvGetAsync.setOnClickListener(view -> getAsync());
        tvPostSync.setOnClickListener(view -> postSync());
        tvPostAsync.setOnClickListener(view -> postAsync());
        tvOkHttpMore.setOnClickListener(view -> {
            Intent intent = new Intent(OkhttpActivity.this, Okhttp2Activity.class);
            startActivity(intent);
        });
        tvRetrofit.setOnClickListener(view -> {
            Intent intent = new Intent(OkhttpActivity.this, RetrofitActivity.class);
            startActivity(intent);
        });

        okHttpClient = new OkHttpClient();
    }

    public void getSync() {
        new Thread(() -> {
            Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                if (response.body() != null) {
                    LogUtils.i("getSync：" + response.body().string());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void getAsync() {
        Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        LogUtils.i("getAsync：" + response.body().string());
                    }
                }
            }
        });
    }

    public void postSync() {
        new Thread(() -> {
            FormBody body = new FormBody.Builder().add("a", "1").add("b", "2").build();
            Request request = new Request.Builder().url("https://www.httpbin.org/post").post(body).build();
            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                if (response.body() != null) {
                    LogUtils.i("postSync：" + response.body().string());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void postAsync() {
        FormBody body = new FormBody.Builder().add("a", "1").add("b", "2").build();
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        LogUtils.i("postAsync：" + response.body().string());
                    }
                }
            }
        });
    }
}
