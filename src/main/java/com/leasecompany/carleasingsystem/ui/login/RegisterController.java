package com.leasecompany.carleasingsystem.ui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        // TODO: Validate name (length, characters)
        validator.createCheck()
                .dependsOn("name", nameField.textProperty())
                .withMethod(c -> {
                    String name = c.get("name");
                    if (name.length() < 3 || name.length() > 25) {
                        c.error("Please keep your name between 3 and 25 characters.");
                    }
                })
                .decorates(nameField)
                .immediate();

        // TODO: Validate email (format)
        // TODO: Validate passwords (the same)
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
