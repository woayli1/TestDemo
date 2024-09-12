package com.enjoypartytime.testdemo.opengl.triangle;

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
public class TriangleActivity extends Activity {

    GLSurfaceView triangleGlSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        triangleGlSurfaceView = findViewById(R.id.triangle_gl_surface_view);

        triangleGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        triangleGlSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        triangleGlSurfaceView.setZOrderOnTop(true);

        triangleGlSurfaceView.setEGLContextClientVersion(3);
        triangleGlSurfaceView.setRenderer(new TriangleRenderer(getApplicationContext()));
        triangleGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        triangleGlSurfaceView.requestRender();
    }

    @Override
    protected void onResume() {
        super.onResume();
        triangleGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        triangleGlSurfaceView.onPause();
    }
}
