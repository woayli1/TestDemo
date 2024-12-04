package com.enjoypartytime.testdemo.opengl;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.brush.BrushActivity;
import com.enjoypartytime.testdemo.opengl.camerax.CameraXMainActivity;
import com.enjoypartytime.testdemo.opengl.live2d.Live2DActivity;
import com.enjoypartytime.testdemo.opengl.mosaic.MosaicActivity;
import com.enjoypartytime.testdemo.opengl.square.SquareActivity;
import com.enjoypartytime.testdemo.opengl.triangle.TriangleActivity;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/29
 */
public class OpenglActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);

        TextView tvStatus = findViewById(R.id.tv_status);
        TextView tvTips = findViewById(R.id.tv_tips);
        TextView tvTriangle = findViewById(R.id.tv_triangle);
        TextView tvSquare = findViewById(R.id.tv_square);
        TextView tvMosaic = findViewById(R.id.tv_mosaic);
        TextView tvBrush = findViewById(R.id.tv_brush);
        TextView tvLive2d = findViewById(R.id.tv_live_2d);
        TextView tvCameraX = findViewById(R.id.tv_camera_x);

        int glVersion = getGLESVersion();
        String version = "OpenGL ES 1.0";

        if (glVersion > 131072) {

            tvTips.setVisibility(View.GONE);

            tvTriangle.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, TriangleActivity.class);
                startActivity(intent);
            });

            tvSquare.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, SquareActivity.class);
                startActivity(intent);
            });

            tvMosaic.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, MosaicActivity.class);
                startActivity(intent);
            });

            tvBrush.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, BrushActivity.class);
                startActivity(intent);
            });

            tvLive2d.setOnClickListener(view -> {
                Intent intent = new Intent(OpenglActivity.this, Live2DActivity.class);
                startActivity(intent);
            });

            tvCameraX.setOnClickListener(view -> requestNeedPermissions());

            if (glVersion > 196609) {
                version = "OpenGL ES 3.2";
            } else if (glVersion > 196608) {
                version = "OpenGL ES 3.1";
            } else {
                version = "OpenGL ES 3.0";
            }

        } else {

            tvTips.setVisibility(View.VISIBLE);

            tvTriangle.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvSquare.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvMosaic.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvBrush.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvLive2d.setTextColor(getResources().getColor(R.color.lightGray, null));
            tvCameraX.setTextColor(getResources().getColor(R.color.lightGray, null));

            if (glVersion > 65537) {
                version = "OpenGL ES 2.0";
            } else if (glVersion > 65536) {
                version = "OpenGL ES 1.1";
            }
        }

        version = "当前设备支持  " + version;
        tvStatus.setText(version);

    }

    /**
     * 返回当前手机支持的openGl ES 所支持的版本
     * OpenGL ES 1.0
     * 0x00010000
     * 65536
     * <p>
     * OpenGL ES 1.1
     * 0x00010001
     * 65537
     * <p>
     * OpenGL ES 2.0
     * 0x00020000
     * 131072
     * <p>
     * OpenGL ES 3.0
     * 0x00030000
     * 196608
     * <p>
     * OpenGL ES 3.1
     * 0x00030001
     * 196609
     * <p>
     * OpenGL ES 3.2
     * 0x00030002
     * 196610
     *
     * @return int
     */
    private int getGLESVersion() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo ci = am.getDeviceConfigurationInfo();
        return ci.reqGlEsVersion;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //android 13及以上
            permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_VIDEO};
        }


    }

    private void requestNeedPermissions() {
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (!checkNeedPermission(permission)) {
                permissionNeeded.add(permission);
            }
        }

        requestNeedPermission(permissionNeeded.toArray(new String[0]));
    }

    private boolean checkNeedPermission(String permission) {
        int result = checkSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestNeedPermission(String[] permissions) {
        if (ObjectUtils.isNotEmpty(permissions)) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        } else {
            Intent intent = new Intent(OpenglActivity.this, CameraXMainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showShort("部分权限被拒绝，请前往设置页面手动授权");
                    new XPopup.Builder(this).asConfirm("需要手动授权", "是否前往设置页面进行授权", PermissionUtils::launchAppDetailsSettings);
                    break;
                }
            }
        }

    }

}
