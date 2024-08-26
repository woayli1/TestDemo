package com.enjoypartytime.testdemo.shop.bean;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class GoodsBean {


    /**
     * goodsId : 0
     * spanSize : 4
     * banners : ["https://i8.mifile.cn/v1/a1/251f0880-423e-fa2d-3c18-1d3ec23f9912.webp","https://i8.mifile.cn/v1/a1/49dfd019-9504-abb5-34bb-26f425b67e45.webp","https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/b9540da01aef9a00a5c640b1c98b955a.jpg"]
     * text: "明星单品",
     * imageUrl: "https://i8.mifile.cn/v1/a1/1d338200-1be1-f868-9695-9d5ae0d6c2c6.webp",
     */

    private int goodsId;
    private int spanSize;
    private List<String> banners;
    private String text;
    private String imageUrl;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
