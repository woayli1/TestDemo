package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 存在重复元素
 */
public class Solution217Test {

    @Test
    public void solution217Test() {
        System.out.println("---------------------------");
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1})); //true
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 4})); //false
        System.out.println("---------------------------");
    }

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            if (!hashSet.add(num)) {
                return true;
            }
        }
        return false;
    }
}
