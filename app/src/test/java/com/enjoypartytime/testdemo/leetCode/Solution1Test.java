package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/11
 * 两数之和
 */
public class Solution1Test {

    @Test
    public void solution1Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(twoSum(new int[]{3, 2, 4}, 6)));
        System.out.println(Arrays.toString(twoSum(new int[]{3, 3}, 6)));
        System.out.println("---------------------------");
    }

    public int[] twoSum(int[] numbs, int target) {
        for (int i = 0; i < numbs.length; i++) {
            int tmpValue = target - numbs[i];
            for (int j = i + 1; j < numbs.length; j++) {
                if (tmpValue == numbs[j]) {
                    return new int[]{i, j};
                }
            }
        }
        return numbs;
    }
}