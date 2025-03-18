#version 300 es

//接收传入的转换矩阵
uniform mat4 uMVPMatrix;
//接收传入的顶点
in vec4 vPosition;
//接收传入的顶点纹理位置
in vec2 aTexCoord;
//增加用于传递给片元着色器的纹理位置变量
out vec2 TextureCoordsVarying;

void main() {
    //矩阵变换计算之后的位置
    gl_Position = uMVPMatrix * vPosition;

    TextureCoordsVarying = aTexCoord;
}