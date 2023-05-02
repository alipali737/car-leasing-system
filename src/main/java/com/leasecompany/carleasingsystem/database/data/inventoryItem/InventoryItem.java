package com.leasecompany.carleasingsystem.database.data.inventoryItem;

import com.leasecompany.carleasingsystem.database.data.car.Car;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Car vehicle;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creation_date;

    @Column(name = "vehicle_in_stock")
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
