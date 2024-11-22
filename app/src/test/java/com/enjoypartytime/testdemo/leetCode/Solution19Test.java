package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/11/7
 * 删除链表的倒数第 N 个结点
 */
public class Solution19Test {

    @Test
    public void solution19Test() {
        System.out.println("---------------------------");
        System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2)); //[1,2,3,5]
        System.out.println(removeNthFromEnd(new ListNode(1), 1)); //[]
        System.out.println(removeNthFromEnd(new ListNode(1, new ListNode(2)), 1)); //[1]
        System.out.println("---------------------------");
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode left = head, right = head;
        for (int i = 0; i < n; i++) {
            right = right.next;
        }

        if (right == null) return head != null ? head.next : new ListNode();

        while (right.next != null) {
            left = left.next;
            right = right.next;
        }

        if (left != null) {
            left.next = left.next.next;
        }

        return head;
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
}
