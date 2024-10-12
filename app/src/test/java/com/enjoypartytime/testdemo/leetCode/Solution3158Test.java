package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 求出出现两次数字的 XOR 值
 */
public class Solution3158Test {

    @Test
    public void solution3158Test() {
        System.out.println("---------------------------");
        System.out.println(duplicateNumbersXOR(new int[]{1, 2, 1, 3})); //1
        System.out.println(duplicateNumbersXOR(new int[]{1, 2, 3})); //0
        System.out.println(duplicateNumbersXOR(new int[]{1, 2, 2, 1})); //3
        System.out.println("---------------------------");
    }

    public int duplicateNumbersXOR(int[] nums) {
        int res = 0;

        Set<Integer> integerSet = new HashSet<>();
        for (int num : nums) {
            if (integerSet.contains(num)) {
                res = res ^ num;
            } else {
                integerSet.add(num);
            }
        }
        return res;
    }
}
