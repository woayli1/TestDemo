#version 300 es

#extension GL_OES_EGL_image_external_essl3: require

//数据精度
precision mediump float;

in vec2 aCoord;
uniform samplerExternalOES vTexture;
out vec4 vFragColor;

void main() {

    //底片效果
    vec4 rgba = texture(vTexture, aCoord);  // rgba
    vFragColor = vec4(1. - rgba.r, 1. - rgba.g, 1. - rgba.b, rgba.a);

}