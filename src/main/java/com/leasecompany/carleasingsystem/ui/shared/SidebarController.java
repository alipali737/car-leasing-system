package com.leasecompany.carleasingsystem.ui.shared;

import com.leasecompany.carleasingsystem.ui.home.HomeApp;
import com.leasecompany.carleasingsystem.ui.login.LoginApp;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SidebarController {
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
        SceneController.changeScene(new HomeApp(), homeButton);
    }

    private void handleCreateButton(ActionEvent event) {
        System.out.println("Create Button Clicked");
    }

    private void handleDatabaseButton(ActionEvent event) {
        System.out.println("Database Button Clicked");
    }

    private void handleExitButton(ActionEvent event) {
        SceneController.changeScene(new LoginApp(), exitButton);
    }

}
