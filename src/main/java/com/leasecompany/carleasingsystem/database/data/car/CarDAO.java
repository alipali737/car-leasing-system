package com.leasecompany.carleasingsystem.database.data.car;

import com.leasecompany.carleasingsystem.database.data.GenericDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CarDAO extends GenericDAO<Car, Long> {
    /**
     * Returns a list of all unique string values for a column in the database
     * @param column the database column to search
     * @return List of unique string values in the passed database column
     */
    List<String> getUniqueStringValuesInColumn(String column);

    /**
     * Convert a file to a byte[]
     * @param file
     * @return byte[]
     */
    byte[] convertToByteArray(File file) throws FileNotFoundException;
}

