package com.enjoypartytime.testdemo.shop.ui.home;

import com.enjoypartytime.testdemo.shop.bean.BaseBean;
import com.enjoypartytime.testdemo.shop.bean.GoodsBean;
import com.enjoypartytime.testdemo.shop.network.RetrofitClient;
import com.enjoypartytime.testdemo.shop.service.GoodsService;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class HomeModel implements HomeContract.IHomeModel {

    @Override
    public Flowable<BaseBean<List<GoodsBean>>> getData() {
        return RetrofitClient.getInstance().getService(GoodsService.class).getGoods();
    }
}
