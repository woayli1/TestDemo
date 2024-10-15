package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;


/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 超过阈值的最少操作数 I
 */
public class Solution3065Test {

    @Test
    public void solution3065Test() {
        System.out.println("---------------------------");
        System.out.println(minOperations(new int[]{2, 11, 10, 1, 3}, 10)); //3
        System.out.println(minOperations(new int[]{1, 1, 2, 4, 9}, 1)); //0
        System.out.println(minOperations(new int[]{1, 1, 2, 4, 9}, 9)); //4
        System.out.println("---------------------------");
    }

    public int minOperations(int[] nums, int k) {
        int length = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < length; i++) {
            if (nums[i] >= k) {
                return i;
            }
        }
        return length;
    }
}
