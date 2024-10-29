package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/28
 * 最后一个单词的长度
 */
public class Solution58Test {

    @Test
    public void solution58Test() {
        System.out.println("---------------------------");
        System.out.println(lengthOfLastWord("Hello World")); //5
        System.out.println(lengthOfLastWord("   fly me   to   the moon  ")); //4
        System.out.println(lengthOfLastWord("luffy is still joyboy")); //6
        System.out.println("---------------------------");
    }

    public int lengthOfLastWord(String s) {
        s = s.trim();
        int index = s.lastIndexOf(" ");
        return s.length() - index - 1;
    }
}
