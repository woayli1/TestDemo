package com.enjoypartytime.testdemo.opengl.image;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.image_swipe_refresh_layout);
        RecyclerView recyclerView = findViewById(R.id.image_recycler_view);

        TextView tvType = findViewById(R.id.tv_image_type);
        TextView tvLocal = findViewById(R.id.tv_image_local);
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

        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("第" + i + "个选项");
        }

        tvOrigin.setOnClickListener(v -> recyclerView.setAdapter(new ImageAdapter(getApplicationContext(), stringList, type, isLocal, true)));

        tvFilter.setOnClickListener(v -> recyclerView.setAdapter(new ImageAdapter(getApplicationContext(), stringList, type, isLocal, false)));
    }
}
