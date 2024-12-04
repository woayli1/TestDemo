package com.enjoypartytime.testdemo.opengl.brush;

import static android.opengl.GLES30.glUniform1f;
import static android.opengl.GLES30.glUniform4f;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.enjoypartytime.testdemo.utils.Bezier;
import com.enjoypartytime.testdemo.utils.Buffers;
import com.enjoypartytime.testdemo.utils.GLUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/3
 */
public class BrushRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;

    private final List<SizeBean> sizeBeanList = new ArrayList<>();
    private final List<float[]> floatList = new ArrayList<>();
    private final List<float[]> floatTmpList = new ArrayList<>();

    private int mProgram;

    private int mPositionHandle;
    private int mColorHandle;

    private float[] mDataPoints;
    private FloatBuffer mBuffer;
    private int mBufferId;
    private int mStartEndHandle;
    private int mControlHandle;
    private int mDataHandle;
    private int mAmpsHandle;
    private int mMvpHandle;

    private final static float lineWidth = 10;

    private float parentWidth, parentHeight, diffHeight;

    //设置颜色，依次为红绿蓝 和 透明通道
    private final float[] color = {
            1.0f, 0.0f, 0.0f, 1.0f // Red
    };

    public BrushRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
//        GLES30.glClearColor(0f, 0f, 0f, 1f);

        //方案1 CPU计算
        String vertexShaderCode = GLUtil.loadFromAssetsFile(mContext, "Shaders/brush_vertex_shader.glsl");
        String fragmentShaderCode = GLUtil.loadFromAssetsFile(mContext, "Shaders/brush_fragment_shader.glsl");

        //方案2 GPU计算
//        String vertexShaderCode = GLUtil.loadFromAssetsFile(mContext, "shader/bezier_line_vertex.glsl");
//        String fragmentShaderCode = GLUtil.loadFromAssetsFile(mContext, "shader/bezier_fragment.glsl");


        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建一个空的OpenGL ES程序
        mProgram = GLES30.glCreateProgram();
        //将顶点着色器加入到程序
        GLES30.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序
        GLES30.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES30.glLinkProgram(mProgram);
        GLES30.glUseProgram(mProgram);

//        GLES30.glLineWidth(lineWidth);//设置线宽

        //方案2 GPU计算
//        mStartEndHandle = glGetUniformLocation(mProgram, "uStartEndData");
//        mControlHandle = glGetUniformLocation(mProgram, "uControlData");
//        mAmpsHandle = glGetUniformLocation(mProgram, "u_Amp");
//        mDataHandle = glGetAttribLocation(mProgram, "aData");
//        mMvpHandle = glGetUniformLocation(mProgram, "u_MVPMatrix");
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        setParentWidth((float) width / 2);
        setParentHeight((float) height / 2);
        diffHeight = (float) (ScreenUtils.getScreenHeight() - height + 70) / 2;
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        int size = floatList.size();
        for (int i = 0; i < size; i++) {
            float[] vertices2 = ArrayUtils.subArray(floatList.get(i), 0, ArrayUtils.lastIndexOf(floatList.get(i), 0.1f) + 1);
            if (ObjectUtils.isNotEmpty(vertices2)) {
                //方案1 CPU计算执行
                int length = vertices2.length;
                int num = 18;
                int num2 = num / 3;

                if (i == size - 1 && length >= num) {
                    float[] floatTmp = ArrayUtils.subArray(vertices2, length - num, length);
                    if (floatTmp != null) {
                        floatTmpList.set(i, ArrayUtils.add(floatTmpList.get(i), computePoint(computePoint(computePoint(floatTmp, num2), num2), num2)));
                    }
                }

                lineDraw(floatTmpList.get(i));
//                lineDraw(vertices2);


                //方案2 GPU计算
//                lineDrawGPU(vertices2);
            }
        }

        //禁止顶点数组的句柄
        GLES30.glDisableVertexAttribArray(mPositionHandle);
        GLES30.glDisableVertexAttribArray(mColorHandle);
    }

    //方案1 CPU计算
    private void lineDraw(float[] vertices2) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices2.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices2);
        vertexBuffer.position(0);

        //获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
        //启用顶点的句柄
        GLES30.glEnableVertexAttribArray(mPositionHandle);
        //准备点的坐标数据
        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false, 12, vertexBuffer);

        mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
        GLES30.glUniform4fv(mColorHandle, 1, color, 0);

        //绘制点
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, vertices2.length / 3);
    }

    private float[] computePoint(float[] vertices, int num) {
        List<Float> floatPointList = new ArrayList<>();
        int size = vertices.length / 3;

        for (int j = 0; j < size - num / 2 + 1; j++) {

            float[] vertices3 = new float[num];
            for (int n = 1; n <= num / 2; n++) {
                vertices3[2 * n - 2] = vertices[3 * j + 3 * n - 3];
                vertices3[2 * n - 1] = vertices[3 * j + 3 * n - 2];
            }

            for (int k = 0; k < num; k++) {

                if (k % 5 == 0) {
                    continue;
                }

                float percent = (float) k / num;
                float[] result = Bezier.bezier(vertices3, percent);
                floatPointList.add(result[0]);
                floatPointList.add(result[1]);
                floatPointList.add(0.1f);
            }
        }

        Float[] tmpPoint = floatPointList.toArray(new Float[0]);
        float[] tmpPoint2 = new float[tmpPoint.length];
        for (int i = 0; i < tmpPoint.length; i++) {
            tmpPoint2[i] = tmpPoint[i];
        }
        return tmpPoint2;
    }

    //方案2 GPU计算
    private void lineDrawGPU(float[] vertices2) {

        mDataPoints = genTData(vertices2.length);

        mBuffer = Buffers.makeInterleavedBuffer(mDataPoints, vertices2.length);

        final int[] buffers = new int[1];
        GLES30.glGenBuffers(1, buffers, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, buffers[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, mBuffer.capacity() * 4,
                mBuffer, GLES30.GL_STATIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        mBufferId = buffers[0];
        mBuffer = null;

        int size = vertices2.length / 3 - 1;
        int m = 8;
        for (int j = 0; j <= size - m + 1; j++) {
            float[] vertices3 = new float[m];
            for (int n = 1; n <= m / 2; n++) {
                vertices3[2 * n - 2] = vertices2[3 * j + 3 * n - 3];
                vertices3[2 * n - 1] = vertices2[3 * j + 3 * n - 2];
            }
            lineDrawGPU2(vertices3);
        }
    }

    private void lineDrawGPU2(float[] vertices3) {
        glUniform4f(mStartEndHandle,
                vertices3[0],
                vertices3[1],
                vertices3[6],
                vertices3[7]);

        glUniform4f(mControlHandle,
                vertices3[2],
                vertices3[3],
                vertices3[4],
                vertices3[5]);

        glUniform1f(mAmpsHandle, 1.0f);

        final int stride = 4;

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mBufferId);
        GLES30.glEnableVertexAttribArray(mDataHandle);
        GLES30.glVertexAttribPointer(mDataHandle,
                1,
                GLES30.GL_FLOAT,
                false,
                stride,
                0);

        // Clear the currently bound buffer (so future OpenGL calls do not use this buffer).
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

//        GLES30.glUniformMatrix4fv(mMvpHandle, 1, false, mvp, 0);

        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 1000 * 3);
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

    private float[] genTData(int NUM_POINTS) {
        //  1---2
        //  | /
        //  3
        float[] tData = new float[3 * 4 * NUM_POINTS];

        float step = 1f / (float) tData.length * 2f;

        for (int i = 0; i < tData.length; i += 3) {
            float t = (float) i / (float) tData.length;
            float t1 = (float) (i + 1) / (float) tData.length;
            float t2 = (float) (i + 2) / (float) tData.length;

            tData[i] = t;
            tData[i + 1] = t1;
            tData[i + 2] = t2;

        }

        return tData;
    }

    public void setParentWidth(float parentWidth) {
        this.parentWidth = parentWidth;
    }

    public void setParentHeight(float parentHeight) {
        this.parentHeight = parentHeight;
    }

    public void setXY(float moveX, float moveY) {

        int i = sizeBeanList.size();

        if (3 * i + 2 >= 5000) {
            setStop();
            setBegin();
            i = sizeBeanList.size();
        }

        sizeBeanList.add(new SizeBean(moveX, moveY));
        int last = floatList.size() - 1;
        float[] vertices = floatList.get(last);
        vertices[3 * i] = (moveX - parentWidth) / parentWidth;
        vertices[3 * i + 1] = (parentHeight - moveY + diffHeight) / parentHeight;
        vertices[3 * i + 2] = 0.1f;
    }

    public void setBegin() {
        floatList.add(new float[5000]);
        floatTmpList.add(new float[0]);
    }

    public void setStop() {
        sizeBeanList.clear();
    }

    public void cleanXY() {
        floatList.clear();
        floatTmpList.clear();
        sizeBeanList.clear();
    }

    private static class SizeBean {
        float sizeX;
        float sizeY;

        public SizeBean(float sizeX, float sizeY) {
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        public float getSizeX() {
            return sizeX;
        }

        public void setSizeX(float sizeX) {
            this.sizeX = sizeX;
        }

        public float getSizeY() {
            return sizeY;
        }

        public void setSizeY(float sizeY) {
            this.sizeY = sizeY;
        }
    }
}
