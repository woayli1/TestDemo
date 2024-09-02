package com.enjoypartytime.testdemo.opengl.live2d;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/28
 */
public class Live2DActivity extends Activity {

    private GLSurfaceView glSurfaceView;
    private Live2DRenderer live2DRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_2d);

        glSurfaceView = findViewById(R.id.gl_surface_view);

        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        glSurfaceView.setZOrderOnTop(true);

        glSurfaceView.setEGLContextClientVersion(3);

        live2DRenderer = new Live2DRenderer();
        glSurfaceView.setRenderer(live2DRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
