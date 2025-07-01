package com.enjoypartytime.testdemo.opengl.brush;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/10
 */
public class BrushActivity extends Activity {

    private GLSurfaceView brushGlSurfaceView;
    private BrushRenderer brushRenderer;

    private boolean isMove = false;

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        TextView tvClose = findViewById(R.id.tv_close);
        TextView tvClean = findViewById(R.id.tv_clean);
        brushGlSurfaceView = findViewById(R.id.brush_gl_surface_view);

        tvClose.setOnClickListener(view -> finish());
        tvClean.setOnClickListener(view -> {
            brushRenderer.cleanXY();
            brushGlSurfaceView.requestRender();
        });

        brushGlSurfaceView.setEGLContextClientVersion(3);

        brushGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        brushGlSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        brushGlSurfaceView.setZOrderOnTop(true);

        brushRenderer = new BrushRenderer(getApplicationContext());
        brushGlSurfaceView.setRenderer(brushRenderer);
        brushGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        brushGlSurfaceView.requestRender();

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isMove) {
                    brushGlSurfaceView.requestRender();
                }
                handler.postDelayed(this, 20);//每20ms循环一次，50fps
            }
        };

        handler.postDelayed(runnable, 20);
    }

    @Override
    protected void onResume() {
        super.onResume();
        brushGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        brushGlSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        brushRenderer = null;

        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            brushRenderer.setBegin();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //移动
            brushRenderer.setXY(event.getRawX(), event.getRawY());
            isMove = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            brushRenderer.setStop();
            isMove = false;
        }
        return true;
    }
}
