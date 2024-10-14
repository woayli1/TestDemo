package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/11
 * 优质数对的总数 I
 */
public class Solution3162Test {

    @Test
    public void solution3162Test() {
        System.out.println("---------------------------");
        System.out.println(numberOfPairs(new int[]{1, 3, 4}, new int[]{1, 3, 4}, 1)); //5
        System.out.println(numberOfPairs(new int[]{1, 2, 4, 12}, new int[]{2, 4}, 3)); //2
        System.out.println("---------------------------");
    }

    public int numberOfPairs(int[] nums1, int[] nums2, int k) {
        int res = 0;
        for (int value : nums1) {
            for (int i : nums2) {
                if (value % (i * k) == 0) {
                    res++;
                }
            }
        }
        return res;
    }
}
