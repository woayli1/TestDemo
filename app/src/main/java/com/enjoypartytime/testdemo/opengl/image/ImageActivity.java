package com.enjoypartytime.testdemo.opengl.image;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.image.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/12
 */
public class ImageActivity extends Activity {

    private String type = "webp";

    /**
     * 是否是本地路径
     * true:本地路径
     * false:网络路径
     */
    private boolean isLocal = true;
    /**
     * 是否是原图
     * true:原图
     * false:过滤后的图
     */
    private boolean isOrigin = true;
    /**
     * 计时
     */
    private boolean countTime = false;
    /**
     * 是否显示加载时间
     * true:显示
     * false:隐藏
     */
    private boolean isShowLoadTime = false;

    private long loadTime = 0;

    private final String[] imageType = {"webp", "jpg", "png"};
    private int imageTypeIndex = 0;

    private List<String> stringList;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.image_swipe_refresh_layout);
        recyclerView = findViewById(R.id.image_recycler_view);

        TextView tvType = findViewById(R.id.tv_image_type);
        TextView tvLocal = findViewById(R.id.tv_image_local);
        TextView tvClearCache = findViewById(R.id.tv_image_clear_cache);
        TextView tvShowLoadTime = findViewById(R.id.tv_image_show_load_time);

        TextView tvOrigin = findViewById(R.id.tv_image_origin);
        TextView tvFilter = findViewById(R.id.tv_image_filter);

        tvType.setOnClickListener(v -> {
            imageTypeIndex++;
            if (imageTypeIndex >= imageType.length) {
                imageTypeIndex = 0;
            }
            type = imageType[imageTypeIndex];
            tvType.setText(type);
        });

        tvLocal.setOnClickListener(v -> {
            isLocal = !isLocal;
            tvLocal.setText(isLocal ? "本地路径" : "网络图片");
        });

        tvClearCache.setOnClickListener(v -> {
            Glide.get(getApplicationContext()).clearMemory();
            new Thread(() -> Glide.get(getApplicationContext()).clearDiskCache()).start();
            ToastUtils.showShort("缓存清理完成");
        });

        tvShowLoadTime.setOnClickListener(v -> {
            isShowLoadTime = !isShowLoadTime;
            tvShowLoadTime.setText(isShowLoadTime ? "显示 加载时间" : "隐藏 加载时间");

            if (!isShowLoadTime) {
                ToastUtils.cancel();
            }

        });

        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(() -> {
                    if (countTime && isShowLoadTime) {
                        countTime = false;
                        loadTime = System.currentTimeMillis() - loadTime;
                        ToastUtils.showShort("加载时长=" + loadTime + "ms");
                    }
                });

        stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("第" + i + "个选项");
        }

        tvOrigin.setOnClickListener(v -> {
            isOrigin = true;
            reloadAdapter();
        });

        tvFilter.setOnClickListener(v -> {
            isOrigin = false;
            reloadAdapter();
        });
    }

    private void reloadAdapter() {
        countTime = true;
        loadTime = System.currentTimeMillis();
        recyclerView.setAdapter(new ImageAdapter(getApplicationContext(), stringList, type, isLocal, isOrigin));
    }
}
