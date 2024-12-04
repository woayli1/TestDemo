package com.enjoypartytime.testdemo.opengl.camerax;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/27
 */
public class CameraXView extends GLSurfaceView {

    private CameraXRenderer cameraXRender;

    public CameraXView(Context context) {
        super(context);
    }

    public CameraXView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraXRender.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraXRender.onResume();
    }

    private final CameraXRenderer.Callback mCallback = new CameraXRenderer.Callback() {
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

        cameraXRender = new CameraXRenderer(getContext(), mCallback);
        setRenderer(cameraXRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void setFilterType(int filterType) {
        cameraXRender.setFilterType(filterType);
    }

    //相机处理
    private void setupCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(getContext());
        Runnable runnable = () -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(cameraXRender);
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle((LifecycleOwner) getContext(), CameraSelector.DEFAULT_BACK_CAMERA, preview);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        cameraProviderListenableFuture.addListener(runnable, ContextCompat.getMainExecutor(getContext()));
    }

}
