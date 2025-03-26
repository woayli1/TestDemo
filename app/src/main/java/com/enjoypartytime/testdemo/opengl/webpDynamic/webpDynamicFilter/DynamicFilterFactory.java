package com.enjoypartytime.testdemo.opengl.webpDynamic.webpDynamicFilter;

import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.filter.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDarkenBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDivideBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHardLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLuminanceThresholdFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageNormalBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageScreenBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSoftLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSourceOverBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSubtractBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageThresholdEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/26
 */
public class DynamicFilterFactory {

    private static final List<DynamicFilterBean> strategyList = new ArrayList<>();

    static {
        strategyList.add(new DynamicFilterBean("原图", new GPUImageFilter()));
        strategyList.add(new DynamicFilterBean("高斯模糊", new GPUImageGaussianBlurFilter(3)));
//        strategyList.add(new DynamicFilterBean("盒装模糊", new GPUImageBoxBlurFilter(3)));
//        strategyList.add(new DynamicFilterBean("3x3卷积", new GPUImage3x3ConvolutionFilter()));
//        strategyList.add(new DynamicFilterBean("3x3抽样卷积", new GPUImage3x3TextureSamplingFilter()));
//        strategyList.add(new DynamicFilterBean("动画变亮模糊效果", new GPUImageAddBlendFilter()));
//        strategyList.add(new DynamicFilterBean("透明混合", new GPUImageAlphaBlendFilter()));
        strategyList.add(new DynamicFilterBean("亮度", new GPUImageBrightnessFilter(0.5f)));
        strategyList.add(new DynamicFilterBean("曝光度", new GPUImageExposureFilter(2.0f)));
        strategyList.add(new DynamicFilterBean("对比度", new GPUImageContrastFilter(3.0f)));
        strategyList.add(new DynamicFilterBean("饱和度", new GPUImageSaturationFilter(2.0f)));
        strategyList.add(new DynamicFilterBean("伽马", new GPUImageGammaFilter(2.0f)));
        strategyList.add(new DynamicFilterBean("凸起失真，鱼眼效果", new GPUImageBulgeDistortionFilter()));
        strategyList.add(new DynamicFilterBean("CGA色彩滤镜", new GPUImageCGAColorspaceFilter()));
        strategyList.add(new DynamicFilterBean("色度键混合", new GPUImageChromaKeyBlendFilter()));
//        strategyList.add(new DynamicFilterBean("色彩加深混合", new GPUImageColorBurnBlendFilter()));
//        strategyList.add(new DynamicFilterBean("色彩减淡混合", new GPUImageColorDodgeBlendFilter()));
        strategyList.add(new DynamicFilterBean("反色", new GPUImageColorInvertFilter()));
        strategyList.add(new DynamicFilterBean("交叉线阴影", new GPUImageCrosshatchFilter()));
//        strategyList.add(new DynamicFilterBean("加深混合", new GPUImageDarkenBlendFilter()));
//        strategyList.add(new DynamicFilterBean("差异混合", new GPUImageDifferenceBlendFilter()));
        strategyList.add(new DynamicFilterBean("扩展边缘模糊", new GPUImageDilationFilter()));
//        strategyList.add(new DynamicFilterBean("溶解", new GPUImageDissolveBlendFilter()));
//        strategyList.add(new DynamicFilterBean("动画变暗模糊", new GPUImageDivideBlendFilter()));
        strategyList.add(new DynamicFilterBean("浮雕效果", new GPUImageEmbossFilter()));
//        strategyList.add(new DynamicFilterBean("排除混合", new GPUImageExclusionBlendFilter()));
        strategyList.add(new DynamicFilterBean("色彩替换", new GPUImageFalseColorFilter()));
        strategyList.add(new DynamicFilterBean("水晶球效果", new GPUImageGlassSphereFilter()));
        strategyList.add(new DynamicFilterBean("灰度", new GPUImageGrayscaleFilter()));
        strategyList.add(new DynamicFilterBean("点染,图像黑白化", new GPUImageHalftoneFilter()));
//        strategyList.add(new DynamicFilterBean("强光混合", new GPUImageHardLightBlendFilter()));
//        strategyList.add(new DynamicFilterBean("朦胧加暗", new GPUImageHazeFilter()));
//        strategyList.add(new DynamicFilterBean("提亮阴影", new GPUImageHighlightShadowFilter()));
        strategyList.add(new DynamicFilterBean("色度", new GPUImageHueFilter()));
        strategyList.add(new DynamicFilterBean("桑原(Kuwahara)滤波", new GPUImageKuwaharaFilter()));
//        strategyList.add(new DynamicFilterBean("色阶", new GPUImageLevelsFilter()));
        strategyList.add(new DynamicFilterBean("减淡混合", new GPUImageLightenBlendFilter()));
//        strategyList.add(new DynamicFilterBean("lookup 色彩调整", new GPUImageLookupFilter()));
        strategyList.add(new DynamicFilterBean("亮度阈", new GPUImageLuminanceThresholdFilter()));
        strategyList.add(new DynamicFilterBean("单色", new GPUImageMonochromeFilter()));
//        strategyList.add(new DynamicFilterBean("通常用于创建阴影和深度效果", new GPUImageMultiplyBlendFilter()));
        strategyList.add(new DynamicFilterBean("非最大抑制，只显示亮度最高的像素，其他为黑", new GPUImageNonMaximumSuppressionFilter()));
//        strategyList.add(new DynamicFilterBean("正常效果", new GPUImageNormalBlendFilter()));
//        strategyList.add(new DynamicFilterBean("不透明度", new GPUImageOpacityFilter()));
        strategyList.add(new DynamicFilterBean("叠加", new GPUImageOverlayBlendFilter()));
        strategyList.add(new DynamicFilterBean("色调分离", new GPUImagePosterizeFilter()));
//        strategyList.add(new DynamicFilterBean("RGB扩展边缘模糊", new GPUImageRGBDilationFilter()));
//        strategyList.add(new DynamicFilterBean("RGB", new GPUImageRGBFilter()));
//        strategyList.add(new DynamicFilterBean("屏幕包裹,通常用于创建亮点和镜头眩光", new GPUImageScreenBlendFilter()));
        strategyList.add(new DynamicFilterBean("锐化", new GPUImageSharpenFilter()));
        strategyList.add(new DynamicFilterBean("素描", new GPUImageSketchFilter()));
        strategyList.add(new DynamicFilterBean("细腻卡通", new GPUImageSmoothToonFilter()));
        strategyList.add(new DynamicFilterBean("Sobel边缘检测算法", new GPUImageSobelEdgeDetectionFilter()));
//        strategyList.add(new DynamicFilterBean("柔光混合", new GPUImageSoftLightBlendFilter()));
//        strategyList.add(new DynamicFilterBean("源混合", new GPUImageSourceOverBlendFilter()));
        strategyList.add(new DynamicFilterBean("球形折射，图形倒立", new GPUImageSphereRefractionFilter()));
//        strategyList.add(new DynamicFilterBean("差值混合", new GPUImageSubtractBlendFilter()));
        strategyList.add(new DynamicFilterBean("漩涡", new GPUImageSwirlFilter()));
        strategyList.add(new DynamicFilterBean("阈值边缘检测", new GPUImageThresholdEdgeDetectionFilter()));
//        strategyList.add(new DynamicFilterBean("色调曲线", new GPUImageToneCurveFilter()));
        strategyList.add(new DynamicFilterBean("卡通效果", new GPUImageToonFilter()));
//        strategyList.add(new DynamicFilterBean("形状变化", new GPUImageTransformFilter()));
        strategyList.add(new DynamicFilterBean("晕影", new GPUImageVignetteFilter()));
//        strategyList.add(new DynamicFilterBean("白平衡", new GPUImageWhiteBalanceFilter()));
    }

    public static DynamicFilterBean getFilter(int position) {
        return strategyList.get(position);
    }

    public static int getFilterSize() {
        return strategyList.size();
    }

}
