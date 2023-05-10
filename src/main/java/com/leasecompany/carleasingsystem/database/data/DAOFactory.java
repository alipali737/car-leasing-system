package com.leasecompany.carleasingsystem.database.data;

import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.database.data.car.CarDAOImpl;
import com.leasecompany.carleasingsystem.database.data.customer.CustomerDAO;
import com.leasecompany.carleasingsystem.database.data.customer.CustomerDAOImpl;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAO;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItemDAOImpl;
import com.leasecompany.carleasingsystem.database.data.leaseAgreement.LeaseAgreementDAO;
import com.leasecompany.carleasingsystem.database.data.leaseAgreement.LeaseAgreementDAOImpl;
import com.leasecompany.carleasingsystem.database.data.user.User;
import com.leasecompany.carleasingsystem.database.data.user.UserDAO;
import com.leasecompany.carleasingsystem.database.data.user.UserDAOImpl;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DAOFactory {
    private static User loggedInUser;
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

    public static void setLoggedInUser(User loggedInUser) {
        DAOFactory.loggedInUser = loggedInUser;
    }
    
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public CarDAO newCarDAO() {
        return new CarDAOImpl(sessionFactory);
    }

    public InventoryItemDAO newInventoryItemDAO() { return new InventoryItemDAOImpl(sessionFactory); }

    public UserDAO newUserDAO() { return new UserDAOImpl(sessionFactory); }

    public CustomerDAO newCustomerDAO() { return new CustomerDAOImpl(sessionFactory); }
    public LeaseAgreementDAO newLeaseAgreementDAO() { return new LeaseAgreementDAOImpl(sessionFactory); }

    public List<String> getTables() {
        Metamodel metamodel = sessionFactory.getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();

        List<String> tables = new ArrayList<>();
        entities.forEach(e -> {
            tables.add(e.getName()+"s");
        });
        return tables;
    }
}
