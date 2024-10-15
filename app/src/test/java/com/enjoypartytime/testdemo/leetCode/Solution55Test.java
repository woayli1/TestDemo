package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 跳跃游戏
 */
public class Solution55Test {

    @Test
    public void solution55Test() {
        System.out.println("---------------------------");
        System.out.println(canJump(new int[]{2, 3, 1, 1, 4})); //true
        System.out.println(canJump(new int[]{3, 2, 1, 0, 4})); //false
        System.out.println("---------------------------");
    }

    public boolean canJump(int[] nums) {
        int length = nums.length;
        int rightmost = 0;

        for (int i = 0; i < length; i++) {
            if (rightmost < i) {
                return false;
            }
            rightmost = Math.max(rightmost, i + nums[i]);
        }
        return true;
    }
}
