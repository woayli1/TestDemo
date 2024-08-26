package com.enjoypartytime.testdemo.media;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Surface;
import android.view.TextureView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class VideoPlayActivity extends Activity {

    private boolean isBegin = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        TextureView textureView = findViewById(R.id.textureView);
        TextView tvPlay = findViewById(R.id.tv_play);


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
                mediaPlayer = new MediaPlayer();
                //设置准备监听
                mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                    tvPlay.setText("开始播放");
                    isBegin = false;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                });

                try {
                    mediaPlayer.setDataSource(fileList.get(0).getAbsolutePath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                mediaPlayer.setSurface(new Surface(textureView.getSurfaceTexture()));
                mediaPlayer.prepareAsync();


            } else {
                tvPlay.setText("开始播放");
                isBegin = false;
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        });
    }
}
