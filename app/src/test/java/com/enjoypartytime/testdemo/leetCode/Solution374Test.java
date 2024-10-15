package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 猜数字大小
 */
public class Solution374Test {

    /**
     * Forward declaration of guess API.
     *
     * @param num your guess
     * @return -1 if num is higher than the picked number
     * 1 if num is lower than the picked number
     * otherwise return 0
     * int guess(int num);
     */

    @Test
    public void solution374Test() {
        System.out.println("---------------------------");
        System.out.println(guessNumber(5)); // pick 6 6
        System.out.println(guessNumber(1)); // pick 1 1
        System.out.println("---------------------------");
    }

    public int guessNumber(int n) {
        int left = 1;
        int right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (guess(mid) == -1) {
                right = mid;
            } else if (guess(mid) == 1) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }


    private int guess(int version) {
        return -1;
    }
}
