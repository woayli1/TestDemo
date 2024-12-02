package com.enjoypartytime.testdemo.opengl.camerax;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/26
 * 照相机 + 滤镜
 */
public class CameraXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);

        TextView tvClose = findViewById(R.id.tv_close);
        tvClose.setOnClickListener(view -> finish());

        TextView tvFilter = findViewById(R.id.tv_filter);
        tvFilter.setOnClickListener(view -> {


        });
    }

}
