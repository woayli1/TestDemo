package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/28
 * 检查是否所有 A 都在 B 之前
 */
public class Solution2124Test {

    @Test
    public void solution2124Test() {
        System.out.println("---------------------------");
        System.out.println(checkString("aaabbb")); //true
        System.out.println(checkString("abab")); //false
        System.out.println(checkString("bbb")); //true
        System.out.println("---------------------------");
    }

    public boolean checkString(String s) {
        if (!s.contains("a") || !s.contains("b")) {
            return true;
        }
        return s.lastIndexOf("a") < s.indexOf("b");
    }

}
