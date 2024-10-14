package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 移除元素
 */
public class Solution27Test {

    @Test
    public void solution27Test() {
        System.out.println("---------------------------");
        System.out.println(removeElement(new int[]{3, 2, 2, 3}, 3)); //2, nums = [2,2,_,_]
        System.out.println(removeElement(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2)); //5, nums = [0,1,4,0,3,_,_,_]
        System.out.println("---------------------------");
    }

    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        int index = 0;
        for (int i = 0; i < length; i++) {
            if (nums[i] != val) {
                nums[index++] = nums[i];
            }
        }
        return index;
    }
}
