package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/11
 * 两数之和
 */
public class Solution1Test {

    @Test
    public void solution1Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9))); //[0,1]
        System.out.println(Arrays.toString(twoSum(new int[]{3, 2, 4}, 6))); //[1,2]
        System.out.println(Arrays.toString(twoSum(new int[]{3, 3}, 6))); //[0,1]
        System.out.println("---------------------------");
    }

//    public int[] twoSum(int[] nums, int target) {
//        for (int i = 0; i < nums.length; i++) {
//            int tmpValue = target - nums[i];
//            for (int j = i + 1; j < nums.length; j++) {
//                if (tmpValue == nums[j]) {
//                    return new int[]{i, j};
//                }
//            }
//        }
//        return nums;
//    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = map.get(target - nums[i]);
                res[1] = i;
                break;
            }
            map.put(nums[i], i);
        }
        return nums;
    }
}