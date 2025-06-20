package com.enjoypartytime.testdemo.opengl.live2d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.live2d.full.GLRenderer;
import com.enjoypartytime.testdemo.opengl.live2d.full.LAppDelegate;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/28
 */
public class Live2DActivity extends Activity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_2d);

        glSurfaceView = findViewById(R.id.gl_surface_view_live_2d);
        glSurfaceView.setEGLContextClientVersion(2);

        GLRenderer glRenderer = new GLRenderer();

        glSurfaceView.setRenderer(glRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    @Override
    protected void onStart() {
        super.onStart();

        LAppDelegate.getInstance().onStart(this);

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
        LAppDelegate.getInstance().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LAppDelegate.getInstance().onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LAppDelegate.getInstance().onDestroy();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LAppDelegate.getInstance().onTouchBegan(pointX, pointY);
                break;
            case MotionEvent.ACTION_UP:
                LAppDelegate.getInstance().onTouchEnd(pointX, pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                LAppDelegate.getInstance().onTouchMoved(pointX, pointY);
                break;
        }
        return super.onTouchEvent(event);
    }
}