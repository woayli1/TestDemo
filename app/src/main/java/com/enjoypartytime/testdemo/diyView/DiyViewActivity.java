package com.enjoypartytime.testdemo.diyView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.diyView.coordinatorLayout.CoordinatorLayoutActivity;
import com.enjoypartytime.testdemo.diyView.flowLayout.FlowLayoutActivity;
import com.enjoypartytime.testdemo.diyView.koi.KoiActivity;
import com.enjoypartytime.testdemo.diyView.noiseSpline.NoiseSplineActivity;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/19
 */
public class DiyViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_view);

        TextView tvFlowLayout = findViewById(R.id.tv_flow_layout);
        tvFlowLayout.setOnClickListener(view -> {
            Intent intent = new Intent(DiyViewActivity.this, FlowLayoutActivity.class);
            startActivity(intent);
        });

        TextView tvCoordinatorLayout = findViewById(R.id.tv_coordinatorLayout);
        tvCoordinatorLayout.setOnClickListener(view -> {
            Intent intent = new Intent(DiyViewActivity.this, CoordinatorLayoutActivity.class);
            startActivity(intent);
        });

        TextView tvKoi = findViewById(R.id.tv_koi);
        tvKoi.setOnClickListener(view -> {
            Intent intent = new Intent(DiyViewActivity.this, KoiActivity.class);
            startActivity(intent);
        });

        TextView tvNoiseSpline = findViewById(R.id.tv_noise_spline);
        tvNoiseSpline.setOnClickListener(view -> {
            Intent intent = new Intent(DiyViewActivity.this, NoiseSplineActivity.class);
            startActivity(intent);
        });

    }
}
