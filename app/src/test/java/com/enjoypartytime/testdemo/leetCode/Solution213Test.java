package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/11
 * 打家劫舍 II
 */
public class Solution213Test {

    @Test
    public void solution213Test() {
        System.out.println("---------------------------");
        System.out.println(rob(new int[]{2, 3, 2})); //3
        System.out.println(rob(new int[]{1, 2, 3, 1})); //4
        System.out.println(rob(new int[]{1, 2, 3})); //3
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
        int[] res1 = new int[length];
        res[0] = nums[0];
        res[1] = nums[0];
        res1[1] = nums[1];
        for (int i = 2; i < length; i++) {
            res[i] = Math.max(res[i - 2] + nums[i], res[i - 1]);
            res1[i] = Math.max(res1[i - 2] + nums[i], res1[i - 1]);
        }

        return Math.max(res[length - 2], res1[length - 1]);
    }
}
