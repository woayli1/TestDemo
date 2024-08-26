package com.enjoypartytime.testdemo.media;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.media.camera.CameraConfig;
import com.enjoypartytime.testdemo.media.camera.CameraModule;
import com.enjoypartytime.testdemo.media.camera.CameraSurfaceView;
import com.enjoypartytime.testdemo.media.camera.CameraUtil;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/23
 */
public class MediaRecordActivity extends Activity {

    private boolean isBegin = false;
    private CameraModule mCameraModule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_record);

        TextView tvRecord = findViewById(R.id.tv_record);
        CameraSurfaceView cameraView = findViewById(R.id.camera_view);

        List<CameraConfig> mCameraConfigList = CameraUtil.getCameraInfo(this);

        CameraConfig config = mCameraConfigList.get(0);
        mCameraModule = new CameraModule(this, config);
        cameraView.setCameraModule(mCameraModule);

        tvRecord.setOnClickListener(view -> {
            if (!isBegin) {
                tvRecord.setText("结束录制");
                isBegin = true;
                mCameraModule.startRecorder();

            } else {
                tvRecord.setText("开始录制");
                isBegin = false;
                mCameraModule.stopRecorder();
            }
        });
    }
}
