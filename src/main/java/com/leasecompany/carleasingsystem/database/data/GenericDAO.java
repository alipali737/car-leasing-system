package com.leasecompany.carleasingsystem.database.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDAO<T, ID extends Serializable> {
    /**
     * Get the entity object with the passed ID from the database
     * @param id entity ID
     * @return entity object that matches the found ID, returns null if not found
     */
    T findByID(ID id);

    /**
     * Get all entities objects from the database
     * @return a list of all entity objects found
     */
    List<T> findAll();

    /**
     * Get all entities objects that match the given criteria
     * @param criteria map of 'column_name' to 'criteria_value'
     * @return a list of all entity objects found that match the criteria
     */
    List<T> findByCriteria(Map<String, Object> criteria);

    /**
     * Create a new entry of the entity object in the database
     * @param entity - entity to be created
     * @return boolean
     */
    boolean create(T entity);

    /**
     * Modify an existing entity object in the database
     * @param entity new entity object with matching ID
     * @return boolean
     */
    boolean update(T entity);

    /**
     * Delete an entity object in the database
     * @param entity with matching ID
     * @return boolean
     */
    boolean delete(T entity);
}

