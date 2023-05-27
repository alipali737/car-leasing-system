package com.leasecompany.carleasingsystem.utils.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationTest {

    @ParameterizedTest
    @MethodSource("postcodeRegexTestCases")
    void Test_postcodeRegex(String sample, boolean expected) {
        assertEquals(expected, InputValidation.matchesRegex(sample, InputValidation.postcodeRegex));
    }

    private static Stream<Arguments> postcodeRegexTestCases() {
        return Stream.of(
                Arguments.of("AA00 0AA", true),
                Arguments.of("AA000AA", true),
                Arguments.of("aa00 0aa", true),
                Arguments.of("aa000aa", true),
                Arguments.of("00AA A00", false),
                Arguments.of("", false),
                Arguments.of("AA!0 0AA", false),
                Arguments.of("AA-10 0AA", false),
                Arguments.of("AA00 0AAA", false),
                Arguments.of("AA00", false)
        );
    }

    @ParameterizedTest
    @MethodSource("phoneRegexTestCases")
    void Test_phoneRegex(String sample, boolean expected) {
        assertEquals(expected, InputValidation.matchesRegex(sample, InputValidation.phoneNumberRegex));
    }

    private static Stream<Arguments> phoneRegexTestCases() {
        return Stream.of(
                Arguments.of("00000 000000", true),
                Arguments.of("00000000000", true),
                Arguments.of("99999 999999", true),
                Arguments.of("ABC", false),
                Arguments.of("", false),
                Arguments.of("12345 12345", false),
                Arguments.of("123451234567", false)
        );
    }

    @ParameterizedTest
    @MethodSource("emailRegexTestCases")
    void Test_emailRegex(String sample, boolean expected) {
        assertEquals(expected, InputValidation.matchesRegex(sample, InputValidation.emailRegex));
    }

    private static Stream<Arguments> emailRegexTestCases() {
        return Stream.of(
                Arguments.of("john.doe@example.com", true),
                Arguments.of("jane.smith@example.co.uk", true),
                Arguments.of("info@company.com", true),
                Arguments.of("support123@domain.io", true),
                Arguments.of("user.name1234@example-domain.com", true),
                Arguments.of("test_email@example123.com", true),
                Arguments.of("sales+support@123company.net", true),
                Arguments.of("my-email@subdomain.example.org", true),
                Arguments.of("john.doe@example", false),
                Arguments.of("jane.smith@examplecom", false),
                Arguments.of("info@company..com", false),
                Arguments.of("support124domain.com", false),
                Arguments.of("user.name123@example_domain.com", false),
                Arguments.of("my-email@subdomain..example.org", false),
                Arguments.of("no-reply@emai lprovider.com", false),
                Arguments.of("contact.us@website_com", false)
        );
    }

    @ParameterizedTest
    @MethodSource("driverLicenseRegexTestCases")
    void Test_driverLicenseRegex(String sample, boolean expected) {
        assertEquals(expected, InputValidation.matchesRegex(sample, InputValidation.driverLicenseNoRegex));
    }

    private static Stream<Arguments> driverLicenseRegexTestCases() {
        return Stream.of(
                Arguments.of("AAAAA000000AA0AA", true),
                Arguments.of("AAAAA000000AA0A", false),
                Arguments.of("AAAAA000000AAAA", false),
                Arguments.of("AAAAA000000A0AA", false),
                Arguments.of("AAAAA00000AA0AA", false),
                Arguments.of("AAAA000000AA0AA", false),
                Arguments.of("AAAAA000000AA0AAA", false),
                Arguments.of("00000AAAAAA00A00", false),
                Arguments.of("AAAAA000 000AA0AA", false)
        );
    }

    @ParameterizedTest
    @MethodSource("dateRegexTestCases")
    void Test_dateRegex(String sample, boolean expected) {
        assertEquals(expected, InputValidation.matchesRegex(sample, InputValidation.dateRegex));
    }

    private static Stream<Arguments> dateRegexTestCases() {
        return Stream.of(
                Arguments.of("01/01/2023", true),
                Arguments.of("01.01.2023", false),
                Arguments.of("01-01-2023", false),
                Arguments.of("1/1/2023", true),
                Arguments.of("31/01/2023", true),
                Arguments.of("01/13/2023", false)
        );
    }

    @ParameterizedTest
    @MethodSource("regRegexTestCases")
    void Test_regRegex(String sample, boolean expected) {
        assertEquals(expected, InputValidation.matchesRegex(sample, InputValidation.ukRegistrationRegex));
    }

    private static Stream<Arguments> regRegexTestCases() {
        return Stream.of(
                Arguments.of("AA00 0A0", true),
                Arguments.of("AA00 AAA", true),
                Arguments.of("AA00 000", true),
                Arguments.of("AA000A0", false),
                Arguments.of("AA00 0A00", false),
                Arguments.of("AA!0 0A0", false),
                Arguments.of("00AA 0A0", false),
                Arguments.of("aa00 0A0", false)
        );
    }

    @ParameterizedTest
    @MethodSource("onlyContainsLettersOrNumbersTestCases")
    void Test_onlyContainsLettersOrNumbers(String sample, boolean expected) {
        assertEquals(expected, InputValidation.onlyContainsLettersOrNumbers(sample));
    }

    private static Stream<Arguments> onlyContainsLettersOrNumbersTestCases() {
        return Stream.of(
                Arguments.of("abc123", true),
                Arguments.of("ABC", true),
                Arguments.of("123", true),
                Arguments.of("abc 123", false),
                Arguments.of("abc!123", false),
                Arguments.of("", false)
        );
    }

    @ParameterizedTest
    @MethodSource("onlyContainsLettersOrSpacesTestCases")
    void Test_onlyContainsLettersOrSpaces(String sample, boolean expected) {
        assertEquals(expected, InputValidation.onlyContainsLettersAndSpaces(sample));
    }

    private static Stream<Arguments> onlyContainsLettersOrSpacesTestCases() {
        return Stream.of(
                Arguments.of("abc ABC", true),
                Arguments.of("abc 123", false),
                Arguments.of("abcabc", true),
                Arguments.of("", false),
                Arguments.of("abc !bc", false)
        );
    }

    @ParameterizedTest
    @MethodSource("onlyContainsLettersTestCases")
    void Test_onlyContainsLetters(String sample, boolean expected) {
        assertEquals(expected, InputValidation.onlyContainsLetters(sample));
    }

    private static Stream<Arguments> onlyContainsLettersTestCases() {
        return Stream.of(
                Arguments.of("aBc", true),
                Arguments.of("abc abc", false),
                Arguments.of("abc123", false),
                Arguments.of("abc!", false),
                Arguments.of("", false)
        );
    }

    @ParameterizedTest
    @MethodSource("isNumericTestCases")
    void Test_isNumeric(String sample, boolean doubleAllowed, boolean expected) {
        assertEquals(expected, InputValidation.isNumeric(sample, doubleAllowed));
    }

    private static Stream<Arguments> isNumericTestCases() {
        return Stream.of(
                Arguments.of("101", true, true),
                Arguments.of("101", false, true),
                Arguments.of("101.25", true, true),
                Arguments.of("101.25", false, false),
                Arguments.of("abc", true, false),
                Arguments.of("101 101", true, false)
        );
    }

    @ParameterizedTest
    @MethodSource("numericRangeTestCases")
    void Test_numericRange(String sample, double min, double max,  boolean expected) {
        assertEquals(expected, InputValidation.numericRange(sample, min, max));
    }

    private static Stream<Arguments> numericRangeTestCases() {
        return Stream.of(
                Arguments.of("5", 0, 10, true),
                Arguments.of("0", 0, 10, true),
                Arguments.of("10", 0, 10, true),
                Arguments.of("5.5", 0.5, 10.5, true),
                Arguments.of("-1", 0, 10, false),
                Arguments.of("11", 0, 10, false),
                Arguments.of("abc", 0, 10, false)
        );
    }

    @ParameterizedTest
    @MethodSource("lengthInRangeTestCases")
    void Test_lengthInRange(String sample, int min, int max, boolean expected) {
        assertEquals(expected, InputValidation.lengthInRange(sample, min, max));
    }

    private static Stream<Arguments> lengthInRangeTestCases() {
        return Stream.of(
                Arguments.of("abc", 1, 5, true),
                Arguments.of("a", 1, 5, true),
                Arguments.of("abcde", 1, 5, true),
                Arguments.of("", 1, 5, false),
                Arguments.of("a", 2, 5, false),
                Arguments.of("abcdef", 1, 5, false)
        );
    }

}
