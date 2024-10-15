package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 搜索旋转排序数组
 */
public class Solution33Test {

    @Test
    public void solution33Test() {
        System.out.println("---------------------------");
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0)); //4
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3)); //-1
        System.out.println(search(new int[]{1}, 0)); //-1
        System.out.println("---------------------------");
    }

    public int search(int[] nums, int target) {
        int length = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            map.put(nums[i], i);
        }

        if (map.containsKey(target)) {
            return map.get(target);
        } else {
            return -1;
        }
    }
}
