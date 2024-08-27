package com.enjoypartytime.testdemo.shop.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.enjoypartytime.testdemo.shop.bean.GoodsBean;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class HomeAdapterDiffCallBack extends DiffUtil.Callback {

    private final List<GoodsBean> mOldData, mNewData;

    public HomeAdapterDiffCallBack(List<GoodsBean> mOldData, List<GoodsBean> mNewData) {
        this.mOldData = mOldData;
        this.mNewData = mNewData;
    }

    @Override
    public int getOldListSize() {
        return mOldData != null ? mOldData.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewData != null ? mNewData.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition).getGoodsId() == (mNewData.get(newItemPosition)
                .getGoodsId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        GoodsBean goodsBeanOld = mOldData.get(oldItemPosition);
        GoodsBean goodsBeanNew = mNewData.get(newItemPosition);

        if (goodsBeanOld.getGoodsId() != goodsBeanNew.getGoodsId()) {
            return false;
        }

        return false;
    }
}
