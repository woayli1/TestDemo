package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/21
 * 加一
 */
public class Solution66Test {

    @Test
    public void solution66Test() {
        System.out.println("---------------------------");
        System.out.println(Arrays.toString(plusOne(new int[]{1, 2, 3}))); //[1,2,4]
        System.out.println(Arrays.toString(plusOne(new int[]{4, 3, 2, 1}))); //[4,3,2,2]
        System.out.println(Arrays.toString(plusOne(new int[]{9}))); //[1,0]
        System.out.println("---------------------------");
    }

    public int[] plusOne(int[] digits) {
        List<Integer> list = new ArrayList<>();
        int length = digits.length;
        int carry = 0;
        int tmpValue;
        for (int i = length - 1; i >= 0; i--) {
            if (i == length - 1) {
                tmpValue = digits[i] + 1;
            } else {
                tmpValue = digits[i] + carry;
            }

            if (tmpValue > 9) {
                carry = 1;
                list.add(tmpValue - 10);
                if (i == 0) {
                    list.add(carry);
                }
            } else {
                carry = 0;
                list.add(tmpValue);
            }
        }

        Collections.reverse(list);
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
