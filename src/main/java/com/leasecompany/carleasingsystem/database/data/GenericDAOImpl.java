package com.leasecompany.carleasingsystem.database.data;

import jakarta.persistence.criteria.Expression;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {
    private final SessionFactory sessionFactory;
    private final Class<T> entityType;

    public GenericDAOImpl(Class<T> tClass, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.entityType = tClass;
    }

    @Override
    public T findByID(ID id) {
        T entity;
        try (Session session = sessionFactory.openSession()) {
            entity = session.get(entityType, id);
        }
        return entity;
    }

    @Override
    public List<T> findAll() {
        // Creates an SQL statement like 'SELECT * FROM <T>'
        List<T> entities;
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<T> criteriaQuery = builder.createQuery(entityType);
            JpaRoot<T> root = criteriaQuery.from(entityType);
            criteriaQuery.select(root);
            Query<T> query = session.createQuery(criteriaQuery);
            entities = query.getResultList();
        }
        return entities;
    }

    @Override
    public List<T> findByCriteria(Map<String, Object> criteria) {
        List<T> entities;
        try (Session session = sessionFactory.openSession()) {
            // Create the basic criteria query
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<T> criteriaQuery = builder.createQuery(entityType);
            JpaRoot<T> root = criteriaQuery.from(entityType);

            // For each criteria, create a predicate SQL filter string
            List<JpaPredicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                JpaPath<?> fieldExpression = root.get(fieldName);
                if (fieldValue instanceof String) {
                    // If its a string, create a criteria 'WHERE fieldName LIKE %fieldValue%'
                    predicates.add(builder.like(builder.upper((Expression<String>) fieldExpression),
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

    @Override
    public boolean create(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            System.err.println("failed to create record in database: " + ex);
            return false;
        }
    }

    @Override
    public boolean update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            System.err.println("failed to update record in database: " + ex);
            return false;
        }
    }

    @Override
    public boolean delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            System.err.println("failed to delete record in database: " + ex);
            return false;
        }
    }
}
