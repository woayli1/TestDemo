package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/14
 * 三数之和
 */
public class Solution15Test {

    @Test
    public void solution15Test() {
        System.out.println("---------------------------");
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4})); //[[-1,-1,2],[-1,0,1]]
        System.out.println(threeSum(new int[]{0, 1, 1})); //[]
        System.out.println(threeSum(new int[]{0, 0, 0})); //[[0,0,0]]
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4})); //[[-4,0,4],[-4,1,3],[-3,-1,4],[-3,0,3],[-3,1,2],[-2,-1,3],[-2,0,2],[-1,-1,2],[-1,0,1]]
        System.out.println(threeSum(new int[]{-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0})); //[[-5,1,4],[-4,0,4],[-4,1,3],[-2,-2,4],[-2,1,1],[0,0,0]]
        System.out.println("---------------------------");
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> resList = new HashSet<>();
        Arrays.sort(nums);
        int length = nums.length;

        for (int i = 0; i < length; i++) {
            if (i < length - 2 && (nums[i] + nums[i + 1] + nums[i + 2]) > 0) {
                break;
            }
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < length; j++) {
                if (map.containsKey(0 - nums[i] - nums[j])
                        && i != j
                        && i != map.get((0 - nums[i] - nums[j]))
                        && j != map.get((0 - nums[i] - nums[j]))) {

                    List<Integer> tmpList = new ArrayList<>();
                    tmpList.add(nums[i]);
                    tmpList.add(nums[j]);
                    tmpList.add((0 - nums[i] - nums[j]));

                    Collections.sort(tmpList);
                    resList.add(tmpList);
                } else {
                    map.put(nums[j], j);
                }
            }
        }

        return resList.stream().toList();
    }
}
