package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/15
 * 最少翻转次数使二进制矩阵回文 I
 */
public class Solution3239Test {

    @Test
    public void solution3239Test() {
        System.out.println("---------------------------");
        System.out.println(minFlips(new int[][]{{1, 0, 0}, {0, 0, 0}, {0, 0, 1}})); //2
        System.out.println(minFlips(new int[][]{{0, 1}, {0, 1}, {0, 0}})); //1
        System.out.println(minFlips(new int[][]{{1}, {0}})); //0
        System.out.println("---------------------------");
    }

    public int minFlips(int[][] grid) {
        int rowCnt = 0, colCnt = 0;
        int m = grid.length, n = grid[0].length;

        for (int j1 = 0, j2 = m - 1; j1 < j2; j1++, j2--) {
            for (int i = 0; i < n; i++) {
                if (grid[j1][i] != grid[j2][i]) {
                    colCnt++;
                }
            }
        }

        for (int j1 = 0, j2 = n - 1; j1 < j2; j1++, j2--) {
            for (int[] ints : grid) {
                if (ints[j1] != ints[j2]) {
                    rowCnt++;
                }
            }
        }

        return Math.min(rowCnt, colCnt);
    }
}
