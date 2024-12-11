package com.enjoypartytime.testdemo.opengl.camerax;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.internal.Camera2PhysicalCameraInfoImpl;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraFilter;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.Identifier;
import androidx.camera.core.impl.LensFacingCameraFilter;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.camerax.view.TouchView;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Collections;
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
public class CameraXActivity extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
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

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));

    }

    private void zoom(float f) {
        if (ObjectUtils.isEmpty(camera)) {
            return;
        }

        if (f > 1) {
            tmpScale = tmpScale + 0.005f;
            tmpScale = Math.min(tmpScale, 1);
        } else {
            tmpScale = tmpScale - 0.005f;
            tmpScale = Math.max(tmpScale, 0);
        }

        camera.getCameraControl().setLinearZoom(tmpScale);
    }

    private void focus(float x, float y) {
        MeteringPointFactory factory = new SurfaceOrientedMeteringPointFactory(previewView.getWidth(), previewView.getHeight());
        MeteringPoint point = factory.createPoint(x, y);
        FocusMeteringAction action = new FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                .setAutoCancelDuration(3, TimeUnit.SECONDS).build();
        CameraControl cameraControl = camera.getCameraControl();
        cameraControl.startFocusAndMetering(action);
    }


    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);

        ImageCapture imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(previewView.getDisplay().getRotation())
                        .build();

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        LogUtils.d("cameraIdList cameraSelector.physicalCameraId=" + cameraSelector.getPhysicalCameraId());

        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.unbindAll();
        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
        getCameraList();

    }

    private void getCameraList() {
        Set<CameraInfo> cameraInfos = camera.getCameraInfo().getPhysicalCameraInfos();
        LogUtils.d("cameraIdList cameraInfos.size=" + cameraInfos.size());

//        for (CameraInfo cameraInfo : cameraInfos) {
//            LogUtils.d("cameraIdList cameraInfo.size=" + cameraInfo.get);
//        }


//        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//
//        String[] cameraIdList = null;
//        try {
//            cameraIdList = manager.getCameraIdList();
//            LogUtils.d("cameraIdList=" + GsonUtils.toJson(cameraIdList));
//
//            for (String id : cameraIdList) {
//                CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(id);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
//                        Set<String> physicalCameraIds = cameraCharacteristics.getPhysicalCameraIds();
//                        LogUtils.d("cameraIdList:" + id + ",physicalCameraIds=" + GsonUtils.toJson(physicalCameraIds));
//                    }
//                }
//            }
//
//        } catch (CameraAccessException | NullPointerException exception) {
//            throw new RuntimeException(exception);
//        }


    }
}
