package com.enjoypartytime.testdemo.shop.ui.home;

import com.enjoypartytime.testdemo.shop.bean.BaseBean;
import com.enjoypartytime.testdemo.shop.bean.GoodsBean;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public interface HomeContract {

    interface IHomePresenter {
        void getData();
    }

    interface IHomeModel {
        Flowable<BaseBean<List<GoodsBean>>> getData();
    }

    interface IHomeView {
        void getGoodsSuccess(List<GoodsBean> goodsBeanList);

        void getGoodsFailure(String msg);
    }
}
