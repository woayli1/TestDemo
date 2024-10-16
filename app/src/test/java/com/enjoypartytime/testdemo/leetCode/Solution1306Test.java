package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/16
 * 跳跃游戏 III
 */
public class Solution1306Test {

    @Test
    public void solution1306Test() {
        System.out.println("---------------------------");
        System.out.println(canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 5)); //true
        System.out.println(canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 0)); //true
        System.out.println(canReach(new int[]{3, 0, 2, 1, 2}, 2)); //false
        System.out.println("---------------------------");
    }

    public boolean canReach(int[] arr, int start) {
        if (start < 0 || start >= arr.length) {
            return false;
        }
        if (arr[start] == 0) {
            return true;
        }
        if (arr[start] < 0) {
            return false;
        }
        arr[start] = -arr[start];
        return canReach(arr, start + arr[start]) || canReach(arr, start - arr[start]);
    }
}
