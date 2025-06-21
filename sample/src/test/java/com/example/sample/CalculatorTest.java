package com.example.sample;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    @Test
    void 덧셈연산을_할_수_있다() {
        // given
        long num1 = 2;
        String operator = "+";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(operator, num1, num2);

        // then
        assertEquals(result, 5); // junit assertion
        assertThat(result).isEqualTo(5); // assertJ assertion
    }

    @Test
    void 곱셈_연산을_할_수_있다() {
        // given
        long num1 = 2;
        String operator = "*";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(operator, num1, num2);

        // then
        assertThat(result).isEqualTo(6);
    }

    @Test
    void 뺄셈_연산을_할_수_있다() {
        // given
        long num1 = 2;
        String operator = "-";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(operator, num1, num2);

        // then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void 나눗셈_연산을_할_수_있다() {
        // given
        long num1 = 2;
        String operator = "/";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(operator, num1, num2);

        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void 잘못된_연산자가_요청으로_들어올_경우_에러가_난다() {
        // given
        long num1 = 6;
        String operator = "x";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        // then
        assertThrows(InvalidOperatorException.class,
                () -> calculator.calculate(operator, num1, num2)); // junit assertion

        assertThatThrownBy(() -> calculator.calculate(operator, num1, num2))
                .isInstanceOf(InvalidOperatorException.class); // assertJ assertion
    }
}
