package com.enjoypartytime.testdemo.opengl.square;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/29
 */
public class SquareActivity extends Activity {

    private GLSurfaceView squareGlSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);

        squareGlSurfaceView = findViewById(R.id.square_gl_surface_view);

        squareGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        squareGlSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        squareGlSurfaceView.setZOrderOnTop(true);

        squareGlSurfaceView.setEGLContextClientVersion(3);
        squareGlSurfaceView.setRenderer(new SquareRender());
        squareGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        squareGlSurfaceView.requestRender();
    }

    @Override
    protected void onResume() {
        super.onResume();
        squareGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        squareGlSurfaceView.onPause();
    }
}
