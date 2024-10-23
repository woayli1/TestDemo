package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 计算应缴税款总额
 */
public class Solution2303Test {

    @Test
    public void solution2303Test() {
        System.out.println("---------------------------");
        System.out.println(calculateTax(new int[][]{{3, 50}, {7, 10}, {12, 25}}, 10)); //2.65
        System.out.println(calculateTax(new int[][]{{1, 0}, {4, 25}, {5, 50}}, 2)); //0.25
        System.out.println(calculateTax(new int[][]{{2, 50}}, 0)); //0
        System.out.println("---------------------------");
    }

    public double calculateTax(int[][] brackets, int income) {
        double tax = 0;
        int tmp = 0;
        for (int[] bracket : brackets) {
            if (income <= bracket[0]) {
                tax += (double) (income - tmp) * bracket[1] / 100;
                return tax;
            }

            tax += (double) (bracket[0] - tmp) * bracket[1] / 100;
            tmp = bracket[0];
        }
        return tax;
    }
}
