package com.enjoypartytime.testdemo.shop.ui.detail;

import com.enjoypartytime.testdemo.shop.bean.BaseBean;
import com.enjoypartytime.testdemo.shop.bean.GoodDetailBean;
import com.enjoypartytime.testdemo.shop.network.RetrofitClient;
import com.enjoypartytime.testdemo.shop.service.GoodsService;

import io.reactivex.rxjava3.core.Flowable;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class GoodsDetailModel implements GoodsDetailContract.IGoodsDetailModel {

    @Override
    public Flowable<BaseBean<GoodDetailBean>> getDetail(int goodsId) {
        return RetrofitClient.getInstance().getService(GoodsService.class).getGoodDetail(goodsId);
    }
}
