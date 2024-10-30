package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/29
 * 电话号码的字母组合
 */
public class Solution17Test {

    @Test
    public void solution17Test() {
        System.out.println("---------------------------");
        System.out.println(letterCombinations("23")); //["ad","ae","af","bd","be","bf","cd","ce","cf"]
        System.out.println(letterCombinations("")); //[]
        System.out.println(letterCombinations("2")); //["a","b","c"]
        System.out.println("---------------------------");
    }

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return List.of();
        }

        List<String> value = new ArrayList<>();
        value.add(""); //0
        value.add(""); //1
        value.add("abc"); //2
        value.add("def"); //3
        value.add("ghi"); //4
        value.add("jkl"); //5
        value.add("mno"); //6
        value.add("pqrs"); //7
        value.add("tuv"); //8
        value.add("wxyz"); //9

        List<String> res = new ArrayList<>();
        for (int i = 0; i < digits.length(); i++) {
            int index = Integer.parseInt(String.valueOf(digits.charAt(i)));
            String str = value.get(index);
            res = letterCombinations(res, str);
        }

        return res;
    }

    public List<String> letterCombinations(List<String> s1, String s2) {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < s2.length(); i++) {
            if (s1.isEmpty()) {
                str.add(s2.charAt(i) + "");
            } else {
                for (String s : s1) {
                    str.add(s + s2.charAt(i));
                }
            }
        }
        return str;
    }
}
