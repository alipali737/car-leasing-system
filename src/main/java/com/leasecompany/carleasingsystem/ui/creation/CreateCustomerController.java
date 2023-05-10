package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.customer.Customer;
import com.leasecompany.carleasingsystem.database.data.customer.CustomerDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;
import org.apache.commons.text.WordUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CreateCustomerController implements UIController {
    @FXML private TextField firstnameField;
    @FXML private TextField surnameField;
    @FXML private TextField address1Field;
    @FXML private TextField address2Field;
    @FXML private TextField cityField;
    @FXML private TextField postcodeField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextField driverLicenseNoField;
    @FXML private TextField dobField;
    @FXML private Button submitButton;

    private Validator validator;
    private CustomerDAO customerDAO;

    public void initialize() {
        customerDAO = DAOFactory.getInstance().newCustomerDAO();
        submitButton.setOnAction(this::handleSubmitButton);

        validator = new Validator();

        createLengthCheck("firstname", firstnameField, 2, 25);
        createLengthCheck("surname", surnameField, 2, 25);
        createLengthCheck("address1", address1Field, 1, 50);
        createLengthCheck("address2", address2Field, 0, 50);
        createLengthCheck("city", cityField, 3, 50);
        createLengthCheck("postcode", postcodeField, 6, 8);
        createLengthCheck("phone", phoneField, 11, 12);
        createLengthCheck("email", emailField, 7, 50);
        createLengthCheck("driverLicenseNo", driverLicenseNoField, 16, 16);
        createLengthCheck("dob", dobField, 10, 10);

        createOnlyLettersCheck("firstname", firstnameField);
        createOnlyLettersCheck("surname", surnameField);
        createOnlyLettersCheck("city", cityField);

        createRegexCheck("postcode", postcodeField, InputValidation.postcodeRegex,
                "Please enter a UK postcode eg. AA00 0AA.");

        createRegexCheck("phone", phoneField, InputValidation.phoneNumberRegex,
                "Please enter a UK phone number eg. 12345 123456.");

        createRegexCheck("email", emailField, InputValidation.emailRegex,
                "Please enter a valid email address.");

        createRegexCheck("driverLicenseNo", driverLicenseNoField, InputValidation.driverLicenseNoRegex,
                "Please enter a driver license number in format: AAAAA000000AA0AA");

        createRegexCheck("dob", dobField, InputValidation.dobRegex,
                "Please enter the DoB in format: DD/MM/YYYY");

    }

    private void createLengthCheck(String key, TextField textField, int min, int max) {
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

    private void createOnlyLettersCheck(String key, TextField textField) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.onlyContainsLetters(var)) {
                        c.error("Please only enter letters, no special characters or numbers allowed.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void createRegexCheck(String key, TextField textField, String regex, String errorMsg) {
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

    private void handleSubmitButton(ActionEvent event) {
        if (!validator.validate()) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dobField.getText(), formatter);

        Customer newCustomer = new Customer(
                WordUtils.capitalizeFully(firstnameField.getText()),
                WordUtils.capitalizeFully(surnameField.getText()),
                WordUtils.capitalizeFully(address1Field.getText()),
                WordUtils.capitalizeFully(address2Field.getText()),
                WordUtils.capitalizeFully(cityField.getText()),
                postcodeField.getText(),
                phoneField.getText(),
                emailField.getText(),
                driverLicenseNoField.getText(),
                Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        if (!customerDAO.create(newCustomer)) {
            System.err.println("Failed to create new customer in database");
            return;
        }

        SceneController.changeScene(SceneController.creationFXMLPath, submitButton);

    }

    @Override
    public void recieveInformation(Object data) {}
}
