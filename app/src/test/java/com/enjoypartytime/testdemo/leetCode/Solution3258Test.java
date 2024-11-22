package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/12
 * 统计满足 K 约束的子字符串数量 I
 */
public class Solution3258Test {

    @Test
    public void solution3258Test() {
        System.out.println("---------------------------");
        System.out.println(countKConstraintSubstrings("10101", 1)); // 12
        System.out.println(countKConstraintSubstrings("1010101", 2)); // 25
        System.out.println(countKConstraintSubstrings("11111", 1)); // 15
        System.out.println(countKConstraintSubstrings("010110", 1)); // 16
        System.out.println("---------------------------");
    }


    public int countKConstraintSubstrings(String s, int k) {
        int length = s.length();
        int res = length;
        for (int i = 2; i <= length; i++) {
            int left = 0;
            int right = i;
            while (right <= length) {
                String tmp = s.substring(left, right);
                if (tmp.length() - tmp.replace("1", "").length() <= k) {
                    res++;
                } else if (tmp.length() - tmp.replace("0", "").length() <= k) {
                    res++;
                }
                left++;
                right++;
            }
        }

        return res;
    }

}