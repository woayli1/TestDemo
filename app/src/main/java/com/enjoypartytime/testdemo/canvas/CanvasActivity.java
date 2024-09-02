package com.enjoypartytime.testdemo.canvas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/2
 */
public class CanvasActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        TextView tvRadar = findViewById(R.id.tv_radar);
        tvRadar.setOnClickListener(view -> {
            Intent intent = new Intent(CanvasActivity.this, RadarActivity.class);
            startActivity(intent);
        });


    }
}
