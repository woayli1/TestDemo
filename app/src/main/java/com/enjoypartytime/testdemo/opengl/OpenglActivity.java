package com.enjoypartytime.testdemo.opengl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.brush.BrushActivity;
import com.enjoypartytime.testdemo.opengl.live2d.Live2DActivity;
import com.enjoypartytime.testdemo.opengl.mosaic.MosaicActivity;
import com.enjoypartytime.testdemo.opengl.square.SquareActivity;
import com.enjoypartytime.testdemo.opengl.triangle.TriangleActivity;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/29
 */
public class OpenglActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);

        TextView tvStatus = findViewById(R.id.tv_status);
        TextView tvTips = findViewById(R.id.tv_tips);
        TextView tvTriangle = findViewById(R.id.tv_triangle);
        TextView tvSquare = findViewById(R.id.tv_square);
        TextView tvMosaic = findViewById(R.id.tv_mosaic);
        TextView tvBrush = findViewById(R.id.tv_brush);
        TextView tvLive2d = findViewById(R.id.tv_live_2d);

        int glVersion = getGLESVersion();
        String version = "OpenGL ES 1.0";

        if (glVersion > 131072) {

            tvTips.setVisibility(View.GONE);

            tvTriangle.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, TriangleActivity.class);
                startActivity(intent);
            });

            tvSquare.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, SquareActivity.class);
                startActivity(intent);
            });

            tvMosaic.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, MosaicActivity.class);
                startActivity(intent);
            });

            tvBrush.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, BrushActivity.class);
                startActivity(intent);
            });

            tvLive2d.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, Live2DActivity.class);
                startActivity(intent);
            });

            if (glVersion > 196609) {
                version = "OpenGL ES 3.2";
            } else if (glVersion > 196608) {
                version = "OpenGL ES 3.1";
            } else {
                version = "OpenGL ES 3.0";
            }

        } else {

            tvTips.setVisibility(View.VISIBLE);

            tvTriangle.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvSquare.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvMosaic.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvBrush.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvLive2d.setTextColor(getResources().getColor(R.color.lightGray, null));

            if (glVersion > 65537) {
                version = "OpenGL ES 2.0";
            } else if (glVersion > 65536) {
                version = "OpenGL ES 1.1";
            }
        }

        version = "当前设备支持  " + version;
        tvStatus.setText(version);

    }

    /**
     * 返回当前手机支持的openGl ES 所支持的版本
     * OpenGL ES 1.0
     * 0x00010000
     * 65536
     * <p>
     * OpenGL ES 1.1
     * 0x00010001
     * 65537
     * <p>
     * OpenGL ES 2.0
     * 0x00020000
     * 131072
     * <p>
     * OpenGL ES 3.0
     * 0x00030000
     * 196608
     * <p>
     * OpenGL ES 3.1
     * 0x00030001
     * 196609
     * <p>
     * OpenGL ES 3.2
     * 0x00030002
     * 196610
     *
     * @return int
     */
    private int getGLESVersion() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo ci = am.getDeviceConfigurationInfo();
        return ci.reqGlEsVersion;
    }

}
