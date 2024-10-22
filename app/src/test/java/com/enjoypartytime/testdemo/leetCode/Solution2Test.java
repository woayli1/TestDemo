package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/21
 * 两数相加
 */
public class Solution2Test {

    @Test
    public void solution2Test() {
        System.out.println("---------------------------");
        System.out.println(addTwoNumbers(new ListNode(2, new ListNode(4, new ListNode(3))),
                new ListNode(5, new ListNode(6, new ListNode(4))))); //[7,0,8]
        System.out.println(addTwoNumbers(new ListNode(0), new ListNode(0))); //0
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

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbers(l1, l2, 0);
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2, int carry) {
        ListNode tmp = new ListNode();
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        if (l1 == null) {
            l1 = new ListNode(0);
        }

        if (l2 == null) {
            l2 = new ListNode(0);
        }

        int tmpValue = l1.val + l2.val + carry;
        tmp.val = tmpValue % 10;
        carry = tmpValue / 10;
        tmp.next = addTwoNumbers(l1.next, l2.next, carry);
        return tmp;
    }
}
