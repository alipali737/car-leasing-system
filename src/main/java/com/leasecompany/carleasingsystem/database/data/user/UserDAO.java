package com.leasecompany.carleasingsystem.database.data.user;

import com.leasecompany.carleasingsystem.database.data.GenericDAO;

public interface UserDAO extends GenericDAO<User, Long> {
    /**
     * Check if a username already exists in the database
     * @param username username to validate
     * @return boolean whether it already exists
     */
    boolean usernameAlreadyExists(String username);

    /**
     * Get the Hash of a password
     * @param password password to hash
     * @return Hex digest of the hashed password
     */
    String hashPassword(String password);
}
