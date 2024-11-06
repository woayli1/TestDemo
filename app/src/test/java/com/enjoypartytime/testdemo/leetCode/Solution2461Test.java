package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/6
 * 长度为 K 子数组中的最大和
 */
public class Solution2461Test {

    @Test
    public void solution2461Test() {
        System.out.println("---------------------------");
        System.out.println(maximumSubarraySum(new int[]{1, 5, 4, 2, 9, 9, 9}, 3)); // 15
        System.out.println(maximumSubarraySum(new int[]{4, 4, 4}, 3)); // 0
        System.out.println(maximumSubarraySum(new int[]{1, 2, 2}, 2)); // 3
        System.out.println(maximumSubarraySum(new int[]{1, 1, 1, 1, 1, 1, 1}, 1)); // 1
        System.out.println("---------------------------");
    }

    public long maximumSubarraySum(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        int n = nums.length, i = 0, j = 0;
        long ans = 0, sum = 0;
        while (j < n) {
            while (set.contains(nums[j])) {
                set.remove(nums[i]);
                sum -= nums[i++];
            }
            set.add(nums[j]);
            sum += nums[j];
            if (j - i + 1 == k) {
                ans = Math.max(sum, ans);
                sum -= nums[i];
                set.remove(nums[i++]);
            }
            j++;
        }
        return ans;
    }
}
