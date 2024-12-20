package com.enjoypartytime.testdemo.okhttp.retrofit;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PathUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.base.BaseActivity;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/22
 */
public class RetrofitConverterActivity extends BaseActivity {

    private HttpConverterService httpConverterService, httpConverterServiceConverter;
    private TextView tvRes;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrofit_converter;
    }

    @Override
    protected void initViews() {
        TextView tvRetrofitConverterPost = findViewById(R.id.tv_retrofit_converter_post);
        TextView tvRetrofitConverterPostBean = findViewById(R.id.tv_retrofit_converter_post_bean);
        TextView tvRetrofitConverterPostFlowable = findViewById(R.id.tv_retrofit_converter_post_flowable);
        TextView tvRetrofitConverterDownload = findViewById(R.id.tv_retrofit_converter_download);
        TextView tvRetrofitConverterDownloadFlowable = findViewById(R.id.tv_retrofit_converter_download_flowable);
        tvRes = findViewById(R.id.tv_res);

        tvRetrofitConverterPost.setOnClickListener(view -> converterPost());
        tvRetrofitConverterPostBean.setOnClickListener(view -> converterPostBean());
        tvRetrofitConverterPostFlowable.setOnClickListener(view -> converterPostFlowable());
        tvRetrofitConverterDownload.setOnClickListener(view -> converterDownload());
        tvRetrofitConverterDownloadFlowable.setOnClickListener(view -> converterDownloadFlowable());


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        httpConverterService = retrofit.create(HttpConverterService.class);


        //gson转换器
        //适配器
        Retrofit retrofitConverter = new Retrofit.Builder().baseUrl("https://www.httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create()) //gson转换器
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) //适配器
                .build();
        httpConverterServiceConverter = retrofitConverter.create(HttpConverterService.class);
    }

    private void converterPost() {
        showProgress();
        Call<ResponseBody> call = httpConverterService.post("aaa", "123");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    String msg = "Converter Post：" + response.body().string();
                    runOnUiThread(() -> {
                        hideProgress();
                        tvRes.setText(msg);
                    });
                    LogUtils.i(msg);

                    ConverterBean converterBean = GsonUtils.fromJson(response.body()
                            .string(), ConverterBean.class);
                    LogUtils.i(GsonUtils.toJson(converterBean));

                    if (ObjectUtils.isNotEmpty(disposable)) {
                        disposable.dispose();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {

            }
        });
    }

    private void converterPostBean() {
        showProgress();
        Call<ConverterBean> call = httpConverterServiceConverter.postBean("aaa", "123");
        call.enqueue(new Callback<ConverterBean>() {
            @Override
            public void onResponse(@NonNull Call<ConverterBean> call, @NonNull Response<ConverterBean> response) {
                String msg = "Converter Post Bean：" + GsonUtils.toJson(response.body());
                runOnUiThread(() -> {
                    hideProgress();
                    tvRes.setText(msg);
                });
                LogUtils.i(msg);

                if (ObjectUtils.isNotEmpty(disposable)) {
                    disposable.dispose();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ConverterBean> call, @NonNull Throwable throwable) {

            }
        });
    }

    Disposable disposable = null;

    private void converterPostFlowable() {
        showProgress();
        disposable = httpConverterServiceConverter.postFlowable("aaa", "123")
                .flatMap((Function<ConverterBean, Publisher<ResponseBody>>) converterBean -> httpConverterServiceConverter.postFlowable2("bbb", "111"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        String msg = "Converter Post Flowable：" + responseBody.string();
                        runOnUiThread(() -> {
                            hideProgress();
                            tvRes.setText(msg);
                        });
                        LogUtils.i(msg);

                        ConverterBean converterBean = GsonUtils.fromJson(responseBody.string(), ConverterBean.class);
                        LogUtils.i(GsonUtils.toJson(converterBean));

                        if (ObjectUtils.isNotEmpty(disposable)) {
                            disposable.dispose();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

    }

    private void converterDownload() {
        showProgress();
        Call<ResponseBody> call = httpConverterService.getDownload("https://media.w3.org/2010/05/sintel/trailer.mp4");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                InputStream inputStream = response.body().byteStream();
                try {
                    String path = PathUtils.getExternalDownloadsPath() + "/trailer2.mp4";
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    int len;
                    byte[] buffer = new byte[4096];
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.close();
                    inputStream.close();

                    String msg = "下载完成 路径:" + path;
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

    Disposable disposableDownload = null;

    private void converterDownloadFlowable() {
        showProgress();
        disposableDownload = httpConverterServiceConverter
                .getDownloadWithRxJava("https://media.w3.org/2010/05/sintel/trailer.mp4")
                .map(responseBody -> {
                    InputStream inputStream = responseBody.byteStream();
                    File file = new File(PathUtils.getExternalDownloadsPath() + "/trailer3.mp4");
                    try {

                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        int len;
                        byte[] buffer = new byte[4096];
                        while ((len = inputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, len);
                        }
                        fileOutputStream.close();
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return file;
                }).subscribe(file -> {
                    String msg = "下载完成 Flowable 路径:" + file.getAbsolutePath();
                    runOnUiThread(() -> {
                        hideProgress();
                        tvRes.setText(msg);
                    });
                    LogUtils.i(msg);

                    if (ObjectUtils.isNotEmpty(disposableDownload)) {
                        disposableDownload.dispose();
                    }
                });
    }
}
