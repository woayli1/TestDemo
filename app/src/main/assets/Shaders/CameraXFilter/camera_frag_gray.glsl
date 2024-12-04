#version 300 es

#extension GL_OES_EGL_image_external_essl3: require

//数据精度
precision mediump float;

in vec2 aCoord;

uniform samplerExternalOES vTexture;

out vec4 vFragColor;

void main() {

    //灰色效果
    vec4 rgba = texture(vTexture, aCoord);
    float color = (rgba.r + rgba.g + rgba.b) / 3.0;
    vFragColor = vec4(color, color, color, 1.0);

}