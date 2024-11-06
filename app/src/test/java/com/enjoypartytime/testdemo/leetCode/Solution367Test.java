package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/4
 * 有效的完全平方数
 */
public class Solution367Test {

    @Test
    public void solution633Test() {
        System.out.println("---------------------------");
        System.out.println(isPerfectSquare(16)); //true
        System.out.println(isPerfectSquare(14)); //false
        System.out.println(isPerfectSquare(1)); //true
        System.out.println(isPerfectSquare(2147483647)); //false
        System.out.println(isPerfectSquare(808201)); //true
        System.out.println("---------------------------");
    }

    public boolean isPerfectSquare(int num) {
        if (num == 1) {
            return true;
        }

        int length = num / 2;
        for (int i = length; i >= 2; i--) {
            if (i * i == num) {
                return true;
            }
        }

        return false;
    }
}
