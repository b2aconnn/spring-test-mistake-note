package com.example.sample;

public class CalculatorRequest {
    private final long num1;
    private final long num2;
    private final String operator;

    public CalculatorRequest(String[] parts) {
        if (parts.length != 3) {
            throw new BadRequestException();
        }
        String operator = parts[1];
        if (operator.length() != 1 || isInvalidOperator(operator)) {
            throw new InvalidOperatorException();
        }

        this.num1 = Long.parseLong(parts[0]);
        this.num2 = Long.parseLong(parts[2]);
        this.operator = operator;
    }

    private boolean isInvalidOperator(String operation) {
        return !operation.equals("+") &&
                !operation.equals("-") &&
                !operation.equals("*") &&
                !operation.equals("/");
    }

    public long getNum1() {
        return num1;
    }

    public long getNum2() {
        return num2;
    }

    public String getOperator() {
        return operator;
    }
}
