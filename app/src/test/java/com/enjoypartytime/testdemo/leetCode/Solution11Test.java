package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 盛最多水的容器
 */
public class Solution11Test {

    @Test
    public void solution11Test() {
        System.out.println("---------------------------");
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})); //49
        System.out.println(maxArea(new int[]{1, 1})); //1
        System.out.println(maxArea(new int[]{1, 2})); //1
        System.out.println("---------------------------");
    }

    public int maxArea(int[] height) {
        int res = 0;
        int length = height.length - 1;
        for (int i = 0; i < length; ) {
            res = Math.max(res, (length - i) * Math.min(height[i], height[length]));
            if (height[i] < height[length]) {
                i++;
            } else {
                length--;
            }
        }

        return res;
    }
}
