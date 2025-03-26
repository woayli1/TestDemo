package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/24
 * 检查单词是否为句中其他单词的前缀
 */
public class Solution1455Test {

    @Test
    public void solution2255Test() {
        System.out.println("---------------------------");
        System.out.println(isPrefixOfWord("i love eating burger", "burg")); //4
        System.out.println(isPrefixOfWord("this problem is an easy problem", "pro")); //2
        System.out.println(isPrefixOfWord("i am tired", "you")); //-1
        System.out.println("---------------------------");
    }

    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].startsWith(searchWord)) {
                return i + 1;
            }
        }
        return -1;
    }

}
