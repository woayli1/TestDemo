#version 300 es

#extension GL_OES_EGL_image_external_essl3: require

//数据精度
precision mediump float;

in vec2 aCoord;
uniform samplerExternalOES vTexture;
out vec4 vFragColor;

void main() {

    //竖向二分屏
    float y = aCoord.y;
    if (y < 0.5) {
        y += 0.25;
    } else {
        y -= 0.25;
    }
    vFragColor = texture(vTexture, vec2(aCoord.x, y));

}