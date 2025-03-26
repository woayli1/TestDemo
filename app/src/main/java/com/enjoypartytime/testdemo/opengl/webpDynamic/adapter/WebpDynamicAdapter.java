package com.enjoypartytime.testdemo.opengl.webpDynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.webpDynamic.view.WebPGLView;
import com.enjoypartytime.testdemo.opengl.webpDynamic.webpDynamicFilter.DynamicFilterBean;
import com.enjoypartytime.testdemo.opengl.webpDynamic.webpDynamicFilter.DynamicFilterFactory;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/26
 */
public class WebpDynamicAdapter extends RecyclerView.Adapter<WebpDynamicAdapter.WebpDynamicHolder> {

    private final Context context;
    private final List<String> stringList;

    public WebpDynamicAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public WebpDynamicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_webp_dynamic_layout, parent, false);
        return new WebpDynamicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WebpDynamicHolder holder, int position) {
        WebPGLView webPGLView = holder.itemView.findViewById(R.id.item_iv_webp_dynamic);
        TextView tvWebpDynamicName = holder.itemView.findViewById(R.id.item_tv_webp_dynamic_name);

        int remainder = position % DynamicFilterFactory.getFilterSize();
        DynamicFilterBean dynamicFilterBean = DynamicFilterFactory.getFilter(remainder);
        tvWebpDynamicName.setText(dynamicFilterBean.getFilterName());
        webPGLView.getDynamicRender().setFilter(dynamicFilterBean.getImageFilter());
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public static class WebpDynamicHolder extends RecyclerView.ViewHolder {

        public WebpDynamicHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
