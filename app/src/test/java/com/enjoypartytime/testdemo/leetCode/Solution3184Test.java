package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 构成整天的下标对数目 I
 */
public class Solution3184Test {

    @Test
    public void solution3184Test() {
        System.out.println("---------------------------");
        System.out.println(countCompleteDayPairs(new int[]{12, 12, 30, 24, 24})); //2
        System.out.println(countCompleteDayPairs(new int[]{72, 48, 24, 3})); //3
        System.out.println("---------------------------");
    }


    public int countCompleteDayPairs(int[] hours) {
        int length = hours.length;
        int res = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if ((hours[i] + hours[j]) % 24 == 0) {
                    res++;
                }
            }
        }

        return res;
    }

}
