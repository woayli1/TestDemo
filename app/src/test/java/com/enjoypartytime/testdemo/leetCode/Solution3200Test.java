package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 三角形的最大高度
 */
public class Solution3200Test {

    @Test
    public void solution3200Test() {
        System.out.println("---------------------------");
        System.out.println(maxHeightOfTriangle(2, 4)); //3
        System.out.println(maxHeightOfTriangle(2, 1)); //2
        System.out.println(maxHeightOfTriangle(1, 1)); //1
        System.out.println(maxHeightOfTriangle(10, 1)); //2
        System.out.println(maxHeightOfTriangle(4, 9)); //4
        System.out.println(maxHeightOfTriangle(9, 3)); //3
        System.out.println("---------------------------");
    }

//    public int maxHeightOfTriangle(int red, int blue) {
//        int a = 0, b = 0, redTmp = red, blueTmp = blue;
//        for (int i = 1; i < 100; i++) {
//            if (i % 2 == 1) {
//                redTmp = redTmp - i;
//            } else {
//                blueTmp = blueTmp - i;
//            }
//
//            if (redTmp < 0 || blueTmp < 0) {
//                a = i - 1;
//                break;
//            }
//        }
//
//        for (int i = 1; i < 100; i++) {
//            if (i % 2 == 1) {
//                blue = blue - i;
//            } else {
//                red = red - i;
//            }
//
//            if (red < 0 || blue < 0) {
//                b = i - 1;
//                break;
//            }
//        }
//
//        return Math.max(a, b);
//
//    }

    public int maxHeightOfTriangle(int red, int blue) {
        return Math.max(maxHeight(red, blue), maxHeight(blue, red));
    }

    public int maxHeight(int x, int y) {
        int odd = 2 * (int)(Math.sqrt(x)) - 1;
        int even = 2 * (int)((-1 + Math.sqrt(1 + 4 * y)) / 2);
        return Math.min(odd, even) + 1;
    }
}
