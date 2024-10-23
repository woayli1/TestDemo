package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 总持续时间可被 60 整除的歌曲
 */
public class Solution1010Test {

    @Test
    public void solution1010Test() {
        System.out.println("---------------------------");
        System.out.println(numPairsDivisibleBy60(new int[]{30, 20, 150, 100, 40})); //3
        System.out.println(numPairsDivisibleBy60(new int[]{60, 60, 60})); //3
        System.out.println("---------------------------");
    }

    public int numPairsDivisibleBy60(int[] time) {
        final int sec = 60;
        int res = 0;
        int[] dp = new int[sec + 1];
        for (int j : time) {
            res += dp[(sec - j % sec) % sec];
            dp[j % sec]++;
        }

        return res;
    }
}
