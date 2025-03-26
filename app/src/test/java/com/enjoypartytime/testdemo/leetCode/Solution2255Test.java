package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/24
 * 统计是给定字符串前缀的字符串数目
 */
public class Solution2255Test {

    @Test
    public void solution2255Test() {
        System.out.println("---------------------------");
        System.out.println(countPrefixes(new String[]{"a", "b", "c", "ab", "bc", "abc"}, "abc")); //3
        System.out.println(countPrefixes(new String[]{"a", "a"}, "aa")); //2
        System.out.println("---------------------------");
    }

    public int countPrefixes(String[] words, String s) {
        int count = 0;
        for (String word : words) {
            if (s.startsWith(word)) {
                count++;
            }
        }
        return count;
    }
}
