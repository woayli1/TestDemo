package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/30
 * 最大交换
 */
public class Solution670Test {

    @Test
    public void solution670Test() {
        System.out.println("---------------------------");
        System.out.println(maximumSwap(2736)); //7236
        System.out.println(maximumSwap(9973)); //9973
        System.out.println(maximumSwap(98368)); //98863
        System.out.println(maximumSwap(99901)); //99910
        System.out.println("---------------------------");
    }

    public int maximumSwap(int num) {
        String str = String.valueOf(num);
        for (int i = 9; i > 0; i--) {
            int position = str.lastIndexOf(i + "");
            if (position > 0) {
                for (int j = 0; j < position; j++) {
                    if (Integer.parseInt(String.valueOf(str.charAt(j))) < i) {
                        String sb = str.substring(0, j) +
                                i +
                                str.substring(j + 1, position) +
                                str.charAt(j) +
                                str.substring(position + 1);
                        return Integer.parseInt(sb);
                    }
                }
            }
        }
        return num;
    }
}
