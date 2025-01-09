package com.enjoypartytime.testdemo.opengl.camera.bigEye;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.utils.ImageUtil;
import com.enjoypartytime.testdemo.utils.bigeye.FaceData;
import com.enjoypartytime.testdemo.utils.bigeye.ImageData;
import com.enjoypartytime.testdemo.utils.bigeye.ImageType;
import com.enjoypartytime.testdemo.utils.bigeye.Point;
import com.enjoypartytime.testdemo.utils.bigeye.ImageUtilsKt;
import com.google.common.util.concurrent.ListenableFuture;
import com.tenginekit.engine.core.ImageConfig;
import com.tenginekit.engine.core.SdkConfig;
import com.tenginekit.engine.core.TengineKitSdk;
import com.tenginekit.engine.face.Face;
import com.tenginekit.engine.face.FaceConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import kotlinx.coroutines.Dispatchers;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/6
 */
public class BigEyeActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    private BigEyeOpenGLView imgGlSurfaceView;
    private BigEyeRenderer bigEyeRenderer;

    private boolean isCamera = true;

    private long timestamp;
    private ImageData imageData;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider cameraProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_eye);

        TextView tvClose = findViewById(R.id.tv_close);
        TextView tvCamera = findViewById(R.id.tv_camera);
        TextView tvChoose = findViewById(R.id.tv_choose);
        imgGlSurfaceView = findViewById(R.id.img_gl_surface_view);
        SeekBar seekBar = findViewById(R.id.seekbar);
        TextView tvStrength = findViewById(R.id.tv_strength);

        tvClose.setOnClickListener(view -> finish());
        tvCamera.setOnClickListener(v -> {
            if (!isCamera) {
                isCamera = true;
                bigEyeRenderer.setCamera(true);
                //重新开启摄像头
                bindPreview(cameraProvider);
            }
        });
        tvChoose.setOnClickListener(view -> requestStoragePermission());

        imgGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        imgGlSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        imgGlSurfaceView.setZOrderOnTop(true);

        bigEyeRenderer = new BigEyeRenderer();
        imgGlSurfaceView.setShapeRender(bigEyeRenderer);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int strength, boolean b) {
                String sth = "强度：" + strength;
                tvStrength.setText(sth);
                bigEyeRenderer.setEnlargeEyesStrength(strength);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        initFaceTengine();

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
    protected void onDestroy() {
        super.onDestroy();
        releaseFaceTengine();
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //android 13及以上
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            } else {
                // 权限已经被授予
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                // 权限已经被授予
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ActivityUtils.startActivityForResult(BigEyeActivity.this, intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else {
            ToastUtils.showShort("权限被拒绝，请手动打开");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imgUri = data.getData();
                if (imgUri != null) {
                    if (isCamera) {
                        //关闭摄像头
                        cameraProvider.unbindAll();
                    }
                    isCamera = false;
                    bigEyeRenderer.setCamera(false);
                    openImage(imgUri);
                }
            }
        }

    }

    //选择图片
    private void openImage(Uri imgUri) {
        timestamp = System.currentTimeMillis();

        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageData = new ImageData(ImageUtil.bitmapToRgba(bitmap), bitmap.getWidth(), bitmap.getHeight(), 0, ImageType.IMAGE, null, timestamp);
        onImageData();
    }

    private void onImageData() {

        imgGlSurfaceView.queueEvent(() -> {
            FaceConfig faceConfig = new FaceConfig();
            faceConfig.setDetect(true);
            faceConfig.setLandmark2d(true);
            faceConfig.setAttribute(true);
            faceConfig.setEyeIris(true);
            faceConfig.setMaxFaceNum(1);

            ImageConfig imageConfig = BigEyeActivity.this.getImageConfig();

            Face[] faces = TengineKitSdk.getInstance().detectFace(imageConfig, faceConfig);
            Face face = null;
            if (faces != null) {
                face = faces[0];
            }

            if (face != null) {
                float[] landmark = face.landmark;
                Point[] check = BigEyeActivity.this.getCameraPoints(landmark, 0, 69);
                Point[] leftEyeBrow = BigEyeActivity.this.getCameraPoints(landmark, 69, 16);
                Point[] rightEyeBrow = BigEyeActivity.this.getCameraPoints(landmark, 85, 16);
                Point[] leftEye = BigEyeActivity.this.getCameraPoints(landmark, 101, 16);
                Point[] rightEye = BigEyeActivity.this.getCameraPoints(landmark, 117, 16);
                Point[] nose = BigEyeActivity.this.getCameraPoints(landmark, 133, 47);
                Point[] upLip = BigEyeActivity.this.getCameraPoints(landmark, 180, 16);
                Point[] downLip = BigEyeActivity.this.getCameraPoints(landmark, 196, 16);

                Point[] leftEyeIris = new Point[5];
                for (int i = 0; i < 5; i++) {
                    int x = i * 3;
                    int y = x + 1;
                    leftEyeIris[i] = new Point(face.eyeIrisLeft[x], face.eyeIrisLeft[y]);
                }

                Point[] rightEyeIris = new Point[5];
                for (int i = 0; i < 5; i++) {
                    int x = i * 3;
                    int y = x + 1;
                    rightEyeIris[i] = new Point(face.eyeIrisRight[x], face.eyeIrisRight[y]);
                }

                Point[] leftEyeIrisFrame = new Point[15];
                for (int i = 0; i < 15; i++) {
                    int x = i * 3;
                    int y = x + 1;
                    leftEyeIrisFrame[i] = new Point(face.eyeLandMarkLeft[x], face.eyeLandMarkLeft[y]);
                }

                Point[] rightEyeIrisFrame = new Point[15];
                for (int i = 0; i < 15; i++) {
                    int x = i * 3;
                    int y = x + 1;
                    rightEyeIrisFrame[i] = new Point(face.eyeLandMarkRight[x], face.eyeLandMarkRight[y]);
                }

                Point[] faceFrame = new Point[]{new Point(face.x1, face.y1), new Point(face.x2, face.y1), new Point(face.x2, face.y2), new Point(face.x1, face.y2)};
                FaceData faceData = new FaceData(timestamp, faceFrame, check, leftEyeBrow, rightEyeBrow, leftEye, rightEye, leftEyeIris, leftEyeIrisFrame, rightEyeIris, rightEyeIrisFrame, nose, upLip, downLip);

                bigEyeRenderer.faceDataReady(faceData);
            }

            bigEyeRenderer.cameraReady(imageData);
        });

    }

    @NonNull
    private ImageConfig getImageConfig() {
        ImageConfig imageConfig = new ImageConfig();
        imageConfig.setData(imageData.getImage());
        imageConfig.setDegree(imageData.getRotation());
        imageConfig.setMirror(false);
        imageConfig.setWidth(imageData.getWidth());
        imageConfig.setHeight(imageData.getHeight());

        if (imageData.getImageType() == ImageType.NV21) {
            // ImageType.NV21
            imageConfig.setFormat(ImageConfig.FaceImageFormat.YUV);
        } else {
            // ImageType.RGBA || ImageType.IMAGE
            imageConfig.setFormat(ImageConfig.FaceImageFormat.RGBA);
        }
        return imageConfig;
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        if (ObjectUtils.isEmpty(imgGlSurfaceView)) {
            ToastUtils.showShort("相机初始化错误，请重新进入页面");
            finish();
            return;
        }

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setImageQueueDepth(1)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                .setBackgroundExecutor((Executor) Dispatchers.getIO()).build();

        //打开摄像头
        imageAnalysis.setAnalyzer((Executor) Dispatchers.getIO(), imageProxy -> {
            if (isCamera) {
                timestamp = System.currentTimeMillis();
                if (imageProxy.getFormat() == ImageFormat.YUV_420_888) {
                    // ImageFormat.YUV_420_888
                    byte[] yuv = ImageUtilsKt.yuv420888ToNv21(imageProxy);
                    int width = imageProxy.getCropRect().width();
                    int height = imageProxy.getCropRect().height();
                    imageData = new ImageData(yuv, width, height, imageProxy.getImageInfo()
                            .getRotationDegrees(), ImageType.NV21, imageProxy, timestamp);
                } else {
                    //  PixelFormat.RGBA_8888
                    int width = imageProxy.getWidth();
                    int height = imageProxy.getHeight();
                    byte[] rgba = new byte[width * height * 4];
                    ImageUtilsKt.toRgba(imageProxy, rgba);
                    imageData = new ImageData(rgba, width, height, imageProxy.getImageInfo()
                            .getRotationDegrees(), ImageType.RGBA, imageProxy, timestamp);
                }
                onImageData();
            }
        });

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();

        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis);
    }

    private void initFaceTengine() {
        File ModelDir = getApplicationContext().getFilesDir();
        List<String> modelNames;
        try {
            modelNames = Arrays.asList(getApplicationContext().getAssets().list("model"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String modelName : modelNames) {
            File modelFile = new File(ModelDir, modelName);
            if (!modelFile.exists()) {
                try {
                    boolean newFile = modelFile.createNewFile();
                    if (newFile) {
                        try (InputStream inputStream = getApplicationContext().getAssets()
                                .open("model/" + modelName); FileOutputStream fileOutputStream = new FileOutputStream(modelFile)) {

                            //定义存储空间
                            byte[] b = new byte[1024 * 5];
                            //开始读文件
                            int len;
                            while ((len = inputStream.read(b)) != -1) {
                                //将b中的数据写入到FileOutputStream对象中
                                fileOutputStream.write(b, 0, len);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        SdkConfig config = new SdkConfig();
        TengineKitSdk.getInstance().initSdk(ModelDir.toString(), config, getApplicationContext());
        TengineKitSdk.getInstance().initFaceDetect();
    }

    private void releaseFaceTengine() {
        TengineKitSdk.getInstance().releaseFaceDetect();
        TengineKitSdk.getInstance().release();
    }

    private Point[] getCameraPoints(float[] dataSource, int offset, int pointSize) {
        Point[] pointList = new Point[pointSize];
        for (int i = 0; i < pointSize; i++) {
            int x = (offset + i) * 2;
            int y = x + 1;
            pointList[i] = new Point(dataSource[x], dataSource[y]);
        }
        return pointList;
    }

}