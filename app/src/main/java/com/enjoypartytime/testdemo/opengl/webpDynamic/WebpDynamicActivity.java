package com.enjoypartytime.testdemo.opengl.webpDynamic;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.webpDynamic.adapter.WebpDynamicAdapter;
import com.enjoypartytime.testdemo.opengl.webpDynamic.webpDynamicFilter.DynamicFilterFactory;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/26
 */
public class WebpDynamicActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webp_dynamic);
        Fresco.initialize(getApplicationContext());

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.webp_dynamic_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));

        RecyclerView recyclerView = findViewById(R.id.webp_dynamic_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemViewCacheSize(-1);

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < DynamicFilterFactory.getFilterSize(); i++) {
            stringList.add("第" + i + "个选项");
        }

        recyclerView.setAdapter(new WebpDynamicAdapter(getApplicationContext(), stringList));

    }
}
