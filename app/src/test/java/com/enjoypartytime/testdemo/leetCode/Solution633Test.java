package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/4
 * 平方数之和
 */
public class Solution633Test {

    @Test
    public void solution633Test() {
        System.out.println("---------------------------");
        System.out.println(judgeSquareSum(5)); //true
        System.out.println(judgeSquareSum(3)); //false
        System.out.println(judgeSquareSum(4)); //true
        System.out.println(judgeSquareSum(2147483600)); //true
        System.out.println("---------------------------");
    }

    public boolean judgeSquareSum(int c) {
        int a = 0, b = (int) Math.sqrt(c);
        while (a <= b) {
            int sum = c - b * b;
            int value = a * a;
            if (sum == value) {
                return true;
            } else if (sum > value) {
                a++;
            } else {
                b--;
            }
        }
        return false;
    }
}
