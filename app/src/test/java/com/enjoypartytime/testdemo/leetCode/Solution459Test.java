package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 重复的子字符串
 */
public class Solution459Test {

    @Test
    public void solution27Test() {
        System.out.println("---------------------------");
        System.out.println(repeatedSubstringPattern("abab")); // true
        System.out.println(repeatedSubstringPattern("aba")); // false
        System.out.println(repeatedSubstringPattern("abcabcabcabc")); // true
        System.out.println("---------------------------");
    }

    public boolean repeatedSubstringPattern(String s) {
//        return (s + s).indexOf(s, 1) != s.length();
        return (s + s).substring(1, 2 * s.length() - 1).contains(s);
    }
}
