package com.enjoypartytime.testdemo.opengl.camera.bigEye;

import com.enjoypartytime.testdemo.utils.bigeye.BigEyeCameraUtilsKt;
import com.enjoypartytime.testdemo.utils.bigeye.JKalman;
import com.enjoypartytime.testdemo.utils.bigeye.Matrix;
import com.enjoypartytime.testdemo.utils.bigeye.Point;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/18
 */
public class KalmanPointFilter {

    private JKalman kalman;
    private Matrix inputMatrix;

    public KalmanPointFilter() {
        init();
    }

    private void init() {
        try {
            kalman = new JKalman(4, 2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        inputMatrix = new Matrix(2, 1);

        reset();
    }

    public void reset() {
        double[][] tr = new double[][]{{1.0, 0.0, 1.0, 0.0}, {0.0, 1.0, 0.0, 1.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}};
        kalman.setControl_matrix(new Matrix(tr));
        kalman.setError_cov_post(kalman.getError_cov_post().identity());
    }

    public Point filter(Point input) {
        float minDistance = 0.018f;
        Matrix predict = kalman.Predict();
        inputMatrix.set(0, 0, input.getX());
        inputMatrix.set(1, 0, input.getY());
        kalman.Correct(inputMatrix);

        float x = (float) predict.get(0, 0);
        float y = (float) predict.get(1, 0);
        Point predictPoint = new Point(x, y);

        if (BigEyeCameraUtilsKt.distance(predictPoint, input) < minDistance) {
            return predictPoint;
        } else {
            return input;
        }
    }

}
