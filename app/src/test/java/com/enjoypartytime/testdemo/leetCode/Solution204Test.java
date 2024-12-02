package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/22
 */
public class Solution204Test {

    @Test
    public void solution204Test() {
        System.out.println("---------------------------");
        System.out.println(countPrimes(10)); //4
        System.out.println(countPrimes(0)); //0
        System.out.println(countPrimes(1)); //0
        System.out.println("---------------------------");
    }

    public int countPrimes(int n) {
        if (n <= 1) {
            return 0;
        }

        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }

        return count;

    }

}
