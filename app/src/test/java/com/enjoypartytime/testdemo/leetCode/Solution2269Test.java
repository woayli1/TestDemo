package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/10
 * 找到一个数字的 K 美丽值
 */
public class Solution2269Test {

    @Test
    public void solution2269Test() {
        System.out.println("---------------------------");
        System.out.println(divisorSubstrings(240, 2)); //2
        System.out.println(divisorSubstrings(430043, 2)); //2
        System.out.println("---------------------------");
    }

    public int divisorSubstrings(int num, int k) {
        String str = String.valueOf(num);
        int res = 0;
        for (int i = 0; i <= str.length() - k; i++) {
            String tmp = str.substring(i, i + k);
            if (tmp.equals("0") || Integer.parseInt(tmp) == 0) {
                continue;
            }
            if (num % Integer.parseInt(tmp) == 0) {
                res++;
            }
        }
        return res;
    }
}
