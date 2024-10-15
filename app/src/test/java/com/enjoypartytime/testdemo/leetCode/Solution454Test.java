package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/14
 * 四数相加 II
 */
public class Solution454Test {

    @Test
    public void solution454Test() {
        System.out.println("---------------------------");
        System.out.println(fourSumCount(new int[]{1, 2}, new int[]{-2, -1}, new int[]{-1, 2}, new int[]{0, 2})); //2
        System.out.println(fourSumCount(new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0})); //1
        System.out.println("---------------------------");
    }


    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int res = 0;
        int length = nums1.length;
        Map<Integer, Integer> mapA = new HashMap<>();

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                mapA.put(nums1[i] + nums2[j], mapA.getOrDefault(nums1[i] + nums2[j], 0) + 1);
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (mapA.containsKey(-nums3[i] - nums4[j])) {
                    res += mapA.get(-nums3[i] - nums4[j]);
                }
            }
        }

        return res;
    }
}
