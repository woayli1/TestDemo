package com.enjoypartytime.testdemo.media;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;

import java.io.File;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class VideoViewActivity extends Activity {

    private boolean isBegin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_view);

        VideoView videoView = findViewById(R.id.video_view);
        TextView tvPlay = findViewById(R.id.tv_play);


        MediaController mediaController = new MediaController(this);
        mediaController.setPrevNextListeners(view -> ToastUtils.showShort("下一个"), view -> ToastUtils.showShort("上一个"));
        videoView.setMediaController(mediaController);

        tvPlay.setOnClickListener(view -> {

            if (!isBegin) {
                File saveDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "CameraRecorder");
                List<File> fileList = FileUtils.listFilesInDir(saveDirectory);
                if (ObjectUtils.isEmpty(fileList)) {
                    ToastUtils.showShort("暂无视频");
                    return;
                }

                tvPlay.setText("结束播放");
                isBegin = true;

                videoView.setVideoPath(fileList.get(0).getAbsolutePath());
                videoView.setOnPreparedListener(mp -> videoView.start());

                videoView.setOnCompletionListener(mp -> {
                    tvPlay.setText("开始播放");
                    isBegin = false;
                });

            } else {
                tvPlay.setText("开始播放");
                isBegin = false;
                videoView.pause();
            }

        });

    }
}
