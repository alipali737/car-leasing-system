package com.leasecompany.carleasingsystem.utils.scene;

import javafx.scene.control.Alert;

public class Alerts {

    public void createErrorAlert(String title, String header, String context) {
        createAlert(Alert.AlertType.ERROR, title, header, context);
    }

    public void createInfoAlert(String title, String header, String context) {
        createAlert(Alert.AlertType.INFORMATION, title, header, context);
    }

    private void createAlert(Alert.AlertType type, String title, String header, String context) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }


}
