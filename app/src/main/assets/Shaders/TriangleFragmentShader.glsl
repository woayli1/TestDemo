#version 300 es

precision mediump float;

uniform vec4 vColor;
out vec4 FragColor;

void main() {
    FragColor = vColor;
}