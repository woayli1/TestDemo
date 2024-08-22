package com.enjoypartytime.testdemo.okhttp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UriUtils;
import com.enjoypartytime.testdemo.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/21
 */
public class Okhttp2Activity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_2);

        TextView tvUploadImg = findViewById(R.id.tv_upload_img);
        TextView tvUploadJson = findViewById(R.id.tv_upload_json);

        tvUploadImg.setOnClickListener(view -> requestStoragePermission());
        tvUploadJson.setOnClickListener(view -> uploadJson());

        okHttpClient = new OkHttpClient();
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            // 权限已经被授予
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ActivityUtils.startActivityForResult(Okhttp2Activity.this, intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else {
            ToastUtils.showShort("权限被拒绝，请手动打开");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                uploadImg(data.getData());
            }
        }

    }

    private void uploadImg(Uri imgUri) {
        File file1 = UriUtils.uri2File(imgUri);

        RequestBody requestBody1 = RequestBody.create(file1, MediaType.parse("image/" + ImageUtils.getImageType(file1)));

        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("file1", file1.getName(), requestBody1)
                .build();

        Request request = new Request.Builder().url("https://www.httpbin.org/post")
                .post(multipartBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        LogUtils.i("uploadImg：" + response.body().string());
                    }
                }
            }
        });
    }

    private void uploadJson() {
        RequestBody requestBody = RequestBody.create("{\"a\":1,\"b\":2}", MediaType.parse("application/json"));

        Request request = new Request.Builder().url("https://www.httpbin.org/post")
                .post(requestBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        LogUtils.i("uploadJson：" + response.body().string());
                    }
                }
            }
        });
    }

}
