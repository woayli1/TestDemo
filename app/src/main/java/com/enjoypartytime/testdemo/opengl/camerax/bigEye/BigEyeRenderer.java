package com.enjoypartytime.testdemo.opengl.camerax.bigEye;

import android.content.Context;

import com.enjoypartytime.testdemo.utils.bigeye.FaceData;
import com.enjoypartytime.testdemo.utils.GLUtil;
import com.enjoypartytime.testdemo.utils.bigeye.ImageData;
import com.enjoypartytime.testdemo.utils.bigeye.InitData;
import com.enjoypartytime.testdemo.utils.bigeye.ScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/6
 */
public class BigEyeRenderer extends BigEyeShapeRender {

    private InitData initData;
    private Context mContext;

    private LinkedBlockingDeque<ImageData> pendingRenderFrames;
    private LinkedBlockingDeque<FaceData> pendingRenderFaceData;
    private List<KalmanPointFilter> pointFilters;

    private BigEyeOpenGLView owner;
    private ScaleType scaleType = ScaleType.CenterCrop;
    private boolean mirror = true;
    private boolean renderFaceFrame = false;

    private AtomicReference<Float> enlargeEyesStrength;
    private AtomicReference<Float> thinFaceStrength;
    private AtomicReference<Float> whiteningStrength;
    private AtomicReference<Float> skinSmoothStrength;

    private static final float MIN_ENLARGE_EYES_STRENGTH = 0.0f;
    private static final float MAX_ENLARGE_EYES_STRENGTH = 30.0f;

    private static final float MIN_THIN_FACE_STRENGTH = 1.0f;
    private static final float MAX_THIN_FACE_STRENGTH = 60.0f;

    private static final float MIN_WHITENING_STRENGTH = 1.0f;
    private static final float MAX_WHITENING_STRENGTH = 6.0f;

    private static final float MIN_SKIN_SMOOTH_STRENGTH = 0.0f;
    private static final float MAX_SKIN_SMOOTH_STRENGTH = 15.0f;

    @Override
    public void onSurfaceCreated(BigEyeOpenGLView owner, GL10 gl, EGLConfig config) {
        isActive = new AtomicBoolean(false);
        width = 0;
        height = 0;

        pendingRenderFrames = new LinkedBlockingDeque<>();
        pendingRenderFaceData = new LinkedBlockingDeque<>();
        pointFilters = new ArrayList<>(256);

        enlargeEyesStrength = new AtomicReference<>((MIN_ENLARGE_EYES_STRENGTH + MAX_ENLARGE_EYES_STRENGTH) / 2.0f);
        thinFaceStrength = new AtomicReference<>((MIN_THIN_FACE_STRENGTH + MAX_THIN_FACE_STRENGTH) / 2.0f);
        whiteningStrength = new AtomicReference<>((MIN_WHITENING_STRENGTH + MAX_WHITENING_STRENGTH) / 2.0f);
        skinSmoothStrength = new AtomicReference<>((MIN_SKIN_SMOOTH_STRENGTH + MAX_SKIN_SMOOTH_STRENGTH) / 2.0f);

        super.onSurfaceCreated(owner, gl, config);

        this.owner = owner;
        mContext = this.owner.getContext();

        int cameraProgram = compileShaderFromAssets(mContext, "Shaders/BigEyeVertexShader.glsl", "Shaders/BigEyeFragmentShader.glsl");
        int faceProgram = compileShaderFromAssets(mContext, "Shaders/BigEyeFaceVertexShader.glsl", "Shaders/BigEyeFaceFragmentShader.glsl");
    }

    @Override
    public void onDrawFrame(BigEyeOpenGLView owner, GL10 gl) {

    }

}
