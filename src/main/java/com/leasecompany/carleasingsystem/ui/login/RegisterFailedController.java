package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.ui.Controller;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RegisterFailedController implements Controller {
    @FXML
    private Button returnToRegisterButton;
    @FXML
    public void initialize() {
        returnToRegisterButton.setOnAction(this::handleReturnButtonClick);
    }

    private void handleReturnButtonClick(ActionEvent event){
        SceneController.changeScene(SceneController.registerFXMLPath, returnToRegisterButton);
    }

    @Override
    public void recieveInformation(Object data) {}
}
