package com.enjoypartytime.testdemo.opengl.brush;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ThreadUtils;
import com.enjoypartytime.testdemo.R;

import java.util.concurrent.TimeUnit;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/10
 */
public class BrushActivity extends Activity {

    private GLSurfaceView brushGlSurfaceView;
    private BrushRenderer brushRenderer;

    private boolean isMove = false;

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

        ThreadUtils.executeByFixedAtFixRate(1, new ThreadUtils.Task<String>() {
            @Override
            public String doInBackground() {
                if (isMove) {
                    brushGlSurfaceView.requestRender();
                }
                return "";
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFail(Throwable t) {

            }
        }, 20, TimeUnit.MILLISECONDS);
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
