package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 优质数对的总数 II
 */
public class Solution3164Test {

    @Test
    public void solution3164Test() {
        System.out.println("---------------------------");
        System.out.println(numberOfPairs(new int[]{1, 3, 4}, new int[]{1, 3, 4}, 1)); //5
        System.out.println(numberOfPairs(new int[]{1, 2, 4, 12}, new int[]{2, 4}, 3)); //2
        System.out.println("---------------------------");
    }

    public long numberOfPairs(int[] nums1, int[] nums2, int k) {

        Map<Integer, Integer> hashMap = new HashMap<>();
        Map<Integer, Integer> hashMap2 = new HashMap<>();
        int max = 0;
        for (int value : nums1) {
            hashMap.put(value, hashMap.getOrDefault(value, 0) + 1);
            max = Math.max(max, value);
        }

        for (int value : nums2) {
            hashMap2.put(value, hashMap2.getOrDefault(value, 0) + 1);
        }

        long res = 0;
        for (Integer key2 : hashMap2.keySet()) {
            for (int i = key2 * k; i <= max; i += key2 * k) {
                if (hashMap.containsKey(i)) {
                    res += hashMap.get(i) * hashMap2.get(key2);
                }
            }
        }

        return res;
    }
}
