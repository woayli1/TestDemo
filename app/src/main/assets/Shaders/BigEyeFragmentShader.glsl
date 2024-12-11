#version 300 es

precision mediump float;

//纹理采样器
uniform sampler2D vTexture;

//纹理坐标
in vec2 aCoord;

//缩放系数，0 无缩放，大于0则放大
uniform float strength;

//左眼中心点
uniform vec2 left_eye;
//右眼中心点
uniform vec2 right_eye;

out vec4 FragColor;

//coord=当前要采样的点， eye=眼睛点， rmax=最大作用半径
vec2 newCoord(vec2 coord, vec2 eye, float rmax) {
    vec2 p = coord;
    //活得当前面画的点和眼睛的距离
    float r = distance(coord, eye);

    //在rmax的范围内，需要缩放
    if (r < rmax) {
        //获得缩放后的距离
        //float fsr = fs(r,rmax);
        float fsr = (1.0 - pow(r / rmax - 1.0, 2.0) * strength);
        //fsr是个float，是距离eye的距离，我们需要得到点的位置，是一个向量
        //那么根据比例关系，可以得出
        //新点与eyed的向量差/老点与eys的向量差=新点与eye的距离/老点与eye的距离。
        //(newCoord - eye) / (coord - eye) = fsr / r;
        //newCoord - eye = fsr /r * (coord - eye);
        p = fsr * (coord - eye) + eye;
    }

    return p;
}

void main() {

    //最大作用半径
    float rmax = distance(left_eye, right_eye) / 2;
    vec2 p = newCoord(aCoord, left_eye, rmax);
    p = newCoord(p, right_eye, rmax);
    FragColor = texture(vTexture, p);
}
