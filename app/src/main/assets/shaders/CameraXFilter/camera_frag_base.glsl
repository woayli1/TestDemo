#version 300 es

#extension GL_OES_EGL_image_external_essl3: require

//数据精度
precision mediump float;

in vec2 aCoord;

//samplerExternalOES：图片 采样器
uniform samplerExternalOES vTexture;
//uniform sampler2D vTexture;

out vec4 vFragColor;

void main() {

    //texture: vTexture采样器，采样， 取出aCoord中各个对应像素点的RGBA值，赋值给 vFragColor
    //标准效果
    vFragColor = texture(vTexture, aCoord);

}