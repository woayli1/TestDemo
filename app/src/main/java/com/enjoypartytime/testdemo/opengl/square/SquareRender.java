package com.enjoypartytime.testdemo.opengl.square;

import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.blankj.utilcode.util.LogUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/29
 */
public class SquareRender implements GLSurfaceView.Renderer {

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private final String vertexShaderCode = "attribute vec4 vPosition;" + "uniform mat4 vMatrix;" + "void main() {" + "  gl_Position = vMatrix*vPosition;" + "}";

    private final String fragmentShaderCode = "precision mediump float;" + "uniform vec4 vColor;" + "void main() {" + "  gl_FragColor = vColor;" + "}";

    private int mProgram;

    private static final int COORDS_PER_VERTEX = 3;
    private static final float[] triangleCoords = {-0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f};

    private static short[] index = {0, 1, 2, 0, 2, 3};

    private int mPositionHandle;
    private int mColorHandle;

    private float[] mViewMatrix = new float[16];
    private float[] mPrpjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    private int mMatrixHandle;

    //设置颜色，依次为红绿蓝 和 透明通道
    private float[] color = {1.0f, 0f, 0f, 1.0f};

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(index.length * 2);
        byteBuffer2.order(ByteOrder.nativeOrder());
        indexBuffer = byteBuffer2.asShortBuffer();
        indexBuffer.put(index);
        indexBuffer.position(0);

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
        //计算宽高比
        float ratio = (float) i / i1;
        //设置透视投影
        Matrix.frustumM(mPrpjectMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7f, 0f, 0f, 0f, 0f, 1f, 1f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mPrpjectMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //将程序加入到OpenGL ES环境
        GLES32.glUseProgram(mProgram);
        //获取变换矩阵vMatrix成员句柄
        mMatrixHandle = GLES32.glGetUniformLocation(mProgram, "vMatrix");
        //指定vMatrix的值
        GLES32.glUniformMatrix4fv(mMatrixHandle, 1, false, mMVPMatrix, 0);
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
        //索引法绘制正方形
        GLES32.glDrawElements(GLES32.GL_TRIANGLES, index.length, GLES32.GL_UNSIGNED_SHORT, indexBuffer);
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
