package com.leasecompany.carleasingsystem.database.data.car;

import com.leasecompany.carleasingsystem.database.data.DataEntity;

public class Car implements DataEntity {
    private Long id;
    private int doors;
    private double engineSize;
    private String color;
    private String fuelType;
    private int seats;
    private String bodyType;
    private String brand;
    private String model;
    private String spec;
    private int prodYear;
    private String description;
    private String registration;
    private int mileage;
    private int value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getProdYear() {
        return prodYear;
    }

    public void setProdYear(int prodYear) {
        this.prodYear = prodYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static final double standardDepreciationRate = 0.01; // 1% depreciation per month
    public static final double standardProfitPercentage = 0.1;  // 10% desired profit

    public double calcMonthlyPaymentPrice(double depreciationRate, double profitPercentage, int contractMonths, int depositMonths) {
        double totalDepreciation = value * (depreciationRate * contractMonths);
        double profitAmount = value * profitPercentage;
        double totalDue = totalDepreciation + profitAmount;
        double monthlyPaymentBeforeDeposit = totalDue / contractMonths;
        double depositAmount = monthlyPaymentBeforeDeposit * depositMonths;
        return Math.ceil((totalDue - depositAmount) / contractMonths);
    }

    public double calcMonthlyPaymentPrice(int contractMonths, int depositMonths) {
        return calcMonthlyPaymentPrice(standardDepreciationRate, standardProfitPercentage, contractMonths, depositMonths);
    }

    @Override
    public String toString() {
        return this.id + " " +
                this.brand + " " +
                this.model + " " +
                this.spec + " " +
                this.prodYear + " " +
                this.bodyType + " " +
                this.color + " " +
                this.fuelType + " " +
                this.registration;
    }

}
