package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.ui.CustomValidationDecoration;
import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    }


    private void handleRegisterButtonClick(ActionEvent event) {
        // Validate name (length, characters)
        validator.createCheck()
                .dependsOn("name", nameField.textProperty())
                .withMethod(c -> {
                    String name = c.get("name");
                    if (InputValidation.lengthInRange(name, 3, 25)) {
                        c.error("Please keep your name between 3 and 25 characters.");
                    }
                })
                .decorates(nameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()))
                .immediate();

        validator.createCheck()
                .dependsOn("name", nameField.textProperty())
                .withMethod(c -> {
                    String name = c.get("name");
                    if (!InputValidation.onlyContainsLetters(name)) {
                        c.error("Please only include letters, no special characters.");
                    }
                })
                .decorates(nameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()))
                .immediate();

        // TODO: Validate email (format)
        validator.createCheck()
                .dependsOn("email", emailField.textProperty())
                .withMethod(c -> {
                    String email = c.get("email");
                    if (!InputValidation.isValidEmailAddress(email)) {
                        c.error("Must follow the email address format: user@domain.ext");
                    }
                })
                .decorates(emailField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()))
                .immediate();

        // TODO: Validate passwords (the same)
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
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()))
                .immediate();

        // TODO: Add user to DB
        // TODO: Change screen to confirmation
    }



    private void handleLoginLinkClick(ActionEvent event) {
        LoginApp loginApp = new LoginApp();
        Stage primaryStage = (Stage) loginLink.getScene().getWindow();
        try {
            loginApp.start(primaryStage);
        } catch (Exception e) {
            System.out.println("Unable to switch to loginScene: "+e.getMessage());
        }
    }
}
