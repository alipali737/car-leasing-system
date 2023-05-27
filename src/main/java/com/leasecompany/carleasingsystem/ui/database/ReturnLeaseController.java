package com.leasecompany.carleasingsystem.ui.database;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAO;
import com.leasecompany.carleasingsystem.database.data.leaseAgreement.LeaseAgreement;
import com.leasecompany.carleasingsystem.database.data.leaseAgreement.LeaseAgreementDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ReturnLeaseController implements UIController {
    @FXML private TextField policyReferenceField;
    @FXML private TextField vehicleField;
    @FXML private TextField customerField;
    @FXML private TextField policyStartField;
    @FXML private TextField policyEndField;
    @FXML private TextField lateFeePercField;
    @FXML private TextField allowedMileageField;
    @FXML private TextField totalPriceField;
    @FXML private TextField dailyPaymentField;
    @FXML private TextField monthlyPaymentField;

    @FXML private TextField dateReturnedField;
    @FXML private TextField returnedMileageField;
    @FXML private TextField additionalFeesField;

    @FXML private TextField returnStatusField;
    @FXML private TextField initialPriceField;
    @FXML private TextField mileageFeeField;
    @FXML private TextField lateFeeField;
    @FXML private TextField additionalFeeField;
    @FXML private TextField amountPaidField;
    @FXML private TextField totalAmountField;
    @FXML private TextField debtAmountField;

    @FXML private Button submitButton;
    @FXML private Button backButton;

    private Validator validator;
    private InventoryItemDAO inventoryItemDAO;
    private LeaseAgreementDAO leaseAgreementDAO;
    private CarDAO carDAO;

    private LeaseAgreement leaseAgreement;

    public void initialize(){
        inventoryItemDAO = DAOFactory.getInstance().newInventoryItemDAO();
        leaseAgreementDAO = DAOFactory.getInstance().newLeaseAgreementDAO();
        carDAO = DAOFactory.getInstance().newCarDAO();

        submitButton.setOnAction(this::handleSubmitButton);
        backButton.setOnAction(event -> SceneController.changeScene(SceneController.databaseFXMLPath, backButton));

        validator = new Validator();

        InputValidation.createRegexCheck(validator, "returnDate", dateReturnedField, InputValidation.dateRegex,
                "Please enter a date in valid format: DD/MM/YYYY");
        InputValidation.createFutureDateCheck(validator, "returnDate", dateReturnedField, policyStartField);

        InputValidation.createNumericCheck(validator, "returnedMileage", returnedMileageField, false);
        InputValidation.createNumericRangeCheck(validator, "returnedMileage", returnedMileageField, 0, 9_999_999);

        InputValidation.createNumericCheck(validator, "additionalFees", additionalFeesField, true);
        InputValidation.createNumericRangeCheck(validator, "additionalFees", additionalFeesField, 0, 999999);

        dateReturnedField.textProperty().addListener((observable, oldValue, newValue) -> updatePriceCalculations());
        returnedMileageField.textProperty().addListener(((observable, oldValue, newValue) -> updatePriceCalculations()));
        additionalFeesField.textProperty().addListener(((observable, oldValue, newValue) -> updatePriceCalculations()));

    }

    private void handleSubmitButton(ActionEvent event) {
        if (!validator.validate()) {
            return;
        }

        leaseAgreement.getInventoryItem().setVehicleInStock(true);
        inventoryItemDAO.update(leaseAgreement.getInventoryItem());

        leaseAgreement.getInventoryItem().getVehicle().setMileage(Integer.parseInt(returnedMileageField.getText()));
        carDAO.update(leaseAgreement.getInventoryItem().getVehicle());

        leaseAgreementDAO.delete(leaseAgreement);

        SceneController.changeScene(SceneController.databaseFXMLPath, submitButton);
    }

    @Override
    public void recieveInformation(Object data) {
        LeaseAgreement leaseAgreement = (LeaseAgreement) data;
        this.leaseAgreement = leaseAgreement;

        createExistingMileageValidationCheck();

        policyReferenceField.setText(leaseAgreement.getPolicyReference());

        vehicleField.setText(String.format("%d - %s %s %s",
                leaseAgreement.getInventoryItem().getVehicle().getId(),
                leaseAgreement.getInventoryItem().getVehicle().getBrand(),
                leaseAgreement.getInventoryItem().getVehicle().getModel(),
                leaseAgreement.getInventoryItem().getVehicle().getSpec()));

        customerField.setText(String.format("%d - %s %s",
                leaseAgreement.getCustomer().getId(),
                leaseAgreement.getCustomer().getFirstname(),
                leaseAgreement.getCustomer().getSurname()));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        policyStartField.setText(formatter.format(leaseAgreement.getPolicyStartDate()));
        policyEndField.setText(formatter.format(leaseAgreement.getPolicyEndDate()));

        lateFeePercField.setText(String.valueOf(leaseAgreement.getDailyLateFeePercentage()));
        allowedMileageField.setText(String.valueOf(leaseAgreement.getAnnualMileageAllowed()));

        DecimalFormat df = new DecimalFormat("0.00");
        totalPriceField.setText("£"+ df.format(leaseAgreement.getTotalPrice()));
        monthlyPaymentField.setText("£"+ df.format(leaseAgreement.getTotalPrice()
                / leaseAgreement.getPolicyTerm()));
        dailyPaymentField.setText("£"+ df.format(leaseAgreement.calcDailyPayment()));

        initialPriceField.setText("£"+ df.format(leaseAgreement.getTotalPrice()));
    }

    private void createExistingMileageValidationCheck() {
        validator.createCheck()
                .dependsOn("mileage", returnedMileageField.textProperty())
                .withMethod(c -> {
                    try {
                        int var = Integer.parseInt(c.get("mileage"));
                        int returnedMileage = leaseAgreement.getInventoryItem().getVehicle().getMileage();
                        if (var < returnedMileage) {
                            c.error("Returned mileage cannot be lower than before lease: "+returnedMileage);
                        }
                    } catch (Exception ignored) {
                    }
                })
                .decorates(returnedMileageField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void updatePriceCalculations() {
        if (!validator.validate()) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate returnedDate = LocalDate.parse(dateReturnedField.getText(), formatter);

        Date temp = sqlDatetoUtilDate(leaseAgreement.getPolicyStartDate());
        LocalDate startDate = temp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        temp = sqlDatetoUtilDate(leaseAgreement.getPolicyEndDate());
        LocalDate endDate = temp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Return Status & Late Fees
        double lateFeeAmount = 0;
        if (returnedDate.equals(endDate)) {
            returnStatusField.setText("On-time");
        } else if (returnedDate.isBefore(endDate)) {
            returnStatusField.setText("Early");
        } else {
            returnStatusField.setText("Late");
            int daysLate = (int) ChronoUnit.DAYS.between(endDate, returnedDate);
            lateFeeAmount = leaseAgreement.calcLateFee(daysLate);
        }
        DecimalFormat df = new DecimalFormat("0.00");
        lateFeeField.setText("£"+df.format(lateFeeAmount));

        // Additional Miles
        double pricePerAdditionalMile = 0.10; // 10p per additional mile
        int mileageAdded = Integer.parseInt(returnedMileageField.getText()) -
                leaseAgreement.getInventoryItem().getVehicle().getMileage();

        double additionalMileageFee = leaseAgreement.calcAdditionalMileFee(pricePerAdditionalMile, mileageAdded);
        mileageFeeField.setText("£"+df.format(additionalMileageFee));

        // Additional Fees added
        double additionalFeeAmount = Double.parseDouble(additionalFeesField.getText());
        additionalFeeField.setText("£" + df.format(additionalFeeAmount));

        // Amount already paid
        double amountPaid;
        if (returnedDate.isBefore(endDate)) {
            amountPaid = leaseAgreement.calcAmountPaidBetweenDates(startDate, returnedDate);
        } else {
            amountPaid = leaseAgreement.getTotalPrice();
        }
        amountPaidField.setText("£" + df.format(amountPaid));

        // Total amount
        double totalAmount = leaseAgreement.getTotalPrice() + additionalMileageFee + lateFeeAmount + additionalFeeAmount;
        totalAmountField.setText("£"+df.format(totalAmount));

        // Amount still owed
        debtAmountField.setText("£" + df.format((totalAmount-amountPaid)));
    }

    /**
     * Fixes strange behaviour where a java.util.Date becomes a java.sql.Date at runtime
     * @param brokenDate
     */
    private java.util.Date sqlDatetoUtilDate(Date brokenDate) {
        java.sql.Date sqlDate = (java.sql.Date) brokenDate;
        return new java.util.Date(sqlDate.getTime());
    }
}
