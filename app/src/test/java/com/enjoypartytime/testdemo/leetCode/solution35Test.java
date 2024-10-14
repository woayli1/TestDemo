package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.TreeSet;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/12
 * 搜索插入位置
 */
public class solution35Test {

    @Test
    public void solution35Test() {
        System.out.println("---------------------------");
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5)); //2
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 2)); //1
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 7)); //4
        System.out.println("---------------------------");
    }

    public int searchInsert(int[] nums, int target) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int num : nums) {
            treeSet.add(num);
        }
        return treeSet.headSet(target).size();
    }
}
