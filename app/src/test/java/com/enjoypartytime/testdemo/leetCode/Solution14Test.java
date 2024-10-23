package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 最长公共前缀
 */
public class Solution14Test {

    @Test
    public void solution14Test() {
        System.out.println("---------------------------");
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"})); //"fl"
        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"})); //""
        System.out.println("---------------------------");
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (String str : strs) {
            while (str.indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        return prefix;
    }
}
