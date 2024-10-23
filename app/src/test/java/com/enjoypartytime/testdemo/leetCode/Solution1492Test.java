package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * n 的第 k 个因子
 */
public class Solution1492Test {

    @Test
    public void solution1492Test() {
        System.out.println("---------------------------");
        System.out.println(kthFactor(12, 3)); //3
        System.out.println(kthFactor(7, 2)); //7
        System.out.println(kthFactor(4, 4)); //-1
        System.out.println("---------------------------");
    }

    public int kthFactor(int n, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                list.add(i);
            }
        }

        return list.size() >= k ? list.get(k - 1) : -1;
    }
}
