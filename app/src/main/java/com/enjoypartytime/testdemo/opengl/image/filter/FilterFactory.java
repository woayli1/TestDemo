package com.enjoypartytime.testdemo.opengl.image.filter;

import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.BlurFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.BrightnessFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.CircleCropFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.ColorFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.ContrastFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.CropCircleWithBorderFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.CropFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.CropSquareFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.GrayscaleFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.InvertFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.KuwaharaFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.MaskFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.PixelationFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.RoundedCornersFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.SepiaFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.SketchFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.SwirlFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.ToonFilter;
import com.enjoypartytime.testdemo.opengl.image.filter.strategy.VignetteFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/20
 */
public class FilterFactory {

    private static final List<FilterBean> strategyList = new ArrayList<>();

    static {
        strategyList.add(new FilterBean("原图", RequestOptions.centerInsideTransform()));
        strategyList.add(new FilterBean("矩形剪裁", new CropFilter().getFilter()));
        strategyList.add(new FilterBean("圆形", new CircleCropFilter().getFilter()));
        strategyList.add(new FilterBean("圆形带边框", new CropCircleWithBorderFilter().getFilter()));
        strategyList.add(new FilterBean("正方形", new CropSquareFilter().getFilter()));
        strategyList.add(new FilterBean("圆角剪裁", new RoundedCornersFilter().getFilter()));
        strategyList.add(new FilterBean("颜色滤镜", new ColorFilter().getFilter()));
        strategyList.add(new FilterBean("灰色滤镜", new GrayscaleFilter().getFilter()));
        strategyList.add(new FilterBean("高斯模糊", new BlurFilter().getFilter()));
        strategyList.add(new FilterBean("遮罩", new MaskFilter().getFilter()));
        strategyList.add(new FilterBean("卡通滤镜", new ToonFilter().getFilter()));
        strategyList.add(new FilterBean("乌墨色滤镜", new SepiaFilter().getFilter()));
        strategyList.add(new FilterBean("对比滤镜", new ContrastFilter().getFilter()));
        strategyList.add(new FilterBean("反转滤镜", new InvertFilter().getFilter()));
        strategyList.add(new FilterBean("像素化滤镜", new PixelationFilter().getFilter()));
        strategyList.add(new FilterBean("素描滤镜", new SketchFilter().getFilter()));
        strategyList.add(new FilterBean("旋转滤镜", new SwirlFilter().getFilter()));
        strategyList.add(new FilterBean("亮度滤镜", new BrightnessFilter().getFilter()));
        strategyList.add(new FilterBean("Kuwahara滤镜", new KuwaharaFilter().getFilter()));
        strategyList.add(new FilterBean("装饰图滤镜", new VignetteFilter().getFilter()));
    }

    public static FilterBean getFilter(int position) {
        return strategyList.get(position);
    }

    public static int getFilterSize() {
        return strategyList.size();
    }

}
