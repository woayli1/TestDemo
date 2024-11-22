package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/15
 * 统计数组中峰和谷的数量
 */
public class Solution2210Test {

    @Test
    public void solution2210Test() {
        System.out.println("---------------------------");
        System.out.println(countHillValley(new int[]{2, 4, 1, 1, 6, 5})); // 3
        System.out.println(countHillValley(new int[]{6, 6, 5, 5, 4, 1})); // 0
        System.out.println(countHillValley(new int[]{5, 7, 7, 1, 7})); // 2
        System.out.println("---------------------------");
    }

    public int countHillValley(int[] nums) {
        int res = 0, left = nums[0], length = nums.length;
        for (int i = 1; i < length - 1; i++) {
            if ((nums[i] < left && nums[i] < nums[i + 1]) || (nums[i] > left && nums[i] > nums[i + 1])) {
                res++;
                left = nums[i];
            }
        }
        return res;
    }
}
