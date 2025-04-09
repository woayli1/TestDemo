package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/31
 * 字母在字符串中的百分比
 */
public class Solution2278Test {


    @Test
    public void solution2278Test() {
        System.out.println("---------------------------");
        System.out.println(percentageLetter("foobar", 'o')); //33
        System.out.println(percentageLetter("jjjj", 'k')); //0
        System.out.println("---------------------------");
    }

    public int percentageLetter(String s, char letter) {
        return (int) (s.chars().filter(ch -> ch == letter).count() * 100 / s.length());
//        return s.replaceAll("[^" + letter + "]", "").length() * 100 / s.length();
    }
}
