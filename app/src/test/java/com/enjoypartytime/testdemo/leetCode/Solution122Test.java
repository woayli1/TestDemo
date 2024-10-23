package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 买卖股票的最佳时机 II
 */
public class Solution122Test {

    @Test
    public void solution121Test() {
        System.out.println("---------------------------");
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); //7
        System.out.println(maxProfit(new int[]{1, 2, 3, 4, 5})); //4
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1})); //0
        System.out.println("---------------------------");
    }

    public int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                max += (prices[i] - prices[i - 1]);
            }
        }
        return max;
    }
}
