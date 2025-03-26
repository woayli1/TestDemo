package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/24
 * 统计包含给定前缀的字符串
 */
public class Solution2185Test {

    @Test
    public void solution2185Test() {
        System.out.println("---------------------------");
        System.out.println(prefixCount(new String[]{"pay", "attention", "practice", "attend"}, "at")); //2
        System.out.println(prefixCount(new String[]{"leetcode", "win", "loops", "success"}, "code")); //0
        System.out.println("---------------------------");
    }

    public int prefixCount(String[] words, String pref) {
        int res = 0;
        for (String word : words) {
            if (word.startsWith(pref)) {
                res++;
            }
        }
        return res;
    }
}
