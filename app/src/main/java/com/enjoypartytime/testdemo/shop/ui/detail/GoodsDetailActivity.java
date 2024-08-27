package com.enjoypartytime.testdemo.shop.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.shop.bean.GoodDetailBean;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class GoodsDetailActivity extends Activity implements GoodsDetailContract.IGoodsDetailView {

    private int goodsId = 0;
    private Toolbar toolbar;
    private TextView tvDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            goodsId = bundle.getInt("goodsId");
        }

        toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        tvDetail = findViewById(R.id.tv_detail);

        GoodsDetailPresenter detailPresenter = new GoodsDetailPresenter(this);
        detailPresenter.getGoodsDetail(goodsId);

    }

    @Override
    public void getDetailSuccess(GoodDetailBean detailBean) {
        toolbar.setTitle(detailBean.getName());
        tvDetail.setText(detailBean.getDescription());
    }

    @Override
    public void getDetailFailure(String msg) {
        ToastUtils.showShort(msg);
    }
}