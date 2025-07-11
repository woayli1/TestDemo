package com.enjoypartytime.testdemo.opengl.camera.cameraXFilter;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Process;
import android.util.AttributeSet;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/27
 */
public class CameraXFilterView extends GLSurfaceView {

    private boolean isSuccessOpenCamera = true;

    private CameraXFilterRenderer cameraXFilterRenderer;

    public CameraXFilterView(Context context) {
        super(context);
    }

    public CameraXFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraXFilterRenderer.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraXFilterRenderer.onPause();
    }

    private final CameraXFilterRenderer.Callback mCallback = new CameraXFilterRenderer.Callback() {
        @Override
        public void onSurfaceChanged() {
            setupCamera();
        }

        @Override
        public void onFrameAvailable() {
            requestRender();
        }
    };

    private void init() {
        setEGLContextClientVersion(3);

        cameraXFilterRenderer = new CameraXFilterRenderer(getContext(), mCallback);
        setRenderer(cameraXFilterRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void setFilterType(int filterType) {
        if (isSuccessOpenCamera) {
            cameraXFilterRenderer.setFilterType(filterType);
        }
    }

    //相机处理
    private void setupCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(getContext());
        Runnable runnable = () -> {
            Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);
            try {
                ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(cameraXFilterRenderer);
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle((LifecycleOwner) getContext(), CameraSelector.DEFAULT_BACK_CAMERA, preview);
                isSuccessOpenCamera = true;
            } catch (ExecutionException | InterruptedException | CancellationException e) {
                ToastUtils.showShort("相机打开错误");
                LogUtils.e(e);
                cameraProviderListenableFuture.cancel(true);
                isSuccessOpenCamera = false;
            }
        };
        cameraProviderListenableFuture.addListener(runnable, ContextCompat.getMainExecutor(getContext()));
    }

}
