package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 跳跃游戏 II
 */
public class Solution45Test {

    @Test
    public void solution45Test() {
        System.out.println("---------------------------");
        System.out.println(jump(new int[]{2, 3, 1, 1, 4})); //2
        System.out.println(jump(new int[]{2, 3, 0, 1, 4})); //2
        System.out.println(jump(new int[]{1})); //0
        System.out.println(jump(new int[]{7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3})); //2
        System.out.println(jump(new int[]{2, 1})); //1
        System.out.println(jump(new int[]{1, 2, 3})); //2
        System.out.println("---------------------------");
    }

    public int jump(int[] nums) {
        int length = nums.length;
        int res = 0;
        int most = 0;
        int rightmost = 0;

        for (int i = 0; i < length - 1; i++) {
            rightmost = Math.max(rightmost, i + nums[i]);
            if (i == most) {
                ++res;
                most = rightmost;
            }
        }
        return res;
    }
}
