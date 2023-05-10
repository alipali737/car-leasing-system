package com.leasecompany.carleasingsystem.database.data.customer;

import com.leasecompany.carleasingsystem.database.data.GenericDAOImpl;
import org.hibernate.SessionFactory;

public class CustomerDAOImpl extends GenericDAOImpl<Customer, Long> implements CustomerDAO {

    public CustomerDAOImpl(SessionFactory sessionFactory) { super(Customer.class, sessionFactory); }
}
