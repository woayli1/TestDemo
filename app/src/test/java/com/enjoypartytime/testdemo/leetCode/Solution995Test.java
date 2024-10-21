package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/18
 * K 连续位的最小翻转次数
 */
public class Solution995Test {

    @Test
    public void solution3191Test() {
        System.out.println("---------------------------");
        System.out.println(minKBitFlips(new int[]{0, 1, 0}, 1)); //2
        System.out.println(minKBitFlips(new int[]{1, 1, 0}, 2)); //-1
        System.out.println(minKBitFlips(new int[]{0, 0, 0, 1, 0, 1, 1, 0}, 3)); //3
        System.out.println("---------------------------");
    }

    public int minKBitFlips(int[] nums, int k) {
        int n = nums.length;  // 数组长度
        int flipCnt = 0;      // 当前翻转操作的状态（奇数表示翻转，偶数表示不翻转）
        int result = 0;       // 总的翻转次数
        int[] isFlipped = new int[n];  // 记录翻转的起点

        for (int i = 0; i < n; i++) {
            if (i >= k) {
                // 取消已经超过翻转范围的翻转效果
                flipCnt ^= isFlipped[i - k];  // 超出范围的翻转取消，XOR 操作
            }

            // 判断当前位是否需要翻转（nums[i] ^ flipCnt 模拟翻转效果）
            if (nums[i] == flipCnt) {
                // 当前位为 0，需要翻转从 i 开始的 k 个元素
                if (i + k > n) return -1;  // 如果剩余元素不足 k 个，无法翻转，返回 -1

                flipCnt ^= 1;  // 切换翻转状态
                isFlipped[i] = 1;  // 标记翻转起点
                result++;  // 翻转次数加一
            }
        }

        return result;
    }
}
