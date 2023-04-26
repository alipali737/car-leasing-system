package com.leasecompany.carleasingsystem.database.data.car;

import com.leasecompany.carleasingsystem.database.data.GenericDAOImpl;
import com.leasecompany.carleasingsystem.database.data.inventoryItem.InventoryItem;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarDAOImpl extends GenericDAOImpl<Car, Integer> implements CarDAO {
    public CarDAOImpl(SessionFactory sessionFactory) {
        super(Car.class ,sessionFactory);
    }

    @Override
    public List<Car> findByCriteriaInStock(Map<String, Object> criteria) {
        List<Car> entities;
        try (Session session = super.sessionFactory.openSession()) {
            // Create the basic criteria query
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Car> criteriaQuery = builder.createQuery(Car.class);
            JpaRoot<Car> root = criteriaQuery.from(Car.class);

            // Join the inventory_items table
            JpaJoin<Car, InventoryItem> invetoryItemJpaJoin = root.join("inventoryItems", JoinType.INNER);

            // For each criteria, create a predicate SQL filter string
            List<JpaPredicate> predicates = new ArrayList<>();

            predicates.add(builder.isTrue(invetoryItemJpaJoin.get("vehicleInStock")));

            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                JpaPath<?> fieldExpression = root.get(fieldName);
                if (fieldValue instanceof String) {
                    // If its a string, create a criteria 'WHERE fieldName LIKE %fieldValue%'
                    predicates.add(builder.like(builder.upper((JpaExpression<String>) fieldExpression),
                            "%" + fieldValue + "%"));
                } else {
                    // else, create a criteria 'WHERE fieldName = fieldValue'
                    predicates.add(builder.equal(fieldExpression, fieldValue));
                }
            }

            criteriaQuery.where(predicates.toArray(new JpaPredicate[0]));
            entities = session.createQuery(criteriaQuery).getResultList();
        }
        return entities;
    }
}