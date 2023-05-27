package com.leasecompany.carleasingsystem.utils.validation;

import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InputValidation {

    public static final String postcodeRegex = "^([A-Za-z]{1,2}\\d{1,2}[A-Za-z]?\\s?\\d{1}[A-Za-z]{2}|[A-Za-z]{1}" +
            "\\d{1}[A-Za-z]?\\s?\\d{1}[A-Za-z]{2})$";
    public static final String phoneNumberRegex = "^\\d{5}\\s\\d{6}|\\d{11}$";
    public static final String emailRegex = "^[a-zA-Z0-9_+&*\\-]+" +
                                            "(?:\\.[a-zA-Z0-9_+&*\\-]+)*" +
                                            "@" +
                                            "(?:[a-zA-Z0-9-]+\\.)+" +
                                            "[a-zA-Z]{2,7}$";

    public static final String driverLicenseNoRegex = "^[A-Z]{5}[0-9]{6}[A-Z]{2}[0-9][A-Z]{2}$";
    public static final String dateRegex = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/\\d{4}$";
    public static final String ukRegistrationRegex = "^[A-Z]{2}[0-9]{2} [A-Z0-9]{3}+$";

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
     * Checks if a string only contains letters or spaces
     * @param sample String
     */
    public static boolean onlyContainsLettersAndSpaces(String sample) {
        return sample.matches("^(?! )([A-Za-z]+ ?)*[A-Za-z]+(?<! )$");
    }

    /**
     * Checks if a string only contains letters, no special characters allowed
     * @param sample String
     */
    public static boolean onlyContainsLetters(String sample) {
        return sample.matches("^[a-zA-Z]+$");
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

    public static void createLengthCheck(Validator validator, String key, TextField textField, int min, int max) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.lengthInRange(var, min, max)) {
                        c.error("Please keep your name between " + min + " and " + max + " characters.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    public static void createOnlyLettersCheck(Validator validator, String key, TextField textField, boolean allowSpaces) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (allowSpaces) {
                        if (!InputValidation.onlyContainsLettersAndSpaces(var)) {
                            c.error("Please only enter letters or spaces.");
                        }
                    } else {
                        if (!InputValidation.onlyContainsLetters(var)) {
                            c.error("Please only enter letters, no special characters or numbers allowed.");
                        }
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    public static void createFutureDateCheck(Validator validator, String key, TextField textField, TextField otherDateField) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .dependsOn("otherDate", otherDateField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    String otherVar = c.get("otherDate");
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate testDate = LocalDate.parse(var, formatter);
                        LocalDate otherDate = LocalDate.parse(otherVar, formatter);
                        if (testDate.isBefore(otherDate)) {
                            c.error("Date must be after "+otherDate+".");
                        }
                    } catch (Exception e) {
                        c.error("Invalid date format.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    public static void createFutureDateCheck(Validator validator, String key, TextField textField) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate testDate = LocalDate.parse(var, formatter);
                        if (testDate.isBefore(LocalDate.now())) {
                            c.error("Date must be in the future.");
                        }
                    } catch (Exception e) {
                        c.error("Invalid date format.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    public static void createRegexCheck(Validator validator, String key, TextField textField, String regex, String errorMsg) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.matchesRegex(var, regex)) {
                        c.error(errorMsg);
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    public static void createNumericCheck(Validator validator, String key, TextField textField, boolean doubleAllowed) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.isNumeric(var, doubleAllowed)) {
                        c.error("Please only enter a number.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    public static void createNumericRangeCheck(Validator validator, String key, TextField textField, double min, double max) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.numericRange(var, min, max)) {
                        c.error("Please keep the value between " + min + " and " + max + ".");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }
}
