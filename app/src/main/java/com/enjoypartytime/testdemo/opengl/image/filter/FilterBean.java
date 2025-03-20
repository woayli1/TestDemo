package com.enjoypartytime.testdemo.opengl.image.filter;

import com.bumptech.glide.request.RequestOptions;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 */
public class FilterBean {

    String filterName;
    RequestOptions requestOptions;

    public FilterBean(String filterName, RequestOptions requestOptions) {
        this.filterName = filterName;
        this.requestOptions = requestOptions;
    }

    public String getFilterName() {
        return filterName;
    }

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }
}
