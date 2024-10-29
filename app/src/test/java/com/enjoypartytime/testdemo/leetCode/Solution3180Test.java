package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Arrays;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/25
 * 执行操作可获得的最大总奖励 I
 */
public class Solution3180Test {

    @Test
    public void solution3180Test() {
        System.out.println("---------------------------");
        System.out.println(maxTotalReward(new int[]{1, 1, 3, 3})); //4
        System.out.println(maxTotalReward(new int[]{1, 6, 4, 3, 2})); //11
        System.out.println("---------------------------");
    }

    public int maxTotalReward(int[] rewardValues) {
        Arrays.sort(rewardValues);
        int n = rewardValues.length;
        // 用来标记存在的数
        boolean[] dp = new boolean[rewardValues[n - 1] * 2];
        dp[0] = true;
        for (int value : rewardValues) {
            // 遍历可以加的数，可加的全部加，方便后续操作
            for (int i = 0; i < value; i++) {
                if (dp[i]) {
                    // 更新存在的数
                    dp[i + value] = true;
                }
            }
        }
        for (int i = dp.length - 1; i >= 0; i--) {
            // dp数组中最后存在的数一定最大
            if (dp[i]) {
                return i;
            }
        }
        return -1;
    }
}
