precision highp float;

varying vec2 vTextureCoord;

uniform lowp sampler2D sTexture;
uniform highp float red;
uniform highp float green;
uniform highp float blue;
uniform lowp float brightness;
uniform int effectType;

uniform highp vec2 center;
uniform highp float radius;
uniform highp float scale;

uniform highp float angle;

// 一次效果的时长
uniform highp float duration;
// 外部传值变化周期
uniform highp float Time;
uniform int colorMaskType;
// 定义π
const highp float PI = 3.14;

void main() {
    highp vec2 textureCoordinateToUse = vTextureCoord;

    highp float dist = distance(center, vTextureCoord); // 提前计算距离

    if (effectType == 1) {
        // 放大
        textureCoordinateToUse -= center;
        if (dist < radius) {
            highp float percent = 1.0 - ((radius - dist) / radius) * scale;
            percent = percent * percent;
            textureCoordinateToUse = textureCoordinateToUse * percent;
        }
        textureCoordinateToUse += center;

    } else if (effectType == 2) {
        // 分屏
        // X轴每屏显示对应的纹理部分
        if (textureCoordinateToUse.x <= 1.0 / 3.0) {
            textureCoordinateToUse.x = textureCoordinateToUse.x + 1.0 / 3.0;
        } else if (textureCoordinateToUse.x >= 2.0 / 3.0) {
            textureCoordinateToUse.x = textureCoordinateToUse.x - 1.0 / 3.0;
        }

        // Y轴每屏显示对应的纹理部分
        if (textureCoordinateToUse.y <= 0.5) {
            textureCoordinateToUse.y = textureCoordinateToUse.y + 0.25;
        } else {
            textureCoordinateToUse.y = textureCoordinateToUse.y - 0.25;
        }

    } else if (effectType == 4) {
        // 旋转
        if (dist < radius) {
            textureCoordinateToUse -= center;
            highp float percent = (radius - dist) / radius;
            highp float theta = percent * percent * angle * 8.0;
            highp float s = sin(theta);
            highp float c = cos(theta);
            textureCoordinateToUse = vec2(dot(textureCoordinateToUse, vec2(c, -s)), dot(textureCoordinateToUse, vec2(s, c)));
            textureCoordinateToUse += center;
        }
    }

    highp vec4 textureColor = texture2D(sTexture, textureCoordinateToUse);

    // 频闪
    highp float time = mod(Time, duration);
    highp vec4 colorMask = vec4(0.0, 0.0, 0.0, 0.0);
    if (colorMaskType == 1) {
        // 白色爆闪
        colorMask = vec4(1.0, 1.0, 1.0, 1.0);
    } else if (colorMaskType == 2) {
        // 红色爆闪
        colorMask = vec4(1.0, 0.0, 0.0, 1.0);
    } else if (colorMaskType == 3) {
        // 橙色爆闪
        colorMask = vec4(1.0, 0.647, 0.0, 1.0);
    } else if (colorMaskType == 4) {
        // 紫色爆闪
        colorMask = vec4(0.5, 0.0, 0.5, 1.0);
    } else if (colorMaskType == 5) {
        // 标准频闪 黑色
        colorMask = vec4(0.0, 0.0, 0.0, 1.0);
    } else if (colorMaskType == 6) {
        // 呼吸频闪
        colorMask = vec4(0.0, 0.0, 0.0, 1.0);
    }

    // 层次的透明度变化
    highp float amplitude = abs(cos(time * (PI / duration)));

    // 亮度
    //    highp vec4 texture2Color = vec4((textureColor.rgb), textureColor.w);
    highp vec4 texture2Color = vec4((textureColor.rgb + vec3(brightness)), textureColor.w);


    // 输出最终颜色
    if (colorMaskType == 0) {
        gl_FragColor = vec4(texture2Color.r * red, texture2Color.g * green, texture2Color.b * blue, 1.0);
    } else {
        gl_FragColor = vec4(texture2Color.r * red, texture2Color.g * green, texture2Color.b * blue, 1.0) * (1.0 - amplitude) + colorMask * amplitude;
    }
}