package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;
import java.util.WeakHashMap;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 只出现一次的数字 III
 */
public class Solution260Test {

    @Test
    public void solution260Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(singleNumber(new int[]{1, 2, 1, 3, 2, 5}))); // [3,5] 或 [5, 3]
        System.out.println(Arrays.toString(singleNumber(new int[]{-1, 0}))); // [-1,0]
        System.out.println(Arrays.toString(singleNumber(new int[]{0, 1}))); // [1,0]
        System.out.println("---------------------------");
    }

    public int[] singleNumber(int[] nums) {
        int[] res = new int[2];

        WeakHashMap<Integer, Integer> map = new WeakHashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int index = 0;
        for (Integer key : map.keySet()) {
            if (map.get(key) == 1) {
                res[index++] = key;
            }
        }

        return res;
    }
}
