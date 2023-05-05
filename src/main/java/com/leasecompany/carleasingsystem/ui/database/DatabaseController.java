package com.leasecompany.carleasingsystem.ui.database;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.DataEntity;
import com.leasecompany.carleasingsystem.database.data.GenericDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Field;
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

    public void initialize() {
        updateButton.setOnAction(this::handleUpdateButton);

        ObservableList<String> tables = FXCollections.observableList(DAOFactory.getInstance().getTables());
        tables.remove("InventoryItems");
        if (!DAOFactory.getLoggedInUser().getAdmin()) {
            // If not admin, don't let them edit the Users Table
            tables.remove("Users");
        }
        tableComboBox.setItems(tables);
    }

    private <T extends DataEntity> void handleUpdateButton(ActionEvent event) {
        GenericDAO dao;
        List<String> columnNames;
        try {
            switch (tableComboBox.getValue()) {
                case "Cars" -> {
                    dao = DAOFactory.getInstance().newCarDAO();
                    columnNames = List.of("brand", "model", "spec", "prodYear", "bodyType", "registration", "engineSize", "doors", "seats", "color", "fuelType", "mileage", "value", "description");
                }
                case "Users" -> {
                    dao = DAOFactory.getInstance().newUserDAO();
                    columnNames = List.of("username", "approved", "admin");
                }
                default -> {
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to find table name, it may not be set: " + e);
            return;
        }

        List<T> results = (List<T>) dao.findAll();
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

    @Override
    public void recieveInformation(Object data) {}
}
