package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginFailedController {
    @FXML
    private Button returnToLoginButton;
    @FXML
    public void initialize() {
        returnToLoginButton.setOnAction(this::handleReturnButtonClick);
    }

    private void handleReturnButtonClick(ActionEvent event){
        SceneController.changeScene(new LoginApp(), returnToLoginButton);
    }
}
