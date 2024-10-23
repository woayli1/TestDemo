package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/23
 * 找出字符串中第一个匹配项的下标
 */
public class Solution28Test {

    @Test
    public void solution28Test() {
        System.out.println("---------------------------");
        System.out.println(strStr("sadbutsad", "sad")); //0
        System.out.println(strStr("leetcode", "leeto")); //-1
        System.out.println("---------------------------");
    }

    public int strStr(String haystack, String needle) {
//        return haystack.indexOf(needle);
        int left = 0;
        int right = needle.length();
        while (right <= haystack.length()) {
            if (haystack.substring(left, right).equals(needle)) {
                return left;
            } else {
                left++;
                right++;
            }
        }
        return -1;
    }
}
