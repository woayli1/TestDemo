package com.enjoypartytime.testdemo.opengl.triangle;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.utils.GLUtil;

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

    private final Context mContext;

    private int mProgram;
    private FloatBuffer vertexBuffer;

    private static final float[] triangleCoords = {0f, 0.25f, 0.0f, -0.5f, -0.25f, 0.0f, 0.5f, -0.25f, 0.0f};

    private final float[] color = {1f, 0f, 0f, 1f};
    private static final int COORDS_PER_VERTEX = 3;

    private int mPositionHandle;
    private int mColorHandle;

    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量 每个顶点四个字节
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public TriangleRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES30.glClearColor(0f, 0f, 0f, 1f);

        //申请底层空间
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        //将坐标数据转换为FloatBuffer,用以传入OpenGl ES程序
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, GLUtil.loadFromAssetsFile(mContext, "shaders/TriangleVertexShader.glsl"));
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, GLUtil.loadFromAssetsFile(mContext, "shaders/TriangleFragmentShader.glsl"));

        //创建一个空的OpenGL ES程序
        mProgram = GLES30.glCreateProgram();
        //将顶点着色器加入到程序
        GLES30.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序
        GLES30.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES30.glLinkProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES30.glViewport(0, 0, i, i1);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClearColor(GLES30.GL_COLOR_ATTACHMENT0, 1f, 1f, 0f);

        GLES30.glUseProgram(mProgram);

        //获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES30.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES30.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, vertexStride, vertexBuffer);
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
        //设置绘制三角形的颜色
        GLES30.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES30.glDisableVertexAttribArray(mPositionHandle);
    }

    private int loadShader(int shaderType, String shaderCode) {
        int shaderId = GLES30.glCreateShader(shaderType);
        GLES30.glShaderSource(shaderId, shaderCode);
        GLES30.glCompileShader(shaderId);

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
        GLES30.glGetShaderiv(shaderId, GLES30.GL_INFO_LOG_LENGTH, logLength, 0);

        if (logLength[0] > 0) {
            String log = GLES30.glGetShaderInfoLog(shaderId);
            LogUtils.e(log);
        }

        int[] status = new int[1];
        GLES30.glGetShaderiv(shaderId, GLES30.GL_COMPILE_STATUS, status, 0);

        if (status[0] == GLES30.GL_FALSE) {
            GLES30.glDeleteShader(shaderId);
            return false;
        }

        return true;
    }
}
