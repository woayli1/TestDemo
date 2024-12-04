package com.enjoypartytime.testdemo.opengl.camerax;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceRequest;
import androidx.core.util.Consumer;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.opengl.camerax.filter.BaseCameraXFilter;
import com.enjoypartytime.testdemo.opengl.camerax.filter.CameraXFilterBlackWhite;
import com.enjoypartytime.testdemo.opengl.camerax.filter.CameraXFilterGray;
import com.enjoypartytime.testdemo.opengl.camerax.filter.CameraXFilterNegative;
import com.enjoypartytime.testdemo.opengl.camerax.filter.CameraXFilterNone;
import com.enjoypartytime.testdemo.opengl.camerax.filter.CameraXFilterX2;
import com.enjoypartytime.testdemo.opengl.camerax.filter.CameraXFilterY2;
import com.enjoypartytime.testdemo.utils.GLUtil;
import com.enjoypartytime.testdemo.utils.ShaderManager;

import java.util.concurrent.Executors;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/26
 */
public class CameraXRenderer implements GLSurfaceView.Renderer, Preview.SurfaceProvider, SurfaceTexture.OnFrameAvailableListener {

    private final Context mContext;
    private SurfaceTexture mCameraTexture;
    private BaseCameraXFilter mCameraXFilter;
    private final Callback mCallback;

    private final int[] textures = new int[1];
    private final float[] mtx = new float[16];

    private boolean isPause = false;
    private boolean isFilterChange = false;
    private int mFilterType = 1;

    public CameraXRenderer(Context context, Callback mCallback) {
        this.mContext = context;
        this.mCallback = mCallback;
    }

    private void init() {
        GLES30.glClearColor(0f, 0f, 0f, 1f);
        textures[0] = GLUtil.getOESTextureId();

        ShaderManager.init(mContext);
        mCameraTexture = new SurfaceTexture(textures[0]);

        //初始化
        updateFilterView();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        if (mCameraTexture == null) {
            init();
        }
        mCameraTexture.setDefaultBufferSize(width, height);

        if (mCallback != null) {
            mCallback.onSurfaceChanged();
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (mCameraTexture == null) {
            return;
        }

        /// glClear(...)填充屏幕使用的颜色，是最后一次调用glClearColor(...)的颜色
        GLES30.glClearColor(1f, 0f, 0f, 1f);
        /// 清空屏幕，擦书屏幕上的所有颜色，并用glClearColor(...)设置的颜色充满整个屏幕
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        mCameraTexture.updateTexImage();
        mCameraTexture.getTransformMatrix(mtx);

        if (isFilterChange) {
            updateFilterView();
        }

        mCameraXFilter.onDrawFrame(textures[0], mtx);
    }

    @Override
    public void onSurfaceRequested(@NonNull SurfaceRequest request) {
        if (mCameraTexture != null) {
            mCameraTexture.setOnFrameAvailableListener(this);
            mCameraTexture.setDefaultBufferSize(request.getResolution().getWidth(), request
                    .getResolution().getHeight());

            Surface surface = new Surface(mCameraTexture);
            Consumer<SurfaceRequest.Result> resultConsumer = result -> {
                if (isPause) {
                    surface.release();
                    mCameraTexture.release();
                }
            };

            request.provideSurface(surface, Executors.newSingleThreadExecutor(), resultConsumer);
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (mCallback != null) {
            mCallback.onFrameAvailable();
        }
    }

    private void updateFilterView() {
        LogUtils.d("updateFilterView");
        mCameraXFilter = null;
        switch (this.mFilterType) {
            case ShaderManager.CAMERA_SHADER_BASE:
                mCameraXFilter = new CameraXFilterNone();
                break;
            case ShaderManager.CAMERA_SHADER_X_2:
                mCameraXFilter = new CameraXFilterX2();
                break;
            case ShaderManager.CAMERA_SHADER_Y_2:
                mCameraXFilter = new CameraXFilterY2();
                break;
            case ShaderManager.CAMERA_SHADER_NEGATIVE:
                mCameraXFilter = new CameraXFilterNegative();
                break;
            case ShaderManager.CAMERA_SHADER_BLACK_WHITE:
                mCameraXFilter = new CameraXFilterBlackWhite();
                break;
            case ShaderManager.CAMERA_SHADER_GRAY:
                mCameraXFilter = new CameraXFilterGray();
                break;
            default:
                mCameraXFilter = new CameraXFilterNone();
                break;
        }
        isFilterChange = false;
    }

    public void setFilterType(int filterType) {
        if (this.mFilterType == filterType) {
            LogUtils.d("setType: this.mFilterType == mFilterType");
            return;
        }
        this.mFilterType = filterType;
        isFilterChange = true;
    }

    public void onPause() {
        isPause = true;
    }

    public void onResume() {
        isPause = false;
    }

    public interface Callback {

        void onSurfaceChanged();

        void onFrameAvailable();
    }
}
