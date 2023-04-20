package com.leasecompany.carleasingsystem.ui.home;

import com.leasecompany.carleasingsystem.ui.shared.SidebarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

public class HomeController {
    @FXML
    private SidebarController sidebarController;
    @FXML
    private CheckComboBox<String> makeCheckComboBox;
    @FXML
    private CheckComboBox<String> typeCheckComboBox;
    @FXML
    private ComboBox<String> budgetComboBox;
    @FXML
    private Button updateButton;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView resultsTable;

    public void initialize() {
        updateButton.setOnAction(this::handleUpdateButton);
    }

    private void handleUpdateButton(ActionEvent event) {
        // TODO: Build Query
        // TODO: Query DB
        // TODO: Get results in a table
        // TODO: Display the results in the TableView
    }
}
