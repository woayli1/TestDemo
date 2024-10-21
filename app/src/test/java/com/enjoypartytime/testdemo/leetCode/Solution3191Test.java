package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/18
 * 使二进制数组全部等于 1 的最少操作次数 I
 */
public class Solution3191Test {

    @Test
    public void solution3191Test() {
        System.out.println("---------------------------");
        System.out.println(minOperations(new int[]{0, 1, 1, 1, 0, 0})); //3
        System.out.println(minOperations(new int[]{0, 1, 1, 1})); //-1
        System.out.println(minOperations(new int[]{0, 0, 0})); //1
        System.out.println(minOperations(new int[]{1, 0, 0, 1, 1, 0, 1, 1, 1})); //-1
        System.out.println("---------------------------");
    }

    public int minOperations(int[] nums) {
        int res = 0;
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (nums[i] == 0) {
                if (i + 3 > length) {
                    return -1;
                }
                res++;
                nums[i] = 1;
                nums[i + 1] = nums[i + 1] == 1 ? 0 : 1;
                nums[i + 2] = nums[i + 2] == 1 ? 0 : 1;
            }
        }

        return res;
    }
}
