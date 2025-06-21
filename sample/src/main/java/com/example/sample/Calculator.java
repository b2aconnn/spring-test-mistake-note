package com.example.sample;

public class Calculator {
    public long calculate(String operator, long num1, long num2) {
        long answer;
        if (operator.equals("+")) {
            answer = num1 + num2;
        } else if (operator.equals("-")) {
            answer = num1 - num2;
        } else if (operator.equals("*")) {
            answer = num1 * num2;
        } else if (operator.equals("/")) {
            answer = num1 / num2;
        } else {
            throw new InvalidOperatorException();
        }
        return answer;
    }
}
