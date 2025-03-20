#version 300 es

#extension GL_OES_EGL_image_external_essl3: require

//数据精度
precision mediump float;

in vec2 aCoord;
uniform samplerExternalOES vTexture;
out vec4 vFragColor;

void main() {

    //横向二分屏
    float x = aCoord.x;
    if (x < 0.5) {
        x += 0.25;
    } else {
        x -= 0.25;
    }
    vFragColor = texture(vTexture, vec2(x, aCoord.y));

}