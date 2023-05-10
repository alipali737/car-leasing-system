package com.leasecompany.carleasingsystem.database.data.leaseAgreement;

import com.leasecompany.carleasingsystem.database.data.GenericDAOImpl;
import org.hibernate.SessionFactory;

public class LeaseAgreementDAOImpl extends GenericDAOImpl<LeaseAgreement, Long> implements LeaseAgreementDAO {
    public LeaseAgreementDAOImpl(SessionFactory sessionFactory) { super(LeaseAgreement.class, sessionFactory); }
}
