package com.leasecompany.carleasingsystem.database.data.car;

import com.leasecompany.carleasingsystem.database.data.GenericDAO;

import java.util.List;

public interface CarDAO extends GenericDAO<Car, Integer> {
    /**
     * Returns a list of all unique string values for a column in the database
     * @param column the database column to search
     * @return List of unique string values in the passed database column
     */
    List<String> getUniqueStringValuesInColumn(String column);
}

