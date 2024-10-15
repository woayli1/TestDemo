package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.TreeSet;

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
        System.out.println(containsNearbyAlmostDuplicate(new int[]{8, 7, 15, 1, 6, 1, 9, 15}, 1, 3)); //true
        System.out.println(containsNearbyAlmostDuplicate(new int[]{1, 2, 1, 1}, 1, 0)); //true
        System.out.println(containsNearbyAlmostDuplicate(new int[]{2, 2}, 2, 0)); //true
        System.out.println(containsNearbyAlmostDuplicate(new int[]{4, 1, -1, 6, 5}, 3, 1)); //true
        System.out.println("---------------------------");
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        int length = nums.length;
        TreeSet<Long> set = new TreeSet<>();

        for (int i = 0; i < length; i++) {
            if (set.ceiling((long) nums[i] - (long) valueDiff) != null
                    && set.ceiling((long) nums[i] - (long) valueDiff) <= (long) nums[i] + (long) valueDiff)
                return true;

            set.add((long) nums[i]);
            if (set.size() == indexDiff + 1) {
                set.remove((long) nums[i - indexDiff]);
            }
        }
        return false;
    }

}
