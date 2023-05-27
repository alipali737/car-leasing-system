package com.leasecompany.carleasingsystem.utils.scene;

import com.leasecompany.carleasingsystem.ui.UIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneController {
    public static final String carDetailFXMLPath = "/com/leasecompany/carleasingsystem/fxml/home/carDetailScene.fxml";
    public static final String homeFXMLPath = "/com/leasecompany/carleasingsystem/fxml/home/homeScene.fxml";
    public static final String loginFXMLPath = "/com/leasecompany/carleasingsystem/fxml/login/loginScene.fxml";
    public static final String loginFailedFXMLPath = "/com/leasecompany/carleasingsystem/fxml/login/loginFailedScene.fxml";
    public static final String registerFXMLPath = "/com/leasecompany/carleasingsystem/fxml/login/registerScene.fxml";
    public static final String registerConfirmationFXMLPath = "/com/leasecompany/carleasingsystem/fxml/login/registerConfirmationScene.fxml";
    public static final String registerFailedFXMLPath = "/com/leasecompany/carleasingsystem/fxml/login/registerFailedScene.fxml";
    public static final String creationFXMLPath = "/com/leasecompany/carleasingsystem/fxml/creation/creationScene.fxml";
    public static final String createVehicleFXMLPath = "/com/leasecompany/carleasingsystem/fxml/creation/createVehicleScene.fxml";
    public static final String createCustomerFXMLPath = "/com/leasecompany/carleasingsystem/fxml/creation/createCustomerScene.fxml";
    public static final String createLeaseFXMLPath = "/com/leasecompany/carleasingsystem/fxml/creation/createLeaseScene.fxml";
    public static final String databaseFXMLPath = "/com/leasecompany/carleasingsystem/fxml/database/databaseScene.fxml";
    public static final String returnLeaseFXMLPath = "/com/leasecompany/carleasingsystem/fxml/database/returnLeaseScene.fxml";

    public static void changeScene(String fxmlPath, Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource(fxmlPath)));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Unable to switch scene: " + e);
            e.printStackTrace();
        }
    }

    public static void changeScene(String fxmlPath, Node node) {
        Stage primaryStage = (Stage) node.getScene().getWindow();
        changeScene(fxmlPath, primaryStage);
    }

    public static void changeScene(String fxmlPath, Object data, Node node) {
        Stage primaryStage = (Stage) node.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlPath));
            Parent root = loader.load();

            UIController controller = loader.getController();
            controller.recieveInformation(data);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Unable to switch scene: " + e);
            e.printStackTrace();
        }
    }
}
