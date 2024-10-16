package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/16
 * 最小元素和最大元素的最小平均值
 */
public class Solution3194Test {

    @Test
    public void solution3194Test() {
        System.out.println("---------------------------");
        System.out.println(minimumAverage(new int[]{7, 8, 3, 4, 15, 13, 4, 1})); //5.5
        System.out.println(minimumAverage(new int[]{1, 9, 8, 3, 10, 5})); //5.5
        System.out.println(minimumAverage(new int[]{1, 2, 3, 7, 8, 9})); //5.0
        System.out.println("---------------------------");
    }

    public double minimumAverage(int[] nums) {
        int length = nums.length / 2;
        Arrays.sort(nums);
        double res = Double.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            res = Math.min(res, (double) (nums[i] + nums[length * 2 - i - 1]) / 2);
        }
        return res;
    }
}
