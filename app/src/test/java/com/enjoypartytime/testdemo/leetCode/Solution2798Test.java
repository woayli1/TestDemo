package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 满足目标工作时长的员工数目
 */
public class Solution2798Test {

    @Test
    public void solution3065Test() {
        System.out.println("---------------------------");
        System.out.println(numberOfEmployeesWhoMetTarget(new int[]{0, 1, 2, 3, 4}, 2)); //3
        System.out.println(numberOfEmployeesWhoMetTarget(new int[]{5, 1, 4, 2, 2}, 6)); //0
        System.out.println("---------------------------");
    }

    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int length = hours.length;
        Arrays.sort(hours);
        for (int i = length - 1; i >= 0; i--) {
            if (hours[i] < target) {
                return (length - i - 1);
            }
        }
        return length;
    }
}
