package com.leasecompany.carleasingsystem.database.data;

import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.database.data.car.CarDAOImpl;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAO;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAOImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAOFactory {
    private static DAOFactory instance;
    private final SessionFactory sessionFactory;

    private DAOFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to initialise a SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static synchronized  DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public CarDAO newCarDAO() {
        return new CarDAOImpl(sessionFactory);
    }

    public InventoryItemDAO newInventoryItemDAO() { return new InventoryItemDAOImpl(sessionFactory); }
}
