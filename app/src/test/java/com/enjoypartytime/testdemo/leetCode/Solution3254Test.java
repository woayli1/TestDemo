package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/6
 * 长度为 K 的子数组的能量值 I
 */
public class Solution3254Test {

    @Test
    public void solution3254Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(resultsArray(new int[]{1, 2, 3, 4, 3, 2, 5}, 3))); // [3,4,-1,-1,-1]
        System.out.println(Arrays.toString(resultsArray(new int[]{2, 2, 2, 2, 2}, 4))); // [-1,-1]
        System.out.println(Arrays.toString(resultsArray(new int[]{3, 2, 3, 2, 3, 2}, 2))); //[-1,3,-1,3,-1]
        System.out.println(Arrays.toString(resultsArray(new int[]{1, 2, 3, 4, 5}, 1))); // [1,2,3,4,5]
        System.out.println(Arrays.toString(resultsArray(new int[]{1, 3, 4}, 2))); // [-1,4]
        System.out.println("---------------------------");
    }

    public int[] resultsArray(int[] nums, int k) {
        int length = nums.length;
        int[] res = new int[length - k + 1];
        if (k == 1) {
            return nums;
        }

        for (int i = 0; i < length - k + 1; i++) {
            for (int j = i + 1; j < i + k; j++) {
                if (nums[j - 1] + 1 != nums[j]) {
                    res[i] = -1;
                    break;
                }
                if (j == i + k - 1) {
                    res[i] = nums[j];
                    break;
                }
            }
        }
        return res;
    }
}
