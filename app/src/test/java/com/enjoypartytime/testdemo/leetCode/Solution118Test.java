package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 杨辉三角
 */
public class Solution118Test {

    @Test
    public void solution15Test() {
        System.out.println("---------------------------");
        System.out.println(generate(5)); //[[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
        System.out.println(generate(1)); //[[1]]
        System.out.println("---------------------------");
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> resList = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> tempList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    tempList.add(1);
                } else {
                    tempList.add(resList.get(i - 1).get(j - 1) + resList.get(i - 1).get(j));
                }
            }
            resList.add(tempList);
        }
        return resList;
    }
}
