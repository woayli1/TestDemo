package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 检查数组对是否可以被 k 整除
 */
public class Solution1497Test {

    @Test
    public void solution1497Test() {
        System.out.println("---------------------------");
        System.out.println(canArrange(new int[]{1, 2, 3, 4, 5, 10, 6, 7, 8, 9}, 5)); //true
        System.out.println(canArrange(new int[]{1, 2, 3, 4, 5, 6}, 7)); //true
        System.out.println(canArrange(new int[]{1, 2, 3, 4, 5, 6}, 10)); //false
        System.out.println("---------------------------");
    }


    public boolean canArrange(int[] arr, int k) {
        long[] dp = new long[k];
        for (int j : arr) {
            dp[(j % k + k) % k]++;
        }

        if (dp[0] % 2 != 0) {
            return false;
        }

        for (int i = 1; i < k; i++) {
            if (dp[i] != dp[k - i]) {
                return false;
            }
        }

        return true;
    }
}
