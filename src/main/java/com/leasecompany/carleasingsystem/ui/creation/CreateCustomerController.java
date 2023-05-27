package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.customer.Customer;
import com.leasecompany.carleasingsystem.database.data.customer.CustomerDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
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

        InputValidation.createLengthCheck(validator, "firstname", firstnameField, 2, 25);
        InputValidation.createLengthCheck(validator, "surname", surnameField, 2, 25);
        InputValidation.createLengthCheck(validator, "address1", address1Field, 1, 50);
        InputValidation.createLengthCheck(validator, "address2", address2Field, 0, 50);
        InputValidation.createLengthCheck(validator, "city", cityField, 3, 50);
        InputValidation.createLengthCheck(validator, "postcode", postcodeField, 6, 8);
        InputValidation.createLengthCheck(validator, "phone", phoneField, 11, 12);
        InputValidation.createLengthCheck(validator, "email", emailField, 7, 50);
        InputValidation.createLengthCheck(validator, "driverLicenseNo", driverLicenseNoField, 16, 16);
        InputValidation.createLengthCheck(validator, "dob", dobField, 10, 10);

        InputValidation.createOnlyLettersCheck(validator, "firstname", firstnameField, false);
        InputValidation.createOnlyLettersCheck(validator, "surname", surnameField, false);
        InputValidation.createOnlyLettersCheck(validator, "city", cityField, true);

        InputValidation.createRegexCheck(validator, "postcode", postcodeField, InputValidation.postcodeRegex,
                "Please enter a UK postcode eg. AA00 0AA.");

        InputValidation.createRegexCheck(validator, "phone", phoneField, InputValidation.phoneNumberRegex,
                "Please enter a UK phone number eg. 12345 123456.");

        InputValidation.createRegexCheck(validator, "email", emailField, InputValidation.emailRegex,
                "Please enter a valid email address.");

        InputValidation.createRegexCheck(validator, "driverLicenseNo", driverLicenseNoField, InputValidation.driverLicenseNoRegex,
                "Please enter a driver license number in format: AAAAA000000AA0AA");

        InputValidation.createRegexCheck(validator, "dob", dobField, InputValidation.dateRegex,
                "Please enter the DoB in format: DD/MM/YYYY");

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
