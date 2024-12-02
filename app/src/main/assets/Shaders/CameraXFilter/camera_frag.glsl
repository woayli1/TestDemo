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

    //texture2D: vTexture采样器，采样， 取出aCoord中各个对应像素点的RGBA值，赋值给 gl_FragColor
    //标准效果
    vFragColor = texture(vTexture, aCoord);

    //横向二分屏
    //    float x = aCoord.x;
    //    if (x < 0.5) {
    //        x += 0.25;
    //    } else {
    //        x -= 0.25;
    //    }
    //    gl_FragColor = texture2D(vTexture, vec2(x, aCoord.y));

    //竖向二分屏
    //    float y = aCoord.y;
    //    if (y < 0.5) {
    //        y += 0.25;
    //    } else {
    //        y -= 0.25;
    //    }
    //    gl_FragColor = texture2D(vTexture, vec2(aCoord.x, y));

    //底片效果
    //    vec4 rgba = texture2D(vTexture,aCoord);  // rgba
    //    gl_FragColor = vec4(1.-rgba.r, 1.-rgba.g, 1.-rgba.b, rgba.a);

    //黑白效果
    //    vec4 rgba =texture2D(vTexture, aCoord);
    //    float gray = (0.30 * rgba.r   + 0.59 * rgba.g + 0.11* rgba.b); // 其实原理就是提取出Y分量 ,就是黑白电视
    //    gl_FragColor = vec4(gray, gray, gray, 1.0);

    //灰色效果
    //    vec4 rgba =texture2D(vTexture, aCoord);
    //    float color=(rgba.r + rgba.g + rgba.b) / 3.0;
    //    gl_FragColor=vec4(color,color,color,1.0);

}