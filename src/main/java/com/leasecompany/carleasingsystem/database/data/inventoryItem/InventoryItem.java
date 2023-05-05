package com.leasecompany.carleasingsystem.database.data.inventoryItem;

import com.leasecompany.carleasingsystem.database.data.DataEntity;
import com.leasecompany.carleasingsystem.database.data.car.Car;

import java.util.Date;

public class InventoryItem implements DataEntity {
    private Long id;

    private Car vehicle;

    private Date creation_date;

    private boolean vehicleInStock;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Car getVehicle() {
        return vehicle;
    }

    public void setVehicle(Car vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isVehicleInStock() {
        return vehicleInStock;
    }

    public void setVehicleInStock(boolean vehicleInStock) {
        this.vehicleInStock = vehicleInStock;
    }
}
