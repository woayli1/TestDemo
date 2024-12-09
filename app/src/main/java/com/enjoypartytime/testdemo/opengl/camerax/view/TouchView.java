package com.enjoypartytime.testdemo.opengl.camerax.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/6
 */
public class TouchView extends View {

    /**
     * 1 单指
     * 2 双指
     */
    private int moveType;
    private float spacing;

    private ScaleListener scaleListener;

    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                moveType = 1;
                if (scaleListener != null) {
                    scaleListener.onFocus(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                moveType = 2;
                spacing = getSpacing(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveType == 2) {
                    float scale = getSpacing(event) / spacing;

                    if (scaleListener != null) {
                        scaleListener.onScale(scale);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                moveType = 0;
                break;
        }
        return true;
    }

    // 触碰两点间距离
    private float getSpacing(MotionEvent event) {
        //通过三角函数得到两点间的距离
        try {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        } catch (IllegalArgumentException ex) {
            LogUtils.e(ex);
        }
        return 1.0F;
    }

    public void setScaleListener(ScaleListener scaleListener) {
        this.scaleListener = scaleListener;
    }


    public interface ScaleListener {
        void onScale(float scale);

        void onFocus(float x, float y);
    }
}
