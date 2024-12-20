package com.enjoypartytime.testdemo.opengl.camerax.bigEye;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.utils.bigeye.FaceData;
import com.enjoypartytime.testdemo.utils.bigeye.ImageData;
import com.enjoypartytime.testdemo.utils.bigeye.ImageType;
import com.enjoypartytime.testdemo.utils.bigeye.Point;
import com.enjoypartytime.testdemo.utils.bigeye.ImageUtilsKt;
import com.tenginekit.engine.core.ImageConfig;
import com.tenginekit.engine.core.SdkConfig;
import com.tenginekit.engine.core.TengineKitSdk;
import com.tenginekit.engine.face.Face;
import com.tenginekit.engine.face.FaceConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import kotlinx.coroutines.Dispatchers;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/6
 */
public class BigEyeActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    private BigEyeOpenGLView imgGlSurfaceView;
    private BigEyeRenderer mosaicRenderer;

    private long timestamp;
    private ImageData imageData;

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
        tvCamera.setOnClickListener(v -> ToastUtils.showShort("打开摄像头"));
        tvChoose.setOnClickListener(view -> requestStoragePermission());

        imgGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        imgGlSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        imgGlSurfaceView.setZOrderOnTop(true);

        mosaicRenderer = new BigEyeRenderer();
        imgGlSurfaceView.setShapeRender(mosaicRenderer);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int strength, boolean b) {
                String sth = "强度：" + strength;
                tvStrength.setText(sth);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (ObjectUtils.isEmpty(mosaicRenderer.getImgUri())) {
//                    ToastUtils.showShort("请先选择图片");
//                    return;
//                }
//
//                int strength = seekBar.getProgress();
//
//                String sth = "强度：" + strength;
//                tvStrength.setText(sth);
//                mosaicRenderer.setStrength(strength);
//                imgGlSurfaceView.requestRender();

            }
        });

        initFaceTengine();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setImageQueueDepth(1)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                .setBackgroundExecutor((Executor) Dispatchers.getIO()).build();

        imageAnalysis.setAnalyzer((Executor) Dispatchers.getIO(), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                timestamp = System.currentTimeMillis();
                if (imageProxy.getFormat() == ImageFormat.YUV_420_888) {
                    byte[] yuv = ImageUtilsKt.yuv420888ToNv21(imageProxy);
                    int width = imageProxy.getCropRect().width();
                    int height = imageProxy.getCropRect().height();
                    imageData = new ImageData(yuv, width, height, imageProxy.getImageInfo()
                            .getRotationDegrees(), ImageType.NV21, imageProxy, timestamp);
                } else if (imageProxy.getFormat() == PixelFormat.RGBA_8888) {
                    int width = imageProxy.getWidth();
                    int height = imageProxy.getHeight();
                    byte[] rgba = new byte[width * height * 4];
                    ImageUtilsKt.toRgba(imageProxy, rgba);
                    imageData = new ImageData(rgba, width, height, imageProxy.getImageInfo()
                            .getRotationDegrees(), ImageType.RGBA, imageProxy, timestamp);
                } else {
                    imageProxy.close();
                }
            }
        });

        imgGlSurfaceView.queueEvent(new Runnable() {
            @Override
            public void run() {
                FaceConfig faceConfig = new FaceConfig();
                faceConfig.setDetect(true);
                faceConfig.setLandmark2d(true);
                faceConfig.setAttribute(true);
                faceConfig.setEyeIris(true);
                faceConfig.setMaxFaceNum(1);

                ImageConfig imageConfig = getImageConfig();

                Face face = TengineKitSdk.getInstance().detectFace(imageConfig, faceConfig)[0];
                float[] landmark = face.landmark;
                Point[] check = getCameraPoints(landmark, 0, 69);
                Point[] leftEyeBrow = getCameraPoints(landmark, 69, 16);
                Point[] rightEyeBrow = getCameraPoints(landmark, 85, 16);
                Point[] leftEye = getCameraPoints(landmark, 101, 16);
                Point[] rightEye = getCameraPoints(landmark, 117, 16);
                Point[] nose = getCameraPoints(landmark, 133, 47);
                Point[] upLip = getCameraPoints(landmark, 180, 16);
                Point[] downLip = getCameraPoints(landmark, 196, 16);

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

                Point[] faceFrame = new Point[4];
                faceFrame[0] = new Point(face.x1, face.y1);
                faceFrame[1] = new Point(face.x2, face.y1);
                faceFrame[2] = new Point(face.x2, face.y2);
                faceFrame[3] = new Point(face.x1, face.y2);
                FaceData faceData = new FaceData(timestamp, faceFrame, check, leftEyeBrow, rightEyeBrow, leftEye, rightEye, leftEyeIris, rightEyeIris, leftEyeIrisFrame, rightEyeIrisFrame, nose, upLip, downLip);
//                mosaicRenderer.setFaceData(faceData);
                imgGlSurfaceView.requestRender();
            }
        });

//        mosaicRenderer.cameraReady(imageData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        imgGlSurfaceView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
//                    mosaicRenderer.setImageURI(imgUri.toString());
                    imgGlSurfaceView.requestRender();
                }
            }
        }

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
            imageConfig.setFormat(ImageConfig.FaceImageFormat.YUV);
        } else if (imageData.getImageType() == ImageType.RGBA) {
            imageConfig.setFormat(ImageConfig.FaceImageFormat.RGBA);
        }
        return imageConfig;
    }

    private void initFaceTengine() {
        File ModelDir = getApplicationContext().getFilesDir();
        List<String> modelNames = new ArrayList<>();
        try {
            modelNames = Arrays.asList(getApplicationContext().getAssets().list("model"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String modelName : modelNames) {
            File modelFile = new File(ModelDir, modelName);
            if (!modelFile.isFile()) {
                try {
                    boolean newFile = modelFile.createNewFile();
                    if (newFile) {
                        try (FileOutputStream fileOutputStream = new FileOutputStream(modelFile)) {
                            InputStream inputStream = getApplicationContext().getAssets()
                                    .open("model/" + modelName);
                            fileOutputStream.write(inputStream.read());
                            inputStream.close();
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