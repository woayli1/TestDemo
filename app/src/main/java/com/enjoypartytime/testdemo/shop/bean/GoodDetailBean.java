package com.enjoypartytime.testdemo.shop.bean;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class GoodDetailBean {


    /**
     * content : 内容描述
     * banners : ["1","2","3"]
     * tabs : [{"name":"商品详情","pictures":["4"]},{"name":"规格参数","pictures":["1"]}]
     * specifications : 规格参数
     * inventory : 商品中包含了哪些东西的清单
     * id : 1
     * name : 小米5c 3GB+64GB 金色
     * description : 搭载澎湃S1 八核高性能处理器 / “暗夜之眼”超感光相机 / 135g 轻薄金属机身 / 5.15 高亮护眼屏
     * price : 12
     * thumb : 1
     */

    private String content;
    private String specifications;
    private String inventory;
    private int id;
    private String name;
    private String description;
    private int price;
    private String thumb;
    private List<String> banners;
    /**
     * name : 商品详情
     * pictures : ["4"]
     */

    private List<TabsBean> tabs;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<TabsBean> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabsBean> tabs) {
        this.tabs = tabs;
    }

    public static class TabsBean {
        private String name;
        private List<String> pictures;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getPictures() {
            return pictures;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }
    }
}
