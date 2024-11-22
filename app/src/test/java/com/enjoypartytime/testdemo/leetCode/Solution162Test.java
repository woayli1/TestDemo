package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/15
 * 寻找峰值
 */
public class Solution162Test {

    @Test
    public void solution162Test() {
        System.out.println("---------------------------");
        System.out.println(findPeakElement(new int[]{1, 2, 3, 1})); // 2
        System.out.println(findPeakElement(new int[]{1, 2, 1, 3, 5, 6, 4})); // 1 or 5
        System.out.println("---------------------------");
    }

    public int findPeakElement(int[] nums) {
        int idx = 0, n = nums.length;
        for (int i = 1; i < n; ++i) {
            idx = nums[i] > nums[idx] ? i : idx;
        }
        return idx;
    }
}
