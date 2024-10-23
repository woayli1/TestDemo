package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 爬楼梯
 */
public class Solution70Test {

    @Test
    public void solution70Test() {
        System.out.println("---------------------------");
        System.out.println(climbStairs(2)); //2
        System.out.println(climbStairs(3)); //3
        System.out.println(climbStairs(45)); //1836311903
        System.out.println("---------------------------");
    }

    public int climbStairs(int n) {
        return climbStairs2(n, new int[n + 1]);
    }

    private int climbStairs2(int n, int[] memo) {
        if (n <= 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        if (memo[n] > 0) {
            return memo[n];
        }

        memo[n] = climbStairs2(n - 1, memo) + climbStairs2(n - 2, memo);
        return memo[n];
    }
}
