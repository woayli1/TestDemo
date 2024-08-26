package com.enjoypartytime.testdemo.media.sound;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class SoundBean {

    private String name;
    private int soundId;

    public SoundBean() {

    }

    public SoundBean(String name, int soundId) {
        this.name = name;
        this.soundId = soundId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }
}
