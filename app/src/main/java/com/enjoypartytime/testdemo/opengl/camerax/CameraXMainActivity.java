package com.enjoypartytime.testdemo.opengl.camerax;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.camerax.cameraXFilter.CameraXFilterActivity;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/26
 */
public class CameraXMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x_main);

        TextView tvCameraX = findViewById(R.id.tv_camera_x);
        TextView tvCameraXFilter = findViewById(R.id.tv_camera_x_filter);

        tvCameraX.setOnClickListener(view -> {
            Intent intent = new Intent(CameraXMainActivity.this, CameraXActivity.class);
            startActivity(intent);
        });

        tvCameraXFilter.setOnClickListener(view -> {
            Intent intent = new Intent(CameraXMainActivity.this, CameraXFilterActivity.class);
            startActivity(intent);
        });
    }

}
