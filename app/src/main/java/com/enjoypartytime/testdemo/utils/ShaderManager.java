package com.enjoypartytime.testdemo.utils;

import android.content.Context;
import android.opengl.GLES30;
import android.util.SparseArray;

/**
 * 着色器管理，初始化一次，以后都从缓存取
 */
public class ShaderManager {

    public static final int CAMERA_SHADER_BASE = 1;  //初始效果
    public static final int CAMERA_SHADER_X_2 = 2;  //横向二分屏
    public static final int CAMERA_SHADER_Y_2 = 3;  //纵向二分屏
    public static final int CAMERA_SHADER_NEGATIVE = 4;  //底片效果
    public static final int CAMERA_SHADER_BLACK_WHITE = 5;  //黑白效果
    public static final int CAMERA_SHADER_GRAY = 6;  //灰色效果

    private static SparseArray<Param> mParamSparseArray;

    public static void init(Context context) {

        mParamSparseArray = new SparseArray<>();

        insertParam(CAMERA_SHADER_BASE, GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_vert_base.glsl")
                , GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_frag_base.glsl"));

        insertParam(CAMERA_SHADER_X_2, GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_vert_base.glsl")
                , GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_frag_x_2.glsl"));

        insertParam(CAMERA_SHADER_Y_2, GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_vert_base.glsl")
                , GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_frag_y_2.glsl"));

        insertParam(CAMERA_SHADER_NEGATIVE, GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_vert_base.glsl")
                , GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_frag_negative.glsl"));

        insertParam(CAMERA_SHADER_BLACK_WHITE, GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_vert_base.glsl")
                , GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_frag_black_white.glsl"));

        insertParam(CAMERA_SHADER_GRAY, GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_vert_base.glsl")
                , GLUtil.loadFromAssetsFile(context, "shaders/CameraXFilter/camera_frag_gray.glsl"));

    }

    public static void insertParam(int key, String vertexShaderCode, String fragmentShaderCode) {
        int program = GLUtil.createProgram(vertexShaderCode, fragmentShaderCode);
        // 获取顶点着色器的位置的句柄（这里可以理解为当前绘制的顶点位置）
        int vPosition = GLES30.glGetAttribLocation(program, "vPosition");
        //获取纹理对象
        int vTexture = GLES30.glGetUniformLocation(program, "vTexture");
        //纹理位置句柄
        int vCoord = GLES30.glGetAttribLocation(program, "vCoord");
        // 获取变换矩阵的句柄
        int vMatrix = GLES30.glGetUniformLocation(program, "vMatrix");

        //缓存OpenGL程序
        Param param = new Param(program, vPosition, vCoord, vTexture, vMatrix);
        mParamSparseArray.append(key, param);
    }

    //通过key获取缓存中的OpenGL程序参数
    public static Param getParam(int key) {
        return mParamSparseArray.get(key);
    }

    /**
     * 定义一些要缓存的参数
     */
    public static class Param {
        public Param(int program, int vPosition, int vCoord, int vTexture, int vMatrix) {
            this.program = program;
            this.vPosition = vPosition;
            this.vCoord = vCoord;
            this.vTexture = vTexture;
            this.vMatrix = vMatrix;
        }

        public int program;
        //一些公用的句柄（顶点位置、矩阵、纹理坐标）
        public int vPosition;
        public int vCoord;
        public int vTexture;
        public int vMatrix;
    }
}
