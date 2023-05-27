package com.leasecompany.carleasingsystem.ui.database;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.DataEntity;
import com.leasecompany.carleasingsystem.database.data.GenericDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.Alerts;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController implements UIController {
    @FXML
    private ComboBox<String> tableComboBox;
    @FXML
    private TextField searchBar;
    @FXML
    private Button updateButton;
    @FXML
    private TableView resultsTable;
    @FXML
    private Label statusLabel;

    private ContextMenu contextMenu;

    public void initialize() {
        statusLabel.setText("");
        statusLabel.setAlignment(Pos.CENTER);

        updateButton.setOnAction(this::handleUpdateButton);

        ObservableList<String> tables = FXCollections.observableList(DAOFactory.getInstance().getTables());
        // No user should be able to edit this table as it's primarily used for backend
        tables.remove("InventoryItems");
        if (!DAOFactory.getLoggedInUser().getAdmin()) {
            // If not admin, don't let them edit the Users Table or the Cars Table
            tables.remove("Users");
            tables.remove("Cars");
        }

        tableComboBox.setItems(tables.sorted());

        contextMenu = new ContextMenu();

        resultsTable.setContextMenu(contextMenu);
    }

    private void handleDeleteRecordButton(ActionEvent event) {
        Object selectedItem = resultsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            GenericDAO dao = getTableDAO();
            dao.delete(selectedItem);
            resultsTable.getItems().remove(selectedItem);
        }
    }

    private <T extends DataEntity> void handleUpdateButton(ActionEvent event) {
        GenericDAO dao = getTableDAO();
        if (dao == null) {
            System.err.println("Unable to create table DAO");
            return;
        }
        List<String> columnNames;
        try {
            // Initially Clear Menu
            contextMenu.getItems().clear();

            // Add Delete function
            MenuItem deleteMenuItem = new MenuItem("Delete Record");
            deleteMenuItem.setOnAction(this::handleDeleteRecordButton);
            contextMenu.getItems().add(deleteMenuItem);

            switch (tableComboBox.getValue()) {
                case "Cars" -> {
                    MenuItem createReportMenuItem = new MenuItem("Create Report");
                    createReportMenuItem.setOnAction(this::handleCreateReportButton);
                    contextMenu.getItems().add(createReportMenuItem);
                    columnNames = List.of("brand", "model", "spec", "prodYear", "bodyType", "registration", "engineSize", "doors", "seats", "color", "fuelType", "mileage", "value", "description");
                }
                case "Users" -> columnNames = List.of("username", "approved", "admin");
                case "Customers" -> columnNames = List.of("firstname", "surname", "addressLine1", "addressLine2", "city", "postcode", "phone", "email", "driverLicenseNumber", "dob");
                case "LeaseAgreements" -> {
                    MenuItem createReportMenuItem = new MenuItem("Create Report");
                    createReportMenuItem.setOnAction(this::handleCreateReportButton);
                    contextMenu.getItems().add(createReportMenuItem);

                    MenuItem returnVehicleMenuItem = new MenuItem("Return Vehicle");
                    // TODO: Add return vehicle functionality
                    returnVehicleMenuItem.setOnAction(this::handleReturnVehicleMenuItem);
                    contextMenu.getItems().add(returnVehicleMenuItem);
                    columnNames = List.of("policyReference", "policyStartDate", "policyEndDate", "policyTerm", "initialDepositMonths", "totalPrice", "dailyLateFeePercentage", "annualMileageAllowed");
                }
                default -> {
                    System.err.println("No table columns defined for " + tableComboBox.getValue());
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to find table name, it may not be set: " + e);
            return;
        }

        List<T> results = (List<T>) dao.findAll();
        if (results.size() == 0) {
            Alerts alerts = new Alerts();
            alerts.createErrorAlert("Empty Results", "No results found", "No results were found for the given criteria");
            return;
        }

        List<T> filteredResults = new ArrayList<>();
        results.forEach(e -> {
            if (e.toString().toLowerCase().contains(searchBar.getText().toLowerCase())) {
                filteredResults.add(e);
            }
        });

        TableColumn<T, ?>[] tableColumns = results.get(0).getTableColumns(columnNames);

        for (TableColumn<T, ?> column : tableColumns) {
            column.setEditable(true);
            column.setOnEditCommit(editEvent -> {
                T entity = editEvent.getRowValue();
                if (!editEvent.getNewValue().equals(editEvent.getOldValue())) {
                    TableColumn<T, ?> editedColumn = editEvent.getTableColumn();
                    String propertyName = editedColumn.getText();
                    try {
                        Field field = entity.getClass().getDeclaredField(propertyName);
                        field.setAccessible(true);
                        field.set(entity, editEvent.getNewValue());
                        dao.update(entity);
                    } catch (Exception e) {
                        System.err.println("Failed to update value in database: " + e);
                    }
                }
            });
        }

        resultsTable.getColumns().setAll(tableColumns);

        resultsTable.getItems().setAll(filteredResults);

    }

    private void handleCreateReportButton(ActionEvent event) {
        Object selectedItem = resultsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        Path dirPath = Paths.get("reports");

        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataEntity<?> entity = (DataEntity<?>) selectedItem;
        entity.generateReportFile(dirPath.toString(), statusLabel);
    }

    private void handleReturnVehicleMenuItem(ActionEvent event) {
        Object selectedItem = resultsTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        SceneController.changeScene(SceneController.returnLeaseFXMLPath, selectedItem, resultsTable);
    }

    private GenericDAO getTableDAO() {
        try {
            return switch (tableComboBox.getValue()) {
                case "Cars" -> DAOFactory.getInstance().newCarDAO();
                case "Users" -> DAOFactory.getInstance().newUserDAO();
                case "Customers" -> DAOFactory.getInstance().newCustomerDAO();
                case "LeaseAgreements" -> DAOFactory.getInstance().newLeaseAgreementDAO();
                default -> null;
            };
        } catch (Exception e) {
            System.out.println("Unable to find table name, it may not be set: " + e);
            return null;
        }
    }

    @Override
    public void recieveInformation(Object data) {}
}
