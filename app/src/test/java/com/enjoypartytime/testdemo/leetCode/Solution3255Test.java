package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/7
 * 长度为 K 的子数组的能量值 II
 */
public class Solution3255Test {

    @Test
    public void solution3255Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(resultsArray(new int[]{1, 2, 3, 4, 3, 2, 5}, 3))); // [3,4,-1,-1,-1]
        System.out.println(Arrays.toString(resultsArray(new int[]{2, 2, 2, 2, 2}, 4))); // [-1,-1]
        System.out.println(Arrays.toString(resultsArray(new int[]{3, 2, 3, 2, 3, 2}, 2))); //[-1,3,-1,3,-1]
        System.out.println("---------------------------");
    }

    public int[] resultsArray(int[] nums, int k) {
        int length = nums.length;
        int[] res = new int[length - k + 1];
        Arrays.fill(res, -1);
        int tmp = 0;
        for (int i = 0; i < length; i++) {
            tmp = (i == 0 || nums[i] - nums[i - 1] == 1) ? tmp + 1 : 1;

            if (tmp >= k) {
                res[i - k + 1] = nums[i];
            }

        }
        return res;
    }
}
