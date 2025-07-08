package com.enjoypartytime.testdemo.diyView.koi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.diyView.koi.view.KoiRelativeLayout;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/27
 */
public class KoiActivity extends Activity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koi);

        KoiRelativeLayout krlKoi = findViewById(R.id.krl_koi);
        krlKoi.invalidate();

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                krlKoi.invalidate();
                handler.postDelayed(this, 40);//每40ms循环一次，25fps
            }
        };
        handler.postDelayed(runnable, 40);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }
    }
}
