package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/5
 * 求出胜利玩家的数目
 */
public class Solution3238Test {

    @Test
    public void solution3238Test() {
        System.out.println("---------------------------");
//        System.out.println(winningPlayerCount(4, new int[][]{{0, 0}, {1, 0}, {1, 0}, {2, 1}, {2, 1}, {2, 0}})); //2
//        System.out.println(winningPlayerCount(5, new int[][]{{1, 1}, {1, 2}, {1, 3}, {1, 4}})); //0
//        System.out.println(winningPlayerCount(5, new int[][]{{1, 1}, {2, 4}, {2, 4}, {2, 4}})); //1
        System.out.println(winningPlayerCount(2, new int[][]{{1, 5}, {0, 10}, {1, 4}})); //1
        System.out.println("---------------------------");
    }

    public int winningPlayerCount(int n, int[][] pick) {
        int res = 0;
        int[][] dp = new int[n][11];
        for (int[] ints : pick) {
            dp[ints[0]][ints[1]]++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 11; j++) {
                if (dp[i][j] > i) {
                    res++;
                    break;
                }
            }
        }
        return res;
    }

}
