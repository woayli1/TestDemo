package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/28
 * 第 N 个泰波那契数
 */
public class Solution1137Test {

    @Test
    public void solution1137Test() {
        System.out.println("---------------------------");
        System.out.println(tribonacci(4)); //4
        System.out.println(tribonacci(25)); //1389537
        System.out.println("---------------------------");
    }

    public int tribonacci(int n) {
        return tribonacci(n, new int[n + 1]);
    }

    private int tribonacci(int n, int[] memo) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        if (memo[n] == 0) {
            memo[n] = tribonacci(n - 1, memo) + tribonacci(n - 2, memo) + tribonacci(n - 3, memo);
        }
        return memo[n];
    }
}
