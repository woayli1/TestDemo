package com.enjoypartytime.testdemo.media;

import android.app.Activity;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.enjoypartytime.testdemo.R;
import com.enjoypartytime.testdemo.media.sound.SoundAdapter;
import com.enjoypartytime.testdemo.media.sound.SoundBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class SoundPlayActivity extends Activity {

    private SoundPool soundPool;
    private List<SoundBean> soundList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_play);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        soundPool = new SoundPool.Builder().setMaxStreams(9).build();
        soundList = new ArrayList<>();
        soundList.add(new SoundBean("r01", soundPool.load(this, R.raw.ring01, 1)));
        soundList.add(new SoundBean("r02", soundPool.load(this, R.raw.ring02, 1)));
        soundList.add(new SoundBean("r03", soundPool.load(this, R.raw.ring03, 1)));
        soundList.add(new SoundBean("r04", soundPool.load(this, R.raw.ring04, 1)));
        soundList.add(new SoundBean("r05", soundPool.load(this, R.raw.ring05, 1)));
        soundList.add(new SoundBean("r06", soundPool.load(this, R.raw.ring06, 1)));
        soundList.add(new SoundBean("r07", soundPool.load(this, R.raw.ring07, 1)));
        soundList.add(new SoundBean("r08", soundPool.load(this, R.raw.ring08, 1)));
        soundList.add(new SoundBean("r09", soundPool.load(this, R.raw.ring09, 1)));

        SoundAdapter soundAdapter = new SoundAdapter(soundList, this);
        soundAdapter.setOnItemClickListener(position -> {
            //播放声音
            SoundBean soundBean = soundList.get(position);
            soundPool.play(soundBean.getSoundId(), 0.5f, 0.5f, 1, 0, 1);
        });
        recyclerView.setAdapter(soundAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ObjectUtils.isNotEmpty(soundPool)) {
            for (SoundBean soundBean : soundList) {
                soundPool.unload(soundBean.getSoundId());
            }

            soundPool.release();
        }
    }
}
