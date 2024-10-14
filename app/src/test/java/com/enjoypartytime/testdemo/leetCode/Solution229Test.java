package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 多数元素 II
 */
public class Solution229Test {

    @Test
    public void solution229Test() {
        System.out.println("---------------------------");
        System.out.println(majorityElement(new int[]{3, 2, 3}));
        System.out.println(majorityElement(new int[]{1}));
        System.out.println(majorityElement(new int[]{1, 2}));
        System.out.println("---------------------------");
    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int length = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        map.forEach((key, value) -> {
            if(value>length/3){
                res.add(key);
            }
        });

        return res;
    }
}
