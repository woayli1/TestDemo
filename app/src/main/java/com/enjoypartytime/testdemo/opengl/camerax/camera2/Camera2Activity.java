package com.enjoypartytime.testdemo.opengl.camerax.camera2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.camerax.camera2.adapter.CameraAdapter;
import com.enjoypartytime.testdemo.opengl.camerax.camera2.view.ResizeAbleSurfaceView;
import com.enjoypartytime.testdemo.opengl.camerax.view.TouchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/1/3
 */
public class Camera2Activity extends AppCompatActivity {

    private ResizeAbleSurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private SurfaceHolder.Callback surfaceCallback;

    private Size previewSize;//图片尺寸
    private ImageReader imageReader;//接受图片数据

    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder previewBuilder;
    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private String currentCameraId;

    private float maxZoom = 10;
    private float minZoom = 1;
    private float tmpScale = 0;

    /**
     * 屏幕宽度
     */
    private int screenW;
    /**
     * 屏幕宽度
     */
    private int screenH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_2);

        surfaceView = findViewById(R.id.surface_view);
        TouchView mTouchView = findViewById(R.id.touch_view);

        getDisplayMetrics();

        mTouchView.setScaleListener(new TouchView.ScaleListener() {
            @Override
            public void onScale(float scale) {
                setZoom(scale);
            }

            @Override
            public void onFocus(float x, float y) {
                setFocus(x, y);
            }
        });

        surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

                previewSize = new Size(holder.getSurfaceFrame().width(), holder.getSurfaceFrame()
                        .height());
                switchCamera("0");

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
                closeCamera();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        //获取相机管理
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        List<String> cameraIdList = getCameraIdList();
        //切换摄像头
        CameraAdapter adapter = new CameraAdapter(getApplicationContext(), cameraIdList, this::switchCamera);
        recyclerView.setAdapter(adapter);

        if (surfaceHolder == null) {
            surfaceHolder = surfaceView.getHolder();
        }
        surfaceHolder.addCallback(surfaceCallback);
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

    /**
     * 关闭相机
     */
    private void closeCamera() {
        //关闭相机
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
        //关闭拍照处理器
        if (imageReader != null) {
            imageReader.close();
            imageReader = null;
        }

        currentCameraId = null;
        surfaceHolder.removeCallback(surfaceCallback);
    }

    private void switchCamera(String cameraId) {
        if (cameraId.equals(currentCameraId)) {
            return;
        }

        currentCameraId = cameraId;
        if (cameraDevice != null) {
            cameraDevice.close();
            stateCallback.onClosed(cameraDevice);
        }

        setAndOpenCamera();
    }

    /**
     * 根据摄像头id获取摄像头属性类
     * 打开对应id的摄像头
     */
    private void setAndOpenCamera() {
        //获取摄像头属性描述
        CameraCharacteristics cameraCharacteristics;
        try {
            cameraCharacteristics = cameraManager.getCameraCharacteristics(currentCameraId);
//            cameraCharacteristics = cameraManager.getCameraCharacteristics(String.valueOf(CameraCharacteristics.LENS_FACING_FRONT));
            //获取支持的缩放
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                /*
                vivo x200 pro 镜头缩放范围
                  0 [0.67, 10.0]
                  1 [1.0, 4.0]
                  2 [1.0, 10.0]
                  3 [1.0, 25.0]
                  4 [1.0, 10.0]
                 */
                Range<Float> zoomRationRange = cameraCharacteristics.get(CameraCharacteristics.CONTROL_ZOOM_RATIO_RANGE);
                Log.d("最大缩放倍数", "zoomRationRange: " + zoomRationRange);
                if (zoomRationRange != null) {
                    minZoom = zoomRationRange.getLower();
                    maxZoom = zoomRationRange.getUpper();
                }
            } else {
                Float tmpfloat = cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
                if (tmpfloat != null) {
                    maxZoom = tmpfloat * 10;
                }
            }
            Log.d("最大缩放倍数", "switchCamera: " + maxZoom);
            //获取该摄像头支持输出的图片尺寸
//            StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            //根据屏幕尺寸即摄像头输出尺寸计算图片尺寸，或者直接选取最大的图片尺寸进行输出
            //初始化imageReader
            imageReader = ImageReader.newInstance(previewSize.getWidth(), previewSize.getHeight(), ImageFormat.JPEG, 2);
            //设置回调处理接受图片数据
            imageReader.setOnImageAvailableListener(reader -> {
                //发送数据进子线程处理
//                    handler.post(new ImageSaver(reader.acquireNextImage(), MainActivity.this));
            }, null);
            //打开相机，先检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //打开摄像头
            cameraManager.openCamera(String.valueOf(currentCameraId), stateCallback, null);
        } catch (CameraAccessException | NullPointerException ignore) {

        }
    }

    /**
     * 打开相机后的状态回调，获取CameraDevice对象
     */
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            //开启预览
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            cameraDevice = null;
            finish();
        }
    };

    /**
     * VIVO x200pro
     * 0 后置默认镜头
     * 1 前置默认镜头
     * 2 后置主镜头
     * 3 后置蔡司镜头
     * 4 后置广角镜头
     */
    private List<String> getCameraIdList() {
        List<String> resList = new ArrayList<>();
        // Camera2

        try {
            String[] cameraIdList = cameraManager.getCameraIdList();
            resList.addAll(List.of(cameraIdList));
            LogUtils.d("cameraIdList=" + GsonUtils.toJson(cameraIdList));

            for (String id : cameraIdList) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                    Set<String> physicalCameraIds = cameraCharacteristics.getPhysicalCameraIds();
                    resList.addAll(physicalCameraIds);
                    LogUtils.d("cameraIdList:" + id + ",physicalCameraIds=" + GsonUtils.toJson(physicalCameraIds));
//                    }
                }
            }

        } catch (CameraAccessException | NullPointerException exception) {
            throw new RuntimeException(exception);
        }
        return resList;
    }

    /**
     * 开启预览的方法
     */
    public void startPreview() {
        try {
            //首先需要构建预览请求
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //设置预览输出界面
            previewBuilder.addTarget(surfaceHolder.getSurface());
            //获取缩放倍数
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Float aFloat = previewBuilder.get(CaptureRequest.CONTROL_ZOOM_RATIO);
                if (aFloat != null) {
                    tmpScale = aFloat;
                }
            }
            //创建相机的会话Session
            cameraDevice.createCaptureSession(Arrays.asList(surfaceHolder.getSurface(), imageReader.getSurface()), statePreviewCallback, null);
        } catch (CameraAccessException ignore) {

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
            //设置自动对焦
            previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //发送预览请求
            try {
                cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, null);
            } catch (CameraAccessException ignore) {

            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            //开启失败时，关闭会话
            session.close();
            cameraCaptureSession = null;
            if (cameraDevice != null) {
                cameraDevice.close();
                cameraDevice = null;
            }

        }
    };

    /**
     * 设置缩放的方法
     */
    public void setZoom(float scale) {

        if (scale > 1) {
            tmpScale = tmpScale + 0.03f;
            tmpScale = Math.min(tmpScale, maxZoom);
        } else {
            tmpScale = tmpScale - 0.05f;
            tmpScale = Math.max(tmpScale, minZoom);
        }
        LogUtils.d("tmpScale=" + tmpScale);

        try {
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
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
            cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, null);
        } catch (CameraAccessException | NullPointerException ignore) {

        }
    }

    /**
     * 获取屏幕尺寸
     */
    private void getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LogUtils.d("init: 屏幕分辨率 metrics : " + metrics.widthPixels + " " + metrics.heightPixels);

        screenW = metrics.widthPixels;
        screenH = metrics.widthPixels;
    }

    /**
     * 对焦
     *
     * @param x float
     * @param y float
     */
    public void setFocus(float x, float y) {

        int realPreviewWidth = 4000;
        int realPreviewHeight = 3000;

        float focusX = (float) realPreviewWidth / screenW * x;
        float focusY = (float) realPreviewHeight / screenH * (y + 112 * 2.54f);

        try {

            float cutDx = 0;
            Rect cropRegion = previewBuilder.get(CaptureRequest.SCALER_CROP_REGION);
            if (cropRegion != null) {
                LogUtils.d("init: cropRegion.height() " + cropRegion.height());
                cutDx = (cropRegion.height() - 1440) / 2.0f;
            }

            Rect rect1 = new Rect();
            rect1.left = (int) (focusX);
            rect1.top = (int) (focusY + cutDx);
            rect1.right = (int) (focusX + 50);
            rect1.bottom = (int) (focusY + cutDx + 50);

            previewBuilder.addTarget(surfaceHolder.getSurface());
            previewBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect1, 1000)});
            previewBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect1, 1000)});
            previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            previewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
            previewBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_START);
            cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, null);

        } catch (CameraAccessException ignore) {

        }
    }
}