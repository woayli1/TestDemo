#version 300 es

#extension GL_OES_EGL_image_external_essl3: require

//数据精度
precision mediump float;

in vec2 aCoord;
uniform samplerExternalOES vTexture;
out vec4 vFragColor;

void main() {

    //黑白效果
    vec4 rgba = texture(vTexture, aCoord);
    float gray = (0.30 * rgba.r + 0.59 * rgba.g + 0.11 * rgba.b); // 其实原理就是提取出Y分量 ,就是黑白电视
    vFragColor = vec4(gray, gray, gray, 1.0);

}