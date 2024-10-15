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
 * 四数之和
 */
public class Solution18Test {

    @Test
    public void solution18Test() {
        System.out.println("---------------------------");
        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0)); //[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
        System.out.println(fourSum(new int[]{2, 2, 2, 2, 2}, 8)); //[[2,2,2,2]]
        System.out.println("---------------------------");
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Set<List<Integer>> resList = new HashSet<>();
        Arrays.sort(nums);
        int length = nums.length;

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                Map<Long, Integer> map = new HashMap<>();
                for (int k = j + 1; k < length; k++) {
                    long sum = (long) target - (long) nums[i] - (long) nums[j] - (long) nums[k];
                    if (map.containsKey(sum)
                            && i != j
                            && i != k
                            && i != map.get(sum)
                            && j != k
                            && j != map.get(sum)
                            && k != map.get(sum)) {

                        List<Integer> tmpList = new ArrayList<>();
                        tmpList.add(nums[i]);
                        tmpList.add(nums[j]);
                        tmpList.add(nums[k]);
                        tmpList.add((int) sum);

                        Collections.sort(tmpList);
                        resList.add(tmpList);
                    } else {
                        map.put((long) nums[k], k);
                    }
                }
            }
        }

        return resList.stream().toList();
    }
}
