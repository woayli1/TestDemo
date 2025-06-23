package com.enjoypartytime.testdemo.opengl.glVideo;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.opengl.glVideo.filter.GlDMXFilter;
import com.enjoypartytime.testdemo.opengl.glVideo.view.eplayer.EPlayerView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/6/23
 */
public class GlVideoActivity extends Activity {

    private EPlayerView mEPlayerView;
    private ExoPlayer player;

    /**
     * codes 数组
     * 数值范围 0~255
     * 0、1 指代播放素材位置（此处仅设置一个素菜，故未实现相关代码）
     * 2 rgb 红色
     * 3 rgb 绿色
     * 4 rgb 蓝色
     * 5 亮度
     * 6 特效
     * 7 播放速度
     * 8 频闪
     */
    private int[] codes;

    private ScrollView scrollSetting;
    private TextView tvDefault;
    private TextView tvRed;
    private TextView tvGreen;
    private TextView tvBlue;
    private TextView tvBrightness;
    private TextView tvEffect;
    private TextView tvSpeed;
    private TextView tvRange;

    private SeekBar seekbarRed;
    private SeekBar seekbarGreen;
    private SeekBar seekbarBlue;
    private SeekBar seekbarBrightness;
    private SeekBar seekbarEffect;
    private SeekBar seekbarSpeed;
    private SeekBar seekbarRange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_video);
        bindView();

        tvDefault.setOnClickListener(v -> {
            codes = new int[]{0, 0, 25, 25, 25, 128, 0, 0, 100};
            setViewValue();
            setViewFilter();
        });

        seekbarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String red = "红色：" + progress;
                tvRed.setText(red);
                codes[2] = progress;
                setViewFilter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String green = "绿色：" + progress;
                tvGreen.setText(green);
                codes[3] = progress;
                setViewFilter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String blue = "蓝色：" + progress;
                tvBlue.setText(blue);
                codes[4] = progress;
                setViewFilter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String brightness = "亮度：" + progress;
                tvBrightness.setText(brightness);
                codes[5] = progress;
                setViewFilter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarEffect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String effect = "特效：" + progress;
                tvEffect.setText(effect);
                codes[6] = progress;
                setViewFilter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Float realSpeed = speedMap.getOrDefault(progress, 1f);
                String speed = "速度：x" + realSpeed;
                tvSpeed.setText(speed);
                codes[7] = progress;
                setViewFilter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String range = "频闪：" + progress;
                tvRange.setText(range);
                codes[8] = progress;
                setViewFilter();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        codes = new int[]{0, 0, 25, 25, 25, 128, 0, 0, 100};
        setViewValue();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpExoPlayer();
        setUpGlPlayerView();
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }

    private void bindView() {
        mEPlayerView = findViewById(R.id.gl_player_view);
        scrollSetting = findViewById(R.id.scroll_setting);
        tvDefault = findViewById(R.id.tv_default);
        tvRed = findViewById(R.id.tv_red);
        tvGreen = findViewById(R.id.tv_green);
        tvBlue = findViewById(R.id.tv_blue);
        tvBrightness = findViewById(R.id.tv_brightness);
        tvEffect = findViewById(R.id.tv_effect);
        tvSpeed = findViewById(R.id.tv_speed);
        tvRange = findViewById(R.id.tv_range);

        seekbarRed = findViewById(R.id.seekbar_red);
        seekbarGreen = findViewById(R.id.seekbar_green);
        seekbarBlue = findViewById(R.id.seekbar_blue);
        seekbarBrightness = findViewById(R.id.seekbar_brightness);
        seekbarEffect = findViewById(R.id.seekbar_effect);
        seekbarSpeed = findViewById(R.id.seekbar_speed);
        seekbarRange = findViewById(R.id.seekbar_range);
    }

    private void setUpExoPlayer() {

        player = new ExoPlayer.Builder(getApplicationContext()).build();
        addItemToExoPlayer();
        player.setRepeatMode(Player.REPEAT_MODE_ONE);
//        player.setVideoScalingMode(MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        player.prepare();
        player.setPlayWhenReady(true);
        player.play();

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_READY) {
                    scrollSetting.setVisibility(View.VISIBLE);
                    setViewFilter();
                }
            }
        });
    }

    private void addItemToExoPlayer() {
        addMediaItem(R.raw.video1);
    }

    public void addMediaItem(int resId) {
        Uri uri = new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .path(Integer.toString(resId)).build();
        MediaItem mediaItem = MediaItem.fromUri(uri);
        player.addMediaItem(mediaItem);
    }

    private void setUpGlPlayerView() {
        mEPlayerView.setSimpleExoPlayer(player);
        mEPlayerView.onResume();
    }

    private void releasePlayer() {
        mEPlayerView.onPause();

        player.stop();
        player.release();
        player = null;
    }

    private void setViewValue() {
//        String red = "红色：" + codes[2];
//        tvRed.setText(red);
        seekbarRed.setProgress(codes[2]);

//        String green = "绿色：" + codes[3];
//        tvGreen.setText(green);
        seekbarGreen.setProgress(codes[3]);

//        String blue = "蓝色：" + codes[4];
//        tvBlue.setText(blue);
        seekbarBlue.setProgress(codes[4]);

//        String brightness = "亮度：" + codes[5];
//        tvBrightness.setText(brightness);
        seekbarBrightness.setProgress(codes[5]);

//        String effect = "特效：" + codes[6];
//        tvEffect.setText(effect);
        seekbarEffect.setProgress(codes[6]);

//        String speed = "速度：" + codes[7];
//        tvSpeed.setText(speed);
        seekbarSpeed.setProgress(codes[7]);

//        String range = "频闪：" + codes[8];
//        tvRange.setText(range);
        seekbarRange.setProgress(codes[8]);
    }

    //定义区间与效果类型的映射关系
    int[][] effectTypeMap = {{31, 40, 1},      // 放大
            {41, 50, 2},      // 分屏
            {51, 60, 3},      // 画中画
            {61, 70, 4}       // 旋转
    };

    //定义区间与颜色掩码类型的映射关系
    int[][] colorMaskTypeMap = {{1, 10, 1},       // 白色爆闪
            {11, 20, 2},      // 红色爆闪
            {21, 30, 3},      // 橙色爆闪
            {71, 80, 4},      // 紫色爆闪
            {201, 220, 5},    // 标准频闪
            {221, 240, 6}     // 呼吸频闪
    };

    private GlDMXFilter glDMXFilter;

    private void setViewFilter() {

        if (player == null || mEPlayerView == null) {
            return;
        }

        if (glDMXFilter == null) {
            glDMXFilter = new GlDMXFilter(getApplicationContext());
        }

        //调色
        glDMXFilter.setRed(mapToPercentage(codes[2]));
        glDMXFilter.setGreen(mapToPercentage(codes[3]));
        glDMXFilter.setBlue(mapToPercentage(codes[4]));

        //亮度
        glDMXFilter.setBrightness(mapToFloat(codes[5]));

        //特效
        //遍历映射表，找到匹配的范围
        for (int[] effect : effectTypeMap) {
            if (codes[6] > effect[0] && codes[6] <= effect[1]) {
                glDMXFilter.setEffectType(effect[2]);

                // 根据效果类型设置额外参数
                switch (effect[2]) {
                    case 1: // 放大
                        int effectValue = codes[6] - 30;
                        glDMXFilter.setRadius(effectValue / 10f);
                        glDMXFilter.setScale(effectValue / 10f);
                        break;
                    case 4: // 旋转
                        glDMXFilter.setAngle((codes[6] - 60) / 25f);
                        break;
                    default:
                        // 分屏、画中画
                        break;
                }
                break;
            } else {
                glDMXFilter.setEffectType(0);
            }
        }

        //频闪
        for (int[] range : colorMaskTypeMap) {
            if (codes[8] >= range[0] && codes[8] <= range[1]) {
                glDMXFilter.setColorMaskType(range[2]);
                break;
            } else {
                glDMXFilter.setColorMaskType(0);
            }
        }

        float v = 5 - (codes[8]) % 10 / 2f;
        glDMXFilter.setDuration(v);

        mEPlayerView.setGlFilter(glDMXFilter);

        //播放速度
        runOnUiThread(() -> setPlaySpeed(codes[7]));
    }

    private static final Map<Integer, Float> speedMap = new HashMap<>();

    static {
        speedMap.put(1, 1f);
        speedMap.put(2, 1f);
        speedMap.put(3, 1f);
        speedMap.put(4, 2f);
        speedMap.put(5, 2f);
        speedMap.put(6, 3f);
        speedMap.put(7, 3f);
        speedMap.put(8, 4f);
    }

    public void setPlaySpeed(int speed) {
        Float realSpeed = speedMap.getOrDefault(speed, 1f);
        player.setPlaybackSpeed(realSpeed != null ? realSpeed : 1f);
    }

    /**
     * 根据输入值生成结果。
     * 如果输入为0，则生成1~10之间的随机数；
     * 否则直接返回输入值。
     *
     * @param input 输入值（范围：0 ~ 10）
     * @return 输出值
     */
    public static int generateValue(int input) {
        if (input < 0 || input > 10) {
            throw new IllegalArgumentException("Input value must be between 0 and 10.");
        }

        if (input == 0) {
            Random random = new Random();
            return random.nextInt(10) + 1; // 生成1到10之间的随机数
        } else {
            return input; // 直接返回输入值
        }
    }

    /**
     * 将0~255的数值映射为0%~100%
     *
     * @param value 输入值（范围：0 ~ 255）
     * @return 对应的百分比值
     */
    public static float mapToPercentage(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Input value must be between 0 and 255.");
        }

        // 计算百分比值
//        double percentage = (value / 255.0) * 100;
//        return (float) (Math.round(percentage * 100) / 100.0); // 四舍五入保留两位小数
        return (float) ((value / 255.0) * 10 + 0.01);
    }

    /**
     * 将0~255的数值映射为1.0f~-1.0f
     *
     * @param value 输入值（范围：0 ~ 255）
     * @return 对应的浮点数值
     */
    public static float mapToFloat(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Input value must be between 0 and 255.");
        }

        // 计算浮点数值
        float floatValue = 1.0f - (2.0f * value / 255.0f);
        return Math.round(floatValue * 100) / 100.0f; // 四舍五入保留两位小数
    }

}
