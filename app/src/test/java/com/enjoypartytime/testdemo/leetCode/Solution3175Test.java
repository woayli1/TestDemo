package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/24
 * 找到连续赢 K 场比赛的第一位玩家
 */
public class Solution3175Test {

    @Test
    public void solution3175Test() {
        System.out.println("---------------------------");
        System.out.println(findWinningPlayer(new int[]{4, 2, 6, 3, 9}, 2)); //2
        System.out.println(findWinningPlayer(new int[]{2, 5, 4}, 3)); //1
        System.out.println(findWinningPlayer(new int[]{18, 15, 20}, 2)); //2
        System.out.println(findWinningPlayer(new int[]{4, 18, 17, 20, 15, 12, 8, 5}, 1)); //1
        System.out.println("---------------------------");
    }

    public int findWinningPlayer(int[] skills, int k) {
        int res = 0;
        int time = 0;
        for (int i = 1; i < skills.length; i++) {
            if (skills[res] > skills[i]) {
                time++;
            } else {
                res = i;
                time = 1;
            }

            if (time == k) {
                break;
            }
        }
        return res;
    }
}
