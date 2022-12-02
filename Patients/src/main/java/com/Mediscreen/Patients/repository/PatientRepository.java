package com.Mediscreen.Patients.repository;

import com.Mediscreen.Patients.model.Patient;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findAll();
    Optional<Patient> findById(int id);
    boolean existsById(int id);
    boolean existsByFirstNameAndLastNameAndBirthdate(String firstName, String lastName, LocalDate birthdate);
}
