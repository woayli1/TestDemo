#version 100

precision highp float;
//纹理坐标
uniform sampler2D Texture;
//纹理采样器
varying vec2 TextureCoordsVarying;
//长宽
uniform int width;
uniform int height;
//马赛克size大小
uniform float strength;

void main() {

    //纹理图片size
    vec2 TexSize = vec2(width, height);
    //马赛克size
    vec2 MosaicSize = vec2(strength, strength);

    //计算实际图像位置
    vec2 intXY = vec2(TextureCoordsVarying.x * TexSize.x, TextureCoordsVarying.y * TexSize.y);

    //floor(x) 内建函数，返回小于/等于x最大的整数，即向下取整
    //floor(intXY.x/mosaicSize.x)*mosaicSize.x 计算出一个小马赛克的坐标
    vec2 XYMosaic = vec2(floor(intXY.x / MosaicSize.x) * MosaicSize.x, floor(intXY.y / MosaicSize.y) * MosaicSize.y);

    //换算回纹理坐标，此时的纹理坐标是小马赛克的部分的纹理坐标，即某一个色块
    vec2 UVMosaic = vec2(XYMosaic.x / TexSize.x, XYMosaic.y / TexSize.y);
    //获取到马赛克后的纹理坐标的颜色值
    vec4 color = texture2D(Texture, UVMosaic);
    //将马赛克颜色值赋值给gl_FragColor
    gl_FragColor = color;
}
