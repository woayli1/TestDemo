package com.enjoypartytime.testdemo.opengl.image.filter.strategy;

import android.graphics.Color;

import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.opengl.image.filter.IFilterStrategy;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 * <p>
 * 圆形带边框
 */
public class CropCircleWithBorderFilter implements IFilterStrategy {

    @Override
    public RequestOptions getFilter() {
        return RequestOptions.bitmapTransform(new CropCircleWithBorderTransformation(2, Color.RED));
    }
}
