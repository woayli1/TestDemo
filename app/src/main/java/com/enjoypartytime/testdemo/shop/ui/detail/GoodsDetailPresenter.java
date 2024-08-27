package com.enjoypartytime.testdemo.shop.ui.detail;

import com.blankj.utilcode.util.LogUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class GoodsDetailPresenter implements GoodsDetailContract.IGoodsDetailPresenter {

    private final GoodsDetailContract.IGoodsDetailView goodsDetailView;
    private final GoodsDetailContract.IGoodsDetailModel goodsDetailModel;

    public GoodsDetailPresenter(GoodsDetailContract.IGoodsDetailView goodsDetailView) {
        this.goodsDetailView = goodsDetailView;
        goodsDetailModel = new GoodsDetailModel();
    }

    @Override
    public void getGoodsDetail(int goodsId) {
        Disposable disposable = goodsDetailModel.getDetail(goodsId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBaseBean -> goodsDetailView.getDetailSuccess(listBaseBean.getData()), throwable -> {
                    LogUtils.e(throwable);
                    goodsDetailView.getDetailFailure(throwable.getMessage());
                });
    }
}
