#version 320 es

precision mediump float;

in vec4 v_texColor;
out vec4 FragColor;

void main() {
    vec2 circleCoord = 2.0 * gl_PointCoord - 1.0;
    if (dot(circleCoord, circleCoord) > 1.0) {
        discard;
    }
    FragColor = v_texColor;
}