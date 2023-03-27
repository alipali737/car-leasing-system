package com.leasecompany.carleasingsystem.ui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginFailedController {
    @FXML
    private Button returnToLoginButton;
    @FXML
    public void initialize() {
        returnToLoginButton.setOnAction(this::handleReturnButtonClick);
    }

    private void handleReturnButtonClick(ActionEvent event){
        LoginApp loginApp = new LoginApp();
        Stage primaryStage = (Stage) returnToLoginButton.getScene().getWindow();
        try {
            loginApp.start(primaryStage);
        } catch (Exception e) {
            System.out.println("Unable to switch to loginScene: "+e.getMessage());
        }
    }
}
