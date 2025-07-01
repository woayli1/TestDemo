package com.enjoypartytime.testdemo.canvas.brush;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.canvas.view.BrushCanvasView;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/9
 */
public class BrushCanvasActivity extends Activity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush_canvas);

        BrushCanvasView bcv_view = findViewById(R.id.bcv_view);

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {

                bcv_view.invalidate();//告诉主线程重新绘制

                handler.postDelayed(this, 20);//每20ms循环一次，50fps
            }
        };

        handler.postDelayed(runnable, 20);
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
