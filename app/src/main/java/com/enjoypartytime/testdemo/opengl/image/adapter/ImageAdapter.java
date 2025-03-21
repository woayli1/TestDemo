package com.enjoypartytime.testdemo.opengl.image.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.image.filter.FilterBean;
import com.enjoypartytime.testdemo.opengl.image.filter.FilterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/12
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final Context context;
    private final List<String> stringList;
    private final String type;
    private final boolean isLocal, isOrigin;

    private static final Map<String, Map<Boolean, Object>> srcMap = new HashMap<>();

    static {
        srcMap.put("jpg", new HashMap<>());
        Objects.requireNonNull(srcMap.get("jpg")).put(true, R.raw.jpg4);
        Objects.requireNonNull(srcMap.get("jpg"))
                .put(false, "https://www.gstatic.cn/webp/gallery/4.jpg");

        srcMap.put("png", new HashMap<>());
        Objects.requireNonNull(srcMap.get("png")).put(true, R.raw.png4);
        Objects.requireNonNull(srcMap.get("png"))
                .put(false, "https://www.gstatic.cn/webp/gallery/4.png");

        srcMap.put("webp", new HashMap<>());
        Objects.requireNonNull(srcMap.get("webp")).put(true, R.raw.webp4);
        Objects.requireNonNull(srcMap.get("webp"))
                .put(false, "https://www.gstatic.cn/webp/gallery/4.webp");
    }

    public ImageAdapter(Context context, List<String> stringList, String type, boolean isLocal, boolean isOrigin) {
        this.context = context;
        this.stringList = stringList;
        this.type = type;
        this.isLocal = isLocal;
        this.isOrigin = isOrigin;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        ImageView ivImage = holder.itemView.findViewById(R.id.item_iv_image);
        TextView tvImageName = holder.itemView.findViewById(R.id.item_tv_image_name);

        Object src = getSrc();
        int remainder = isOrigin ? 0 : position % FilterFactory.getFilterSize();

        FilterBean filterBean = FilterFactory.getFilter(remainder);
        tvImageName.setText(filterBean.getFilterName());
        Glide.with(context).load(src).apply(filterBean.getRequestOptions()).into(ivImage);

    }

    @NonNull
    private Object getSrc() {
        return Objects.requireNonNull(Objects.requireNonNull(srcMap.get(type)).get(isLocal));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
