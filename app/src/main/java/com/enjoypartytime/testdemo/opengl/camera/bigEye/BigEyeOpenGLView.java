package com.enjoypartytime.testdemo.opengl.camera.bigEye;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/18
 */
public class BigEyeOpenGLView extends GLSurfaceView {

    private SurfaceCreatedCache createdCache;
    private SurfaceSizeCache sizeCache;

    private BigEyeShapeRender shapeRender;

    public BigEyeOpenGLView(Context context) {
        super(context);
    }

    public BigEyeOpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setShapeRender(BigEyeShapeRender shapeRender) {
        if (this.shapeRender != null) {
            this.shapeRender.onViewDestroyed(this);
        }

        this.shapeRender = shapeRender;
        init();
        requestRender();
    }

    public BigEyeShapeRender getShapeRender() {
        return shapeRender;
    }

    private void init() {
        setEGLContextClientVersion(3);
        setRenderer(new MyRenderer(this));
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private static class MyRenderer implements Renderer {

        private final BigEyeOpenGLView owner;

        public MyRenderer(BigEyeOpenGLView owner) {
            this.owner = owner;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            BigEyeShapeRender render = owner.getShapeRender();
            if (render != null) {
                render.onSurfaceCreated(owner, gl, config);
                owner.requestRender();
            }

            owner.createdCache = new SurfaceCreatedCache(gl, config);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            BigEyeShapeRender render = owner.getShapeRender();
            if (render != null) {
                if (render.isActive.get()) {
                    render.onSurfaceChanged(owner, gl, width, height);
                } else {
                    SurfaceCreatedCache cache = owner.createdCache;
                    if (cache != null) {
                        render.onSurfaceCreated(owner, cache.GL, cache.config);
                        render.onSurfaceChanged(owner, gl, width, height);
                    }
                }
            }
            owner.sizeCache = new SurfaceSizeCache(gl, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            BigEyeShapeRender render = owner.getShapeRender();
            if (render != null) {
                if (render.isActive.get()) {
                    render.onDrawFrame(owner, gl);
                } else {
                    SurfaceCreatedCache cache = owner.createdCache;
                    if (cache != null) {
                        render.onSurfaceCreated(owner, cache.GL, cache.config);
                        SurfaceSizeCache sizeCache = owner.sizeCache;
                        if (sizeCache != null) {
                            render.onSurfaceChanged(owner, sizeCache.gl, sizeCache.width, sizeCache.height);
                        }
                        render.onDrawFrame(owner, gl);
                    }
                }
            }
        }
    }

    private static class SurfaceCreatedCache {
        public GL10 GL;
        public EGLConfig config;

        public SurfaceCreatedCache(GL10 GL, EGLConfig config) {
            this.GL = GL;
            this.config = config;
        }
    }

    private static class SurfaceSizeCache {
        public GL10 gl;
        public int width;
        public int height;


        public SurfaceSizeCache(GL10 gl, int width, int height) {
            this.gl = gl;
            this.width = width;
            this.height = height;
        }
    }
}
