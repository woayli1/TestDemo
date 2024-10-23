package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 有效的括号
 */
public class Solution20Test {

    @Test
    public void solution20Test() {
        System.out.println("---------------------------");
        System.out.println(isValid("()")); //true
        System.out.println(isValid("()[]{}")); //true
        System.out.println(isValid("(]")); //false
        System.out.println("---------------------------");
    }

    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        List<Character> list = new ArrayList<>();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (aChar == '(' || aChar == '[' || aChar == '{') {
                list.add(aChar);
            } else {
                if (list.isEmpty()) {
                    return false;
                }
                char pop = list.remove(list.size() - 1);
                if (aChar == ')' && pop != '(') {
                    return false;
                }
                if (aChar == ']' && pop != '[') {
                    return false;
                }
                if (aChar == '}' && pop != '{') {
                    return false;
                }
            }
        }
        return list.isEmpty();
    }
}
