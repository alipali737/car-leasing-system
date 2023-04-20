package com.leasecompany.carleasing.utils.validation;

import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationTest {

    @ParameterizedTest(name = "{index} isValidEmailAddress({0}) => {1}")
    @MethodSource("isValidEmailAddressTestCases")
    void Test_isValidEmailAddress(String sample, boolean expected) {
        assertEquals(expected, InputValidation.isValidEmailAddress(sample));
    }

    private static Stream<Arguments> isValidEmailAddressTestCases() {
        return Stream.of(
                Arguments.of("a@b.com", true),
                Arguments.of("alpha@bravo.org", true),
                Arguments.of("", false),
                Arguments.of("a.com", false),
                Arguments.of("@b.com", false),
                Arguments.of("@.blah", false),
                Arguments.of("abc", false),
                Arguments.of("this contains spaces @blah.com", false),
                Arguments.of("thisIsValid&ThisIsToo@blah.org", true),
                Arguments.of("abc@foo.thisissuperlongandwayoverthetop", false)
        );
    }

    @ParameterizedTest(name = "{index} onlyContainsLetters({0}) => {1}")
    @MethodSource("onlyContainsLettersTestCases")
    void Test_onlyContainsLetters(String sample, boolean expected) {
        assertEquals(expected, InputValidation.onlyContainsLetters(sample));
    }

    private static Stream<Arguments> onlyContainsLettersTestCases() {
        return Stream.of(
                Arguments.of("abc", true),
                Arguments.of("", false),
                Arguments.of("ab12c", false),
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
