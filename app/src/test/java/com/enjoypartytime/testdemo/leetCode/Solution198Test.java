package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/11
 * 打家劫舍
 */
public class Solution198Test {

    @Test
    public void solution198Test() {
        System.out.println("---------------------------");
        System.out.println(rob(new int[]{1, 2, 3, 1})); //4
        System.out.println(rob(new int[]{2, 7, 9, 3, 1})); //12
        System.out.println(rob(new int[]{2, 1, 1, 2})); //4
        System.out.println(rob(new int[]{1, 2, 2, 1})); //3
        System.out.println(rob(new int[]{1, 3, 1, 3, 100})); //103
        System.out.println(rob(new int[]{6, 3, 10, 8, 2, 10, 3, 5, 10, 5, 3})); //39
        System.out.println(rob(new int[]{2, 1, 2, 6, 1, 8, 10, 10})); //26
        System.out.println("---------------------------");
    }

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }

        int[] res = new int[length];
        res[0] = nums[0];
        res[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            res[i] = Math.max(res[i - 2] + nums[i], res[i - 1]);
        }
        return res[length - 1];
    }
}
