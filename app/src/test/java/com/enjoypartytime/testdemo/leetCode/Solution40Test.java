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
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, 0, target, new ArrayList<>(), ans);
        return ans;
    }

    private void dfs(int[] candidates, int index, int target, List<Integer> combination, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(combination));
            return;
        }
        for (int i = index; i < candidates.length && target >= candidates[i]; ++i) {
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            combination.add(candidates[i]);
            dfs(candidates, i + 1, target - candidates[i], combination, ans);
            combination.remove(combination.size() - 1);
        }
    }
}
