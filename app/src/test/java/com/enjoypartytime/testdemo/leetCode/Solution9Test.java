package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 回文数
 */
public class Solution9Test {

    @Test
    public void solution9Test() {
        System.out.println("---------------------------");
        System.out.println(isPalindrome(121)); //true
        System.out.println(isPalindrome(-121)); //false
        System.out.println(isPalindrome(10)); //false
        System.out.println("---------------------------");
    }

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        if (x < 10) {
            return true;
        }

        int res = 0;
        int temp = x;
        while (temp > 0) {
            res = res * 10 + temp % 10;
            temp /= 10;
        }
        return res == x;
    }
}
