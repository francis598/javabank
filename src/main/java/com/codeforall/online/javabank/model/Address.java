package com.codeforall.online.javabank.model;

import jakarta.persistence.Embeddable;

/**
 * A class which represents a customer's address
 */
@Embeddable
public class Address {

    private String street;
    private String city;
    private String zipcode;

    /**
     * Get the street
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Get the city
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Get the zipcode
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Set the street to the given street
     * @param street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Set the city to the given city
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Set the zipcode to the given zipcode
     * @param zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
