package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 删除有序数组中的重复项 II
 */
public class Solution80Test {

    @Test
    public void solution80Test() {
        System.out.println("---------------------------");
        System.out.println(removeDuplicates(new int[]{1, 1, 1, 2, 2, 3})); //5, nums = [1,1,2,2,3]
        System.out.println(removeDuplicates(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3})); //7, nums = [0,0,1,1,2,3,3]
        System.out.println("---------------------------");
    }

    public int removeDuplicates(int[] nums) {
        int length = nums.length;
        boolean tag = false;
        if (length == 0) {
            return 0;
        }
        int index = 1;
        for (int i = 1; i < length; i++) {
            if (nums[i] == nums[i - 1]) {
                if (!tag) {
                    nums[index++] = nums[i];
                    tag = true;
                }
            }

            if (nums[i] != nums[i - 1]) {
                nums[index++] = nums[i];
                tag = false;
            }
        }

        return index;
    }
}
