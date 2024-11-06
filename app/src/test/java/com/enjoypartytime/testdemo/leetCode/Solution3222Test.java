package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/5
 * 求出硬币游戏的赢家
 */
public class Solution3222Test {

    @Test
    public void solution3222Test() {
        System.out.println("---------------------------");
        System.out.println(losingPlayer(2, 7)); //Alice
        System.out.println(losingPlayer(4, 11)); //Bob
        System.out.println("---------------------------");
    }

    public String losingPlayer(int x, int y) {
//        int winner = 0;
//        for (int i = 0; i <= 25; i++) {
//            if (x < 1 || y < 4) {
//                winner = i % 2;
//                break;
//            }
//            x -= 1;
//            y -= 4;
//        }
//
//        return winner == 0 ? "Bob" : "Alice";

        return Math.min(x, y / 4) % 2 != 0 ? "Alice" : "Bob";
    }
}
