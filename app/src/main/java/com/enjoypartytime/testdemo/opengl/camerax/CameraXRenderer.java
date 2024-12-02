package com.enjoypartytime.testdemo.opengl.camerax;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceRequest;
import androidx.core.util.Consumer;

import com.blankj.utilcode.util.LogUtils;
import com.enjoypartytime.testdemo.utils.GLUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/26
 */
public class CameraXRenderer implements GLSurfaceView.Renderer, Preview.SurfaceProvider, SurfaceTexture.OnFrameAvailableListener {

    private final Context mContext;
    private SurfaceTexture mCameraTexture;

    private final Callback mCallback;
    private final ExecutorService mExecutor;

    private final int[] textures = new int[1];
    private final float[] mtx = new float[16];

    FloatBuffer vertexBuffer; //顶点坐标
    FloatBuffer textureBuffer; // 纹理坐标

    private int mProgram;
    private int vPosition;
    private int vCoord;
    private int vTexture;
    private int vMatrix;

    private static final float[] VERTEX = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    private static final float[] TEXTURE = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};

    public CameraXRenderer(Context context, Callback mCallback) {
        this.mContext = context;
        this.mCallback = mCallback;
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        LogUtils.i("onSurfaceCreated");
        GLES30.glClearColor(0f, 0f, 0f, 1f);

        GLES30.glGenTextures(textures.length, textures, 0);

        LogUtils.i("texture: " + textures[0]);

        mCameraTexture = new SurfaceTexture(textures[0]);

        //初始化
        vertexBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.clear();
        vertexBuffer.put(VERTEX);

        textureBuffer = ByteBuffer.allocateDirect(4 * 4 * 2).order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureBuffer.clear();
        textureBuffer.put(TEXTURE);

        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, GLUtil.loadFromAssetsFile(mContext, "Shaders/CameraXFilter/camera_vert.glsl"));
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, GLUtil.loadFromAssetsFile(mContext, "Shaders/CameraXFilter/camera_frag.glsl"));

        //创建一个空的OpenGL ES程序
        mProgram = GLES30.glCreateProgram();
        //将顶点着色器加入到程序
        GLES30.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序
        GLES30.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES30.glLinkProgram(mProgram);
        GLES30.glUseProgram(mProgram);

        vPosition = GLES30.glGetAttribLocation(mProgram, "vPosition");
        vCoord = GLES30.glGetAttribLocation(mProgram, "vCoord");
        vTexture = GLES30.glGetUniformLocation(mProgram, "vTexture");
        vMatrix = GLES30.glGetUniformLocation(mProgram, "vMatrix");

        vertexBuffer.position(0);
        GLES30.glVertexAttribPointer(vPosition, 2, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        GLES30.glEnableVertexAttribArray(vPosition);

        textureBuffer.position(0);
        GLES30.glVertexAttribPointer(vCoord, 2, GLES30.GL_FLOAT, false, 0, textureBuffer);
        GLES30.glEnableVertexAttribArray(vCoord);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        LogUtils.i("onSurfaceChanged");
        GLES30.glViewport(0, 0, width, height);
        if (mCallback != null) {
            mCallback.onSurfaceChanged();
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (mCameraTexture == null) {
            return;
        }

        /// glClear(...)填充屏幕使用的颜色，是最后一次调用glClearColor(...)的颜色
        GLES30.glClearColor(1f, 0f, 0f, 1f);
        /// 清空屏幕，擦书屏幕上的所有颜色，并用glClearColor(...)设置的颜色充满整个屏幕
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        mCameraTexture.updateTexImage();
        mCameraTexture.getTransformMatrix(mtx);

        //gpu获取读取
        GLES30.glUniformMatrix4fv(vMatrix, 1, false, mtx, 0);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);
        GLES30.glUniform1i(vTexture, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
    }

    @Override
    public void onSurfaceRequested(@NonNull SurfaceRequest request) {
        LogUtils.i("onSurfaceRequested, request: " + request);
        if (mCameraTexture != null) {
            mCameraTexture.setOnFrameAvailableListener(this);
            mCameraTexture.setDefaultBufferSize(request.getResolution().getWidth(), request
                    .getResolution().getHeight());

            Surface surface = new Surface(mCameraTexture);
            Consumer<SurfaceRequest.Result> resultConsumer = result -> {
                LogUtils.i("resultListener be called!");
                surface.release();
                mCameraTexture.release();
            };

            request.provideSurface(surface, mExecutor, resultConsumer);
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (mCallback != null) {
            mCallback.onFrameAvailable();
        }
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

    public interface Callback {

        void onSurfaceChanged();

        void onFrameAvailable();
    }
}
