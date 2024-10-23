package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/22
 * 合并两个有序链表
 */
public class Solution21Test {


    @Test
    public void solution21Test() {
        System.out.println("---------------------------");
        System.out.println(mergeTwoLists(new ListNode(1, new ListNode(2, new ListNode(4))),
                new ListNode(1, new ListNode(3, new ListNode(4))))); //[7,0,8]
        System.out.println(mergeTwoLists(new ListNode(), new ListNode())); //[]
        System.out.println(mergeTwoLists(new ListNode(), new ListNode(0))); //0
        System.out.println("---------------------------");
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        return mergeTwoLists(list1, list2, new ListNode());
    }

    private ListNode mergeTwoLists(ListNode list1, ListNode list2, ListNode res) {
        if (list1 == null) {
            return list2;
        }

        if (list2 == null) {
            return list1;
        }

        if (list1.val <= list2.val) {
            res.val = list1.val;
            res.next = mergeTwoLists(list1.next, list2, new ListNode());
        } else {
            res.val = list2.val;
            res.next = mergeTwoLists(list1, list2.next, new ListNode());
        }
        return res;
    }
}
