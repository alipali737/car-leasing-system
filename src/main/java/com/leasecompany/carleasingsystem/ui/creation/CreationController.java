package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.ui.UIController;
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
        createVehicleButton.setOnAction(this::handleCreateVehicle);
    }

    private void handleCreateCustomer(ActionEvent event) {
        System.out.println("Create Customer Button Pressed");
    }

    private void handleCreateVehicle(ActionEvent event) {
        System.out.println("Create Vehicle Button Pressed");
    }

    @Override
    public void recieveInformation(Object data) {}
}
