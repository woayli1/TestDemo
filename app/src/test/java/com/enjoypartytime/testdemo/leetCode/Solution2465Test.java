package com.enjoypartytime.testdemo.leetCode;

import android.util.ArraySet;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/16
 * 不同的平均值数目
 */
public class Solution2465Test {

    @Test
    public void solution2465Test() {
        System.out.println("---------------------------");
        System.out.println(distinctAverages(new int[]{4, 1, 4, 0, 3, 5})); //2
        System.out.println(distinctAverages(new int[]{1, 100})); //1
        System.out.println(distinctAverages(new int[]{0, 0, 7, 2})); //2
        System.out.println("---------------------------");
    }

    public int distinctAverages(int[] nums) {
        int length = nums.length / 2;
        Arrays.sort(nums);
        Set<Double> set = new HashSet<>();
        for (int i = 0; i < length; i++) {
            double key = (double) (nums[i] + nums[2 * length - i - 1]) / 2;
            set.add(key);
        }

        return set.size();
    }
}
