package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/21
 * 子字符串的最优划分
 */
public class Solution2405Test {

    @Test
    public void solution2405Test() {
        System.out.println("---------------------------");
        System.out.println(partitionString("abacaba")); //4
        System.out.println(partitionString("ssssss")); //6
        System.out.println("---------------------------");
    }

    public int partitionString(String s) {
        int length = s.length();
        List<Character> list = new ArrayList<>();

        int res = 1;
        for (int i = 0; i < length; i++) {
            Character tmp = s.charAt(i);
            if (list.contains(tmp)) {
                res++;
                list = new ArrayList<>();
            }
            list.add(tmp);
        }

        return res;
    }
}
