package com.enjoypartytime.testdemo.opengl.live2d;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/28
 */
public class Live2DActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_2d);

        TextView tvLive2dBasic = findViewById(R.id.tv_live_2d_basic);
        TextView tvLive2dClickChange = findViewById(R.id.tv_live_2d_click_change);
        TextView tvLive2dClickResponse = findViewById(R.id.tv_live_2d_click_response);
        TextView tvLive2dMoveResponse = findViewById(R.id.tv_live_2d_move_response);
        TextView tvLive2dMore = findViewById(R.id.tv_live_2d_more);

//        tvLive2dBasic.setOnClickListener(view -> ActivityUtils.startActivity(Live2DActivity.this, Live2DBasicActivity.class));


    }

}
