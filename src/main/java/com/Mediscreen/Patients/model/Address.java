package com.Mediscreen.Patients.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"
    )
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Patient patientAddress;

    @Column(name = "street")
    @NotBlank(message="Street is mandatory")
    private String street;

    @Column(name = "city")
    @NotBlank(message="City is mandatory")
    private String city;

    @Column(name = "zipCode")
    @NotBlank(message="ZipCode is mandatory")
    private String zipCode;

    @Column(name = "state")
    @NotBlank(message="State is mandatory")
    private String state;

    @Column(name = "country")
    @NotBlank(message="Country is mandatory")
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(Patient patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
