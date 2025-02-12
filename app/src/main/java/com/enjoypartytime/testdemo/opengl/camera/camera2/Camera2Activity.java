package com.enjoypartytime.testdemo.opengl.camera.camera2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.SurfaceHolder;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.camera.camera2.adapter.CameraAdapter;
import com.enjoypartytime.testdemo.opengl.camera.camera2.view.ResizeAbleSurfaceView;
import com.enjoypartytime.testdemo.opengl.camera.view.TouchView;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/1/3
 */
public class Camera2Activity extends AppCompatActivity {

    private TextView tvRatio;
    private TextView tvFps;

    private ResizeAbleSurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private SurfaceHolder.Callback surfaceCallback;

//    private Size previewSize;//图片尺寸
//    private ImageReader imageReader;//接受图片数据

    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder previewBuilder;
    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private String currentCameraId;

    private float maxZoom = 10;
    private float minZoom = 1;
    private float tmpScale = 0;

    private List<Range<Integer>> rangeList;
    private Integer rangeSize = 0;
    private Size[] previewSizes;
    private Integer previewSize = 0;

    private boolean isStabilization = false;

    /**
     * 屏幕宽度
     */
    private int screenW;
    /**
     * 屏幕宽度
     */
    private int screenH;

    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_2);

        surfaceView = findViewById(R.id.surface_view);
        TouchView mTouchView = findViewById(R.id.touch_view);
        tvRatio = findViewById(R.id.tv_ratio);
        tvFps = findViewById(R.id.tv_fps);
        TextView tvStabilization = findViewById(R.id.tv_stabilization);

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

        tvRatio.setOnClickListener(v -> {
            String[] strings = new String[previewSizes.length];
            for (int i = 0; i < previewSizes.length; i++) {
                strings[i] = previewSizes[i].getWidth() + "x" + previewSizes[i].getHeight();
            }

            new XPopup.Builder(Camera2Activity.this).isViewMode(true)
                    .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                    .maxHeight(1200)
                    .asBottomList("分辨率", strings, null, previewSize, (position, text) -> {
                        tvRatio.setText(text);
                        setPreviewSize(position);
                    }).show();
        });

        tvFps.setOnClickListener(v -> {
            String[] strings = new String[rangeList.size()];
            for (int i = 0; i < rangeList.size(); i++) {
                strings[i] = rangeList.get(i).toString();
            }

            new XPopup.Builder(Camera2Activity.this).isViewMode(true)
                    .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                    .maxHeight(1200)
                    .asBottomList("FPS", strings, null, rangeSize, (position, text) -> {
                        tvFps.setText(String.format("FPS：%s", text));
                        setRangeSize(position);
                    }).show();
        });

        tvStabilization.setOnClickListener(v -> {
            if (isStabilization) {
                isStabilization = false;
                tvStabilization.setText("防抖：关");
            } else {
                isStabilization = true;
                tvStabilization.setText("防抖：开");
            }
            startPreview();
        });

        surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

//                previewSize = new Size(holder.getSurfaceFrame().width(), holder.getSurfaceFrame()
//                        .height());
                switchCamera("0");
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

                LogUtils.d("width=" + width + ",height=" + height);
//                //解决预览拉升
//                if (height > width) {
//                    //正常情况，竖屏
//                    float justH = width * 4.f / 3;
//                    //设置View在水平方向的缩放比例,保证宽高比为3:4
//                    surfaceView.setScaleX(height / justH);
//                } else {
//                    float justW = height * 4.f / 3;
//                    surfaceView.setScaleY(width / justW);
//                }

                startPreview();
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
//        if (imageReader != null) {
//            imageReader.close();
//            imageReader = null;
//        }

        currentCameraId = null;
        surfaceHolder.removeCallback(surfaceCallback);

        stopBackgroundThread();
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException exception) {
            LogUtils.e(exception);
        }
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
        new Handler(Looper.getMainLooper()).post(() -> {
            mBackgroundThread = new HandlerThread("CameraBackground");
            mBackgroundThread.start();
            mBackgroundHandler = new Handler(mBackgroundThread.getLooper());

            try {
                //获取摄像头属性描述
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(currentCameraId);
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

                previewSizes = getMaxSize(currentCameraId);
                rangeList = getFPSRanges(currentCameraId);

                String ration = previewSizes[previewSize].getWidth() + "x" + previewSizes[previewSize].getHeight();

                rangeSize = rangeList.size() - 1;
                String fps = rangeList.get(rangeSize).toString();

                runOnUiThread(() -> {
                    tvRatio.setText(ration);
                    tvFps.setText(String.format("FPS：%s", fps));
                });

                setPreviewSize(previewSize);

                //获取该摄像头支持输出的图片尺寸
//            StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                //根据屏幕尺寸即摄像头输出尺寸计算图片尺寸，或者直接选取最大的图片尺寸进行输出
                //初始化imageReader
//            imageReader = ImageReader.newInstance(previewSize.getWidth(), previewSize.getHeight(), ImageFormat.JPEG, 2);
                //设置回调处理接受图片数据
//            imageReader.setOnImageAvailableListener(reader -> {
                //发送数据进子线程处理
//                    handler.post(new ImageSaver(reader.acquireNextImage(), MainActivity.this));
//            }, null);
                //打开相机，先检查权限
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //打开摄像头
                cameraManager.openCamera(currentCameraId, stateCallback, mBackgroundHandler);
            } catch (CameraAccessException | NullPointerException ignore) {

            }
        });
    }

    /**
     * 打开相机后的状态回调，获取CameraDevice对象
     */
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            //开启预览
            runOnUiThread(() -> startPreview());
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
                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map != null) {
                    Size[] previewSizes = map.getOutputSizes(SurfaceTexture.class);
                    LogUtils.d("cameraIdList=" + id + ",,,previewSizes=" + Arrays.asList(previewSizes));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                        Set<String> physicalCameraIds = cameraCharacteristics.getPhysicalCameraIds();
                        resList.addAll(physicalCameraIds);
                        LogUtils.d("cameraIdList:" + id + ",physicalCameraIds=" + GsonUtils.toJson(physicalCameraIds));
//                    }
                    }
                }
            }

        } catch (CameraAccessException | NullPointerException exception) {
            throw new RuntimeException(exception);
        }
        return resList;
    }

    private List<Range<Integer>> getFPSRanges(String cameraId) {
        try {
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
            Range<Integer>[] ranges = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
//            LogUtils.d("cameraIdList=" + cameraId + ",,,ranges=" + Arrays.asList(ranges));
            if (ranges == null) {
                return Collections.singletonList(new Range<>(25, 25));
            } else {
                return Arrays.asList(ranges);
            }
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private Size[] getMaxSize(String cameraId) {
        try {
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (map == null) {
                return new Size[]{new Size(1920, 1440)};
            } else {
                return map.getOutputSizes(SurfaceTexture.class);
            }
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 开启预览的方法
     */
    public void startPreview() {

        if (cameraDevice == null) {
            return;
        }

        try {

            //首先需要构建预览请求
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //设置自动对焦
            previewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //设置预览输出界面
            previewBuilder.addTarget(surfaceHolder.getSurface());
            //设置FPS
            previewBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, rangeList.get(rangeSize));
            //获取缩放倍数
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Float aFloat = previewBuilder.get(CaptureRequest.CONTROL_ZOOM_RATIO);
                if (aFloat != null) {
                    tmpScale = aFloat;
                }
            }

            //设置防抖
            //设置EIS电子防抖
            previewBuilder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, isStabilization ? CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_ON : CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_OFF);
            //设置OIS光学稳定器防抖
            previewBuilder.set(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, isStabilization ? CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_ON : CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_OFF);

            //创建相机的会话Session
            cameraDevice.createCaptureSession(Collections.singletonList(surfaceHolder.getSurface()), statePreviewCallback, mBackgroundHandler);
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
            //发送预览请求
            try {
                cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, mBackgroundHandler);
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
        if (cameraCaptureSession == null || previewBuilder == null) {
            return;
        }

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
            cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, mBackgroundHandler);
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
        if (cameraCaptureSession == null || previewBuilder == null) {
            return;
        }

        int realPreviewWidth = 4000;
        int realPreviewHeight = 3000;

        float focusX = (float) realPreviewWidth / screenW * x;
        float focusY = (float) realPreviewHeight / screenH * (y + 112 * 2.54f);

        try {

            float cutDx = 0;
            Rect cropRegion = previewBuilder.get(CaptureRequest.SCALER_CROP_REGION);
            if (cropRegion != null) {
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
            cameraCaptureSession.setRepeatingRequest(previewBuilder.build(), null, mBackgroundHandler);

        } catch (CameraAccessException ignore) {

        }
    }

    public void setRangeSize(Integer rangeSize) {
        this.rangeSize = rangeSize;
        startPreview();
    }

    public void setPreviewSize(Integer previewSize) {
        this.previewSize = previewSize;

        //设置长宽
        Size viewSize = previewSizes[previewSize];
        surfaceView.resize(viewSize.getHeight(), viewSize.getWidth());
        surfaceHolder.setFixedSize(viewSize.getWidth(), viewSize.getHeight());
    }
}
