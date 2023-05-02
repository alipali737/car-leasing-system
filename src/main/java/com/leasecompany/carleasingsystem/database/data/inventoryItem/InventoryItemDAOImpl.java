package com.leasecompany.carleasingsystem.database.data.inventoryItem;

import com.leasecompany.carleasingsystem.database.data.GenericDAOImpl;
import org.hibernate.SessionFactory;

public class InventoryItemDAOImpl extends GenericDAOImpl<InventoryItem, Integer> implements InventoryItemDAO {

    public InventoryItemDAOImpl(SessionFactory sessionFactory) {
        super(InventoryItem.class, sessionFactory);
    }
}
