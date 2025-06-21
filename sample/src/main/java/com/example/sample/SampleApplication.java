package com.example.sample;

public class SampleApplication {
    public static void main(String[] args) {
        CalculatorRequest calculatorRequest = new CalculationRequestReader().read();

        long answer = new Calculator().calculate(
                calculatorRequest.getOperator(),
                calculatorRequest.getNum1(),
                calculatorRequest.getNum2());

        System.out.println(answer);
    }

}
