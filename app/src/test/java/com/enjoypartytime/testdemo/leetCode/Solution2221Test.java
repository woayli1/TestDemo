package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 数组的三角和
 */
public class Solution2221Test {

    @Test
    public void solution119Test() {
        System.out.println("---------------------------");
        System.out.println(triangularSum(new int[]{1, 2, 3, 4, 5})); //8
        System.out.println(triangularSum(new int[]{5})); //5
        System.out.println("---------------------------");
    }

    public int triangularSum(int[] nums) {
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        } else {
            for (int i = length - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    nums[j] = (nums[j] + nums[j + 1]) % 10;
                }
            }
        }
        return nums[0];
    }
}
