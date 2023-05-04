package com.leasecompany.carleasingsystem.utils.validation;

public class InputValidation {


    /**
     * Checks if a string only contains letters or numbers [a-zA-Z0-9], no special characters allowed
     * @param sample String
     */
    public static boolean onlyContainsLettersOrNumbers(String sample) {
        return sample.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * Checks if a string's length is within the given boundaries (inclusive)
     * @param sample String
     * @param min the minimum length of the string allowed (inclusive)
     * @param max the maximum length of the string allowed (inclusive)
     */
    public static boolean lengthInRange(String sample, int min, int max) {
        return (sample.length() >= min && sample.length() <= max);
    }

}
