package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.TreeSet;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 买卖股票的最佳时机
 */
public class Solution121Test {

    @Test
    public void solution121Test() {
        System.out.println("---------------------------");
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); //5
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1})); //0
        System.out.println(maxProfit(new int[]{2, 1, 4})); //3
        System.out.println("---------------------------");
    }

    public int maxProfit(int[] prices) {
        int max = 0;
        TreeSet<Integer> set = new TreeSet<>();
        set.add(Integer.MAX_VALUE);
        for (int price : prices) {
            if (price > set.first()) {
                max = Math.max(max, price - set.first());
            }
            set.add(price);
        }

        return max;
    }
}
