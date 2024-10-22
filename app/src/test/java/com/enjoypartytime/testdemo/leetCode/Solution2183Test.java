package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 统计可以被 K 整除的下标对数目
 */
public class Solution2183Test {

    @Test
    public void solution2183Test() {
        System.out.println("---------------------------");
        System.out.println(countPairs(new int[]{1, 2, 3, 4, 5}, 2)); //7
        System.out.println(countPairs(new int[]{1, 2, 3, 4}, 5)); //0
        System.out.println(countPairs(new int[]{8, 10, 2, 5, 9, 6, 3, 8, 2}, 6)); //18
        System.out.println("---------------------------");
    }


    public long countPairs(int[] nums, int k) {
        List<Integer> divisors = new ArrayList<>(); // 预处理 k 的所有因子
        for (int d = 1; d * d <= k; d++) {
            if (k % d == 0) {
                divisors.add(d);
                if (d * d < k) divisors.add(k / d);
            }
        }
        long ans = 0L;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int v : nums) {
            ans += cnt.getOrDefault(k / gcd(v, k), 0);
            for (int d : divisors)
                if (v % d == 0)
                    cnt.put(d, cnt.getOrDefault(d, 0) + 1);
        }
        return ans;
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
