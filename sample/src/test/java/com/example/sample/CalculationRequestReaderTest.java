package com.example.sample;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculationRequestReaderTest {

    @Test
    void Sysetem_In으로_데이터를_읽어드릴_수_있다() {
        // given
        CalculationRequestReader reader = new CalculationRequestReader();

        // when
        System.setIn(new ByteArrayInputStream("1 + 2".getBytes()));
        CalculatorRequest result = reader.read();

        // then
        assertThat(result.getNum1()).isEqualTo(1) ;
        assertThat(result.getOperator()).isEqualTo("+") ;
        assertThat(result.getNum2()).isEqualTo(2) ;
    }
}