package com.enjoypartytime.testdemo.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.shop.bean.GoodsBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/27
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<GoodsBean> goodsBeanList;
    private OnItemClickListener clickListener;

    public HomeAdapter(Context context, List<GoodsBean> goodsBeanList) {
        this.context = context;
        this.goodsBeanList = goodsBeanList;
    }

    public void setGoodsBeanList(List<GoodsBean> goodsBeanList) {
        this.goodsBeanList = goodsBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == R.layout.home_recycler_banner) {
            ((Banner) holder.itemView.findViewById(R.id.banner))
                    .setAdapter(new BannerImageAdapter<String>(goodsBeanList.get(position)
                            .getBanners()) {
                        @Override
                        public void onBindView(BannerImageHolder holder, String imgUrl, int position, int size) {
                            //图片加载自己实现
                            Glide.with(holder.itemView)
                                    .load(imgUrl)
//                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                    .apply(RequestOptions.centerCropTransform())
                                    .into(holder.imageView);
                        }
                    })
                    .addBannerLifecycleObserver((LifecycleOwner) context)//添加生命周期观察者
                    .setIndicator(new CircleIndicator(context));

        } else if (itemViewType == R.layout.home_recycler_text) {
            ((TextView) holder.itemView.findViewById(R.id.text)).setText(goodsBeanList.get(position)
                    .getText());

        } else if (itemViewType == R.layout.home_recycler_img) {
            Glide.with(holder.itemView)
                    .load(goodsBeanList.get(position).getImageUrl())
//                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .apply(RequestOptions.centerCropTransform())
                    .into((ImageView) holder.itemView.findViewById(R.id.image));

        } else if (itemViewType == R.layout.home_recycler_text_img) {
            holder.itemView.findViewById(R.id.ll_ti).setOnClickListener(view -> {
                if (clickListener != null) {
                    clickListener.onItemClick(goodsBeanList.get(position));
                }
            });
            Glide.with(holder.itemView)
                    .load(goodsBeanList.get(position).getImageUrl())
//                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .apply(RequestOptions.centerCropTransform())
                    .into((ImageView) holder.itemView.findViewById(R.id.iv_ti));

            ((TextView) holder.itemView.findViewById(R.id.tv_ti)).setText(goodsBeanList
                    .get(position)
                    .getText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        GoodsBean goodsBean = goodsBeanList.get(position);

        if (goodsBean.getBanners() != null) {
            //banner
            return R.layout.home_recycler_banner;
        }

        if (goodsBean.getImageUrl() == null) {
            //显示文字
            return R.layout.home_recycler_text;
        }

        if (goodsBean.getText() == null) {
            //显示图片
            return R.layout.home_recycler_img;
        }

        //显示文字+图片
        return R.layout.home_recycler_text_img;

    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    private static class HomeViewHolder extends RecyclerView.ViewHolder {

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(GoodsBean goodsBean);
    }
}
