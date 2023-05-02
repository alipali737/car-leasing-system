package com.leasecompany.carleasingsystem.ui.home;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.car.Car;
import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItem;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAO;
import com.leasecompany.carleasingsystem.ui.shared.SidebarController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableView resultsTable;

    private CarDAO carDAO;
    private InventoryItemDAO inventoryItemDAO;

    public void initialize() {
        updateButton.setOnAction(this::handleUpdateButton);

        carDAO = DAOFactory.getInstance().newCarDAO();
        inventoryItemDAO = DAOFactory.getInstance().newInventoryItemDAO();

        ObservableList<String> uniqueBrands = FXCollections.observableList(carDAO.getUniqueStringValuesInColumn("brand"));
        uniqueBrands.add(0, "Any Make");
        setComboBoxOptions(makeComboBox, uniqueBrands);

        ObservableList<String> uniqueTypes = FXCollections.observableList(carDAO.getUniqueStringValuesInColumn("bodyType"));
        uniqueTypes.add(0, "Any Type");
        setComboBoxOptions(typeComboBox, uniqueTypes);

        ObservableList<String> budgetDropdownOptions = FXCollections.observableList(List.of("Any Budget", "Up to £150", "Up to £250", "Up to £350", "Up to £500", "Up to £750", "Up to £1000"));
        setComboBoxOptions(budgetComboBox, budgetDropdownOptions);
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

        System.out.printf("selectedMake: %s, selectedType: %s, selectedBudget: %s, searchBarText: %s%n", selectedMake, selectedType, selectedBudget, searchBarText);

        // TODO: Get all in stock inventory items
        List<InventoryItem> inventoryItems = inventoryItemDAO.findByCriteria(Map.of("vehicleInStock", true));

        System.out.println(inventoryItems);

        // TODO: filter them based on their vehicle associated
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
                if (!(itemVehicle.getModel() + " " + itemVehicle.getSpec()).contains(searchBarText)) {
                    continue;
                }
            }

            filteredCars.add(itemVehicle);
        }

        System.out.println(filteredCars);

        // TODO: Get results in a table
        // TODO: Display the results in the TableView
    }
}
