package com.leasecompany.carleasingsystem.utils.validation;

public class InputValidation {

    public static final String postcodeRegex = "^([A-Za-z]{1,2}\\d{1,2}[A-Za-z]?\\s?\\d{1}[A-Za-z]{2}|[A-Za-z]{1}" +
            "\\d{1}[A-Za-z]?\\s?\\d{1}[A-Za-z]{2})$";
    public static final String phoneNumberRegex = "^\\d{5}\\s\\d{6}|\\d{11}$";
    public static final String emailRegex = "^[\\w-.]+@[\\w-]+(.[\\w-]+)*$";
    public static final String driverLicenseNoRegex = "^[A-Z]{5}[0-9]{6}[A-Z]{2}[0-9][A-Z]{2}$";
    public static final String dobRegex = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/\\d{4}$";

    /**
     * Checks if a string matches the given regex
     * @param sample
     * @param regex
     */
    public static boolean matchesRegex(String sample, String regex) { return sample.matches(regex); }

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
