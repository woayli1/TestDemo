package com.enjoypartytime.testdemo.shop.service;

import com.enjoypartytime.testdemo.shop.bean.BaseBean;
import com.enjoypartytime.testdemo.shop.bean.GoodDetailBean;
import com.enjoypartytime.testdemo.shop.bean.GoodsBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public interface GoodsService {

    @GET("edu-lance/edu-lance.github.io/master/goods_list")
    Flowable<BaseBean<List<GoodsBean>>> getGoods();


    @GET("edu-lance/edu-lance.github.io/master/goods_detail")
    Flowable<BaseBean<GoodDetailBean>> getGoodDetail(@Query("goodsId") int goodsId);

}
