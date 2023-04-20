package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RegisterConfirmationController {
    @FXML
    private Button returnButton;

    public void initialize() {
        returnButton.setOnAction(this::handleReturnButtonClick);
    }

    private void handleReturnButtonClick(ActionEvent event) {
        SceneController.changeScene(new LoginApp(), returnButton);
    }
}
