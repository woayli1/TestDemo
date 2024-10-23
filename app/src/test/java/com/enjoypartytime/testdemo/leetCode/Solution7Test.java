package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 整数反转
 */
public class Solution7Test {

    @Test
    public void solution7Test() {
        System.out.println("---------------------------");
        System.out.println(reverse(123)); // 321
        System.out.println(reverse(-123)); // -321
        System.out.println(reverse(120)); // 21
        System.out.println(reverse(0)); // 0
        System.out.println("---------------------------");
    }

    public int reverse(int x) {
        String tmp = String.valueOf(x);
        if (tmp.length() == 1) {
            return x;
        }
        if (x < 0) {
            tmp = tmp.replace("-", "");
        }
        StringBuilder res = new StringBuilder();
        for (int i = tmp.length() - 1; i >= 0; i--) {
            res.append(tmp.charAt(i));
        }
        if (x < 0) {
            res.insert(0, "-");
        }
        if (Long.parseLong(res.toString()) > Integer.MAX_VALUE || Long.parseLong(res.toString()) < Integer.MIN_VALUE) {
            return 0;
        }
        return Integer.parseInt(res.toString());
    }
}
