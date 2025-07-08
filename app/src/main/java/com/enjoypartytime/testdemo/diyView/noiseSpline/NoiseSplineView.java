package com.enjoypartytime.testdemo.diyView.noiseSpline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/7/7
 */
public class NoiseSplineView extends View {

    private float totalWidth = 1050f;
    private float totalHeight = 129f;

    private int currentDb = 0;
    private int lastDb = 0;

    private final Paint soundPaint = new Paint();

    private final static int totalAmount = 18;

    private final int colorGreen = Color.parseColor("#FF0FDD72");
    private final int colorParentGreen = Color.parseColor("#660FDD72");
    private final int colorYellow = Color.parseColor("#FFFFE620");
    private final int colorParentYellow = Color.parseColor("#66FFE620");
    private final int colorDefault = Color.parseColor("#FF4C4C4C");

    private final Pair<Integer, Integer> colorPair = new Pair<>(colorGreen, colorParentGreen);
    private final Pair<Integer, Integer> colorPair2 = new Pair<>(colorYellow, colorParentYellow);

    public NoiseSplineView(Context context) {
        super(context);
    }

    public NoiseSplineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initNoiseSpline();
    }

    public NoiseSplineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNoiseSpline();
    }

    private void initNoiseSpline() {
        soundPaint.setStrokeWidth(0f);
        soundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        soundPaint.setAntiAlias(true);
        soundPaint.setDither(true);
        soundPaint.setFilterBitmap(true);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        //Width of total View
        totalWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        //Height of unitRect
        float secondHeight = totalWidth / 1050 * 96;
        //Height of total view
        totalHeight = totalWidth / 1050 * 129;
        //Height of unitRect
        float highUnitHeight = totalWidth / 1050 * 120;
        //Height of unitRect
        float unitWidth = totalWidth / 1050 * 50;
        //Space between unitRect
        float space = totalWidth / 1050 * 8;
        //corner of the rectangle
        float corner = 4F;
        Pair<Float, Float> center = new Pair<>(getWidth() / 2f, getHeight() / 2f);

        float leftBound = center.first - totalWidth / 2;
        float unitUpperBound = center.second - secondHeight / 2;
        float unitLowerBound = center.second + secondHeight / 2;

        float highUnitUpperBound = center.second - highUnitHeight / 2;
        float highUnitLowerBound = center.second + highUnitHeight / 2;

        int numOfColor = (Math.min(lastDb, currentDb) - 30) / 2;
        int numOfChangingColor = Math.abs(currentDb - lastDb) / 2;

        for (int index = 0; index < totalAmount; index++) {
            if (index < numOfColor) {
                soundPaint.setColor(currentDb < 50 ? colorPair.first : colorPair2.first);
            } else if (index < numOfColor + numOfChangingColor) {
                soundPaint.setColor(currentDb < 50 ? colorPair.second : colorPair2.second);
            } else {
                soundPaint.setColor(colorDefault);
            }

            //if index ==10, draw highUnit
            float thisLeftBound = leftBound + space * (index + 1) + index * unitWidth;
            if (index == 10) {
                canvas.drawRoundRect(thisLeftBound, highUnitUpperBound, thisLeftBound + unitWidth, highUnitLowerBound, corner, corner, soundPaint);
            } else {
                canvas.drawRoundRect(thisLeftBound, unitUpperBound, thisLeftBound + unitWidth, unitLowerBound, corner, corner, soundPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measureDimension((int) totalWidth, widthMeasureSpec);
        int height = measureDimension((int) totalHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize;

            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    public void setLastDb(int lastDb) {
        this.lastDb = lastDb;
    }

    public void setCurrentDb(int currentDb) {
        setLastDb(this.currentDb);
        this.currentDb = currentDb;
        invalidate();

    }
}
