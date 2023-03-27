package com.leasecompany.carleasingsystem;

import com.leasecompany.carleasingsystem.ui.login.LoginApp;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setResizable(false);
        primaryStage.setTitle("Car Leasing System");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);

        LoginApp loginApp = new LoginApp();
        loginApp.start(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
