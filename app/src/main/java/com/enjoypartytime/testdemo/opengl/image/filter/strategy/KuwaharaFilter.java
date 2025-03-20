package com.enjoypartytime.testdemo.opengl.image.filter.strategy;

import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.opengl.image.filter.IFilterStrategy;

import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 * <p>
 * Kuwahara滤镜
 */
public class KuwaharaFilter implements IFilterStrategy {

    @Override
    public RequestOptions getFilter() {
        return RequestOptions.bitmapTransform(new KuwaharaFilterTransformation());
    }
}
