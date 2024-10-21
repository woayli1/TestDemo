package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/18
 * 灯泡开关
 */
public class Solution319Test {

    @Test
    public void solution3191Test() {
        System.out.println("---------------------------");
        System.out.println(bulbSwitch(3)); //1
        System.out.println(bulbSwitch(0)); //0
        System.out.println(bulbSwitch(1)); //1
        System.out.println("---------------------------");
    }

    public int bulbSwitch(int n) {
        if (n < 1) return 0;
        return (int) Math.sqrt(n);
    }
}
