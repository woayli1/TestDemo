package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/21
 * 最小差值 I
 */
public class Solution908Test {

    @Test
    public void solution908Test() {
        System.out.println("---------------------------");
        System.out.println(smallestRangeI(new int[]{1}, 0)); //0
        System.out.println(smallestRangeI(new int[]{0, 10}, 2)); //6
        System.out.println(smallestRangeI(new int[]{1, 3, 6}, 3)); //0
        System.out.println("---------------------------");
    }

    public int smallestRangeI(int[] nums, int k) {
        Arrays.sort(nums);
        return Math.max(0, nums[nums.length - 1] - nums[0] - 2 * k);
    }
}
