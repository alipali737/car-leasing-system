package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.database.data.car.Car;

public class CreateLeaseData {
    public Car vehicle;
    public int initialDepositMonths;
    public int policyTerm;

    public CreateLeaseData(Car vehicle, int initialDepositMonths, int policyTerm) {
        this.vehicle = vehicle;
        this.initialDepositMonths = initialDepositMonths;
        this.policyTerm = policyTerm;
    }
}
