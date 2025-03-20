package com.enjoypartytime.testdemo.opengl.image.filter.strategy;

import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.opengl.image.filter.IFilterStrategy;

import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 * <p>
 * 对比滤镜
 */
public class ContrastFilter implements IFilterStrategy {

    @Override
    public RequestOptions getFilter() {
        return RequestOptions.bitmapTransform(new ContrastFilterTransformation(3.0f));
    }
}
