package com.leasecompany.carleasingsystem.utils.scene;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class SceneController {
    public static void changeScene(Application app, Button btn) {
        Stage primaryStage = (Stage) btn.getScene().getWindow();
        try {
            app.start(primaryStage);
        } catch (Exception e) {
            System.out.println("Unable to switch scene: " + e);
            e.printStackTrace();
        }
    }
    public static void changeScene(Application app, Hyperlink link) {
        Stage primaryStage = (Stage) link.getScene().getWindow();
        try {
            app.start(primaryStage);
        } catch (Exception e) {
            System.out.println("Unable to switch scene: " + e);
            e.printStackTrace();
        }
    }
}
