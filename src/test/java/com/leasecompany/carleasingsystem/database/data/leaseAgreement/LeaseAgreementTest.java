package com.leasecompany.carleasingsystem.database.data.leaseAgreement;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class LeaseAgreementTest {

    @ParameterizedTest
    @MethodSource("calcLateFeeTestCases")
    void Test_calcLateFee(int daysLate, double dailyPayment, double rate, double expected) {
        LeaseAgreement leaseAgreement = new LeaseAgreement();
        assertEquals(expected, leaseAgreement.calcLateFee(daysLate, dailyPayment, rate));
    }

    private static Stream<Arguments> calcLateFeeTestCases() {
        return Stream.of(
                // int daysLate, double dailyPayment, double rate, double expected
                Arguments.of(10, 5, 0.05, 62.89),
                Arguments.of(0, 5, 0.05, 0),
                Arguments.of(-1, 5, 0.05, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("calcAdditionalMileFeeTestCases")
    void Test_calcAdditionalMileFee(double pricePerMile, int mileage, int allowedAnnualMileage, int policyTerm, double expected) {
        LeaseAgreement leaseAgreement = new LeaseAgreement();
        leaseAgreement.setAnnualMileageAllowed(allowedAnnualMileage);
        leaseAgreement.setPolicyTerm(policyTerm);
        assertEquals(expected, leaseAgreement.calcAdditionalMileFee(pricePerMile, mileage));
    }

    private static Stream<Arguments> calcAdditionalMileFeeTestCases() {
        return Stream.of(
                // double pricePerMile, int mileage, int allowedAnnualMileage, int policyTerm, double expected
                Arguments.of(1, 100, 100, 36, 0),
                Arguments.of(1, 400, 100, 36, 100),
                Arguments.of(0.1, 400, 100, 36, 10),
                Arguments.of(-1, 400, 100, 36, 0),
                Arguments.of(1, 300, 100, 36, 0),
                Arguments.of(1, 301, 100, 36, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("calcDailyPaymentTestCases")
    void Test_calcDailyPayment(double vehiclePrice, String startDate, String endDate, double expected) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        LeaseAgreement leaseAgreement = new LeaseAgreement();
        leaseAgreement.setTotalPrice(vehiclePrice);
        try {
            leaseAgreement.setPolicyStartDate(format.parse(startDate));
            leaseAgreement.setPolicyEndDate(format.parse(endDate));
        } catch (ParseException e) {
            fail("Couldn't convert date: " + e.getMessage());
        }
        assertEquals(expected, leaseAgreement.calcDailyPayment());
    }

    private static Stream<Arguments> calcDailyPaymentTestCases() {
        return Stream.of(
                // double vehiclePrice, String startDate, String endDate, double expected
                Arguments.of(10000, "01/01/2023", "01/01/2025", 13.66),
                Arguments.of(0, "01/01/2023", "01/01/2025", 0),
                Arguments.of(-100000, "01/01/2023", "01/01/2025", 0),
                Arguments.of(10000, "01/01/2023", "01/06/2023", 66.23),
                Arguments.of(10000, "01/01/2023", "05/01/2023", 2000),
                Arguments.of(10000, "01/01/2023", "01/01/2022", 0),
                Arguments.of(10000, "01/01/2023", "01/01/2023", 10000)
        );
    }

    @ParameterizedTest
    @MethodSource("calcAmountPaidBetweenDatesTestCases")
    void Test_calcAmountPaidBetweenDates(String startDate, String endDate, double dailyPayment, double expected) {
        LeaseAgreement leaseAgreement = new LeaseAgreement();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startLocalDate = LocalDate.parse(startDate, formatter);
        LocalDate endLocalDate = LocalDate.parse(endDate, formatter);
        assertEquals(expected, leaseAgreement.calcAmountPaidBetweenDates(startLocalDate, endLocalDate, dailyPayment));
    }

    private static Stream<Arguments> calcAmountPaidBetweenDatesTestCases() {
        return Stream.of(
                // String startDate, String endDate, double dailyPayment, double expected
                Arguments.of("01/01/2023", "01/01/2025", 5, 3660),
                Arguments.of("01/01/2023", "01/06/2023", 5, 760),
                Arguments.of("01/01/2023", "05/01/2023", 5, 25),
                Arguments.of("01/01/2023", "01/01/2023", 5, 5),
                Arguments.of("02/01/2023", "01/01/2023", 5, 0)
        );
    }
}