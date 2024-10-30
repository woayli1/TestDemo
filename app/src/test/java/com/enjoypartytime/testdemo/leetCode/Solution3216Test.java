package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/30
 * 交换后字典序最小的字符串
 */
public class Solution3216Test {

    @Test
    public void solution3216Test() {
        System.out.println("---------------------------");
        System.out.println(getSmallestString("45320")); //"43520"
        System.out.println(getSmallestString("001")); //"001"
        System.out.println(getSmallestString("11")); //"11"
        System.out.println("---------------------------");
    }

    public String getSmallestString(String s) {
        char[] res = s.toCharArray();
        for (int i = 1; i < res.length; i++) {
            if (res[i] % 2 == res[i - 1] % 2 && res[i] < res[i - 1]) {
                char tmp = res[i];
                res[i] = res[i - 1];
                res[i - 1] = tmp;
                break;
            }
        }

        return new String(res);
    }

}