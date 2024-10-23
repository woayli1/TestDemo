package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 */
public class Solution88Test {

    @Test
    public void solution20Test() {
        System.out.println("---------------------------");
        int[] num1 = new int[]{1, 2, 3, 0, 0, 0};
        merge(num1, 3, new int[]{2, 5, 6}, 3);
        System.out.println(Arrays.toString(num1)); //[1,2,2,3,5,6]

        num1 = new int[]{1};
        merge(num1, 1, new int[]{}, 0);
        System.out.println(Arrays.toString(num1)); //[1]

        num1 = new int[]{0};
        merge(num1, 0, new int[]{1}, 1);
        System.out.println(Arrays.toString(num1)); //[1]

        System.out.println("---------------------------");
    }

//    public void merge(int[] nums1, int m, int[] nums2, int n) {
//        for (int i = m; i < m + n; i++) {
//            nums1[i] = nums2[i - m];
//        }
//        Arrays.sort(nums1);
//    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }
        if (m == 0) {
            if (n >= 0) System.arraycopy(nums2, 0, nums1, 0, n);
            return;
        }
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p--] = nums1[p1--];
                nums1[p1 + 1] = 0;
            } else {
                nums1[p--] = nums2[p2--];
            }
        }
    }
}
