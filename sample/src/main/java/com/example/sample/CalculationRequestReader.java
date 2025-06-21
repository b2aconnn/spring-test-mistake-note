package com.example.sample;

import java.util.Scanner;

public class CalculationRequestReader {
    public CalculatorRequest read() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter to numbers and an operator (e.g 1 + 2) : ");
        String result = scanner.nextLine();
        String[] parts = result.split(" ");

        return new CalculatorRequest(parts);
    }
}
