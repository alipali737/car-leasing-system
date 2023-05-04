package com.leasecompany.carleasingsystem.ui.home;

import com.leasecompany.carleasingsystem.database.data.car.Car;
import com.leasecompany.carleasingsystem.ui.Controller;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CarDetailController implements Controller {
    @FXML
    private Text vehicleTitle;
    @FXML
    private Circle vehicleColor;
    @FXML
    private Text vehicleDescription;
    @FXML
    private TableView<Car> vehicleInfoTable;
    @FXML
    private Text vehicleReg;
    @FXML
    private ToggleGroup leaseTermToggles;
    @FXML
    private ToggleGroup initialDepositToggles;
    @FXML
    private Text leasePriceText;
    @FXML
    private Button createButton;
    @FXML
    private Button backButton;

    private Car vehicle;
    @Override
    public void recieveInformation(Object data) {
        this.vehicle = (Car) data;
        updateSceneData();
    }

    public void initialize() {
        // Setup right column defaults
        leaseTermToggles.selectToggle(leaseTermToggles.getToggles().get(1));
        initialDepositToggles.selectToggle(initialDepositToggles.getToggles().get(1));

        // Setup CellValueFactories for columns in table
        TableColumn<Car, String> fuelColumn = (TableColumn<Car, String>) vehicleInfoTable.getColumns().get(0);
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelType"));

        TableColumn<Car, String> engineColumn = (TableColumn<Car, String>) vehicleInfoTable.getColumns().get(1);
        engineColumn.setCellValueFactory(data -> {
            Car car = data.getValue();
            return new SimpleStringProperty(car.getEngineSize() + "L");
        });

        TableColumn<Car, Integer> doorsColumn = (TableColumn<Car, Integer>) vehicleInfoTable.getColumns().get(2);
        doorsColumn.setCellValueFactory(new PropertyValueFactory<>("doors"));

        TableColumn<Car, Integer> seatsColumn = (TableColumn<Car, Integer>) vehicleInfoTable.getColumns().get(3);
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        TableColumn<Car, Integer> yearColumn = (TableColumn<Car, Integer>) vehicleInfoTable.getColumns().get(4);
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("prodYear"));

        TableColumn<Car, Integer> mileageColumn = (TableColumn<Car, Integer>) vehicleInfoTable.getColumns().get(5);
        mileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));

        // Assign handlers
        leaseTermToggles.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updateLeasePriceText();
            }
        });

        initialDepositToggles.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updateLeasePriceText();
            }
        });

        createButton.setOnAction(this::handleCreateButton);
        backButton.setOnAction(this::handleBackButton);

    }

    private void updateSceneData() {
        // Fill data from vehicle
        vehicleTitle.setText(String.format("%s %s %s", vehicle.getBrand(), vehicle.getModel(), vehicle.getSpec()));
        vehicleColor.setFill(Color.valueOf(vehicle.getColor()));
        vehicleDescription.setText(vehicle.getDescription());
        vehicleReg.setText(vehicle.getRegistration());

        ObservableList<Car> data = FXCollections.observableArrayList(vehicle);
        vehicleInfoTable.setItems(data);

        updateLeasePriceText();
    }

    private void updateLeasePriceText() {
        ToggleButton leaseTermSelectedToggle = (ToggleButton) leaseTermToggles.getSelectedToggle();
        ToggleButton initialDepositSelectedToggle = (ToggleButton) initialDepositToggles.getSelectedToggle();

        leasePriceText.setText("£" +
                vehicle.calcMonthlyPaymentPrice(Integer.parseInt(leaseTermSelectedToggle.getText()),
                Integer.parseInt(initialDepositSelectedToggle.getText())) +
                "/mo");
    }

    private void handleCreateButton(ActionEvent event) {
        System.out.println("Create Button Clicked");
    }

    private void handleBackButton(ActionEvent event) {
        SceneController.changeScene(SceneController.homeFXMLPath, backButton);
    }

}
