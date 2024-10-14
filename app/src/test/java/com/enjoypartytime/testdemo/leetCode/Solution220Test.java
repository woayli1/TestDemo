package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 存在重复元素 III
 */
public class Solution220Test {

    @Test
    public void solution220Test() {
        System.out.println("---------------------------");
        System.out.println(containsNearbyAlmostDuplicate(new int[]{1, 2, 3, 1}, 3, 0)); //true
        System.out.println(containsNearbyAlmostDuplicate(new int[]{1, 5, 9, 1, 5, 9}, 2, 3)); //false
        System.out.println("---------------------------");
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (Math.abs(i - j) <= indexDiff && Math.abs((long) nums[i] - (long) nums[j]) <= valueDiff) {
                    return true;
                }
            }
        }
        return false;
    }
}
