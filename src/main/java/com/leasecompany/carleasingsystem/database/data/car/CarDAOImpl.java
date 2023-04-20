package com.leasecompany.carleasingsystem.database.data.car;

import com.leasecompany.carleasingsystem.database.data.GenericDAOImpl;
import org.hibernate.SessionFactory;

public class CarDAOImpl extends GenericDAOImpl<Car, Integer> implements CarDAO {
    public CarDAOImpl(SessionFactory sessionFactory) {
        super(Car.class ,sessionFactory);
    }
}
