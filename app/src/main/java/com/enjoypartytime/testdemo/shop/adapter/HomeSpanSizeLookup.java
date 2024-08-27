package com.enjoypartytime.testdemo.shop.adapter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.enjoypartytime.testdemo.shop.bean.GoodsBean;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class HomeSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final List<GoodsBean> goodsBeanList;

    public HomeSpanSizeLookup(List<GoodsBean> data) {
        goodsBeanList = data;
    }

    @Override
    public int getSpanSize(int position) {
        return goodsBeanList.get(position).getSpanSize();
    }

    public void setGoodsBeanList(List<GoodsBean> goodsBeanList1) {
        this.goodsBeanList.clear();
        this.goodsBeanList.addAll(goodsBeanList1);
    }
}
