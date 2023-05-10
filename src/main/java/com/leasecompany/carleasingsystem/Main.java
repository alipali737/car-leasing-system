package com.leasecompany.carleasingsystem;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Initialised the DAOFactory and creates the internal sessionFactory
        DAOFactory.getInstance();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Car Leasing System");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(735);

        SceneController.changeScene(SceneController.loginFXMLPath, primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
