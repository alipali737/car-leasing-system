package com.leasecompany.carleasingsystem.database.data.leaseAgreement;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.DataEntity;
import com.leasecompany.carleasingsystem.database.data.customer.Customer;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItem;
import javafx.scene.control.Label;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    public LeaseAgreement(){}

    public LeaseAgreement(InventoryItem inventoryItem, Customer customer, String policyReference, Date policyStartDate,
                          Date policyEndDate, int policyTerm, int initialDepositMonths, double totalPrice,
                          double dailyLateFeePercentage, int annualMileageAllowed) {
        this.inventoryItem = inventoryItem;
        this.customer = customer;
        this.policyReference = policyReference;
        this.policyStartDate = policyStartDate;
        this.policyEndDate = policyEndDate;
        this.policyTerm = policyTerm;
        this.initialDepositMonths = initialDepositMonths;
        this.totalPrice = totalPrice;
        this.dailyLateFeePercentage = dailyLateFeePercentage;
        this.annualMileageAllowed = annualMileageAllowed;
    }

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
                "Policy Reference: " + this.policyReference,
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
                "Fuel Type: " + this.inventoryItem.getVehicle().getFuelType(),
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

    @Override
    public String toString() {
        return this.id + " " + this.policyReference;
    }

    public double calcLateFee(int daysLate, double standardDailyPayment, double rate) {
        if (daysLate <= 0) { return 0; }

        double lateFeeAmount = 0;
        for (int i = 0; i < daysLate; i++) {
            // Calculate interest for each day and add it on
            lateFeeAmount += standardDailyPayment + (lateFeeAmount * rate);
        }

        return round(lateFeeAmount, 2);
    }

    /**
     * Calculate the late fee based on the number of days late
     * @param daysLate number of days overdue
     * @return lateFeeAmount
     */
    public double calcLateFee(int daysLate) {
        double standardDailyPayment = calcDailyPayment();
        double rate = (dailyLateFeePercentage / 100); // Get rate as a decimal eg. 5% = 0.05
        return calcLateFee(daysLate, standardDailyPayment, rate);
    }

    /**
     * Calculate fee amount for any additional miles
     * @param pricePerMile price per additional mile
     * @param vehicleMileage mileage of the vehicle
     * @return feeAmount
     */
    public double calcAdditionalMileFee(double pricePerMile, int vehicleMileage) {
        if (pricePerMile <= 0) { return 0; }
        int milesAllowed = annualMileageAllowed * (policyTerm / 12);
        int milesOver = vehicleMileage - milesAllowed;
        return (milesOver >= 0) ? round(pricePerMile * milesOver, 2) : 0;
    }

    /**
     * Calculate the amount to be paid each day, not considering the deposit amount
     * @return dailyPaymentAmount
     */
    public double calcDailyPayment() {
        if (policyStartDate.getTime() > policyEndDate.getTime()) { return 0; }
        long diffInMils = Math.abs(policyStartDate.getTime() - policyEndDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMils, TimeUnit.MILLISECONDS) + 1; // Add 1 day to include the final day
        if (diff <= 0 || totalPrice <= 0) {
            return 0;
        }
        return round(totalPrice / diff, 2);
    }


    public double calcAmountPaidBetweenDates(LocalDate startDate, LocalDate endDate, double standardDailyPayment) {
        if (endDate.isBefore(startDate)) { return 0; }
        int days = (int)ChronoUnit.DAYS.between(startDate, endDate) + 1; // Include the final day
        return (days > 0) ? round(standardDailyPayment * days, 2) : 0;
    }

    /**
     * Calculate the amount the customer has paid between two dates
     * @param startDate date to begin calc from
     * @param endDate date to end calc at
     * @return amountPaid
     */
    public double calcAmountPaidBetweenDates(LocalDate startDate, LocalDate endDate) {
        double standardDailyPayment = calcDailyPayment();
        return calcAmountPaidBetweenDates(startDate, endDate, standardDailyPayment);
    }

    private static double round(double value, int places) {
        if (places < 0 ) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
