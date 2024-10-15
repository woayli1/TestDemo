package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/15
 * 杨辉三角 II
 */
public class Solution119Test {

    @Test
    public void solution119Test() {
        System.out.println("---------------------------");
        System.out.println(getRow(3)); //[1,3,3,1]
        System.out.println(getRow(0)); //[1]
        System.out.println("---------------------------");
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> resList = new ArrayList<>();
        resList.add(1);
        for (int i = 0; i < rowIndex + 1; i++) {
            List<Integer> tempList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    tempList.add(1);
                } else {
                    tempList.add(resList.get(j - 1) + resList.get(j));
                }
            }
            resList = tempList;
        }
        return resList;
    }
}
