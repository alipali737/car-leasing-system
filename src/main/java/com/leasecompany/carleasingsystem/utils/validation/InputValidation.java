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
     * Checks if a string only contains letters, no special characters allowed
     * @param sample String
     */
    public static boolean onlyContainsLetters(String sample) {
        return sample.matches("^[a-zA-Z]+$");
    }

    /**
     * Checks if the string is a valid UK registration in format AA00 AAA or AA00 000
     * @param sample String
     */
    public static boolean isUkRegistration(String sample) {
        return sample.matches("^[A-Z]{2}[0-9]{2} [A-Z0-9]{3}+$");
    }

    /**
     * Checks if a string is numeric
     * @param sample String
     * @param doubleAllowed Whether the number can be a double
     */
    public static boolean isNumeric(String sample, boolean doubleAllowed) {
        if (sample == null) {
            return false;
        }
        try {
            if (doubleAllowed) {
                Double.parseDouble(sample);
            } else {
                Integer.parseInt(sample);
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a number is within the given boundaries (inclusive)
     * @param sample String
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     */
    public static boolean numericRange(String sample, double min, double max) {
        if (sample == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(sample);
            return (d >= min && d <= max);
        } catch (NumberFormatException ex) {
            return false;
        }
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
