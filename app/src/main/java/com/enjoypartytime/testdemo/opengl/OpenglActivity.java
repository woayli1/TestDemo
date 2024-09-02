package com.enjoypartytime.testdemo.opengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
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

        TextView tvTriangle = findViewById(R.id.tv_triangle);
        tvTriangle.setOnClickListener(view -> {
            Intent intent = new Intent(OpenglActivity.this, TriangleActivity.class);
            startActivity(intent);
        });

        TextView tvSquare = findViewById(R.id.tv_square);
        tvSquare.setOnClickListener(view -> {
            Intent intent = new Intent(OpenglActivity.this, SquareActivity.class);
            startActivity(intent);
        });

        TextView tvMosaic = findViewById(R.id.tv_mosaic);
        tvMosaic.setOnClickListener(view -> {
            Intent intent = new Intent(OpenglActivity.this, MosaicActivity.class);
            startActivity(intent);
        });

        TextView tvLive2d = findViewById(R.id.tv_live_2d);
        tvLive2d.setOnClickListener(view -> {
            Intent intent = new Intent(OpenglActivity.this, Live2DActivity.class);
            startActivity(intent);
        });
    }

}
