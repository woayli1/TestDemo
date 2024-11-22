package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/21
 * 组合总和
 */
public class Solution39Test {

    @Test
    public void solution39Test() {
        System.out.println("---------------------------");
        System.out.println(combinationSum(new int[]{2, 3, 6, 7}, 7)); // [[2,2,3],[7]]
        System.out.println(combinationSum(new int[]{2, 3, 5}, 8)); // [[2,2,2,2],[2,3,3],[3,5]]
        System.out.println(combinationSum(new int[]{2}, 1)); // []
        System.out.println(combinationSum(new int[]{8, 7, 4, 3}, 11)); // [[8,3],[7,4],[4,4,3]]
        System.out.println("---------------------------");
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int[] candidates, int target, int i, List<Integer> integerList, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(integerList));
            return;
        }

        for (int j = i; j < candidates.length; j++) {
            if (candidates[j] > target) {
                break;
            }
            integerList.add(candidates[j]);
            dfs(candidates, target - candidates[j], j, integerList, res);
            integerList.remove(integerList.size() - 1);
        }
    }

}
