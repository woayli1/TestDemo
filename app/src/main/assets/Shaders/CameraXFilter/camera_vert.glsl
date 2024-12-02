#version 300 es

in vec4 vPosition;
in vec2 vCoord;

uniform mat4 vMatrix;

out vec2 aCoord;

void main() {
    aCoord = (vMatrix * vec4(vCoord.x, vCoord.y, 1.0, 1.0)).xy;
    //内置变量：把坐标点赋值给gl_position
    gl_Position = vPosition;
}