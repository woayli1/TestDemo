package com.enjoypartytime.testdemo.diyView.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/19
 */
public class FlowLayout extends ViewGroup {

    /**
     * 自定义View 主要是实现 onMeasure + onDraw
     * 自定义ViewGroup 主要是实现 onMeasure + onLayout
     */

    //每个item的横向间距
    private final int mHorizontalSpacing = SizeUtils.dp2px(16);
    //每个item的竖向间距
    private final int mVerticalSpacing = SizeUtils.dp2px(8);

    //记录所有的行，一行一行的存储，用于layout
    private List<List<View>> allLines;
    //记录每一行的行高
    private List<Integer> lineHeights;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initMeasureParams() {
        if (ObjectUtils.isEmpty(allLines)) {
            allLines = new ArrayList<>();
        } else {
            allLines.clear();
        }

        if (ObjectUtils.isEmpty(lineHeights)) {
            lineHeights = new ArrayList<>();
        } else {
            lineHeights.clear();
        }
    }

    /**
     * 测量view的大小
     *
     * @param widthMeasureSpec  int
     * @param heightMeasureSpec int
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //初始化
        initMeasureParams();

        //度量孩子
        int childCount = getChildCount();

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //ViewGroup解析的宽度
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        //ViewGroup解析的高度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        //保存一行中的所有View
        List<View> lineViews = new ArrayList<>();
        //记录这行已经使用了多宽的size
        int lineWidthUsed = 0;
        //一行的行高
        int lineHeight = 0;

        //measure过程中，子View要求的父ViewGroup的宽
        int parentNeededWidth = 0;
        //measure过程中，子View要求的父ViewGroup的高
        int parentNeededHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams childLP = childView.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLP.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLP.height);

            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            //获取子View的宽高
            int childMeasureWidth = childView.getMeasuredWidth();
            int childMeasureHeight = childView.getMeasuredHeight();

            //通过宽度来判断是否需要换行，通过换行后的每行的行高来获取整个viewGroup的行高
            if (childMeasureWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                allLines.add(lineViews);
                lineHeights.add(lineHeight);

                //一旦换行，我们就可以判断当前行需要的宽和高了，所以此时要记录下来
                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                lineViews = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;
            }

            //view 是分行layout的，所以要记录每一行有哪些view，这样可以方便layout布局
            lineViews.add(childView);

            //每行都会有自己的宽高
            lineWidthUsed = lineWidthUsed + childMeasureWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight, childMeasureHeight);

            if (i == childCount - 1) {
                allLines.add(lineViews);
                lineHeights.add(lineHeight);

                //一旦换行，我们就可以判断当前行需要的宽和高了，所以此时要记录下来
                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
            }
        }

        //度量自己

        //根据子View的度量结果，来重新度量自己ViewGroup
        //作为一个ViewGroup，它自己也是一个View，它的大小也需要根据它父亲给它提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;

        setMeasuredDimension(realWidth, realHeight);
    }

    /**
     * 确定子view的布局
     *
     * @param changed boolean
     * @param left    int
     * @param top     int
     * @param right   int
     * @param bottom  int
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //获取layout中的行数
        int lineCount = allLines.size();

        int curL = getPaddingLeft();
        int curT = getPaddingTop();

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);
            int lineHeight = lineHeights.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);
                int childLeft = curL;
                int childTop = curT;
                int childRight = childLeft + childView.getMeasuredWidth();
                int childBottom = childTop + childView.getMeasuredHeight();

                childView.layout(childLeft, childTop, childRight, childBottom);

                curL = childRight + mHorizontalSpacing;
            }

            curL = getPaddingLeft();
            curT = curT + lineHeight + mVerticalSpacing;
        }
    }

    /**
     * 实际绘制内容
     * ViewGroup一般不需要onDraw
     *
     * @param canvas Canvas
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
    }
}
