package com.leasecompany.carleasingsystem.ui.shared;

import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SidebarController implements UIController {
    @FXML
    private Button homeButton;
    @FXML
    private Button createButton;
    @FXML
    private Button databaseButton;
    @FXML
    private Button exitButton;

    public void initialize() {
        homeButton.setOnAction(this::handleHomeButton);
        createButton.setOnAction(this::handleCreateButton);
        databaseButton.setOnAction(this::handleDatabaseButton);
        exitButton.setOnAction(this::handleExitButton);
    }

    private void handleHomeButton(ActionEvent event) {
        SceneController.changeScene(SceneController.homeFXMLPath, homeButton);
    }

    private void handleCreateButton(ActionEvent event) {
        SceneController.changeScene(SceneController.creationFXMLPath, createButton);
    }

    private void handleDatabaseButton(ActionEvent event) {
        SceneController.changeScene(SceneController.databaseFXMLPath, databaseButton);
    }

    private void handleExitButton(ActionEvent event) {
        SceneController.changeScene(SceneController.loginFXMLPath, exitButton);
    }

    @Override
    public void recieveInformation(Object data) {}
}
