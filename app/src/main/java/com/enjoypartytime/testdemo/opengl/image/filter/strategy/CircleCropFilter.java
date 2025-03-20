package com.enjoypartytime.testdemo.opengl.image.filter.strategy;

import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.opengl.image.filter.IFilterStrategy;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 * <p>
 * 圆形
 */
public class CircleCropFilter implements IFilterStrategy {

    @Override
    public RequestOptions getFilter() {
        return RequestOptions.circleCropTransform();
    }
}
