package com.enjoypartytime.testdemo.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;


/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/2
 */
public class RadarView extends View {

    private final Paint mPaint;
    private final SweepGradient mSweepGradient;

    private final Matrix matrix;

    private final RectF rectF;

    private final float x;
    private final float y;
    private final float radius;

    private final float lineXDistance;

    /**
     * 旋转的角度
     **/
    private int degree = 0;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int mWidth = ScreenUtils.getAppScreenWidth();
        int mHeight = ScreenUtils.getAppScreenHeight();

        x = (float) mWidth / 2;
        y = (float) mHeight / 2;

        radius = Math.min(x, y) - 10 - Math.max(BarUtils.getStatusBarHeight(), BarUtils.getActionBarHeight());

        lineXDistance = Math.min(x, y) / 2;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        matrix = new Matrix();

        mSweepGradient = new SweepGradient(x, y, new int[]{Color.TRANSPARENT, Color.parseColor("#41CC00")}, null);

        rectF = new RectF();

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        for (int i = 0; i < 2; i++) {

            rectF.left = x - radius + i * lineXDistance;
            rectF.top = y - radius + i * lineXDistance;

            rectF.right = x + radius - i * lineXDistance;
            rectF.bottom = y + radius - i * lineXDistance;

            //先画圆圈
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(10);
            mPaint.setColor(Color.parseColor("#05E60B"));
            canvas.drawArc(rectF, 0, 360, false, mPaint);
        }

        //画点
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        canvas.drawPoint(x, y, mPaint);

        //画扫描框
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(mSweepGradient);
        canvas.drawCircle(x, y, radius, mPaint);

        // 一定要Reset（具体原因还在调研中）
        mPaint.reset();
        //使用Matrix旋转
        mSweepGradient.setLocalMatrix(matrix);
        matrix.setRotate(degree, x, y);
        degree++;
        if (degree > 360) {
            degree = 0;
        }
        postInvalidate();
    }
}