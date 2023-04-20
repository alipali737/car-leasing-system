package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.ui.home.HomeApp;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.synedra.validatorfx.Validator;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    private Validator validator;

    @FXML
    public void initialize() {
        loginButton.setOnAction(this::handleLoginButtonClick);
        registerLink.setOnAction(this::handleRegisterLink);

        validator = new Validator();

        // Validate entry boxes aren't empty (length == 0)
        validator.createCheck()
                .dependsOn("username", usernameField.textProperty())
                .withMethod(c -> {
                    String username = c.get("username");
                    if (username.isEmpty()) {
                        c.error("Field cannot be empty.");
                    }
                })
                .decorates(usernameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    if (password.isEmpty()) {
                        c.error("Field cannot be empty.");
                    }
                })
                .decorates(passwordField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void handleLoginButtonClick(ActionEvent event) {
        if (!validator.validate()) {
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            // TODO: authenticated, move to home screen
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Welcome, "+username+"!");
            alert.setContentText("You have been successfully authenticated.");
            alert.showAndWait();
            SceneController.changeScene(new HomeApp(), loginButton);
        } else {
            // auth rejected, move to failed screen
            SceneController.changeScene(new LoginFailedApp(), loginButton);
        }
    }

    private void handleRegisterLink(ActionEvent event) {
        SceneController.changeScene(new RegisterApp(), registerLink);
    }

    private boolean authenticate(String username, String password) {
        // TODO: Query DB for password hash of username
        // TODO: Get entered password hash

        // TEMP
        return (username.equals("admin") && password.equals("admin"));
    }
}
