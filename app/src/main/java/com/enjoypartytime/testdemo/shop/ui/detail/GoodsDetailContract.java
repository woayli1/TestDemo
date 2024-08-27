package com.enjoypartytime.testdemo.shop.ui.detail;

import com.enjoypartytime.testdemo.shop.bean.BaseBean;
import com.enjoypartytime.testdemo.shop.bean.GoodDetailBean;

import io.reactivex.rxjava3.core.Flowable;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class GoodsDetailContract {

    interface IGoodsDetailPresenter {
        void getGoodsDetail(int goodsId);
    }

    interface IGoodsDetailModel {
        Flowable<BaseBean<GoodDetailBean>> getDetail(int goodsId);
    }

    interface IGoodsDetailView {
        void getDetailSuccess(GoodDetailBean detailBean);

        void getDetailFailure(String msg);
    }
}
