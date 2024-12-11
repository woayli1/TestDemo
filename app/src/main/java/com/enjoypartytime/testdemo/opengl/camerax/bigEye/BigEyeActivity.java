package com.enjoypartytime.testdemo.opengl.camerax.bigEye;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/6
 */
public class BigEyeActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    private GLSurfaceView imgGlSurfaceView;
    private BigEyeRenderer mosaicRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_eye);

        TextView tvClose = findViewById(R.id.tv_close);
        TextView tvChoose = findViewById(R.id.tv_choose);
        imgGlSurfaceView = findViewById(R.id.img_gl_surface_view);
        SeekBar seekBar = findViewById(R.id.seekbar);
        TextView tvStrength = findViewById(R.id.tv_strength);

        tvClose.setOnClickListener(view -> finish());
        tvChoose.setOnClickListener(view -> requestStoragePermission());

        imgGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        imgGlSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        imgGlSurfaceView.setZOrderOnTop(true);

        imgGlSurfaceView.setEGLContextClientVersion(3);

        mosaicRenderer = new BigEyeRenderer(getApplicationContext());
        imgGlSurfaceView.setRenderer(mosaicRenderer);
        imgGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        imgGlSurfaceView.requestRender();

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
                if (ObjectUtils.isEmpty(mosaicRenderer.getImgUri())) {
                    ToastUtils.showShort("请先选择图片");
                    return;
                }

                int strength = seekBar.getProgress();

                String sth = "强度：" + strength;
                tvStrength.setText(sth);
                mosaicRenderer.setStrength(strength);
                imgGlSurfaceView.requestRender();

            }
        });
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
                    mosaicRenderer.setImageURI(imgUri.toString());
                    imgGlSurfaceView.requestRender();
                }
            }
        }

    }
}