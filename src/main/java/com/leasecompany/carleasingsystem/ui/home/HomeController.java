package com.leasecompany.carleasingsystem.ui.home;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.car.Car;
import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.ui.shared.SidebarController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import java.util.List;

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

    private CarDAO carDAO;

    public void initialize() {
        updateButton.setOnAction(this::handleUpdateButton);

        DAOFactory daoFactory = new DAOFactory();
        carDAO = daoFactory.newCarDAO();

        setCheckComboBoxOptions(makeCheckComboBox, Car.makeDropdownOptions);
        setCheckComboBoxOptions(typeCheckComboBox, Car.typeDropdownOptions);
    }

    private void setCheckComboBoxOptions(CheckComboBox<String> checkComboBox, ObservableList<String> options) {
        List<String> items = checkComboBox.getItems();
        items.addAll(options);
    }

    private void handleUpdateButton(ActionEvent event) {
        // TODO: Build Query

//        make = makeCheckComboBox.getCheckModel().getCheckedItems();
//
//        Map<String, Object> criteria = new HashMap<>();
//        List<Car> cars = carDAO.findByCriteriaInStock(criteria);
        // TODO: Query DB
        // TODO: Get results in a table
        // TODO: Display the results in the TableView
    }
}
