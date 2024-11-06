package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/4
 * 特殊元素平方和
 */
public class Solution2778Test {

    @Test
    public void solution2778Test() {
        System.out.println("---------------------------");
        System.out.println(sumOfSquares(new int[]{1, 2, 3, 4})); //21
        System.out.println(sumOfSquares(new int[]{2, 7, 1, 19, 18, 3})); //63
        System.out.println("---------------------------");
    }

    public int sumOfSquares(int[] nums) {
        int length = nums.length;
        int res = 0;
        for (int i = 0; i < length; i++) {
            if (length % (i + 1) == 0) {
                res += nums[i] * nums[i];
            }
        }
        return res;
    }

}
