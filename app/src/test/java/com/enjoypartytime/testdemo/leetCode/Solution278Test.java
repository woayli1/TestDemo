package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 第一个错误的版本
 */
public class Solution278Test {

    /* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

    @Test
    public void solution278Test() {
        System.out.println("---------------------------");
        System.out.println(firstBadVersion(5)); //4
        System.out.println(firstBadVersion(1)); //1
        System.out.println("---------------------------");
    }

    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    private boolean isBadVersion(int version) {
        return true;
    }
}
