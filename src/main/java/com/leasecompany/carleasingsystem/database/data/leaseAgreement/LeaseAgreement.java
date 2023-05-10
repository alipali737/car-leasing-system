package com.leasecompany.carleasingsystem.database.data.leaseAgreement;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.DataEntity;
import com.leasecompany.carleasingsystem.database.data.customer.Customer;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItem;
import javafx.scene.control.Label;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LeaseAgreement implements DataEntity {
    private Long id;
    private InventoryItem inventoryItem;
    private Customer customer;
    private String policyReference;
    private Date policyStartDate;
    private Date policyEndDate;
    private int policyTerm;
    private int initialDepositMonths;
    private double totalPrice;
    private double dailyLateFeePercentage;
    private int annualMileageAllowed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(Date policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public Date getPolicyEndDate() {
        return policyEndDate;
    }

    public void setPolicyEndDate(Date policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public int getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(int policyTerm) {
        this.policyTerm = policyTerm;
    }

    public int getInitialDepositMonths() {
        return initialDepositMonths;
    }

    public void setInitialDepositMonths(int initialDepositMonths) {
        this.initialDepositMonths = initialDepositMonths;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDailyLateFeePercentage() {
        return dailyLateFeePercentage;
    }

    public void setDailyLateFeePercentage(double dailyLateFeePercentage) {
        this.dailyLateFeePercentage = dailyLateFeePercentage;
    }

    public int getAnnualMileageAllowed() {
        return annualMileageAllowed;
    }

    public void setAnnualMileageAllowed(int annualMileageAllowed) {
        this.annualMileageAllowed = annualMileageAllowed;
    }

    public String getPolicyReference() {
        return policyReference;
    }

    public void setPolicyReference(String policyReference) {
        this.policyReference = policyReference;
    }

    @Override
    public void generateReportFile(String path, Label statusLabel) {
        String[] lines = {
                "========= Generate Lease Agreement Report =========",
                "Date Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                "Generate by: " + DAOFactory.getLoggedInUser().getUsername(),
                "---------------------------------------------------",
                "=== Lease Agreement ===",
                "ID: " + this.id,
                "Policy Start Date: " + this.policyStartDate,
                "Policy End Date: " + this.policyEndDate,
                "Policy Term: " + this.policyTerm,
                "Initial Deposit Months: " + this.initialDepositMonths,
                "Total Price: " + this.totalPrice,
                "Daily Late Fee Percentage: " + this.dailyLateFeePercentage + "%",
                "Annual Mileage Allowed: " + this.annualMileageAllowed,
                "\n=== Customer Details ===",
                "Firstname: " + this.customer.getFirstname(),
                "Surname: " + this.customer.getSurname(),
                "Address Line 1: " + this.customer.getAddressLine1(),
                "Address Line 2: " + this.customer.getAddressLine2(),
                "City: " + this.customer.getCity(),
                "Postcode: " + this.customer.getPostcode(),
                "Phone: " + this.customer.getPhone(),
                "Email: " + this.customer.getEmail(),
                "Driver License Number: " + this.customer.getDriverLicenseNumber(),
                "Date of Birth: " + this.customer.getDob(),
                "\n=== Vehicle Details ===",
                "ID: " + this.inventoryItem.getVehicle().getId(),
                "Brand: " + this.inventoryItem.getVehicle().getBrand(),
                "Model: " + this.inventoryItem.getVehicle().getModel(),
                "Spec: " + this.inventoryItem.getVehicle().getSpec(),
                "Prod Year: " + this.inventoryItem.getVehicle().getProdYear(),
                "Body Type: " + this.inventoryItem.getVehicle().getBodyType(),
                "Colour: " + this.inventoryItem.getVehicle().getColor(),
                "Fuel Type:" + this.inventoryItem.getVehicle().getFuelType(),
                "Doors: " + this.inventoryItem.getVehicle().getDoors(),
                "Seats: " + this.inventoryItem.getVehicle().getSeats(),
                "Engine Size: " + this.inventoryItem.getVehicle().getEngineSize() + "L",
                "Description: " + this.inventoryItem.getVehicle().getDescription(),
                "Registration: " + this.inventoryItem.getVehicle().getRegistration(),
                "Mileage: " + this.inventoryItem.getVehicle().getMileage(),
                "Value: £" + this.inventoryItem.getVehicle().getValue()
        };

        try {
            String fullPath = path + "/GeneratedLeaseAgreementReport-" + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) + ".txt";

            FileWriter writer = new FileWriter(fullPath);
            for (String line : lines) {
                writer.write(line + "\n");
            }
            writer.close();
            statusLabel.setText("File saved to: "+ fullPath);
        } catch (IOException e) {
            statusLabel.setText("Failed to generate report");
            System.err.println("Failed to generate report");
            e.printStackTrace();
        }
    }

}
