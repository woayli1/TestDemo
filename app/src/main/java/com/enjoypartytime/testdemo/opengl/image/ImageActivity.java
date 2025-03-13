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
    private boolean isLocal = true;
    private long loadTime = 0;
    private boolean countTime = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.image_swipe_refresh_layout);
        RecyclerView recyclerView = findViewById(R.id.image_recycler_view);

        TextView tvType = findViewById(R.id.tv_image_type);
        TextView tvLocal = findViewById(R.id.tv_image_local);
        TextView tvClearCache = findViewById(R.id.tv_image_clear_cache);
        TextView tvOrigin = findViewById(R.id.tv_image_origin);
        TextView tvFilter = findViewById(R.id.tv_image_filter);

        tvType.setOnClickListener(v -> {

            if (type.equals("jpg")) {
                type = "png";
            } else if (type.equals("png")) {
                type = "webp";
            } else {
                type = "jpg";
            }
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

        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(() -> {
                    if (countTime) {
                        countTime = false;
                        loadTime = System.currentTimeMillis() - loadTime;
                        ToastUtils.showShort("加载时长=" + loadTime + "ms");
                    }
                });

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("第" + i + "个选项");
        }

        tvOrigin.setOnClickListener(v -> {
            countTime = true;
            loadTime = System.currentTimeMillis();
            recyclerView.setAdapter(new ImageAdapter(getApplicationContext(), stringList, type, isLocal, true));
        });

        tvFilter.setOnClickListener(v -> {
            countTime = true;
            loadTime = System.currentTimeMillis();
            recyclerView.setAdapter(new ImageAdapter(getApplicationContext(), stringList, type, isLocal, false));
        });
    }
}
