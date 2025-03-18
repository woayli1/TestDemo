#version 300 es

in vec4 vPosition;
uniform vec4 vColor;

out vec4 vTexColor;

void main() {
    gl_Position = vPosition;
    gl_PointSize = 10.0;
    vTexColor = vColor;
}