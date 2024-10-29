package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/25
 * 组合总和 II
 */
public class Solution40Test {

    @Test
    public void solution40Test() {
        System.out.println("---------------------------");
        System.out.println(combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8)); //[[1,1,6],[1,2,5],[1,7],[2,6]]
        System.out.println(combinationSum2(new int[]{2, 5, 2, 1, 2}, 5)); //[[1,2,2],[5]]
        System.out.println("---------------------------");
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<Integer> list = Arrays.stream(candidates).sorted().boxed().toList();
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) <= target) {
                boolean[] dp = new boolean[target + 1];
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(j) <= target) {
                        dp[list.get(i) + list.get(j)] = list.get(i) + list.get(j) < target;
                        if (dp[target - list.get(j)]) {
                            List<Integer> tmp = new ArrayList<>();
                            tmp.add(list.get(i));
                            tmp.add(list.get(j));
                            res.add(tmp);
                        }
                    }
                }
            }
        }

        return res;

    }
}
