package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.car.Car;
import com.leasecompany.carleasingsystem.database.data.customer.Customer;
import com.leasecompany.carleasingsystem.database.data.customer.CustomerDAO;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItem;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAO;
import com.leasecompany.carleasingsystem.database.data.leaseAgreement.LeaseAgreement;
import com.leasecompany.carleasingsystem.database.data.leaseAgreement.LeaseAgreementDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.Alerts;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CreateLeaseController implements UIController {
    @FXML private TextField vehicleField;
    @FXML private TextField policyStartField;
    @FXML private TextField policyEndField;
    @FXML private TextField lateFeePercField;
    @FXML private TextField allowedMileageField;
    @FXML private TextField totalPriceField;
    @FXML private TextField dailyPaymentField;
    @FXML private TextField monthlyPaymentField;
    @FXML private ComboBox<String> customerComboBox;
    @FXML private Button submitButton;
    @FXML private Button backButton;

    private int initialDepositMonths;
    private int policyTerm;
    private Car vehicle;
    private InventoryItemDAO inventoryItemDAO;
    private LeaseAgreementDAO leaseAgreementDAO;
    private CustomerDAO customerDAO;
    private Validator validator;

    public void initialize() {
        submitButton.setOnAction(this::handleSubmitButton);
        backButton.setOnAction(event -> SceneController.changeScene(SceneController.carDetailFXMLPath, vehicle, backButton));

        policyStartField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!policyStartField.getText().matches("^\\d{2}/\\d{2}/\\d{4}+$")) {
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate = LocalDate.parse(policyStartField.getText(), formatter);
            LocalDate endDate = startDate.plusMonths(policyTerm);
            policyEndField.setText(endDate.format(formatter));
        }));

        inventoryItemDAO = DAOFactory.getInstance().newInventoryItemDAO();
        leaseAgreementDAO = DAOFactory.getInstance().newLeaseAgreementDAO();
        customerDAO = DAOFactory.getInstance().newCustomerDAO();

        validator = new Validator();

        validator.createCheck()
                .dependsOn("customerComboBox", customerComboBox.getSelectionModel().selectedItemProperty())
                .withMethod(c -> {
                    String var = c.get("customerComboBox");
                    if (var == null || var.isBlank()) {
                        c.error("Please select an option.");
                    }
                })
                .decorates(customerComboBox)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        InputValidation.createRegexCheck(validator, "policyStartDate", policyStartField, InputValidation.dateRegex,
                "Please enter a date in valid format: DD/MM/YYYY");
        InputValidation.createFutureDateCheck(validator, "policyStartDate", policyStartField);

        InputValidation.createNumericCheck(validator, "dailyLateFeePerc", lateFeePercField, true);
        InputValidation.createNumericRangeCheck(validator, "dailyLateFeePerc", lateFeePercField, 0, 100);
        InputValidation.createNumericCheck(validator, "allowedMileage", allowedMileageField, false);
        InputValidation.createNumericRangeCheck(validator, "allowedMileage", allowedMileageField, 2000, 999_999);

    }

    private void handleSubmitButton(ActionEvent event) {
        if (!validator.validate()){
            return;
        }

        // Get Inventory Item
        InventoryItem inventoryItem = inventoryItemDAO.findByCriteria(Map.of("vehicle", vehicle)).get(0);

        // Get Customer
        String customerIDStr = customerComboBox.getValue().replaceAll("\\D", "");
        Long customerID = Long.parseLong(customerIDStr);
        Customer customer = customerDAO.findByID(customerID);

        String policyRef = createPolicyReference(customer);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(policyStartField.getText(), formatter);
        LocalDate endDate = LocalDate.parse(policyEndField.getText(), formatter);

        LeaseAgreement newLeaseAgreement = new LeaseAgreement(
                inventoryItem,
                customer,
                policyRef,
                Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                policyTerm,
                initialDepositMonths,
                Double.parseDouble(totalPriceField.getText().replaceAll("£", "")),
                Double.parseDouble(lateFeePercField.getText()),
                Integer.parseInt(allowedMileageField.getText())
        );

        Alerts alerts = new Alerts();
        if (leaseAgreementDAO.create(newLeaseAgreement)) {
            // Set the vehicle to out of stock
            inventoryItem.setVehicleInStock(false);
            inventoryItemDAO.update(inventoryItem);

            alerts.createInfoAlert("Lease Created", "Lease Agreement has been created", "Lease Reference: "+policyRef);
            SceneController.changeScene(SceneController.homeFXMLPath, submitButton);
        } else {
            alerts.createErrorAlert("Failed to create Lease", "The lease couldn't be created", "Please try again");
        }
    }

    private String createPolicyReference(Customer customer) {
        String firstnameInitial = customer.getFirstname().substring(0,1).toUpperCase();
        String surnameInitials = customer.getSurname().substring(0,2).toUpperCase();

        Random random = new Random();
        int randomDigits = random.nextInt(100);

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        return firstnameInitial + surnameInitials + randomDigits + timestamp;
    }

    @Override
    public void recieveInformation(Object data) {
        CreateLeaseData createLeaseData = (CreateLeaseData) data;
        this.vehicle = createLeaseData.vehicle;
        vehicleField.setText(String.format("%d - %s %s %s", vehicle.getId(), vehicle.getBrand(),
                vehicle.getModel(), vehicle.getSpec()));

        List<Customer> customers = customerDAO.findAll();
        List<String> customerStrings = new ArrayList<>();
        for (Customer c : customers) {
            customerStrings.add(String.format("%d - %s %s", c.getId(), c.getFirstname(), c.getSurname()));
        }
        customerComboBox.setItems(FXCollections.observableList(customerStrings));


        this.initialDepositMonths = createLeaseData.initialDepositMonths;
        this.policyTerm = createLeaseData.policyTerm;

        policyStartField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        double monthlyPrice = vehicle.calcMonthlyPaymentPrice(policyTerm, initialDepositMonths);

        DecimalFormat df = new DecimalFormat("0.00");
        totalPriceField.setText("£"+ df.format(monthlyPrice * policyTerm));
        monthlyPaymentField.setText("£"+ df.format(monthlyPrice));
        dailyPaymentField.setText("£"+ df.format(monthlyPrice / 30));
    }

}


