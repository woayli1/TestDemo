package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/28
 * 将句子排序
 */
public class Solution1859Test {

    @Test
    public void solution1859Test() {
        System.out.println("---------------------------");
        System.out.println(sortSentence("is2 sentence4 This1 a3")); //This is a sentence
        System.out.println(sortSentence("Myself2 Me1 I4 and3")); //Me Myself and I
        System.out.println("---------------------------");
    }

    public String sortSentence(String s) {
        String[] arr = s.split(" ");
        String[] res = new String[arr.length];
        for (String str : arr) {
            int index = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1)));
            res[index - 1] = str.substring(0, str.length() - 1);
        }

        StringBuilder reString = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (i != res.length - 1) {
                reString.append(res[i]).append(" ");
            } else {
                reString.append(res[i]);
            }
        }

        return reString.toString();
    }
}
