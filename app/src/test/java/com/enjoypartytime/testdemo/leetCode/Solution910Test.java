package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/21
 * 最小差值 II
 */
public class Solution910Test {

    @Test
    public void solution910Test() {
        System.out.println("---------------------------");
        System.out.println(smallestRangeII(new int[]{1}, 0)); //0
        System.out.println(smallestRangeII(new int[]{0, 10}, 2)); //6
        System.out.println(smallestRangeII(new int[]{1, 3, 6}, 3)); //3
        System.out.println(smallestRangeII(new int[]{7, 8, 8}, 5)); //1
        System.out.println(smallestRangeII(new int[]{4, 7, 4}, 4)); //3
        System.out.println(smallestRangeII(new int[]{4, 8, 2, 7, 2}, 5)); //6
        System.out.println("---------------------------");
    }

    public int smallestRangeII(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, max = nums[n - 1], min = nums[0];
        int res = max - min;
        for (int i = 0; i < n - 1; i++)
            res = Math.min(res, Math.max(max - k, nums[i] + k) - Math.min(min + k, nums[i + 1] - k));

        return res;
    }
}
