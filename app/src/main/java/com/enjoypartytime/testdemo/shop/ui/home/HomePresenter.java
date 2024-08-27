package com.enjoypartytime.testdemo.shop.ui.home;

import com.blankj.utilcode.util.LogUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class HomePresenter implements HomeContract.IHomePresenter {

    private final HomeContract.IHomeView homeView;
    private final HomeContract.IHomeModel homeModel;

    public HomePresenter(HomeContract.IHomeView homeView) {
        this.homeView = homeView;
        homeModel = new HomeModel();
    }

    @Override
    public void getData() {
        Disposable disposable = homeModel.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBaseBean -> homeView.getGoodsSuccess(listBaseBean.getData()), throwable -> {
                    LogUtils.e(throwable);
                    homeView.getGoodsFailure(throwable.getMessage());
                });
    }
}
