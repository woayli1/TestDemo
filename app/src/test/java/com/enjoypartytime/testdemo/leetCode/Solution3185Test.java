package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 构成整天的下标对数目 II
 */
public class Solution3185Test {

    @Test
    public void solution3185Test() {
        System.out.println("---------------------------");
        System.out.println(countCompleteDayPairs(new int[]{12, 12, 30, 24, 24})); //2
        System.out.println(countCompleteDayPairs(new int[]{72, 48, 24, 3})); //3
        System.out.println("---------------------------");
    }


    public long countCompleteDayPairs(int[] hours) {
        final int time = 24;
        int[] dp = new int[time];
        long res = 0;
        for (int hour : hours) {
            res += dp[(time - hour % time) % time];
            dp[hour % time]++;
        }
        return res;
    }
}
