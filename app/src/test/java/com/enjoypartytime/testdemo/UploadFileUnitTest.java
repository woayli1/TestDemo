package com.enjoypartytime.testdemo;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
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
public class UploadFileUnitTest {

    @Test
    public void uploadFileUnitTest() {
        OkHttpClient okHttpClient = new OkHttpClient();

        File file1 = new File("/Users/user/code/TestDemo/app/src/main/res/mipmap-hdpi/ic_launcher.webp");
        File file2 = new File("/Users/user/code/TestDemo/app/src/main/res/mipmap-hdpi/ic_launcher_round.webp");

        RequestBody requestBody1 = RequestBody.create(file1, MediaType.parse("application/octet-stream"));
        RequestBody requestBody2 = RequestBody.create(file2, MediaType.parse("application/octet-stream"));

        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("file1", file1.getName(), requestBody1)
                .addFormDataPart("file2", file2.getName(), requestBody2)
                .build();

        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(multipartBody).build();

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
