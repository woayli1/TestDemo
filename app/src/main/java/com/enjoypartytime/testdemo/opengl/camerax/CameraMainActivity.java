package com.enjoypartytime.testdemo.opengl.camerax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import com.blankj.utilcode.util.RomUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.camerax.bigEye.BigEyeActivity;
import com.enjoypartytime.testdemo.opengl.camerax.camera2.Camera2Activity;
import com.enjoypartytime.testdemo.opengl.camerax.cameraXFilter.CameraXFilterActivity;
import com.enjoypartytime.testdemo.opengl.camerax.vivo.VivoZeissActivity;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/26
 */
public class CameraMainActivity extends AppCompatActivity {

    @OptIn(markerClass = ExperimentalCamera2Interop.class)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x_main);

        TextView tvCamera2 = findViewById(R.id.tv_camera_2);
        TextView tvCameraX = findViewById(R.id.tv_camera_x);
        TextView tvCameraXFilter = findViewById(R.id.tv_camera_x_filter);
        TextView tvBigEye = findViewById(R.id.tv_big_eye);
        TextView tvVivoZeiss = findViewById(R.id.tv_vivo_zeiss);

        tvVivoZeiss.setVisibility(RomUtils.isVivo() ? View.VISIBLE : View.GONE);

        tvCamera2.setOnClickListener(view -> {
            Intent intent = new Intent(CameraMainActivity.this, Camera2Activity.class);
            startActivity(intent);
        });

        tvCameraX.setOnClickListener(view -> {
            Intent intent = new Intent(CameraMainActivity.this, CameraXActivity.class);
            startActivity(intent);
        });

        tvCameraXFilter.setOnClickListener(view -> {
            Intent intent = new Intent(CameraMainActivity.this, CameraXFilterActivity.class);
            startActivity(intent);
        });

        tvBigEye.setOnClickListener(view -> {
            Intent intent = new Intent(CameraMainActivity.this, BigEyeActivity.class);
            startActivity(intent);
        });

        tvVivoZeiss.setOnClickListener(view -> {
            Intent intent = new Intent(CameraMainActivity.this, VivoZeissActivity.class);
            startActivity(intent);
        });
    }

}
