package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/24
 * 检查字符串是否为数组前缀
 */
public class Solution1961Test {

    @Test
    public void solution1961Test() {
        System.out.println("---------------------------");
        System.out.println(isPrefixString("iloveleetcode", new String[]{"i", "love", "leetcode", "apples"})); //true
        System.out.println(isPrefixString("iloveleetcode", new String[]{"apples", "i", "love", "leetcode"})); //false
        System.out.println(isPrefixString("", new String[]{"z"})); //true
        System.out.println("---------------------------");
    }

    public boolean isPrefixString(String s, String[] words) {
        for (String word : words) {
            if (s.startsWith(word)) {
                s = s.substring(word.length());
            } else {
                break;
            }
        }

        return s.isEmpty();
    }
}
