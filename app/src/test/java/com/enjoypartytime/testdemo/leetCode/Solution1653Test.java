package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/28
 * 使字符串平衡的最少删除次数
 */
public class Solution1653Test {

    @Test
    public void solution1653Test() {
        System.out.println("---------------------------");
        System.out.println(minimumDeletions("aababbab")); //2
        System.out.println(minimumDeletions("bbaaaaabb")); //2
        System.out.println("---------------------------");
    }

    public int minimumDeletions(String s) {
        int len = s.length();
        while (s.contains("ba")) {
            s = s.replace("ba", "");
        }
        return (len - s.length()) / 2;
    }
}
