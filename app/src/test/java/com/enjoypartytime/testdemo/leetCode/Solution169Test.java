package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 多数元素
 */
public class Solution169Test {

    @Test
    public void solution169Test() {
        System.out.println("---------------------------");
        System.out.println(majorityElement(new int[]{3, 2, 3})); //3
        System.out.println(majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2})); //2
        System.out.println("---------------------------");
    }

    public int majorityElement(int[] nums) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) > nums.length / 2) {
                res = num;
                break;
            }
        }

        return res;
    }
}
