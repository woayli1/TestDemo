package com.enjoypartytime.testdemo.utils;

import java.util.LinkedList;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/9/6
 */

public class Bezier {

    public static float[] bezier(LinkedList<Float> theArrayX, LinkedList<Float> theArrayY, float t) { //贝塞尔公式调用
        float x = 0;
        float y = 0;
        //控制点数组
        int n = theArrayX.size() - 1;
        int size = theArrayX.size();
        for (int index = 0; index < size; index++) {
            float itemX = theArrayX.get(index);
            float itemY = theArrayY.get(index);
            double pow = Math.pow((1 - t), n - index) * Math.pow(t, index);

            if (index == 0) {
                x += (float) (itemX * pow);
                y += (float) (itemY * pow);
            } else {
                //factorial为阶乘函数
                double value = (double) factorial(n) / factorial(index) / factorial(n - index);
                x += (float) (value * itemX * pow);
                y += (float) (value * itemY * pow);
            }
        }
        return new float[]{x, y};
    }

    public static long factorial(int num) {
        if (num < 0) {
            return -1;
        } else if (num == 0 || num == 1) {
            return 1;
        } else {
            return (num * factorial(num - 1));
        }
    }

    public static int[] rainBow(float t) {
        int red, green, blue;
        if (t < 0.334) {
            red = (int) (255 - t * 3 * 255);
            green = (int) (t * 3 * 255);
            blue = 0;
        } else if (t < 0.667) {
            red = 0;
            green = (int) (255 - (t - 0.334) * 3 * 255);
            blue = (int) ((t - 0.334) * 3 * 255);
        } else {
            red = (int) ((t - 0.667) * 3 * 255);
            green = 0;
            blue = (int) (255 - (t - 0.667) * 3 * 255);
        }
        return new int[]{red, green, blue};
    }

}
