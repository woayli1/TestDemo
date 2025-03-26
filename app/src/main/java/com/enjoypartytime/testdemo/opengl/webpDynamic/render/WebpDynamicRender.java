package com.enjoypartytime.testdemo.opengl.webpDynamic.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.enjoypartytime.testdemo.opengl.webpDynamic.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Queue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.util.Rotation;
import jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/26
 */
public class WebpDynamicRender implements GLSurfaceView.Renderer {

    public static final float[] CUBE = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
    };

    private static final float[] TEX_VERTEX = {   // in clockwise order:
            1f, 0,  // bottom right
            0, 0,  // bottom left
            0, 1f,  // top left
            1f, 1f,  // top right
    };

    private final FloatBuffer mVertexBuffer;
    private final FloatBuffer mTexVertexBuffer;

    private int mTexName = -1;

    private Drawable drawable;
    private Bitmap cacheBitmap;

    private Rotation rotation;
    private int contentWidth;
    private int contentHeight;
    private int width;
    private int height;

    private boolean flipHorizontal;
    private boolean flipVertical;

    private boolean shouldChangeRespect;
    private final GPUImage.ScaleType scaleType = GPUImage.ScaleType.CENTER_CROP;

    private GPUImageFilter filter;

    private final Queue<Runnable> runOnDraw;

    public WebpDynamicRender() {
        this.filter = new GPUImageFilter();
        runOnDraw = new LinkedList<>();

        mVertexBuffer = ByteBuffer.allocateDirect(CUBE.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(CUBE);
        mVertexBuffer.position(0);

        mTexVertexBuffer = ByteBuffer.allocateDirect(TEX_VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(TEX_VERTEX);
        mTexVertexBuffer.position(0);

        setRotation(Rotation.NORMAL, false, false);
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setContentSize(int width, int height) {
        mTexName = -1;
        if (cacheBitmap != null && width == this.contentWidth && this.contentHeight == height) {
            return;
        }

        if (cacheBitmap != null) {
            cacheBitmap.recycle();
        }

        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.contentWidth = width;
        this.contentHeight = height;
        shouldChangeRespect = true;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        filter.ifNeedInit();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        this.width = width;
        this.height = height;

        GLES20.glViewport(0, 0, width, height);
        GLES20.glUseProgram(filter.getProgram());
        filter.onOutputSizeChanged(width, height);

        shouldChangeRespect = true;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        if (cacheBitmap != null) {
            cacheBitmap.eraseColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(cacheBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            if (drawable != null) {
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            }

            if (shouldChangeRespect) {
                adjustImageScaling();
                shouldChangeRespect = false;
            }

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            mTexName = Util.loadTexture(cacheBitmap, mTexName, false);
            runAll(runOnDraw);
            filter.onDraw(mTexName, mVertexBuffer, mTexVertexBuffer);

        } else {
            Log.e("Null", "Content not ready...");
        }

    }

    private void adjustImageScaling() {
        float outputWidth = this.contentWidth;
        float outputHeight = this.contentHeight;
        if (rotation == Rotation.ROTATION_270 || rotation == Rotation.ROTATION_90) {
            outputWidth = this.contentHeight;
            outputHeight = this.contentWidth;
        }

        float ratio1 = outputWidth / width;
        float ratio2 = outputHeight / height;
        float ratioMax = Math.max(ratio1, ratio2);
        int imageWidthNew = Math.round(width * ratioMax);
        int imageHeightNew = Math.round(height * ratioMax);

        float ratioWidth = imageWidthNew / outputWidth;
        float ratioHeight = imageHeightNew / outputHeight;

        float[] cube = CUBE;
        float[] textureCords = TextureRotationUtil.getRotation(rotation, flipHorizontal, flipVertical);
        if (scaleType == GPUImage.ScaleType.CENTER_CROP) {
            float distHorizontal = (1 - 1 / ratioWidth) / 2;
            float distVertical = (1 - 1 / ratioHeight) / 2;
            textureCords = new float[]{
                    addDistance(textureCords[0], distHorizontal), addDistance(textureCords[1], distVertical),
                    addDistance(textureCords[2], distHorizontal), addDistance(textureCords[3], distVertical),
                    addDistance(textureCords[4], distHorizontal), addDistance(textureCords[5], distVertical),
                    addDistance(textureCords[6], distHorizontal), addDistance(textureCords[7], distVertical),
            };
        } else {
            cube = new float[]{
                    CUBE[0] / ratioHeight, CUBE[1] / ratioWidth,
                    CUBE[2] / ratioHeight, CUBE[3] / ratioWidth,
                    CUBE[4] / ratioHeight, CUBE[5] / ratioWidth,
                    CUBE[6] / ratioHeight, CUBE[7] / ratioWidth,
            };
        }

        mVertexBuffer.clear();
        mVertexBuffer.put(cube).position(0);
        mTexVertexBuffer.clear();
        mTexVertexBuffer.put(textureCords).position(0);
    }

    private float addDistance(float coordinate, float distance) {
        return coordinate == 0.0f ? distance : 1 - distance;
    }

    public void setRotation(final Rotation rotation,
                            final boolean flipHorizontal, final boolean flipVertical) {
        this.flipHorizontal = flipHorizontal;
        this.flipVertical = flipVertical;
        setRotation(rotation);
    }

    public void setRotation(final Rotation rotation) {
        this.rotation = rotation;
        adjustImageScaling();
    }

    public void setFilter(final GPUImageFilter filter) {
        runOnDraw(() -> {
            GPUImageFilter oldFilter = WebpDynamicRender.this.filter;
            WebpDynamicRender.this.filter = filter;
            if (oldFilter != null) {
                oldFilter.destroy();
            }
            WebpDynamicRender.this.filter.ifNeedInit();
            GLES20.glUseProgram(WebpDynamicRender.this.filter.getProgram());
            WebpDynamicRender.this.filter.onOutputSizeChanged(width, height);
        });
    }

    protected void runOnDraw(final Runnable runnable) {
        synchronized (runOnDraw) {
            runOnDraw.add(runnable);
        }
    }

    private void runAll(Queue<Runnable> queue) {
        synchronized (queue) {
            while (!queue.isEmpty()) {
                queue.poll().run();
            }
        }
    }
}