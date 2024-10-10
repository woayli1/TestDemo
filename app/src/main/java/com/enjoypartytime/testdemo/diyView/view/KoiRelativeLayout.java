package com.enjoypartytime.testdemo.diyView.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/27
 */
public class KoiRelativeLayout extends RelativeLayout {

    private Paint mPaint;
    private ImageView ivKoi;
    private KoiDrawable koiDrawable;

    private float touchX = 0, touchY = 0;
    private float ripple = 0;
    private int alpha = 0;

    public KoiRelativeLayout(Context context) {
        this(context, null);
    }

    public KoiRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KoiRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        ivKoi = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivKoi.setLayoutParams(layoutParams);

        koiDrawable = new KoiDrawable();
        ivKoi.setImageDrawable(koiDrawable);
        addView(ivKoi);

    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAlpha(alpha);
        canvas.drawCircle(touchX, touchY, ripple * 150, mPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "ripple", 0, 1f)
                .setDuration(1000);
        objectAnimator.start();

        makeTrail();

        return super.onTouchEvent(event);
    }

    private void makeTrail() {
        PointF fishRelativeMiddle = koiDrawable.getMiddlePoint();
        PointF fishRelativeHead = koiDrawable.getHeadPoint();
        // 起始点
        PointF fishMiddle = new PointF(ivKoi.getX() + fishRelativeMiddle.x, ivKoi.getY() + fishRelativeMiddle.y);
        // 第一个控制点
        PointF fishHead = new PointF(ivKoi.getX() + fishRelativeHead.x, ivKoi.getY() + fishRelativeHead.y);
        //结束点
        PointF touch = new PointF(touchX, touchY);

        float angle = includeAngle(fishMiddle, fishHead, touch);
        float delta = includeAngle(fishMiddle, new PointF(fishMiddle.x + 1, fishMiddle.y), fishHead);
        // 鱼游动，对应贝塞尔曲线的 第二个控制点
        PointF controlPoint = KoiDrawable.calculatePoint(fishMiddle, KoiDrawable.HEAD_RADIUS * 1.6f, angle / 2 + delta);

        Path path = new Path();
        path.moveTo(fishMiddle.x - fishRelativeMiddle.x, fishMiddle.y - fishRelativeMiddle.y);
        path.cubicTo(fishHead.x - fishRelativeMiddle.x, fishHead.y - fishRelativeMiddle.y, controlPoint.x - fishRelativeMiddle.x, controlPoint.y - fishRelativeMiddle.y, touch.x - fishRelativeMiddle.x, touch.y - fishRelativeMiddle.y);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivKoi, "x", "y", path);
        objectAnimator.setDuration(2000);

        PathMeasure pathMeasure = new PathMeasure(path, false);
        float[] tan = new float[2];
        objectAnimator.addUpdateListener(animator -> {
            float fraction = animator.getAnimatedFraction();
            pathMeasure.getPosTan(pathMeasure.getLength() * fraction, null, tan);
            float angle1 = (float) Math.toDegrees(Math.atan2(-tan[1], tan[0]));
            koiDrawable.setFishMainAngle(angle1);
        });

        objectAnimator.start();
    }

    public void setRipple(float ripple) {
        alpha = (int) (150 * (1 - ripple));
        this.ripple = ripple;
    }

    public static float includeAngle(PointF O, PointF A, PointF B) {
        // OA*OB=(Ax-Ox)*(Bx-Ox)+(Ay-Oy)*(By-Oy)
        float AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y);
        // OA的长度
        float OALength = (float) Math.sqrt((A.x - O.x) * (A.x - O.x) + (A.y - O.y) * (A.y - O.y));
        // OB的长度
        float OBLength = (float) Math.sqrt((B.x - O.x) * (B.x - O.x) + (B.y - O.y) * (B.y - O.y));
        // cosAOB = (OA*OB)/(|OA|*|OB|)
        float cosAB = AOB / (OALength * OBLength);

        // toDegrees:将弧度转为度数。Math.aCos:反余弦。angleAOB:计算得出AOB的角度大小
        float angleAOB = (float) Math.toDegrees(Math.acos(cosAB));
        // 判断方向 正右侧 负右侧 0线上，但是Android的坐标系y是朝下的，所以左右颠倒一下
        // AB与X轴的夹角的tan值 - OB与X轴的夹角的tan值 --> 角度是直角三角形里面的，肯定是0~90度，tan角度越大，值越大
        float direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) / (O.x - B.x);
        if (direction == 0) {
            if (AOB >= 0) {
                return 0;
            } else return 180;
        } else {
            if (direction > 0) {
                //右侧顺时针为负
                return -angleAOB;
            } else return angleAOB;
        }
    }
}
