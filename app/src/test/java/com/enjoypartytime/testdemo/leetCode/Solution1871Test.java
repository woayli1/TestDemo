package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/16
 * 跳跃游戏 VII
 */
public class Solution1871Test {

    @Test
    public void solution1871Test() {
        System.out.println("---------------------------");
        System.out.println(canReach("011010", 2, 3)); //true
        System.out.println(canReach("01101110", 2, 3)); //false
        System.out.println(canReach("0000000000", 2, 5)); //true
        System.out.println(canReach("01111111011110", 1, 9)); //true
        System.out.println(canReach("00111010", 3, 5)); //false
        System.out.println(canReach("00111011110", 1, 2)); //false
        System.out.println(canReach("01000110110", 2, 3)); //true
        System.out.println(canReach("011001110001000", 3, 5)); //true
        System.out.println("---------------------------");
    }

    public boolean canReach(String s, int minJump, int maxJump) {
        int length = s.length();
        boolean[] dp = new boolean[length];
        dp[0] = true;

        if (s.charAt(0) == '1' || s.charAt(length - 1) != '0') {
            return false;
        }

        if (length == 1) {
            return true;
        }

        int j = 0;
        for (int i = 0; i < length; i++) {
            if (dp[i]) {
                for (j = Math.max(i + minJump, j); j <= Math.min(i + maxJump, length - 1); j++) {
                    if (s.charAt(j) == '0') {
                        dp[j] = true;
                    }
                }
            }
        }

        return dp[length - 1];
    }
}
