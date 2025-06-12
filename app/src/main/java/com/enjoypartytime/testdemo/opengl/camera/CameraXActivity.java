package com.enjoypartytime.testdemo.opengl.camera;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.Camera2CameraInfoImpl;
import androidx.camera.camera2.internal.Camera2PhysicalCameraInfoImpl;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.impl.RestrictedCameraInfo;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.camera.view.TouchView;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/4
 * 基础CameraX照相机
 */
@ExperimentalCamera2Interop
@SuppressLint("RestrictedApi")
public class CameraXActivity extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider cameraProvider;
    private PreviewView previewView;

    private Camera camera;
    private float tmpScale = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        previewView = findViewById(R.id.preview_view);
        TouchView mTouchView = findViewById(R.id.touch_view);

        mTouchView.setScaleListener(new TouchView.ScaleListener() {
            @Override
            public void onScale(float scale) {
                zoom(scale);
            }

            @Override
            public void onFocus(float x, float y) {
                focus(x, y);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (cameraProvider != null) {
            cameraProvider.unbindAll();
        }
    }

    private void zoom(float f) {
        if (ObjectUtils.isEmpty(camera)) {
            return;
        }

        if (f > 1) {
            tmpScale = tmpScale + 0.01f;
            tmpScale = Math.min(tmpScale, 10);
        } else {
            tmpScale = tmpScale - 0.01f;
            tmpScale = Math.max(tmpScale, 0);
        }

        camera.getCameraControl().setLinearZoom(tmpScale);
    }

    private void focus(float x, float y) {
        if (ObjectUtils.isEmpty(camera)) {
            return;
        }

        MeteringPointFactory factory = new SurfaceOrientedMeteringPointFactory(previewView.getWidth(), previewView.getHeight());
        MeteringPoint point = factory.createPoint(x, y);
        FocusMeteringAction action = new FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                .setAutoCancelDuration(3, TimeUnit.SECONDS).build();
        CameraControl cameraControl = camera.getCameraControl();
        cameraControl.startFocusAndMetering(action);
    }


    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        if (ObjectUtils.isEmpty(previewView)) {
            ToastUtils.showShort("相机初始化错误，请重新进入页面");
            finish();
            return;
        }

        previewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);

        Preview preview = new Preview.Builder()
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageCapture imageCapture =
                new ImageCapture.Builder()
//                        .setTargetRotation(previewView.getDisplay().getRotation())
                        .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        LogUtils.d("cameraIdList cameraSelector.physicalCameraId=" + cameraSelector.getPhysicalCameraId());

        cameraProvider.unbindAll();
        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
        getCameraList();
    }

    private void getCameraList() {

        // CameraX
        Set<CameraInfo> cameraInfos = camera.getCameraInfo().getPhysicalCameraInfos();
        LogUtils.d("cameraIdList cameraInfos.size=" + cameraInfos.size());
        List<CameraInfo> cameraInfoList = new ArrayList<>(cameraInfos);
        for (CameraInfo cameraInfo : cameraInfoList) {
            LogUtils.d("cameraIdList cameraInfo.id=" + ((Camera2PhysicalCameraInfoImpl) cameraInfo).getCameraId());
        }

        // available
        List<List<CameraInfo>> availableConcurrentCameraInfos = cameraProvider.getAvailableConcurrentCameraInfos();
        LogUtils.d("cameraIdList availableConcurrentCameraInfos.size=" + availableConcurrentCameraInfos.size());
        for (List<CameraInfo> availableCameraInfos : availableConcurrentCameraInfos) {
            LogUtils.d("cameraIdList cameraInfos.size=" + availableCameraInfos.size());
            for (CameraInfo cameraInfo : availableCameraInfos) {
                LogUtils.d("cameraIdList cameraInfo.id=" + ((RestrictedCameraInfo) cameraInfo).getCameraId());
            }
        }

        List<CameraInfo> availableCameraInfos1 = cameraProvider.getAvailableCameraInfos();
        LogUtils.d("cameraIdList availableCameraInfos1.size=" + availableCameraInfos1.size());
        for (CameraInfo cameraInfo : availableCameraInfos1) {
            LogUtils.d("cameraIdList cameraInfo.id=" + ((Camera2CameraInfoImpl) cameraInfo).getCameraId());
        }
    }
}
