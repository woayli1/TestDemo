package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/16
 * 最长回文子串
 */
public class Solution5Test {

    @Test
    public void solution5Test() {
        System.out.println("---------------------------");
        System.out.println(longestPalindrome("babad")); //bab / aba
        System.out.println(longestPalindrome("cbbd")); //bb
        System.out.println(longestPalindrome("a")); //a
        System.out.println(longestPalindrome("abcbda")); //bcb
        System.out.println(longestPalindrome("adavbvd")); //vbv
        System.out.println(longestPalindrome("a12223332221adavbvd")); //12223332221
        System.out.println("---------------------------");
    }

    public String longestPalindrome(String s) {
        int length = s.length();
        int leftTmp = 0, rightTmp = 0;

        if (length < 2) {
            return s;
        }

        for (int left = 0; left < length; left++) {
            for (int right = length - 1; right > left; right--) {
                if (s.charAt(left) == s.charAt(right)) {
                    int m = right;
                    for (int k = left; k < m; k++, m--) {
                        if (s.charAt(k) != s.charAt(m)) {
                            break;
                        }
                        if (k + 2 >= m) {
                            if (right - left > rightTmp - leftTmp) {
                                leftTmp = left;
                                rightTmp = right;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return s.substring(leftTmp, rightTmp + 1);
    }

}
