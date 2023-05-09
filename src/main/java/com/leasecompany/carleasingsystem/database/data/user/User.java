package com.leasecompany.carleasingsystem.database.data.user;

import com.leasecompany.carleasingsystem.database.data.DataEntity;

public class User implements DataEntity {
    private Long id;
    private String username;
    private String passwordHash;
    private Boolean approved;
    private Boolean admin;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        approved = false;
        admin = false;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return this.id + " " + this.username;
    }
}
