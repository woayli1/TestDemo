package com.enjoypartytime.testdemo.opengl.glVideo.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.enjoypartytime.testdemo.opengl.glVideo.other.EFramebufferObject;
import com.enjoypartytime.testdemo.opengl.glVideo.utils.EglUtil;


/**
 * author gc
 * company enjoyPartyTime
 * date 2025/6/5
 */
public class GlDMXFilter extends GlFilter {

    private static final String RGB_FRAGMENT_SHADER = "Shaders/fragment_shader_dmx.glsl";

    public GlDMXFilter(Context context) {
        super(DEFAULT_VERTEX_SHADER, EglUtil.loadAsset(context, RGB_FRAGMENT_SHADER));
    }

    //特效类型
    private int effectType;

    private float centerX = 0.5f;
    private float centerY = 0.5f;
    private float radius = 1f;
    private float scale = 1f;

    private float angle = 1f;

    private float duration = 0f;
    private int colorMaskType = 0;

    private float red = 1f;
    private float green = 1f;
    private float blue = 1f;
    //亮度 -1 全黑； 1 全亮 ； 0 不变
    private float brightness = 0f;

    private int _width, _height;

    public void setEffectType(int effectType) {
        this.effectType = effectType;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setColorMaskType(int colorMaskType) {
        this.colorMaskType = colorMaskType;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    @Override
    public void setFrameSize(int width, int height) {
        super.setFrameSize(width, height);
        _width = width;
        _height = height;
    }

    @Override
    public void draw(int texName, EFramebufferObject fbo) {
        super.draw(texName, fbo);
    }

    private float currentTime = 1f;

    @Override
    public void onDraw() {

        if (effectType == 3) {
            //画中画
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        }

        //颜色
        GLES20.glUniform1f(getHandle("red"), red);
        GLES20.glUniform1f(getHandle("green"), green);
        GLES20.glUniform1f(getHandle("blue"), blue);
        //亮度
        GLES20.glUniform1f(getHandle("brightness"), brightness);

        //特效
        GLES20.glUniform1i(getHandle("effectType"), effectType);
        GLES20.glUniform2f(getHandle("center"), centerX, centerY);
        GLES20.glUniform1f(getHandle("radius"), radius);
        GLES20.glUniform1f(getHandle("scale"), scale);
        GLES20.glUniform1f(getHandle("angle"), angle);

        //频闪 duration == 5时，说明传值为10的倍数，此时只显示对应爆闪颜色画面
        currentTime = currentTime + 0.1f;
        GLES20.glUniform1f(getHandle("Time"), duration == 5 ? 0 : currentTime);
        GLES20.glUniform1f(getHandle("duration"), duration == 5 ? 1 : duration);
        GLES20.glUniform1i(getHandle("colorMaskType"), colorMaskType);

        if (effectType == 3) {
            //画中画 数字控制尺寸大小？
            int w = _width / 3, h = _height / 3;
            int x = _width / 2 - w / 2, y = _height / 2 - h / 2;
            GLES20.glViewport(x, y, w, h);
        }
    }
}
