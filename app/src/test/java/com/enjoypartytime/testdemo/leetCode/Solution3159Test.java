package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/27
 */
public class Solution3159Test {

    @Test
    public void solution3159Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(occurrencesOfElement(new int[]{1, 3, 1, 7}, new int[]{1, 3, 2, 4}, 1))); //[0,-1,2,-1]
        System.out.println(Arrays.toString(occurrencesOfElement(new int[]{1, 2, 3}, new int[]{10}, 5))); //[-1]
        System.out.println("---------------------------");
    }

    public int[] occurrencesOfElement(int[] nums, int[] queries, int x) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (x == nums[i]) {
                hashMap.put(hashMap.size(), i);
            }
        }

        for (int i = 0; i < queries.length; i++) {
            Integer integer = hashMap.getOrDefault(queries[i] - 1, -1);
            queries[i] = integer != null ? integer : -1;
        }

        return queries;
    }
}
