package com.enjoypartytime.testdemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLUtil {

    private static final String TAG = "ShaderUtil";

    public static int createProgram(String vertexShaderCode, String fragmentShaderCode) {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        Log.d(TAG, "createProgram: vertexShader =" + vertexShader);
        Log.d(TAG, "createProgram: fragmentShader =" + fragmentShader);
        int program = GLES30.glCreateProgram();
        Log.d(TAG, "createProgram: program=" + program);
        // 添加顶点着色器到程序中
        GLES30.glAttachShader(program, vertexShader);
        // 添加片段着色器到程序中
        GLES30.glAttachShader(program, fragmentShader);
        //链接程序
        GLES30.glLinkProgram(program);
        return program;
    }

    public static int loadShader(int shaderType, String source) {
        // 创造顶点着色器类型(GLES30.GL_VERTEX_SHADER)
        // 或者是片段着色器类型 (GLES30.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(shaderType);
        checkGlError("glCreateShader type=" + shaderType);
        // 添加上面编写的着色器代码并编译它
        GLES30.glShaderSource(shader, source);
        GLES30.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, "Could not compile shader " + shaderType + ":");
            Log.e(TAG, " " + GLES30.glGetShaderInfoLog(shader));
            GLES30.glDeleteShader(shader);
        }
        return shader;
    }

    /**
     * Checks to see if a GLES error has been raised.
     */
    private static void checkGlError(String op) {
        int error = GLES30.glGetError();
        if (error != GLES30.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.e(TAG, msg);
            throw new RuntimeException(msg);
        }
    }

    public static IntBuffer intArray2IntBuffer(int[] arr) {
        IntBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    /**
     * float 数组转换成FloatBuffer，OpenGL才能使用
     *
     * @param arr float[]
     * @return FloatBuffer
     */
    public static FloatBuffer floatArray2FloatBuffer(float[] arr) {
        FloatBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    public static ShortBuffer shortArray2ShortBuffer(short[] arr) {
        ShortBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*2，因为一个short占2个字节
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                arr.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        mBuffer = dlb.asShortBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    public static int createProgram(Resources res, String vertexRes, String fragmentRes) {
        return createProgram(loadFromAssetsFile(vertexRes, res), loadFromAssetsFile(fragmentRes, res));
    }

    public static String loadFromAssetsFile(String fname, Resources res) {
        StringBuilder result = new StringBuilder();
        try (InputStream is = res.getAssets().open(fname)) {
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (IOException ignore) {
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }

    public static String loadFromAssetsFile(Context context, String fileName) {
        StringBuilder result = new StringBuilder();
        try (InputStream is = context.getResources().getAssets().open(fileName)) {
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (IOException ignore) {
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }

    /**
     * 相机预览使用EXTERNAL_OES纹理，创建方式与2D纹理创建基本相同
     *
     * @return int
     */
    public static int getOESTextureId() {
        int[] texture = new int[1];
        GLES30.glGenTextures(1, texture, 0);
        //于我们创建的是扩展纹理，所以绑定的时候我们也需要绑定到扩展纹理上才可以正常使用，
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return texture[0];
    }
}
