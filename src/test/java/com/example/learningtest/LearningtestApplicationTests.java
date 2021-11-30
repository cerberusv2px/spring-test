package com.example.learningtest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class LearningtestApplicationTests {

    Calculator calculator = new Calculator();

    @Test
    void itShouldAddNumbers() {
        // given
        int num1 = 20;
        int num2 = 20;

        // when
        int result = calculator.add(num1, num2);

        // then
        assertThat(result).isEqualTo(40);
    }

    class Calculator {

        int add(int a, int b) {
            return a + b;
        }
    }

}
