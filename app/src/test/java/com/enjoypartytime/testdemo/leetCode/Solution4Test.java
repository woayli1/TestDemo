package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 寻找两个正序数组的中位数
 */
public class Solution4Test {

    @Test
    public void solution4Test() {
        System.out.println("---------------------------");
        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2})); //2.0
        System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})); //2.5
        System.out.println("---------------------------");
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int[] tmpNums = new int[length1 + length2];
        System.arraycopy(nums1, 0, tmpNums, 0, length1);
        System.arraycopy(nums2, 0, tmpNums, length1, length2);

        Arrays.sort(tmpNums);

        if (tmpNums.length % 2 == 0) {
            return (tmpNums[tmpNums.length / 2] + tmpNums[tmpNums.length / 2 - 1]) / 2.0;
        } else {
            return tmpNums[tmpNums.length / 2];
        }
    }
}
