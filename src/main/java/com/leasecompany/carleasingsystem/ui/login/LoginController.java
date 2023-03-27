package com.leasecompany.carleasingsystem.ui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    @FXML
    public void initialize() {
        // Create the listener on the loginButton
        loginButton.setOnAction(this::handleLoginButtonClick);

        registerLink.setOnAction(this::handleRegisterLink);
    }

    private void handleLoginButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            // TODO: authenticated, move to home screen
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Welcome, "+username+"!");
            alert.setContentText("You have been successfully authenticated.");
            alert.showAndWait();
        } else {
            // auth rejected, move to failed screen
            loadLoginFailedScene();
        }
    }

    private void handleRegisterLink(ActionEvent event) {
        RegisterApp registerApp = new RegisterApp();
        Stage primaryStage = (Stage) registerLink.getScene().getWindow();
        try {
            registerApp.start(primaryStage);
        } catch (Exception e) {
            System.out.println("Unable to switch to registerScene: "+e.getMessage());
        }
    }

    private boolean authenticate(String username, String password) {
        // TODO: Query DB for password hash of username
        // TODO: Get entered password hash

        // TEMP
        return (username.equals("admin") && password.equals("admin"));
    }

    private void loadLoginFailedScene() {
        LoginFailedApp loginFailedApp = new LoginFailedApp();
        Stage primaryStage = (Stage) loginButton.getScene().getWindow();
        try {
            loginFailedApp.start(primaryStage);
        } catch (Exception e) {
            System.out.println("Unable to switch to loginFailedScene: "+e.getMessage());
        }
    }




}
