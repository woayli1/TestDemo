package com.enjoypartytime.testdemo.okhttp.retrofit;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/22
 */
public interface HttpConverterService {

    @POST("post")
    @FormUrlEncoded
    Call<ResponseBody> post(@Field("username") String username, @Field("password") String pwd);

    @POST("post")
    @FormUrlEncoded
    Call<ConverterBean> postBean(@Field("username") String username, @Field("password") String pwd);

    @POST("post")
    @FormUrlEncoded
    Flowable<ConverterBean> postFlowable(@Field("username") String username, @Field("password") String pwd);

    @POST("post")
    @FormUrlEncoded
    Flowable<ResponseBody> postFlowable2(@Field("username") String username, @Field("password") String pwd);

    @GET
    @Streaming
    Call<ResponseBody> getDownload(@Url String url);

    @GET
    @Streaming
    Flowable<ResponseBody> getDownloadWithRxJava(@Url String url);
}
