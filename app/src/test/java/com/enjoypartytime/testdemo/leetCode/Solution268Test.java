package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 丢失的数字
 */
public class Solution268Test {

    @Test
    public void solution268Test() {
        System.out.println("---------------------------");
        System.out.println(missingNumber(new int[]{3, 0, 1})); //2
        System.out.println(missingNumber(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1})); //8
        System.out.println("---------------------------");
    }

    public int missingNumber(int[] nums) {
        int res = 0;
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            res = res - nums[i] + i + 1;
        }
        return res;
    }
}
