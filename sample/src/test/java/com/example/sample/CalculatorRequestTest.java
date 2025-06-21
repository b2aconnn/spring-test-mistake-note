package com.example.sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorRequestTest {
    @Test
    void 유효한_숫자를_파싱할_수_있다() {
        // given
        String[] parts = {"1", "+", "2"};

        // when
        CalculatorRequest request = new CalculatorRequest(parts);

        // then
        assertEquals(request.getNum1(), 1);
        assertEquals(request.getOperator(), "+");
        assertEquals(request.getNum2(), 2);
    }

    @Test
    void 세자리_숫자가_넘어가는_유효한_숫자를_파싱할_수_있다() {
        // given
        String[] parts = {"232", "+", "123"};

        // when
        CalculatorRequest request = new CalculatorRequest(parts);

        // then
        assertEquals(request.getNum1(), 232);
        assertEquals(request.getOperator(), "+");
        assertEquals(request.getNum2(), 123);
    }

    @Test
    void 유효한_길이의_숫자가_들어오지_않으면_에러를_던진다() {
        // given
        String[] parts = {"232", "+"};

        // when
        // then
        assertThrows(BadRequestException.class, () -> new CalculatorRequest(parts));
    }

    @Test
    void 유효하지_않은_연산자가_들어오면_에러를_던진다() {
        // given
        String[] parts = {"232", "x", "2"};

        // when
        // then
        assertThrows(InvalidOperatorException.class, () -> new CalculatorRequest(parts));
    }

    @Test
    void 유효하지_않은_길이의_연산자가_들어오면_에러를_던진다() {
        // given
        String[] parts = {"232", "+-", "2"};

        // when
        // then
        assertThrows(InvalidOperatorException.class, () -> new CalculatorRequest(parts));
    }
}