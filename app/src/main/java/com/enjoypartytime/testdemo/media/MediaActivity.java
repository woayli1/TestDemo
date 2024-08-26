package com.enjoypartytime.testdemo.media;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/23
 */
public class MediaActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private final String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        TextView tvMediaRecord = findViewById(R.id.tv_media_record);
        TextView tvMediaPlayVideo = findViewById(R.id.tv_media_play_video);
        TextView tvMediaPlayVideoView = findViewById(R.id.tv_media_play_video_view);
        TextView tvMediaPlayAudio = findViewById(R.id.tv_media_play_audio);


        tvMediaRecord.setOnClickListener(view -> record());
        tvMediaPlayVideo.setOnClickListener(view -> playVideo());
        tvMediaPlayVideoView.setOnClickListener(view -> playVideoView());
        tvMediaPlayAudio.setOnClickListener(view -> playAudio());
    }

    @Override
    protected void onResume() {
        super.onResume();

        requestNeedPermissions();
    }

    public void record() {
        ActivityUtils.startActivity(MediaActivity.this, MediaRecordActivity.class);
    }

    public void playVideo() {
        ActivityUtils.startActivity(MediaActivity.this, VideoPlayActivity.class);
    }

    public void playVideoView() {
        ActivityUtils.startActivity(MediaActivity.this, VideoViewActivity.class);
    }

    public void playAudio() {
        ActivityUtils.startActivity(MediaActivity.this, SoundPlayActivity.class);
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
