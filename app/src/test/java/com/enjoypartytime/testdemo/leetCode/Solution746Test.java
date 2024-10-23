package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 使用最小花费爬楼梯
 */
public class Solution746Test {

    @Test
    public void solution1010Test() {
        System.out.println("---------------------------");
        System.out.println(minCostClimbingStairs(new int[]{10, 15, 20})); //15
        System.out.println(minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1})); //6
        System.out.println("---------------------------");
    }

    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        for (int i = 2; i < n; i++) {
            cost[i] = Math.min(cost[i - 1] + cost[i], cost[i - 2] + cost[i]);
        }
        return Math.min(cost[n - 1], cost[n - 2]);
    }

}
