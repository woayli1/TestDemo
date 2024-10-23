package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 斐波那契数
 */
public class Solution509Test {

    @Test
    public void solution509Test() {
        System.out.println("---------------------------");
        System.out.println(fib(2)); //1
        System.out.println(fib(3)); //2
        System.out.println(fib(4)); //3
        System.out.println("---------------------------");
    }

    public int fib(int n) {
        return fib(n, new int[n + 1]);
    }

    private int fib(int n, int[] memo) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 1;
        }

        if (memo[n] != 0) {
            return memo[n];
        }

        memo[n] = fib(n - 1, memo) + fib(n - 2, memo);
        return memo[n];
    }
}
