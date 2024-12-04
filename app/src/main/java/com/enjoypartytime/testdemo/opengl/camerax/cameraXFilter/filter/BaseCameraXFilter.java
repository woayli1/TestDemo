package com.enjoypartytime.testdemo.opengl.camerax.cameraXFilter.filter;

import android.opengl.GLES11Ext;
import android.opengl.GLES30;

import com.enjoypartytime.testdemo.utils.ShaderManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/2
 */
public abstract class BaseCameraXFilter {

    //顶点坐标
    private FloatBuffer vertexBuffer;
    // 纹理坐标
    private FloatBuffer textureBuffer;

    private int mProgram;
    private int vTexture;
    private int vMatrix;

    private static final float[] VERTEX = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    private static final float[] TEXTURE = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};


    public BaseCameraXFilter() {
        init();
    }

    public void init() {

        vertexBuffer = ByteBuffer.allocateDirect(4 * 4 * 2)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.clear();
        vertexBuffer.put(VERTEX);

        textureBuffer = ByteBuffer.allocateDirect(4 * 4 * 2)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureBuffer.clear();
        textureBuffer.put(TEXTURE);

        setProgram();
    }

    public void setProgram() {
        //加载指定的shader文件
        ShaderManager.Param param = getProgram();

        mProgram = param.program;
        int vPosition = param.vPosition;
        int vCoord = param.vCoord;
        vTexture = param.vTexture;
        vMatrix = param.vMatrix;

        vertexBuffer.position(0);
        GLES30.glVertexAttribPointer(vPosition, 2, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        GLES30.glEnableVertexAttribArray(vPosition);

        textureBuffer.position(0);
        GLES30.glVertexAttribPointer(vCoord, 2, GLES30.GL_FLOAT, false, 0, textureBuffer);
        GLES30.glEnableVertexAttribArray(vCoord);
    }

    public void onDrawFrame(int textureId, float[] mtx) {

        GLES30.glUseProgram(mProgram);

        GLES30.glUniformMatrix4fv(vMatrix, 1, false, mtx, 0);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
        /* x取值： 0: GL_TEXTURE0~GL_TEXTURE31+64; n: GL_TEXTUREn(n取值1~95) */
        GLES30.glUniform1i(vTexture, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
    }

    protected abstract ShaderManager.Param getProgram();

}
