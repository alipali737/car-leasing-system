package com.leasecompany.carleasingsystem.database.data;

import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.database.data.car.CarDAOImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAOFactory {
    private final SessionFactory sessionFactory;

    public DAOFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to initialise a SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public CarDAO newCarDAO() {
        return new CarDAOImpl(sessionFactory);
    }
}
