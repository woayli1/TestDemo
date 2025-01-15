package com.enjoypartytime.testdemo.opengl.camera.vivo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.camera.camera2.view.ResizeAbleSurfaceView;

import java.util.Collections;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/1/2
 * <p>
 * <p>
 * * VIVO x200pro
 * * 0 后置默认镜头 ZOOM_RATIO [1,10]
 * * 1 前置默认镜头 ZOOM_RATIO [1,4]
 * * 2 后置主镜头  ZOOM_RATIO [1,10]
 * * 3 后置蔡司镜头 ZOOM_RATIO [1,25]
 * * 4 后置广角镜头 ZOOM_RATIO [1,10]
 */
public class VivoCameraActivity extends AppCompatActivity {

    private ResizeAbleSurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private SurfaceHolder.Callback surfaceCallback;

    private SeekBar vivoSeekBar;

    private CameraManager cameraManager;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder previewBuilder;
    private CameraDevice cameraDevice;

    private String currentCameraId = "0";

    private TextView tvStrength;

    private float tmpScale = 0;
    private boolean isSwitch = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivo_camera);

        surfaceView = findViewById(R.id.surface_view);
        vivoSeekBar = findViewById(R.id.vivo_seekbar);
        tvStrength = findViewById(R.id.tv_strength);

        vivoSeekBar.setMax(1000);
        vivoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float tmpFloat = (float) (progress + 1) / 100;
                setZoom(tmpFloat);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

                initCamera();

                //解决预览拉升
                int height = surfaceView.getHeight();
                int width = surfaceView.getWidth();
                if (height > width) {
                    //正常情况，竖屏
                    float justH = width * 4.f / 3;
                    //设置View在水平方向的缩放比例,保证宽高比为3:4
                    surfaceView.setScaleX(height / justH);
                } else {
                    float justW = height * 4.f / 3;
                    surfaceView.setScaleY(width / justW);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                closeAllCamera();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        //获取相机管理
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        if (surfaceHolder == null) {
            surfaceHolder = surfaceView.getHolder();
        }
        surfaceHolder.addCallback(surfaceCallback);
        vivoSeekBar.setProgress(99);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
        if (previewBuilder != null) {
            previewBuilder = null;
        }
        if (cameraManager != null) {
            cameraManager = null;
        }
        if (surfaceHolder != null) {
            surfaceHolder = null;
        }
    }

    private void initCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
            stateCallback.onClosed(cameraDevice);
        }

        openCamera();
    }

    /**
     * 根据摄像头id获取摄像头属性类
     * 打开对应id的摄像头
     */
    private void openCamera() {
        try {
            //打开相机，先检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //打开摄像头
            cameraManager.openCamera(currentCameraId, stateCallback, null);
        } catch (CameraAccessException exception) {
            LogUtils.e(exception);
        }
    }

    /**
     * 打开相机后的状态回调，获取CameraDevice对象
     */
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            //初始化预览
            initPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
        }
    };

    /**
     * 初始化预览
     */
    public void initPreview() {
        try {
            //首先需要构建预览请求
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //设置预览输出界面
            previewBuilder.addTarget(surfaceHolder.getSurface());
            startPreview();
        } catch (CameraAccessException exception) {
            LogUtils.e(exception);
        }
    }

    private void startPreview() {
        //设置自动对焦
        previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

        //设置缩放
        previewBuilder.addTarget(surfaceHolder.getSurface());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            previewBuilder.set(CaptureRequest.CONTROL_ZOOM_RATIO, tmpScale);
        } else {
            //这里的Rect表示一个矩形区域，由四条边的坐标组成
            Rect cropRegion = previewBuilder.get(CaptureRequest.SCALER_CROP_REGION);
            if (cropRegion != null) {
                cropRegion.left = (int) (cropRegion.left / tmpScale);
                cropRegion.top = (int) (cropRegion.top / tmpScale);
                cropRegion.right = (int) (cropRegion.right / tmpScale);
                cropRegion.bottom = (int) (cropRegion.bottom / tmpScale);
                previewBuilder.set(CaptureRequest.SCALER_CROP_REGION, cropRegion);
            }
        }

        try {
            if (cameraCaptureSession == null) {
                cameraDevice.createCaptureSession(Collections.singletonList(surfaceHolder.getSurface()), statePreviewCallback, null);
            } else {
                cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, null);
            }
        } catch (CameraAccessException | IllegalStateException |
                 IllegalArgumentException exception) {
            LogUtils.e(exception);
        }

    }

    /**
     * 创建Session的状态回调
     */
    private final CameraCaptureSession.StateCallback statePreviewCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            //会话已经建立，可以开启预览了
            cameraCaptureSession = session;

            try {
                cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, null);
                isSwitch = false;
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            //开启失败时，关闭会话
            session.close();
            cameraCaptureSession = null;
            closeAllCamera();
        }
    };

    /**
     * 设置缩放的方法
     */
    public void setZoom(float scale) {

        if (scale < 3.7) {
            //主镜头
            switchCamera("0");
            if (scale < 1) {
                //广角镜头
                tmpScale = (float) (0.6 + (scale - 0) * (1 - 0.6));
            } else {
                tmpScale = (float) (1 + (scale - 1) / (3.7 - 1) * (4.5 - 1));
            }
        } else {
            //长焦镜头
            switchCamera("3");
            tmpScale = (float) (1 + (scale - 3.7) / (10 - 3.7) * (25 - 1));
        }

        LogUtils.d("setZoom=" + scale + ",,tmpScale=" + tmpScale);

        tvStrength.setText(String.format("放大倍数：%sx", scale));
    }

    private void switchCamera(String cameraId) {
        if (!cameraId.equals(currentCameraId)) {
            isSwitch = true;
            currentCameraId = cameraId;
            if (cameraDevice != null) {
                cameraDevice.close();
                stateCallback.onClosed(cameraDevice);
                cameraCaptureSession.close();
                cameraCaptureSession = null;
            }
            openCamera();
        } else {
            if (cameraDevice != null && !isSwitch) {
                startPreview();
            }
        }
    }

    /**
     * 关闭相机
     */
    private void closeAllCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }

        currentCameraId = "2";
        surfaceHolder.removeCallback(surfaceCallback);
    }

}
