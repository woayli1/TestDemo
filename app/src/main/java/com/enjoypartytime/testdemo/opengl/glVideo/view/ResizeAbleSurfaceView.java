package com.enjoypartytime.testdemo.opengl.glVideo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by dang on 2021-01-11.
 * Time will tell.
 * 自定义一个surfaceView继承surfaceView
 */
public class ResizeAbleSurfaceView extends SurfaceView {

    private int mWidth = -1;
    private int mHeight = -1;

    private boolean isRatio = false;

    public ResizeAbleSurfaceView(Context context) {
        super(context);
    }

    public ResizeAbleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeAbleSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (isRatio) {
            isRatio = false;
            if (-1 == mWidth || -1 == mHeight) {
                //未设定宽高比，使用预览窗口默认宽高
                setMeasuredDimension(width, height);
            } else {
                //设定宽高比，调整预览窗口大小（调整后窗口大小不超过默认值）
                if (width < height * mWidth / mHeight) {
                    setMeasuredDimension(width, width * mHeight / mWidth);
                } else {
                    setMeasuredDimension(height * mWidth / mHeight, height);
                }
            }
        } else {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

    /**
     * 设置宽高比
     *
     * @param width  int
     * @param height int
     */
    public void resize(int width, int height) {
        mWidth = width;
        mHeight = height;
        isRatio = true;
        requestLayout();
    }
}