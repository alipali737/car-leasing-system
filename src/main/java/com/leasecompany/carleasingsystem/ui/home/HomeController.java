package com.leasecompany.carleasingsystem.ui.home;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.car.Car;
import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItem;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAO;
import com.leasecompany.carleasingsystem.ui.shared.SidebarController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeController {
    @FXML
    private SidebarController sidebarController;
    @FXML
    private ComboBox<String> makeComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<String> budgetComboBox;
    @FXML
    private Button updateButton;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Car> resultsTable;

    private InventoryItemDAO inventoryItemDAO;

    public void initialize() {
        updateButton.setOnAction(this::handleUpdateButton);

        CarDAO carDAO = DAOFactory.getInstance().newCarDAO();
        inventoryItemDAO = DAOFactory.getInstance().newInventoryItemDAO();

        ObservableList<String> uniqueBrands = FXCollections.observableList(carDAO.getUniqueStringValuesInColumn("brand"));
        uniqueBrands.add(0, "Any Make");
        setComboBoxOptions(makeComboBox, uniqueBrands);

        ObservableList<String> uniqueTypes = FXCollections.observableList(carDAO.getUniqueStringValuesInColumn("bodyType"));
        uniqueTypes.add(0, "Any Type");
        setComboBoxOptions(typeComboBox, uniqueTypes);

        ObservableList<String> budgetDropdownOptions = FXCollections.observableList(List.of("Any Budget", "Up to £150", "Up to £250", "Up to £350", "Up to £500", "Up to £750", "Up to £1000"));
        setComboBoxOptions(budgetComboBox, budgetDropdownOptions);

        resultsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Car selectedCar = resultsTable.getSelectionModel().getSelectedItem();
                System.out.printf("Selected vehicle id: %d\n", selectedCar.getId());
            }
        });

        TableColumn<Car, Long> idColumn = (TableColumn<Car, Long>) resultsTable.getColumns().get(0);
        idColumn.setCellValueFactory(new PropertyValueFactory<Car, Long>("id"));

        TableColumn<Car, String> vehicleColumn = (TableColumn<Car, String>) resultsTable.getColumns().get(1);
        vehicleColumn.setCellValueFactory(data -> {
            Car car = data.getValue();
            return new SimpleStringProperty(car.getBrand() + " " + car.getModel() + " " + car.getSpec());
        });

        TableColumn<Car, String> fuelColumn = (TableColumn<Car, String>) resultsTable.getColumns().get(2);
        fuelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("fuelType"));

        TableColumn<Car, Integer> doorsColumn = (TableColumn<Car, Integer>) resultsTable.getColumns().get(3);
        doorsColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("doors"));

        TableColumn<Car, String> engineColumn = (TableColumn<Car, String>) resultsTable.getColumns().get(4);
        engineColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("engineSize"));

        TableColumn<Car, String> colourColumn = (TableColumn<Car, String>) resultsTable.getColumns().get(5);
        colourColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("color"));

        TableColumn<Car, String> priceColumn = (TableColumn<Car, String>) resultsTable.getColumns().get(6);
        priceColumn.setCellValueFactory(data -> {
            Car car = data.getValue();
            return new SimpleStringProperty("£" + String.valueOf(car.calcMonthlyPaymentPrice(36, 9)) + "/mo");
        });
    }

    private void setComboBoxOptions(ComboBox<String> comboBox, ObservableList<String> options) {
        List<String> items = comboBox.getItems();
        items.addAll(options);
    }

    private void handleUpdateButton(ActionEvent event) {

        String selectedMake = makeComboBox.getValue();
        String selectedType = typeComboBox.getValue();
        String selectedBudget = budgetComboBox.getValue();
        String searchBarText = searchBar.getText();

        List<InventoryItem> inventoryItems = inventoryItemDAO.findByCriteria(Map.of("vehicleInStock", true));
        List<Car> filteredCars = new ArrayList<>();

        for (InventoryItem item : inventoryItems) {
            Car itemVehicle = item.getVehicle();
            if (selectedMake != null && !selectedMake.equals("Any Make")) {
                if (!itemVehicle.getBrand().equals(selectedMake)) {
                    continue;
                }
            }

            if (selectedType != null && !selectedType.equals("Any Type")) {
                if (!itemVehicle.getBodyType().equals(selectedType)) {
                    continue;
                }
            }

            if (selectedBudget != null && !selectedBudget.equals("Any Budget")) {
                double budget = Double.parseDouble(selectedBudget.replaceAll("[^0-9]", ""));
                if (itemVehicle.calcMonthlyPaymentPrice(36,9) > budget) {
                    continue;
                }
            }

            if (!searchBarText.isBlank()) {
                if (!(itemVehicle.getModel() + " " + itemVehicle.getSpec()).toLowerCase().contains(searchBarText.toLowerCase())) {
                    continue;
                }
            }

            filteredCars.add(itemVehicle);
        }

        ObservableList<Car> data = FXCollections.observableArrayList(filteredCars);
        resultsTable.setItems(data);
    }
}
