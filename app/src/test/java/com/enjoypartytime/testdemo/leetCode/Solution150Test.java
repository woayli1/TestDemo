package com.enjoypartytime.testdemo.leetCode;

import org.junit.Test;

import java.util.Stack;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/10/29
 * 逆波兰表达式求值
 */
public class Solution150Test {

    @Test
    public void solution150Test() {
        System.out.println("---------------------------");
        System.out.println(evalRPN(new String[]{"2", "1", "+", "3", "*"})); //9
        System.out.println(evalRPN(new String[]{"4", "13", "5", "/", "+"})); //6
        System.out.println(evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"})); //22
        System.out.println("---------------------------");
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            switch (token) {
                case "+": {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a + b);
                    break;
                }
                case "-": {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b - a);
                    break;
                }
                case "/": {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b / a);
                    break;
                }
                case "*": {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a * b);
                    break;
                }
                default:
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }

        return stack.pop();
    }
}
