package com.leasecompany.carleasingsystem.database.data.customer;

import com.leasecompany.carleasingsystem.database.data.DataEntity;

import java.util.Date;

public class Customer implements DataEntity {
    private Long id;
    private String firstname;
    private String surname;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postcode;
    private String phone;
    private String email;
    private String driverLicenseNumber;
    private Date dob;

    public Customer(){}

    public Customer(String firstname, String surname, String addressLine1, String addressLine2, String city,
                    String postcode, String phone, String email, String driverLicenseNumber, Date dob) {
        this.firstname = firstname;
        this.surname = surname;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.postcode = postcode;
        this.phone = phone;
        this.email = email;
        this.driverLicenseNumber = driverLicenseNumber;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
