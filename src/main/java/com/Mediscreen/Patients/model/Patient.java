package com.Mediscreen.Patients.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity(name = "Patients")
@DynamicUpdate
@Table(name = "patients")
public class Patient {

    @Id
    @SequenceGenerator(
            name = "patients_sequence",
            sequenceName = "patients_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patients_sequence"
    )
    @Column(name = "id")
    private int id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message="First Name is mandatory")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message="Last Name is mandatory")
    private String lastName;

    @Column(name = "birthdate")
    @PastOrPresent(message = "Date of birth must be past or present")
    @NotNull(message="Birthdate cannot be empty")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate birthdate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @NotNull(message="gender is mandatory")
    private Gender gender;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone_number")
    @Pattern(message = "must be a US phone number, i.e : 123-456-7890",
            regexp = "^(?:(\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4}))|)$")
    private String phoneNumber;

    public Patient() {
    }

    public Patient(int id, String firstName, String lastName, LocalDate birthDate, Gender gender, Address address, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthDate;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthdate +
                ", gender=" + gender +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
