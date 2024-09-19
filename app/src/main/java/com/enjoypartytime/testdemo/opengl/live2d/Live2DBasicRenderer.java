package com.enjoypartytime.testdemo.opengl.live2d;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.live2d.sdk.cubism.framework.CubismFramework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/2
 */
public class Live2DBasicRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;

    public Live2DBasicRenderer(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * モデルディレクトリ名
     */
    private final List<String> modelDir = new ArrayList<>();

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // テクスチャサンプリング設定
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        // 透過設定
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Initialize Cubism SDK framework
        CubismFramework.initialize();

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // 描画範囲指定
        GLES20.glViewport(0, 0, width, height);

        // load models
        modelDir.clear();

        final AssetManager assets = mContext.getResources().getAssets();
        try {
            String[] root = assets.list("");
            if (root != null) {
                for (String subdir : root) {
                    String[] files = assets.list(subdir);
                    String target = subdir + ".model3.json";
                    // フォルダと同名の.model3.jsonがあるか探索する
                    if (files != null) {
                        for (String file : files) {
                            if (file.equals(target)) {
                                modelDir.add(subdir);
                                break;
                            }
                        }
                    }
                }
            }
            Collections.sort(modelDir);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
