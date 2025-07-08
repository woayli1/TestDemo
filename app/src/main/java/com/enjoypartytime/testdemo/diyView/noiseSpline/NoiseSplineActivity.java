package com.enjoypartytime.testdemo.diyView.noiseSpline;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

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
 * date 2025/7/7
 */
public class NoiseSplineActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private TextView tvNoiseSplineDb;
    private NoiseSplineView noiseSplineView;

    private String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};

    private static final int SAMPLE_RATE_IN_HZ = 8000;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    private AudioRecord mAudioRecord;
    private boolean isGetVoiceRun;
    private Object mLock;

    private HandlerThread handlerThread;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noise_spline);

        tvNoiseSplineDb = findViewById(R.id.tv_noise_spline_db);
        TextView tvNoiseSplineBegin = findViewById(R.id.tv_noise_spline_begin);
        TextView tvNoiseSplineStop = findViewById(R.id.tv_noise_spline_stop);
        noiseSplineView = findViewById(R.id.noiseSplineView);

        tvNoiseSplineBegin.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                //android 13及以上
                permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_VIDEO};
            }
            requestNeedPermissions();
        });

        tvNoiseSplineStop.setOnClickListener(v -> stopDB());

        handlerThread = new HandlerThread("handlerBackground");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());
        runnable = () -> {
            mAudioRecord.startRecording();
            short[] buffer = new short[BUFFER_SIZE];
            while (isGetVoiceRun) {
                //r是实际读取的数据长度，一般而言r会小于bufferSize
                int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                long v = 0;
                // 将 buffer 内容取出，进行平方和运算
                for (short value : buffer) {
                    v += value * value;
                }
                // 平方和除以数据总长度，得到音量大小。
                double mean = v / (double) r;
                double volume = 10 * Math.log10(mean);
//                LogUtils.e("分贝值:" + volume);
                setNoiseDB((int) volume);
                //等待下一次检测
                synchronized (mLock) {
                    try {
                        mLock.wait(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopDB();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mLock = null;

        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }

        if (handlerThread != null) {
            handlerThread.quit();
            handlerThread = null;
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
            //获得授权 开始检测
            getNoiseDB();
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

    public void getNoiseDB() {
        if (isGetVoiceRun) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);

        isGetVoiceRun = true;
        mLock = new Object();

        handler.post(runnable);
    }

    private void setNoiseDB(int db) {
        String stringDb = db + "";
        runOnUiThread(() -> {
            noiseSplineView.setCurrentDb(db);
            tvNoiseSplineDb.setText(stringDb);
            if (db < 30) {
                noiseSplineView.setLastDb(db);
            }
        });
    }

    private void stopDB() {
        isGetVoiceRun = false;
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
        setNoiseDB(0);
    }
}
