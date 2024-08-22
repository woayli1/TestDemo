package com.enjoypartytime.testdemo.okhttp.retrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/21
 */
public class RetrofitActivity extends Activity {

    Retrofit retrofit;
    HttpBinService httpBinService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        TextView tvRetrofitGet = findViewById(R.id.tv_retrofit_get);
        TextView tvRetrofitPost = findViewById(R.id.tv_retrofit_post);
        TextView tvRetrofitBody = findViewById(R.id.tv_retrofit_body);
        TextView tvRetrofitPath = findViewById(R.id.tv_retrofit_path);
        TextView tvRetrofitHeaders = findViewById(R.id.tv_retrofit_headers);
        TextView tvRetrofitUrl = findViewById(R.id.tv_retrofit_url);
        TextView tvRetrofitConverter = findViewById(R.id.tv_retrofit_converter);

        tvRetrofitGet.setOnClickListener(view -> retrofitGet());
        tvRetrofitPost.setOnClickListener(view -> retrofitPost());
        tvRetrofitBody.setOnClickListener(view -> retrofitBody());
        tvRetrofitPath.setOnClickListener(view -> retrofitPath());
        tvRetrofitHeaders.setOnClickListener(view -> retrofitHeaders());
        tvRetrofitUrl.setOnClickListener(view -> retrofitUrl());
        tvRetrofitConverter.setOnClickListener(view -> {
            Intent intent = new Intent(RetrofitActivity.this, RetrofitConverterActivity.class);
            startActivity(intent);
        });

        retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        httpBinService = retrofit.create(HttpBinService.class);
    }

    private void retrofitGet() {
        Call<ResponseBody> responseBodyCall = httpBinService.get("aaa", "123");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    LogUtils.i("retrofitGet：" + response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {

            }
        });

    }

    private void retrofitPost() {
        Call<ResponseBody> responseBodyCall = httpBinService.post("aaa", "123");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    LogUtils.i("retrofitPost：" + response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {

            }
        });
    }

    private void retrofitBody() {
        FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
        httpBinService.postBody(formBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    LogUtils.i("retrofitBody：" + response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    private void retrofitPath() {
        httpBinService.postInPath("post", "android", "aaa", "123")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            LogUtils.i("retrofitPath：" + response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {

                    }
                });
    }

    private void retrofitHeaders() {
        httpBinService.postWithHeaders().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    LogUtils.i("retrofitHeaders：" + response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {

            }
        });
    }

    private void retrofitUrl() {
        httpBinService.postWithUrl("https://www.httpbin.org/post")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            LogUtils.i("retrofitHeaders：" + response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {

                    }
                });
    }
}
