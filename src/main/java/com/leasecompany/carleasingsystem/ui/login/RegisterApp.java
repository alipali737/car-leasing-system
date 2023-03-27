package com.leasecompany.carleasingsystem.ui.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/leasecompany/carleasingsystem/fxml/login/registerScene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }
}
