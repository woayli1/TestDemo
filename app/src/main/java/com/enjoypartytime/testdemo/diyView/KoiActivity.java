package com.enjoypartytime.testdemo.diyView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.diyView.view.KoiDrawable;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/27
 */
public class KoiActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koi);
    }
}
