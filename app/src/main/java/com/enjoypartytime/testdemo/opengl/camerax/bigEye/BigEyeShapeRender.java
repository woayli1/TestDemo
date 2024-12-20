package com.enjoypartytime.testdemo.opengl.camerax.bigEye;

import android.content.Context;

import com.enjoypartytime.testdemo.utils.GLUtil;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/18
 */
public abstract class BigEyeShapeRender {

    public AtomicBoolean isActive;
    public int width;
    public int height;

    public void onSurfaceCreated(BigEyeOpenGLView owner, GL10 gl, EGLConfig config) {
        isActive.set(true);
    }

    public void onSurfaceChanged(BigEyeOpenGLView owner, GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void onDrawFrame(BigEyeOpenGLView owner, GL10 gl);

    public void onViewDestroyed(BigEyeOpenGLView owner) {
        isActive.set(false);
    }

    public int compileShaderFromAssets(Context context, String vertexShaderName, String fragmentShaderName) {
        String vertexShaderCode = GLUtil.loadFromAssetsFile(context, vertexShaderName);
        String fragmentShaderCode = GLUtil.loadFromAssetsFile(context, fragmentShaderName);
        return GLUtil.createProgram(vertexShaderCode, fragmentShaderCode);
    }
}
