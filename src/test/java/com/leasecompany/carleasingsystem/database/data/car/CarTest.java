package com.leasecompany.carleasingsystem.database.data.car;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {
    @ParameterizedTest
    @MethodSource("calcMonthlyPaymentPriceTestCases")
    void Test_calcMonthlyPaymentPrice(double depreciationRate, double profitPercentage, int contractMonths, int depositMonths,
                               double vehicleValue, int expected) {
        Car vehicle = new Car();
        vehicle.setValue(vehicleValue);
        assertEquals(expected, vehicle.calcMonthlyPaymentPrice(depreciationRate, profitPercentage,
                contractMonths, depositMonths));
    }

    private static Stream<Arguments> calcMonthlyPaymentPriceTestCases() {
        return Stream.of(
                // double depreciationRate, double profitPercentage, int contractMonths, int depositMonths,
                //                               double vehicleValue, int expected
                Arguments.of(0.01, 0.1, 36, 9, 20000, 192),
                Arguments.of(0.5, 0.8, 24, 3, 200000, 93334),
                Arguments.of(1, 1, 36, 0, 20000, 20556),
                Arguments.of(0.0, 0.0, 36, 9, 20000, 0),
                Arguments.of(-1, 0.1, 36, 9, 20000, 42),
                Arguments.of(0.01, -1, 36, 9, 20000, 150),
                Arguments.of(0.01, 0.1, 0, 9, 20000, 0),
                Arguments.of(0.01, 0.1, -1, 9, 20000, 0),
                Arguments.of(0.01, 0.1, 36, -1, 20000, 256),
                Arguments.of(0.01, 0.1, 36, 9, -100000, 0)
        );
    }
}