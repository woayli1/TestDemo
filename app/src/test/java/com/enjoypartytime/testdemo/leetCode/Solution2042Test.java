package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/28
 * 检查句子中的数字是否递增
 */
public class Solution2042Test {

    @Test
    public void solution2042Test() {
        System.out.println("---------------------------");
        System.out.println(areNumbersAscending("1 box has 3 blue 4 red 6 green and 12 yellow marbles")); //true
        System.out.println(areNumbersAscending("hello world 5 x 5")); //false
        System.out.println(areNumbersAscending("sunset is at 7 51 pm overnight lows will be in the low 50 and 60 s")); //false
        System.out.println(areNumbersAscending("4 5 11 26")); //true
        System.out.println("---------------------------");
    }

    public boolean areNumbersAscending(String s) {
        int tmp = Integer.MIN_VALUE;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                int parsed = Integer.parseInt(s.charAt(i) + "");
                if (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    i++;
                    parsed = parsed * 10 + Integer.parseInt(s.charAt(i) + "");
                }
                if (tmp >= parsed) {
                    return false;
                } else {
                    tmp = parsed;
                }
            }
        }

        return true;
    }

}
