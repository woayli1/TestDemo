package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.math.BigInteger;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/21
 * 二进制求和
 */
public class Solution67Test {

    @Test
    public void solution67Test() {
        System.out.println("---------------------------");
        System.out.println(addBinary("11", "1")); //100
        System.out.println(addBinary("1010", "1011")); //10101
        System.out.println("---------------------------");
    }

    public String addBinary(String a, String b) {
//        return Integer.toBinaryString(
//                Integer.parseInt(a, 2) + Integer.parseInt(b, 2)
//        );
        return new BigInteger(a, 2).add(new BigInteger(b, 2)).toString(2);
    }
}
