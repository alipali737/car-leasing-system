package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

public class RegisterController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confPasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink loginLink;

    private Validator validator;

    public void initialize() {
        registerButton.setOnAction(this::handleRegisterButtonClick);
        loginLink.setOnAction(this::handleLoginLinkClick);

        validator = new Validator();

        // Validate name (length, characters)
        validator.createCheck()
                .dependsOn("name", nameField.textProperty())
                .withMethod(c -> {
                    String name = c.get("name");
                    if (!InputValidation.lengthInRange(name, 3, 25)) {
                        c.error("Please keep your name between 3 and 25 characters.");
                    }
                })
                .decorates(nameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        validator.createCheck()
                .dependsOn("name", nameField.textProperty())
                .withMethod(c -> {
                    String name = c.get("name");
                    if (!InputValidation.onlyContainsLetters(name)) {
                        c.error("Please only include letters, no special characters.");
                    }
                })
                .decorates(nameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        // Validate email (format)
        validator.createCheck()
                .dependsOn("email", emailField.textProperty())
                .withMethod(c -> {
                    String email = c.get("email");
                    if (!InputValidation.isValidEmailAddress(email)) {
                        c.error("Must follow the email address format: user@domain.ext");
                    }
                })
                .decorates(emailField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        // Validate passwords (length, the same)
        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String name = c.get("password");
                    if (!InputValidation.lengthInRange(name, 8, 50)) {
                        c.error("Please keep your password between 8 and 50 characters.");
                    }
                })
                .decorates(passwordField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .dependsOn("conf-password", confPasswordField.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    String confPassword = c.get("conf-password");
                    if (!password.equals(confPassword)) {
                        c.error("Password entries do not match.");
                    }
                })
                .decorates(passwordField)
                .decorates(confPasswordField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }


    private void handleRegisterButtonClick(ActionEvent event) {
        if (validator.validate()) {
            // TODO: Add user to DB
            // Change screen to confirmation
            SceneController.changeScene(new RegisterConfirmationApp(), registerButton);
        }
    }

    private void handleLoginLinkClick(ActionEvent event) {
        SceneController.changeScene(new LoginApp(), loginLink);
    }
}
