package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 最接近的三数之和
 */
public class Solution16Test {

    @Test
    public void solution16Test() {
        System.out.println("---------------------------");
        System.out.println(threeSumClosest(new int[]{-1, 2, 1, -4}, 1)); //2
        System.out.println(threeSumClosest(new int[]{0, 0, 0}, 1)); //0
        System.out.println(threeSumClosest(new int[]{2, 3, 8, 9, 10}, 16)); //15
        System.out.println("---------------------------");
    }

    public int threeSumClosest(int[] nums, int target) {
        int length = nums.length;
        Arrays.sort(nums);
        int closeSum = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < length - 2; i++) {
            if (nums[i] + nums[i + 1] + nums[i + 2] > target) {
                break;
            }

            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                if (nums[i] + nums[right - 2] + nums[right - 1] < target) {
                    break;
                }

                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(closeSum - target)) {
                    closeSum = sum;
                }
                if (sum > target) {
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    return target;
                }
            }
        }
        return closeSum;
    }
}
