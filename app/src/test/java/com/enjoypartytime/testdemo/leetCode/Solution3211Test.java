package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/29
 * 生成不含相邻零的二进制字符串
 */
public class Solution3211Test {

    @Test
    public void solution3211Test() {
        System.out.println("---------------------------");
        System.out.println(validStrings(3)); // ["010","011","101","110","111"]
        System.out.println(validStrings(2)); // ["01","10","11"]
        System.out.println(validStrings(1)); // ["0","1"]
        System.out.println("---------------------------");
    }

    public List<String> validStrings(int n) {
        return validStrings(n, new ArrayList<>(), "");
    }

    public List<String> validStrings(int n, List<String> res, String str) {
        if (n == 0) {
            res.add(str);
            return res;
        }

        if (str.isEmpty() || str.charAt(str.length() - 1) != '0') {
            validStrings(n - 1, res, str + "0");
        }

        validStrings(n - 1, res, str + "1");

        return res;
    }
}
