package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/29
 * 螺旋矩阵
 */
public class Solution54Test {

    @Test
    public void solution54Test() {
        System.out.println("---------------------------");
        System.out.println(spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})); //[1,2,3,6,9,8,7,4,5]
        System.out.println(spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}})); //[1,2,3,4,8,12,11,10,9,5,6,7]
        System.out.println("---------------------------");
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        int m = matrix.length;
        int n = matrix[0].length;
        int time = 0;           // 第time轮
        while (ans.size() < m * n) {            // 总体边界条件：加头 不加尾
            if (time == m / 2 && m <= n) {      // 如果m<n，则到最后一轮时 只剩一行
                for (int j = time; j < n - time; j++) {
                    ans.add(matrix[time][j]);
                }
                break;
            }
            if (time == n / 2 && n <= m) {      // 如果n<m，则到最后一轮时 只剩一列
                for (int i = time; i < m - time; i++) {
                    ans.add(matrix[i][time]);
                }
                break;
            }                                   // 如果m==n，则可以顺利完成time轮，不需要特殊处理最后一轮的情况
            for (int j = time; j < n - 1 - time; j++) {
                ans.add(matrix[time][j]);
            }
            for (int i = time; i < m - 1 - time; i++) {
                ans.add(matrix[i][n - 1 - time]);
            }
            for (int j = n - 1 - time; j > time; j--) {
                ans.add(matrix[m - 1 - time][j]);
            }
            for (int i = m - 1 - time; i > time; i--) {
                ans.add(matrix[i][time]);
            }
            time++;
        }
        return ans;
    }
}
