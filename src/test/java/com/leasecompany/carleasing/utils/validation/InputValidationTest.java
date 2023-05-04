package com.leasecompany.carleasing.utils.validation;

import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationTest {
    @ParameterizedTest(name = "{index} onlyContainsLettersOrNumbers({0}) => {1}")
    @MethodSource("onlyContainsLettersOrNumbersTestCases")
    void Test_onlyContainsLettersOrNumbers(String sample, boolean expected) {
        assertEquals(expected, InputValidation.onlyContainsLettersOrNumbers(sample));
    }

    private static Stream<Arguments> onlyContainsLettersOrNumbersTestCases() {
        return Stream.of(
                Arguments.of("abc", true),
                Arguments.of("", false),
                Arguments.of("ab12c", true),
                Arguments.of("a&c", false)
        );
    }

    @ParameterizedTest(name = "{index} lengthInRange({0}) => {1}")
    @MethodSource("lengthInRangeTestCases")
    void Test_lengthInRange(String sample, int min, int max, boolean expected) {
        assertEquals(expected, InputValidation.lengthInRange(sample, min, max));
    }

    private static Stream<Arguments> lengthInRangeTestCases() {
        return Stream.of(
                Arguments.of("sample", 0, 10, true),
                Arguments.of("sample", 1, 6, true),
                Arguments.of("sample", -30, 10, true),
                Arguments.of("sample", 0, 4, false),
                Arguments.of("sample", 10, 20, false)
        );
    }

}
