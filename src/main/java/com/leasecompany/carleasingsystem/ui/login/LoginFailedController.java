package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.ui.Controller;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginFailedController implements Controller {
    @FXML
    private Button returnToLoginButton;
    @FXML
    public void initialize() {
        returnToLoginButton.setOnAction(this::handleReturnButtonClick);
    }

    private void handleReturnButtonClick(ActionEvent event){
        SceneController.changeScene(SceneController.loginFXMLPath, returnToLoginButton);
    }

    @Override
    public void recieveInformation(Object data) {}
}
