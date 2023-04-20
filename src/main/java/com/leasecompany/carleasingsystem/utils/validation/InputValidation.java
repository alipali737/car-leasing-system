package com.leasecompany.carleasingsystem.utils.validation;

import java.util.regex.Pattern;

public class InputValidation {

    /**
     * Tests whether the given address is in a valid email address format of (user@domain.ext)
     * @param email String
     */
    public static boolean isValidEmailAddress(String email) {
        // What a vile bit of regex, should probably find a better way of doing this :?
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)" +
                            "*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    /** 628 109 903
     * Checks if a string only contains letters [a-zA-Z], no special characters allowed
     * @param sample String
     */
    public static boolean onlyContainsLetters(String sample) {
        return sample.matches("^[a-zA-Z]+$");
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
