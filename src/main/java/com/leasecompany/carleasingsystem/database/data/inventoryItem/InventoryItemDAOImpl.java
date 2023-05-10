package com.leasecompany.carleasingsystem.database.data.inventoryItem;

import com.leasecompany.carleasingsystem.database.data.GenericDAOImpl;
import org.hibernate.SessionFactory;

import java.time.Instant;
import java.util.Date;

public class InventoryItemDAOImpl extends GenericDAOImpl<InventoryItem, Long> implements InventoryItemDAO {

    public InventoryItemDAOImpl(SessionFactory sessionFactory) {
        super(InventoryItem.class, sessionFactory);
    }

    @Override
    public boolean create(InventoryItem entity) {
        entity.setCreation_date(Date.from(Instant.now()));
        entity.setVehicleInStock(true);
        return super.create(entity);
    }
}
