package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.WeakHashMap;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 只出现一次的数字 II
 */
public class Solution137Test {

    @Test
    public void solution137Test() {
        System.out.println("---------------------------");
        System.out.println(singleNumber(new int[]{2, 2, 3, 2})); //3
        System.out.println(singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99})); //99
        System.out.println("---------------------------");
    }

    public int singleNumber(int[] nums) {
        int res = 0;

        WeakHashMap<Integer, Integer> map = new WeakHashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (Integer key : map.keySet()) {
            if (map.get(key) == 1) {
                res = key;
                break;
            }
        }

        return res;
    }
}
