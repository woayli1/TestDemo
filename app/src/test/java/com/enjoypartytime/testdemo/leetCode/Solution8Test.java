package com.enjoypartytime.testdemo.leetCode;

import static java.lang.Character.isDigit;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/28
 * 字符串转换整数 (atoi)
 */
public class Solution8Test {

    @Test
    public void solution8Test() {
        System.out.println("---------------------------");
        System.out.println(myAtoi("42")); //42
        System.out.println(myAtoi(" -042")); //-42
        System.out.println(myAtoi("1337c0d3")); //1337
        System.out.println(myAtoi("0-1")); //0
        System.out.println(myAtoi("words and 987")); //0
        System.out.println(myAtoi("-91283472332")); //-2147483648
        System.out.println(myAtoi("21474836460")); //2147483647
        System.out.println("---------------------------");
    }

    public int myAtoi(String s) {
        if (s.isEmpty()) return 0;
        int index = 0, n = s.length(), sign = 1, res = 0;
        // 处理前置空格
        while (index < n && s.charAt(index) == ' ') {
            ++index;
        }
        // 处理符号
        if (index < n && (s.charAt(index) == '+' || s.charAt(index) == '-')) {
            sign = s.charAt(index++) == '+' ? 1 : -1;
        }
        // 处理数字
        while (index < n && isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';
            // 判断是否溢出
            if (res > (Integer.MAX_VALUE - digit) / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res = res * 10 + digit;
            ++index;
        }
        return res * sign;
    }

}
