package com.enjoypartytime.testdemo.shop.ui.home;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.shop.adapter.HomeAdapter;
import com.enjoypartytime.testdemo.shop.adapter.HomeAdapterDiffCallBack;
import com.enjoypartytime.testdemo.shop.adapter.HomeSpanSizeLookup;
import com.enjoypartytime.testdemo.base.BaseFragment;
import com.enjoypartytime.testdemo.shop.bean.GoodsBean;
import com.enjoypartytime.testdemo.shop.ui.detail.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class HomeFragment extends BaseFragment implements HomeContract.IHomeView {

    private List<GoodsBean> goodsBeanList;
    private HomePresenter homePresenter;
    private HomeAdapter homeAdapter;
    private SwipeRefreshLayout homeSwipeRefreshLayout;
    private HomeSpanSizeLookup homeSpanSizeLookup;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        homeSwipeRefreshLayout = find(R.id.home_swipe_refresh_layout);
        RecyclerView homeRecyclerView = find(R.id.home_recycler_view);
        ImageView ivHome = find(R.id.iv_home);
        EditText etHome = find(R.id.et_home);

        goodsBeanList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        homeSpanSizeLookup = new HomeSpanSizeLookup(goodsBeanList);
        gridLayoutManager.setSpanSizeLookup(homeSpanSizeLookup);
        homeRecyclerView.setLayoutManager(gridLayoutManager);

        homeAdapter = new HomeAdapter(getContext(), goodsBeanList);
        homeAdapter.setOnItemClickListener(goodsBean -> {
            Bundle bundle = new Bundle();
            bundle.putInt("goodsId", goodsBean.getGoodsId());
            ActivityUtils.startActivity(GoodsDetailActivity.class, bundle);
        });
        homeRecyclerView.setAdapter(homeAdapter);

        homePresenter = new HomePresenter(this);

        homePresenter.getData();
        showProgress();
        homeSwipeRefreshLayout.setOnRefreshListener(() -> {
            homeSwipeRefreshLayout.setRefreshing(false);
            homePresenter.getData();
        });

        ivHome.setOnClickListener(view -> ToastUtils.showShort("扫码"));

        etHome.setOnEditorActionListener((textView, i, keyEvent) -> {
            ToastUtils.showShort("搜索内容为：" + textView.getText().toString());
            etHome.setText("");
            return false;
        });
    }


    @Override
    public void getGoodsSuccess(List<GoodsBean> goodsBeanNewList) {
        hideProgress();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new HomeAdapterDiffCallBack(goodsBeanList, goodsBeanNewList), true);
        diffResult.dispatchUpdatesTo(homeAdapter);

        homeSpanSizeLookup.setGoodsBeanList(goodsBeanNewList);
        goodsBeanList = goodsBeanNewList;
        homeAdapter.setGoodsBeanList(goodsBeanNewList);
    }

    @Override
    public void getGoodsFailure(String msg) {
        ToastUtils.showShort(msg);
    }

}
