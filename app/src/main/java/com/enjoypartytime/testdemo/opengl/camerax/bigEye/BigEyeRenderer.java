package com.enjoypartytime.testdemo.opengl.camerax.bigEye;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.enjoypartytime.testdemo.utils.bigeye.BigEyeCameraUtilsKt;
import com.enjoypartytime.testdemo.utils.bigeye.BigEyeUtilsKt;
import com.enjoypartytime.testdemo.utils.bigeye.FaceData;
import com.enjoypartytime.testdemo.utils.bigeye.ImageData;
import com.enjoypartytime.testdemo.utils.bigeye.ImageType;
import com.enjoypartytime.testdemo.utils.bigeye.ImageUtilsKt;
import com.enjoypartytime.testdemo.utils.bigeye.InitData;
import com.enjoypartytime.testdemo.utils.bigeye.Oval;
import com.enjoypartytime.testdemo.utils.bigeye.Point;
import com.enjoypartytime.testdemo.utils.bigeye.ScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kotlin.Pair;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/6
 */
public class BigEyeRenderer extends BigEyeShapeRender {

    private InitData initData;

    private LinkedBlockingDeque<ImageData> pendingRenderFrames;
    private LinkedBlockingDeque<FaceData> pendingRenderFaceData;
    private List<KalmanPointFilter> pointFilters;

    private BigEyeOpenGLView owner;
    private final ScaleType scaleType = ScaleType.CenterCrop;
    // 镜像
    private boolean mirror = true;
    // 绘制脸部框架点
    private boolean renderFaceFrame = true;

    private AtomicReference<Float> enlargeEyesStrength;
//    private AtomicReference<Float> thinFaceStrength;
//    private AtomicReference<Float> whiteningStrength;
//    private AtomicReference<Float> skinSmoothStrength;

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
//        thinFaceStrength = new AtomicReference<>((MIN_THIN_FACE_STRENGTH + MAX_THIN_FACE_STRENGTH) / 2.0f);
//        whiteningStrength = new AtomicReference<>((MIN_WHITENING_STRENGTH + MAX_WHITENING_STRENGTH) / 2.0f);
//        skinSmoothStrength = new AtomicReference<>((MIN_SKIN_SMOOTH_STRENGTH + MAX_SKIN_SMOOTH_STRENGTH) / 2.0f);

        super.onSurfaceCreated(owner, gl, config);

        this.owner = owner;
        Context mContext = this.owner.getContext();

        int cameraProgram = compileShaderFromAssets(mContext, "Shaders/BigEyeVertexShader.glsl", "Shaders/BigEyeFragmentShader.glsl");
        int faceProgram = compileShaderFromAssets(mContext, "Shaders/BigEyeFaceVertexShader.glsl", "Shaders/BigEyeFaceFragmentShader.glsl");
        int cameraVAO = BigEyeUtilsKt.glGenVertexArrays();
        int cameraVBO = BigEyeUtilsKt.glGenBuffers();
        int cameraEBO = BigEyeUtilsKt.glGenBuffers();

        int cameraTexture = BigEyeUtilsKt.glGenTextureAndSetDefaultParams();

        int faceVAO = BigEyeUtilsKt.glGenVertexArrays();
        int faceVBO = BigEyeUtilsKt.glGenBuffers();

        initData = new InitData(cameraProgram, cameraVAO, cameraVBO, cameraEBO, cameraTexture, faceProgram, faceVAO, faceVBO);
    }

    @Override
    public void onDrawFrame(BigEyeOpenGLView owner, GL10 gl) {
        ImageData imageData = pendingRenderFrames.pollFirst();
        if (imageData != null && initData != null) {

            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, initData.getCameraTexture());

            Bitmap bitmap;
            if (imageData.getImageType() == ImageType.NV21) {
                // ImageType.NV21
                bitmap = ImageUtilsKt.nv21ToBitmap(imageData.getImage(), imageData.getWidth(), imageData.getHeight());
            } else {
                // ImageType.RGBA
                bitmap = ImageUtilsKt.rgbaToBitmap(imageData.getImage(), imageData.getWidth(), imageData.getHeight());
            }
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();

            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, initData.getCameraTexture());
            GLES30.glUseProgram(initData.getCameraProgram());

            int rotation = imageData.getRotation() % 360;
            int imageWidth, imageHeight;
            if (90 <= rotation && rotation < 180 || 270 <= rotation) {
                imageWidth = imageData.getHeight();
                imageHeight = imageData.getWidth();
            } else {
                imageWidth = imageData.getWidth();
                imageHeight = imageData.getHeight();
            }

            float imageRadio = (float) imageWidth / (float) imageHeight;
            float renderRadio = (float) width / (float) height;
            Point textureTl, textureRb;
            if (scaleType == ScaleType.CenterFit) {
                // ScaleType.CenterFit
                textureTl = new Point(0.0f, 0.0f);
                textureRb = new Point(1.0f, 1.0f);
            } else {
                // ScaleType.CenterCrop
                Pair<Point, Point> pointPair = BigEyeCameraUtilsKt.centerCropTextureRect(renderRadio / imageRadio, new Point(0.0f, 0.0f), new Point(1.0f, 1.0f));
                textureTl = pointPair.getFirst();
                textureRb = pointPair.getSecond();
            }
            Point positionTl, positionRb;
            if (scaleType == ScaleType.CenterFit) {
                // ScaleType.CenterFit
                Pair<Point, Point> pointPair = BigEyeCameraUtilsKt.centerCropPositionRect(imageRadio, new Point(-1.0f * renderRadio, 1.0f), new Point(renderRadio, -1.0f));
                positionTl = pointPair.getFirst();
                positionRb = pointPair.getSecond();
            } else {
                // ScaleType.CenterCrop
                positionTl = new Point(-1.0f * renderRadio, 1.0f);
                positionRb = new Point(renderRadio, -1.0f);
            }

            float[] textureTopLeft = new float[]{textureTl.getX(), textureTl.getY()};
            float[] textureBottomLeft = new float[]{textureTl.getX(), textureRb.getY()};
            float[] textureTopRight = new float[]{textureRb.getX(), textureTl.getY()};
            float[] textureBottomRight = new float[]{textureRb.getX(), textureRb.getY()};

            Matrix textureTransform = new Matrix();
            Point rotateCenter = BigEyeCameraUtilsKt.centerPoint(textureTl, textureRb);
            textureTransform.setRotate(360f - (float) rotation, rotateCenter.getX(), rotateCenter.getY());
            textureTransform.mapPoints(textureTopLeft);
            textureTransform.mapPoints(textureBottomLeft);
            textureTransform.mapPoints(textureTopRight);
            textureTransform.mapPoints(textureBottomRight);

            float xMin = positionTl.getX();
            float xMax = positionRb.getX();
            float yMin = positionRb.getY();
            float yMax = positionTl.getY();
            float[] cameraVertices = new float[]{
                    xMin, yMax, 0.0f, textureTopLeft[0], textureTopLeft[1], //左上角
                    xMax, yMax, 0.0f, textureTopRight[0], textureTopRight[1], //右上角
                    xMax, yMin, 0.0f, textureBottomRight[0], textureBottomRight[1], //右下角
                    xMin, yMin, 0.0f, textureBottomLeft[0], textureBottomLeft[1], //左下角
                    0.0f};

            GLES30.glBindVertexArray(initData.getCameraVAO());
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, initData.getCameraVBO());
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, cameraVertices.length * 4,
                    BigEyeUtilsKt.toGlBuffer(cameraVertices), GLES30.GL_STREAM_DRAW);
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 5 * 4, 0);
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 5 * 4, 3 * 4);
            GLES30.glEnableVertexAttribArray(1);
            imageData.getImageProxy().close();

            // view
            float[] viewMatrix = BigEyeUtilsKt.newGlFloatMatrix(4);
            android.opengl.Matrix.scaleM(viewMatrix, 0, 1 / renderRadio, 1.0f, 1.0f);

            if (mirror) {
                //镜像显示
                android.opengl.Matrix.rotateM(viewMatrix, 0, 180f, 0f, 1f, 0f);
            }
            GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(initData.getCameraProgram(), "view"), 1, false, viewMatrix, 0);

            // model
            float[] modelMatrix = BigEyeUtilsKt.newGlFloatMatrix(4);
            GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(initData.getCameraProgram(), "model"), 1, false, modelMatrix, 0);

            // transform
            float[] transformMatrix = BigEyeUtilsKt.newGlFloatMatrix(4);
            GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(initData.getCameraProgram(), "transform"), 1, false, transformMatrix, 0);

            FaceData faceData = findFaceData();

            //美颜
            beautify(initData, imageData, faceData, rotation);
            int[] indices = new int[]{0, 1, 2, 2, 3, 0};
            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, initData.getCameraEBO());
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4,
                    BigEyeUtilsKt.toGlBuffer(indices), GLES30.GL_STREAM_DRAW);
            GLES30.glDrawElements(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_INT, 0);

            drawFaceFrame(initData, textureTl, textureRb, viewMatrix, modelMatrix, transformMatrix, xMin, xMax, yMin, yMax, faceData);
        }
    }

    @Override
    public void onViewDestroyed(BigEyeOpenGLView owner) {
        super.onViewDestroyed(owner);
        for (ImageData imageData : pendingRenderFrames) {
            imageData.getImageProxy().close();
        }

        pendingRenderFrames.clear();
        pendingRenderFaceData.clear();
        initData = null;
        pointFilters.clear();
        this.owner = null;
    }

    public void cameraReady(ImageData imageData) {
        if (this.owner != null) {
            try {
                pendingRenderFrames.put(imageData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.owner.requestRender();
        } else {
            imageData.getImageProxy().close();
        }
    }

    public void faceDataReady(FaceData faceData) {
        if (this.owner != null) {
            try {
                pendingRenderFaceData.put(faceData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setEnlargeEyesStrength(float strength) {
        enlargeEyesStrength.set(transformInputStrengthToInternal(strength, MIN_ENLARGE_EYES_STRENGTH, MAX_ENLARGE_EYES_STRENGTH));
    }

    public float getEnlargeEyesStrength() {
        return transformInternalStrengthToInput(enlargeEyesStrength.get(), MIN_ENLARGE_EYES_STRENGTH, MAX_ENLARGE_EYES_STRENGTH);
    }

    private float transformInputStrengthToInternal(float inputStrength, float min, float max) {
        float value = (max - min) * inputStrength / 100.0f + min;
        return Math.max(Math.min(value, max), min);
    }

    public float transformInternalStrengthToInput(float inputStrength, float min, float max) {
        return (inputStrength - min) / (max - min) * 100.0f;
    }

    private void beautify(InitData initData, ImageData imageData, FaceData faceData, int rotation) {
        // 大眼
        float[] leftEyeCenter = new float[]{0.0f, 0.0f};
        float leftEyeAxisA = 0f;
        float leftEyeAxisB = 0f;
        float[] rightEyeCenter = new float[]{0.0f, 0.0f};
        float rightEyeAxisA = 0f;
        float rightEyeAxisB = 0f;
        if (faceData != null && enlargeEyesStrength.get() > MIN_ENLARGE_EYES_STRENGTH) {
            Oval leftOval = BigEyeCameraUtilsKt.rotate(BigEyeCameraUtilsKt.computeFaceTextureOval(faceData.getLeftEyeIrisF()), 360 - rotation, 0.5f, 0.5f);
            leftEyeCenter[0] = leftOval.getCenter().getX();
            leftEyeCenter[1] = leftOval.getCenter().getY();
            leftEyeAxisA = leftOval.getA();
            leftEyeAxisB = leftOval.getB();
            Oval rightOval = BigEyeCameraUtilsKt.rotate(BigEyeCameraUtilsKt.computeFaceTextureOval(faceData.getRightEyeIrisF()), 360 - rotation, 0.5f, 0.5f);
            rightEyeCenter[0] = rightOval.getCenter().getX();
            rightEyeCenter[1] = rightOval.getCenter().getY();
            rightEyeAxisA = rightOval.getA();
            rightEyeAxisB = rightOval.getB();
        }
        GLES30.glUniform2f(GLES30.glGetUniformLocation(initData.getCameraProgram(), "leftEyeCenter"), leftEyeCenter[0], leftEyeCenter[1]);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(initData.getCameraProgram(), "leftEyeAxisA"), leftEyeAxisA);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(initData.getCameraProgram(), "leftEyeAxisB"), leftEyeAxisB);
        GLES30.glUniform2f(GLES30.glGetUniformLocation(initData.getCameraProgram(), "rightEyeCenter"), rightEyeCenter[0], rightEyeCenter[1]);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(initData.getCameraProgram(), "rightEyeAxisA"), rightEyeAxisA);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(initData.getCameraProgram(), "rightEyeAxisB"), rightEyeAxisB);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(initData.getCameraProgram(), "enlargeEyesStrength"), enlargeEyesStrength.get());
    }

    //绘制脸部框架
    private void drawFaceFrame(InitData initData, Point textureTl, Point textureRb, float[] viewMatrix, float[] modelMatrix,
                               float[] transformMatrix, float xMin, float xMax, float yMin, float yMax, FaceData faceData) {
        if (faceData != null && renderFaceFrame) {
            //绘制face frame
            GLES30.glUseProgram(initData.getFaceProgram());
            GLES30.glBindVertexArray(initData.getCameraVAO());
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, initData.getCameraVBO());
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 6 * 4, 0);
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 6 * 4, 3 * 4);
            GLES30.glEnableVertexAttribArray(1);

            float textureRadio = (textureRb.getX() - textureTl.getX()) / (textureRb.getY() - textureTl.getY());
            android.opengl.Matrix.scaleM(viewMatrix, 0, 1 / textureRadio, 1.0f, 1.0f);
            GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(initData.getFaceProgram(), "view"), 1, false, viewMatrix, 0);
            GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(initData.getFaceProgram(), "model"), 1, false, modelMatrix, 0);
            GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(initData.getFaceProgram(), "transform"), 1, false, transformMatrix, 0);
            GLES30.glLineWidth(3f);

            //绘制 Frame
            GLES30.glBindVertexArray(initData.getCameraVAO());
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, initData.getCameraVBO());
            float[] faceVertices = BigEyeCameraUtilsKt.toGlFacePoints(faceData.getFaceFrame(), xMin, xMax, yMin, yMax, 1.0f, 0.0f, 0.0f);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, faceVertices.length * 4, BigEyeUtilsKt.toGlBuffer(faceVertices), GLES30.GL_STREAM_DRAW);
            GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, faceVertices.length / 6);

//            //绘制 脸颊
//            drawFacePoints(initData, faceData.getCheck(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 左眉毛
//            drawFacePoints(initData, faceData.getLeftEyebrow(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 右眉毛
//            drawFacePoints(initData, faceData.getRightEyebrow(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 左眼
//            drawFacePoints(initData, faceData.getLeftEye(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 右眼
//            drawFacePoints(initData, faceData.getRightEye(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 左眼虹膜
//            drawFacePoints(initData, faceData.getLeftEyeIris(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//            drawFacePoints(initData, faceData.getLeftEyeIrisF(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 右眼虹膜
//            drawFacePoints(initData, faceData.getRightEyeIris(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//            drawFacePoints(initData, faceData.getRightEyeIrisF(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 鼻子
//            drawFacePoints(initData, faceData.getNose(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//
//            //绘制 嘴巴
//            drawFacePoints(initData, faceData.getUpLip(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
//            drawFacePoints(initData, faceData.getDownLip(), xMin, xMax, yMin, yMax, 0.0f, 1.0f, 0.0f);
        }
    }

    private void drawFacePoints(InitData initData, Point[] points, float xMin, float xMax,
                                float yMin, float yMax, float colorR, float colorG, float colorB) {
        GLES30.glBindVertexArray(initData.getFaceVAO());
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, initData.getFaceVBO());
        float[] faceVertices = BigEyeCameraUtilsKt.toGlFacePoints(points, xMin, xMax, yMin, yMax, colorR, colorG, colorB);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, faceVertices.length * 4,
                BigEyeUtilsKt.toGlBuffer(faceVertices), GLES30.GL_STREAM_DRAW);
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, faceVertices.length / 6);
    }

    private FaceData findFaceData() {
        FaceData needToFix = pendingRenderFaceData.pollFirst();
        if (needToFix == null) {
            resetPointFilters();
            return null;
        } else {
            return needToFix.copy(System.currentTimeMillis(),
                    filterPoints(needToFix.getFaceFrame(), 0),
                    filterPoints(needToFix.getCheck(), 4),
                    filterPoints(needToFix.getLeftEyebrow(), 73),
                    filterPoints(needToFix.getRightEyebrow(), 89),
                    filterPoints(needToFix.getLeftEye(), 105),
                    filterPoints(needToFix.getRightEye(), 121),
                    filterPoints(needToFix.getLeftEyeIris(), 137),
                    filterPoints(needToFix.getLeftEyeIrisF(), 142),
                    filterPoints(needToFix.getRightEyeIris(), 157),
                    filterPoints(needToFix.getRightEyeIrisF(), 162),
                    filterPoints(needToFix.getNose(), 177),
                    filterPoints(needToFix.getUpLip(), 224),
                    filterPoints(needToFix.getDownLip(), 240));
        }
    }

    private Point[] filterPoints(Point[] points, int filterOffset) {
        Point[] filteredPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            int filterIndex = i + filterOffset;
            if (filterIndex >= 0 && filterIndex < pointFilters.size()) {
                filteredPoints[i] = pointFilters.get(filterIndex).filter(points[i]);
            } else {
                filteredPoints[i] = points[i];
            }
        }
        return filteredPoints;
    }

    private void resetPointFilters() {
        for (KalmanPointFilter filter : pointFilters) {
            filter.reset();
        }
    }
}
