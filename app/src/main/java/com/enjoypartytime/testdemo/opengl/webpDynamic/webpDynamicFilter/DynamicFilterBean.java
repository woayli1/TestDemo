package com.enjoypartytime.testdemo.opengl.webpDynamic.webpDynamicFilter;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/26
 */
public class DynamicFilterBean {

    String filterName;
    GPUImageFilter imageFilter;

    public DynamicFilterBean(String filterName, GPUImageFilter imageFilter) {
        this.filterName = filterName;
        this.imageFilter = imageFilter;
    }

    public String getFilterName() {
        return filterName;
    }

    public GPUImageFilter getImageFilter() {
        return imageFilter;
    }
}
