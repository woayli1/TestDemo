package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * Z 字形变换
 */
public class Solution6Test {

    @Test
    public void solution6Test() {
        System.out.println("---------------------------");
        System.out.println(convert("PAYPALISHIRING", 3)); // "PAHNAPLSIIGYIR"
        System.out.println(convert("PAYPALISHIRING", 4)); // "PINALSIGYAHRPI"
        System.out.println("---------------------------");
    }

    public String convert(String s, int numRows) {
        int rowNum = 2 * numRows - 2;
        if (numRows == 1) {
            rowNum = 1;
        }
        StringBuilder res = new StringBuilder();
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < s.length(); i++) {
                if (i % rowNum == j || i % rowNum == rowNum - j) {
                    res.append(s.charAt(i));
                }
            }
        }
        return res.toString();
    }
}
