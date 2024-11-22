package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/15
 * 找出峰值
 */
public class Solution2951Test {

    @Test
    public void solution2951Test() {
        System.out.println("---------------------------");
        System.out.println(findPeaks(new int[]{2, 4, 4})); // []
        System.out.println(findPeaks(new int[]{1, 4, 3, 8, 5})); // [1，3]
        System.out.println("---------------------------");
    }

    public List<Integer> findPeaks(int[] mountain) {
        List<Integer> resList = new ArrayList<>();
        int length = mountain.length;
        for (int i = 1; i < length - 1; i++) {
            if (mountain[i] > mountain[i - 1] && mountain[i] > mountain[i + 1]) {
                resList.add(i);
            }
        }
        return resList;
    }

}
