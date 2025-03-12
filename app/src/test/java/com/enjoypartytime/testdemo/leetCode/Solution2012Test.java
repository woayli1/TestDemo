package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/11
 * 数组美丽值求和
 */
public class Solution2012Test {

    @Test
    public void solution2012Test() {
        System.out.println("---------------------------");
        System.out.println(sumOfBeauties(new int[]{1, 2, 3})); //2
        System.out.println(sumOfBeauties(new int[]{2, 4, 6, 4})); //1
        System.out.println(sumOfBeauties(new int[]{3, 2, 1})); //0
        System.out.println(sumOfBeauties(new int[]{2, 2, 6})); //0
        System.out.println(sumOfBeauties(new int[]{6, 8, 3, 7, 8, 9, 4, 8})); //2
        System.out.println(sumOfBeauties(new int[]{1, 2, 3, 4, 5, 7, 8, 9, 10})); //14
        System.out.println(sumOfBeauties(new int[]{60, 60, 60})); //0
        System.out.println("---------------------------");
    }

    public int sumOfBeauties(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n];
        int[] suf = new int[n];
        pre[0] = nums[0];
        suf[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            pre[i] = Math.max(pre[i - 1], nums[i]);
        }
        for (int i = n - 2; i >= 0; i--) {
            suf[i] = Math.min(suf[i + 1], nums[i]);
        }
        int res = 0;
        for (int i = 1; i < n - 1; i++) {
            if (pre[i - 1] < nums[i] && nums[i] < suf[i + 1]) {
                res += 2;
            } else if (nums[i - 1] < nums[i] && nums[i] < nums[i + 1]) {
                res++;
            }
        }
        return res;
    }
}
