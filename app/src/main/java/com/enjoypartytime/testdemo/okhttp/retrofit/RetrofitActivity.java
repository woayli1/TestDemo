package com.enjoypartytime.testdemo.okhttp.retrofit;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.base.BaseActivity;

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
public class RetrofitActivity extends BaseActivity {

    private HttpBinService httpBinService;
    private TextView tvRes;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void initViews() {
        TextView tvRetrofitGet = findViewById(R.id.tv_retrofit_get);
        TextView tvRetrofitPost = findViewById(R.id.tv_retrofit_post);
        TextView tvRetrofitBody = findViewById(R.id.tv_retrofit_body);
        TextView tvRetrofitPath = findViewById(R.id.tv_retrofit_path);
        TextView tvRetrofitHeaders = findViewById(R.id.tv_retrofit_headers);
        TextView tvRetrofitUrl = findViewById(R.id.tv_retrofit_url);
        TextView tvRetrofitConverter = findViewById(R.id.tv_retrofit_converter);
        tvRes = findViewById(R.id.tv_res);

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

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        httpBinService = retrofit.create(HttpBinService.class);
    }

    private void retrofitGet() {
        showProgress();
        Call<ResponseBody> responseBodyCall = httpBinService.get("aaa", "123");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String msg = "Retrofit Get：" + response.body().string();
                    runOnUiThread(() -> {
                        hideProgress();
                        tvRes.setText(msg);
                    });
                    LogUtils.i(msg);
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
        showProgress();
        Call<ResponseBody> responseBodyCall = httpBinService.post("aaa", "123");
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String msg = "Retrofit Post：" + response.body().string();
                    runOnUiThread(() -> {
                        hideProgress();
                        tvRes.setText(msg);
                    });
                    LogUtils.i(msg);
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
        showProgress();
        FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
        httpBinService.postBody(formBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String msg = "Retrofit Body：" + response.body().string();
                    runOnUiThread(() -> {
                        hideProgress();
                        tvRes.setText(msg);
                    });
                    LogUtils.i(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {

            }
        });
    }

    private void retrofitPath() {
        showProgress();
        httpBinService.postInPath("post", "android", "aaa", "123")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            String msg = "Retrofit Path：" + response.body().string();
                            runOnUiThread(() -> {
                                hideProgress();
                                tvRes.setText(msg);
                            });
                            LogUtils.i(msg);
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
        showProgress();
        httpBinService.postWithHeaders().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String msg = "Retrofit Headers：" + response.body().string();
                    runOnUiThread(() -> {
                        hideProgress();
                        tvRes.setText(msg);
                    });
                    LogUtils.i(msg);
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
        showProgress();
        httpBinService.postWithUrl("https://www.httpbin.org/post")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            String msg = "Retrofit Url：" + response.body().string();
                            runOnUiThread(() -> {
                                hideProgress();
                                tvRes.setText(msg);
                            });
                            LogUtils.i(msg);
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
