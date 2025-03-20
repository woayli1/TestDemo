package com.enjoypartytime.testdemo.opengl.image.filter.strategy;

import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.opengl.image.filter.IFilterStrategy;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 * <p>
 * 装饰图滤镜
 */
public class VignetteFilter implements IFilterStrategy {

    @Override
    public RequestOptions getFilter() {
        return RequestOptions.bitmapTransform(new VignetteFilterTransformation());
    }
}
