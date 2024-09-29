package com.enjoypartytime.testdemo.diyView.view;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/27
 */
public class KoiDrawable extends Drawable {

    private Path mPath;
    private Paint mPaint;

    private PointF headPoint;

    //除鱼身外的所有透明度
    private final static int OTHER_ALPHA = 110;
    //鱼身透明度
    private final static int BODY_ALPHA = 160;
    //转弯更自然的重心（身体的中心点）
    private PointF middlePoint;
    //鱼的主角度
    private float fishMainAngle = 90;

    private float currentValue;

    /**
     * 鱼身具体尺寸可查看 锦鲤尺寸示意图.png
     * 位于assets目录
     */
    public final static float HEAD_RADIUS = 40;

    //鱼身的长度
    private final static float BODY_LENGTH = 3.2f * HEAD_RADIUS;

    //-----------鱼鳍-------------
    //寻找鱼鳍开始点的线长
    private final static float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    //鱼鳍的长度
    private final static float FINS_LENGTH = 1.3f * HEAD_RADIUS;

    //-----------鱼尾-------------
    //鱼尾部大圆的半径（圆心就是身体底部的中点）
    private final static float BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS;
    //鱼尾部中圆的半径
    private final static float MIDDLE_CIRCLE_RADIUS = BIG_CIRCLE_RADIUS * 0.6f;
    //鱼尾部小圆的半径
    private final static float SMALL_CIRCLE_RADIUS = MIDDLE_CIRCLE_RADIUS * 0.4f;
    //--寻找尾部中圆圆心的线长
    private final static float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS + MIDDLE_CIRCLE_RADIUS;
    //--寻找尾部小圆圆心的线长
    private final static float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    //--寻找大三角形底边中心点的线长
    private final static float FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f;

    public KoiDrawable() {
        init();
    }

    private void init() {
        //路径
        mPath = new Path();
        //画笔
        mPaint = new Paint();
        //画笔类型，填充
        mPaint.setStyle(Paint.Style.FILL);
        //设置颜色
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖
        mPaint.setDither(true);

        middlePoint = new PointF(4.18f * HEAD_RADIUS, 4.18f * HEAD_RADIUS);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(1500);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animator -> currentValue = (float) animator.getAnimatedValue());
        valueAnimator.start();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                invalidateSelf();
                handler.postDelayed(this, 40);//每40ms循环一次，25fps
            }
        };
        handler.postDelayed(runnable, 40);
    }

    /**
     * 等同于 view 中的 onDraw
     *
     * @param canvas The canvas to draw into
     */
    @Override
    public void draw(@NonNull Canvas canvas) {
        float fishAngle = (float) (fishMainAngle + Math.cos(Math.toRadians(currentValue)) * 5);

        //鱼头
        headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);

        //右鱼鳍
        PointF rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeFins(canvas, rightFinsPoint, fishAngle, true);

        //左鱼鳍
        PointF leftFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110);
        makeFins(canvas, leftFinsPoint, fishAngle, false);

        //身体底部中心点
        PointF bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGTH, fishAngle - 180);
        //节肢1
        PointF middleCircleCenterPoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS,
                FIND_MIDDLE_CIRCLE_LENGTH, true);

        //节肢2
        makeSegment(canvas, middleCircleCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS,
                FIND_SMALL_CIRCLE_LENGTH, false);

        //尾巴
        makeTriangle(canvas, middleCircleCenterPoint, FIND_TRIANGLE_LENGTH, BIG_CIRCLE_RADIUS);
        makeTriangle(canvas, middleCircleCenterPoint, FIND_TRIANGLE_LENGTH - 10, BIG_CIRCLE_RADIUS - 20);

        //身体
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle);
    }

    /**
     * @param startPoint 起始点
     * @param length     两点的距离
     * @param angle      两点连线与X轴的夹角
     * @return 新点的坐标
     */
    public static PointF calculatePoint(PointF startPoint, float length, float angle) {
        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        float deltaY = (float) (Math.sin(Math.toRadians(angle - 180)) * length);
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
    }

    private void makeFins(Canvas canvas, PointF startPoint, float fishAngle, boolean isRightFins) {
        float controlAngle = 115;
        PointF endPoint = calculatePoint(startPoint, FINS_LENGTH, fishAngle - 180);
        PointF controlPoint = calculatePoint(startPoint, 2.5f * FINS_LENGTH, isRightFins ? fishAngle - controlAngle : fishAngle + controlAngle);

        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);

        canvas.drawPath(mPath, mPaint);
    }

    private PointF makeSegment(Canvas canvas, PointF bottomCenterPoint, float bigRadius, float smallRadius,
                               float findSmallCircleLength, boolean hasBigCircle) {

        float segmentAngle;
        if (hasBigCircle) {
            segmentAngle = (float) (fishMainAngle + Math.cos(Math.toRadians(currentValue * 2)) * 10);
        } else {
            segmentAngle = (float) (fishMainAngle + Math.sin(Math.toRadians(currentValue * 3)) * 20);
        }

        //梯形上底的中心点（中等大的圆的圆心）
        PointF upperCenterPoint = calculatePoint(bottomCenterPoint, findSmallCircleLength, segmentAngle - 180);
        //梯形的四个点
        PointF bottomLeftPoint = calculatePoint(bottomCenterPoint, bigRadius, segmentAngle + 90);
        PointF bottomRightPoint = calculatePoint(bottomCenterPoint, bigRadius, segmentAngle - 90);
        PointF upperLeftPoint = calculatePoint(upperCenterPoint, smallRadius, segmentAngle + 90);
        PointF upperRightPoint = calculatePoint(upperCenterPoint, smallRadius, segmentAngle - 90);

        if (hasBigCircle) {
            //画大圆
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadius, mPaint);
        }
        //画小圆
        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, smallRadius, mPaint);
        //画梯形
        mPath.reset();
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(upperLeftPoint.x, upperLeftPoint.y);
        mPath.lineTo(upperRightPoint.x, upperRightPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        canvas.drawPath(mPath, mPaint);

        return upperCenterPoint;
    }

    private void makeTriangle(Canvas canvas, PointF startPoint, float findCenterLength,
                              float findEdgeLength) {
        float triangleAngle = (float) (fishMainAngle + Math.sin(Math.toRadians(currentValue * 3)) * 20);

        //三角形底边的中心点
        PointF centerPoint = calculatePoint(startPoint, findCenterLength, triangleAngle - 180);
        //三角形底边的两点
        PointF leftPoint = calculatePoint(centerPoint, findEdgeLength, triangleAngle + 90);
        PointF rightPoint = calculatePoint(centerPoint, findEdgeLength, triangleAngle - 90);

        //绘制三角形
        mPath.reset();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.lineTo(leftPoint.x, leftPoint.y);
        mPath.lineTo(rightPoint.x, rightPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void makeBody(Canvas canvas, PointF headPoint, PointF bodyBottomCenterPoint, float fishAngle) {
        //身体的四个点
        PointF topLeftPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90);
        PointF topRightPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90);
        PointF bottomLeftPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle + 90);
        PointF bottomRightPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle - 90);

        //二阶贝塞尔曲线的控制点，决定鱼的胖瘦
        PointF controlLeft = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle + 130);
        PointF controlRight = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle - 130);

        //画身体
        mPath.reset();
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y);
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y);
        mPaint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    /**
     * 这个值，可以根据setAlpha中设置的值进行调整。
     * 比如，alpha==0时，设置为PixelFormat.TRANSPARENT。
     * 在alpha==255时，设置为PixelFormat.OPAQUE。
     * 在其它时候设置为PixelFormat.TRANSLUCENT
     * <p>
     * PixelFormat.OPAQUE： 完全不透明，遮盖在它下面的所有内容
     * PixelFormat.TRANSPARENT：透明，完全不显示本身任何东西
     * PixelFormat.TRANSLUCENT：只有绘制的地方才覆盖底下的内容
     *
     * @return int
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    public PointF getMiddlePoint() {
        return middlePoint;
    }

    public PointF getHeadPoint() {
        return headPoint;
    }

    public void setFishMainAngle(float fishMainAngle) {
        this.fishMainAngle = fishMainAngle;
    }
}
