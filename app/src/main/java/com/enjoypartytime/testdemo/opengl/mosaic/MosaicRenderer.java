package com.enjoypartytime.testdemo.opengl.mosaic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.enjoypartytime.testdemo.utils.GLUtil;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/30
 */
public class MosaicRenderer implements GLSurfaceView.Renderer {

    private Uri imgUri;
    private float strength = 2;
    private int imgWidth, imgHeight;
    private int parentWidth, parentHeight;
    private Bitmap mBitmap;
    private final Context mContext;

    //这个可以理解为一个OpenGL程序句柄
    private int mProgram;
    //当前绘制的顶点位置句柄
    private int vPositionHandle;
    //变换矩阵句柄
    private int mMVPMatrixHandle;
    //纹理坐标句柄
    private int maTexCoordHandle;

    private int mStrength, mWidth, mHeight;

    /**
     * 投影和相机视图相关矩阵
     **/
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    public MosaicRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        //着色器脚本
        String vertexShaderCode = GLUtil.loadFromAssetsFile(mContext, "Shaders/VertexShader.glsl");
        String fragmentShaderCode = GLUtil.loadFromAssetsFile(mContext, "Shaders/MosaicShader.glsl");

        //获取程序，封装了加载、链接等操作
        mProgram = GLUtil.createProgram(vertexShaderCode, fragmentShaderCode);

        // 获取顶点着色器的位置的句柄（这里可以理解为当前绘制的顶点位置）
        vPositionHandle = GLES32.glGetAttribLocation(mProgram, "vPosition");
        // 获取变换矩阵的句柄
        mMVPMatrixHandle = GLES32.glGetUniformLocation(mProgram, "uMVPMatrix");
        //纹理位置句柄
        maTexCoordHandle = GLES32.glGetAttribLocation(mProgram, "aTexCoord");

        mStrength = GLES32.glGetUniformLocation(mProgram, "strength");
        mWidth = GLES32.glGetUniformLocation(mProgram, "width");
        mHeight = GLES32.glGetUniformLocation(mProgram, "height");

        int[] textures = new int[1]; //生成纹理id

        GLES32.glGenTextures(  //创建纹理对象
                1, //产生纹理id的数量
                textures, //纹理id的数组
                0  //偏移量
        );
        int mTextureId = textures[0];

        //绑定纹理id，将对象绑定到环境的纹理单元
        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, mTextureId);

        //设置MIN 采样方式
        GLES32.glTexParameterf(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MIN_FILTER, GLES32.GL_NEAREST);

        GLES32.glClearColor(0f, 0f, 0f, 1.0f);
        GLES32.glEnable(GLES32.GL_TEXTURE_2D);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        this.parentWidth = width;
        this.parentHeight = height;

        GLES32.glViewport(0, 0, width, height);
        //通过投影设置，适配横屏
        float sWH = width / (float) height;
        float sWidthHeight = width / (float) height;
        if (width > height) {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight * sWH, sWidthHeight * sWH, -1, 1, 3, 7);
            } else {
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight / sWH, sWidthHeight / sWH, -1, 1, 3, 7);
            }
        } else {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -1 / sWidthHeight * sWH, 1 / sWidthHeight * sWH, 3, 7);
            } else {
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -sWH / sWidthHeight, sWH / sWidthHeight, 3, 7);
            }
        }
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {


        if (ObjectUtils.isEmpty(imgUri)) {
            return;
        }
        try {
            mBitmap = BitmapFactory.decodeStream(mContext.getContentResolver()
                    .openInputStream(imgUri));
        } catch (FileNotFoundException e) {
            LogUtils.e(e);
        }

        if (ObjectUtils.isEmpty(mBitmap)) {
            ToastUtils.showShort("图片初始化错误！");
        } else {
            imgWidth = mBitmap.getWidth();
            imgHeight = mBitmap.getHeight();
        }

        if (mBitmap == null) {
            Log.e("lxb", "initTexture: mBitmap == null");
            return;
        }

        //顶点位置
        float[] vertices = new float[]{
                -(float) imgWidth / parentWidth / 2, (float) imgHeight / parentHeight / 2, 0,
                -(float) imgWidth / parentWidth / 2, -(float) imgHeight / parentHeight / 2, 0,
                (float) imgWidth / parentWidth / 2, (float) imgHeight / parentHeight / 2, 0,
                (float) imgWidth / parentWidth / 2, -(float) imgHeight / parentHeight / 2, 0,
        };

        //纹理顶点数组
        float[] colors = new float[]{
                0, 0,
                0, 1,
                1, 0,
                1, 1,
        };

        //顶点坐标数据要转化成FloatBuffer格式
        FloatBuffer mVertexBuffer = GLUtil.floatArray2FloatBuffer(vertices);
        //顶点纹理坐标缓存
        FloatBuffer mTexCoordBuffer = GLUtil.floatArray2FloatBuffer(colors);

        //加载图片
        GLUtils.texImage2D( //实际加载纹理进显存
                GLES32.GL_TEXTURE_2D, //纹理类型
                0, //纹理的层次，0表示基本图像层，可以理解为直接贴图
                mBitmap, //纹理图像
                0 //纹理边框尺寸
        );

        GLES32.glClearColor(GLES32.GL_COLOR_ATTACHMENT0, 1f, 1f, 1f);

        // 将程序添加到OpenGL ES环境
        GLES32.glUseProgram(mProgram);

        //设置数据
        // 启用顶点属性，最后对应禁用
        GLES32.glEnableVertexAttribArray(vPositionHandle);
        GLES32.glEnableVertexAttribArray(maTexCoordHandle);

        //设置三角形坐标数据（一个顶点三个坐标）
        GLES32.glVertexAttribPointer(vPositionHandle, 3, GLES32.GL_FLOAT, false, 3 * 4, mVertexBuffer);
        //设置纹理坐标数据
        GLES32.glVertexAttribPointer(maTexCoordHandle, 2, GLES32.GL_FLOAT, false, 2 * 4, mTexCoordBuffer);

        // 将投影和视图转换传递给着色器，可以理解为给uMVPMatrix这个变量赋值为mvpMatrix
        GLES32.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES32.glUniform1i(mWidth, imgWidth);
        GLES32.glUniform1i(mHeight, imgHeight);
        GLES32.glUniform1f(mStrength, strength);

        //设置使用的纹理编号
        GLES32.glActiveTexture(GLES32.GL_TEXTURE0);

        // 绘制三角形，三个顶点
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP, 0, 4);

        // 禁用顶点数组（好像不禁用也没啥问题）
        GLES32.glDisableVertexAttribArray(vPositionHandle);
        GLES32.glDisableVertexAttribArray(maTexCoordHandle);

    }

    public void setImageURI(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setStrength(int i) {
        if (i == 0) {
            i = 1;
        }
        this.strength = i;
    }

}
