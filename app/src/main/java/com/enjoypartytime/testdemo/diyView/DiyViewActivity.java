package com.enjoypartytime.testdemo.diyView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;

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
    }
}
