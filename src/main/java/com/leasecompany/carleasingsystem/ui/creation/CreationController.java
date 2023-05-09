package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreationController implements UIController {
    @FXML
    private Button createCustomerButton;
    @FXML
    private Button createVehicleButton;

    public void initialize() {
        createCustomerButton.setOnAction(this::handleCreateCustomer);

        if (!DAOFactory.getLoggedInUser().getAdmin()) {
            // Non-admin users shouldn't be able to add a vehicle
            createVehicleButton.setVisible(false);
        } else {
            createVehicleButton.setOnAction(this::handleCreateVehicle);
        }

    }

    private void handleCreateCustomer(ActionEvent event) {

    }

    private void handleCreateVehicle(ActionEvent event) {
        SceneController.changeScene(SceneController.createVehicleFXMLPath, createCustomerButton);
    }

    @Override
    public void recieveInformation(Object data) {}
}
