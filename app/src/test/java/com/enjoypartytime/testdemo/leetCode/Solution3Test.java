package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/21
 * 无重复字符的最长子串
 */
public class Solution3Test {

    @Test
    public void solution3Test() {
        System.out.println("---------------------------");
        System.out.println(lengthOfLongestSubstring("abcabcbb")); //3
        System.out.println(lengthOfLongestSubstring("bbbbb")); //1
        System.out.println(lengthOfLongestSubstring("pwwkew")); //3
        System.out.println(lengthOfLongestSubstring("dvdf")); //3
        System.out.println(lengthOfLongestSubstring("ynyo")); //3
        System.out.println(lengthOfLongestSubstring("ohvhjdml")); //6
        System.out.println("---------------------------");
    }

    public int lengthOfLongestSubstring(String s) {
        int length = s.length();
        List<Character> list = new ArrayList<>();

        int res = 0;
        for (int i = 0; i < length; i++) {
            Character tmp = s.charAt(i);
            if (list.contains(tmp)) {
                res = Math.max(res, list.size());
                list = list.subList(list.indexOf(tmp) + 1, list.size());
            }
            list.add(tmp);

        }
        return Math.max(res, list.size());
    }
}
