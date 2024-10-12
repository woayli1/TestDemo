package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 只出现一次的数字
 */
public class Solution136Test {

    @Test
    public void solution136Test() {
        System.out.println("---------------------------");
        System.out.println(singleNumber(new int[]{2,2,1})); //1
        System.out.println(singleNumber(new int[]{4,1,2,1,2})); //4
        System.out.println("---------------------------");
    }

    public int singleNumber(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();

        for (int num : nums) {
            if(hashSet.contains(num)){
                hashSet.remove(num);
            }else{
                hashSet.add(num);
            }
        }

        return hashSet.iterator().next();
    }
}
