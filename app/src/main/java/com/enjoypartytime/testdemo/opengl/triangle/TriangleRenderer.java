package com.enjoypartytime.testdemo.opengl.triangle;

import android.opengl.GLES32;
import android.opengl.GLSurfaceView;

import com.blankj.utilcode.util.LogUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/29
 */
public class TriangleRenderer implements GLSurfaceView.Renderer {

    private int mProgram;
    private FloatBuffer vertexBuffer;

    private final String vertexShaderCode = "attribute vec4 vPosition;" + "void main() {" + "  gl_Position = vPosition;" + "}";

    private final String fragmentShaderCode = "precision mediump float;" + "uniform vec4 vColor;" + "void main() {" + "  gl_FragColor = vColor;" + "}";

    private static final float[] triangleCoords = {0f, 0.25f, 0.0f, -0.5f, -0.25f, 0.0f, 0.5f, -0.25f, 0.0f};

    private final float[] color = {1f, 0f, 0f, 1f};
    private static final int COORDS_PER_VERTEX = 3;

    private int mPositionHandle;
    private int mColorHandle;

    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量 每个顶点四个字节
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
//        GLES32.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        GLES32.glClearColor(0, 0, 0, 0);

        //申请底层空间
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        //将坐标数据转换为FloatBuffer,用以传入OpenGl ES程序
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = loadShader(GLES32.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES32.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建一个空的OpenGL ES程序
        mProgram = GLES32.glCreateProgram();
        //将顶点着色器加入到程序
        GLES32.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序
        GLES32.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES32.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES32.glViewport(0, 0, i, i1);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES32.glClearColor(GLES32.GL_COLOR_ATTACHMENT0, 1f, 1f, 0f);

        GLES32.glUseProgram(mProgram);

        //获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES32.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES32.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES32.GL_FLOAT, false, vertexStride, vertexBuffer);
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES32.glGetUniformLocation(mProgram, "vColor");
        //设置绘制三角形的颜色
        GLES32.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES32.glDisableVertexAttribArray(mPositionHandle);
    }

    private int loadShader(int shaderType, String shaderCode) {
        int shaderId = GLES32.glCreateShader(shaderType);
        GLES32.glShaderSource(shaderId, shaderCode);
        GLES32.glCompileShader(shaderId);

        if (!checkShader(shaderId)) {
            return 0;
        }

        return shaderId;
    }

    /**
     * CreateShader内部関数。エラーチェックを行う。
     *
     * @param shaderId シェーダーID
     * @return エラーチェック結果。trueの場合、エラーなし。
     */
    private boolean checkShader(int shaderId) {
        int[] logLength = new int[1];
        GLES32.glGetShaderiv(shaderId, GLES32.GL_INFO_LOG_LENGTH, logLength, 0);

        if (logLength[0] > 0) {
            String log = GLES32.glGetShaderInfoLog(shaderId);
            LogUtils.e(log);
        }

        int[] status = new int[1];
        GLES32.glGetShaderiv(shaderId, GLES32.GL_COMPILE_STATUS, status, 0);

        if (status[0] == GLES32.GL_FALSE) {
            GLES32.glDeleteShader(shaderId);
            return false;
        }

        return true;
    }
}
