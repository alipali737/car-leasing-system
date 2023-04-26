package com.leasecompany.carleasingsystem.database.data.car;

import com.leasecompany.carleasingsystem.database.data.GenericDAO;

import java.util.List;
import java.util.Map;

public interface CarDAO extends GenericDAO<Car, Integer> {
    /**
     * Find all cars that match the given criteria and are labeled as in-stock in their inventory item record
     * @param criteria map of 'column_name' to 'criteria_value'
     * @return a list of all entity objects found that match the criteria, and are also in-stock
     */
    List<Car> findByCriteriaInStock(Map<String, Object> criteria);
}

