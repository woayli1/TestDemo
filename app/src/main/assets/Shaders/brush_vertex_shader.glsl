#version 320 es

in vec4 vPosition;
uniform vec4 vColor;

out vec4 v_texColor;

void main() {
    gl_Position = vPosition;
    v_texColor = vColor;
}