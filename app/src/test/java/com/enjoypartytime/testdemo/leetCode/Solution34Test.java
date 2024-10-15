package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 在排序数组中查找元素的第一个和最后一个位置
 */
public class Solution34Test {

    @Test
    public void solution34Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8))); // [3,4]
        System.out.println(Arrays.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6))); // [-1,-1]
        System.out.println(Arrays.toString(searchRange(new int[]{}, 0))); // [-1,-1]
        System.out.println(Arrays.toString(searchRange(new int[]{1}, 1))); // [0,0]
        System.out.println(Arrays.toString(searchRange(new int[]{2, 2}, 2))); // [0,1]
        System.out.println(Arrays.toString(searchRange(new int[]{-1}, 0))); // [-1,-1]
        System.out.println("---------------------------");
    }

    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0 || nums[0] > target || nums[nums.length - 1] < target) {
            return new int[]{-1, -1};
        }

        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                left = mid;
                right = mid;
            }

            if (nums[left] == target && nums[right] == target) {
                while (left > 0 && nums[left - 1] == target) {
                    left--;
                }
                while (right < nums.length - 1 && nums[right + 1] == target) {
                    right++;
                }
                return new int[]{left, right};
            }

            if (nums[left] > target || nums[right] < target) {
                return new int[]{-1, -1};
            }

        }

        return new int[]{-1, -1};
    }

}
