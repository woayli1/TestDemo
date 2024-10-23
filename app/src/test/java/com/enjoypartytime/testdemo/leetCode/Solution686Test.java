package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 重复叠加字符串匹配
 */
public class Solution686Test {

    @Test
    public void solution686Test() {
        System.out.println("---------------------------");
        System.out.println(repeatedStringMatch("abcd", "cdabcdab")); //3
        System.out.println(repeatedStringMatch("a", "aa")); //2
        System.out.println(repeatedStringMatch("a", "a")); //1
        System.out.println(repeatedStringMatch("abc", "wxyz")); //-1
        System.out.println(repeatedStringMatch("aa", "a")); //1
        System.out.println("---------------------------");
    }

    public int repeatedStringMatch(String a, String b) {
        int length = a.length() * 2 + b.length();
        int count = 1;
        StringBuilder sb = new StringBuilder(a);
        while (sb.length() <= length) {
            if (sb.indexOf(b) >= 0) {
                return count;
            }
            sb.append(a);
            count++;
        }
        return -1;
    }
}
