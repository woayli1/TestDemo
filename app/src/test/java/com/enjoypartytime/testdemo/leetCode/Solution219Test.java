package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 存在重复元素 II
 */
public class Solution219Test {

    @Test
    public void solution219Test() {
        System.out.println("---------------------------");
        System.out.println(containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3)); //true
        System.out.println(containsNearbyDuplicate(new int[]{1, 0, 1, 1}, 1)); //true
        System.out.println(containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3}, 2)); //false
        System.out.println("---------------------------");
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (map.containsKey(nums[i]) && Math.abs(i - map.get(nums[i])) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }
}
