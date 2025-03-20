package com.enjoypartytime.testdemo.opengl.image.filter.strategy;

import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.opengl.image.filter.IFilterStrategy;

import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 * <p>
 * 旋转滤镜
 */
public class SwirlFilter implements IFilterStrategy {

    @Override
    public RequestOptions getFilter() {
        return RequestOptions.bitmapTransform(new SwirlFilterTransformation());
    }
}
