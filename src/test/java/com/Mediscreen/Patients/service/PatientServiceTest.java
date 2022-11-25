package com.Mediscreen.Patients.service;

import com.Mediscreen.Patients.model.Gender;
import com.Mediscreen.Patients.model.Patient;
import com.Mediscreen.Patients.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository mockPatientRepository;

    @InjectMocks
    private PatientService patientServiceUnderTest;

    public Patient patientUnderTest() {
        String date = "2000-01-01";
        LocalDate birthdate = LocalDate.parse(date);
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Smith");
        patient.setBirthdate(birthdate);
        patient.setAddress("Downtown street 1");
        patient.setGender(Gender.MALE);
        patient.setEmail("johnsmith@example.com");
        patient.setPhoneNumber("111-222-3333");
        return patient;
    }

    public Patient patientUnderTest2() {
        String date = "2000-01-01";
        LocalDate birthdate = LocalDate.parse(date);
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Smith");
        patient.setBirthdate(birthdate);
        patient.setAddress("Downtown street 1");
        patient.setGender(Gender.MALE);
        patient.setEmail("johnsmith@example.com");
        patient.setPhoneNumber("111-222-3333");
        return patient;
    }

    @Test
    void testGetAllPatients() {
        // Setup
        final List<Patient> patients = List.of(patientUnderTest(), patientUnderTest2());
        when(mockPatientRepository.findAll()).thenReturn(patients);

        // Run the test
        final List<Patient> result = patientServiceUnderTest.getAllPatients();
    }

    @Test
    void testGetAllPatients_ReturnsNoContent() {
        // Setup
        when(mockPatientRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Patient> result = patientServiceUnderTest.getAllPatients();

        // Verify the results
        assertEquals(result, Collections.emptyList());
    }

    @Test
    void testGetPatientById() {
        // Setup
        final Optional<Patient> optionalPatient = Optional.of(patientUnderTest());
        when(mockPatientRepository.findById(0)).thenReturn(optionalPatient);

        // Run the test
        final Patient result = patientServiceUnderTest.getPatientById(0);
    }

    @Test
    void testGetPatientById_ReturnsBadRequest() {
        // Setup
        when(mockPatientRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Patient result = patientServiceUnderTest.getPatientById(0);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testSavePatient() {
        // Setup
        final Patient patient = patientUnderTest();

        // Configure
        final Patient patient2 = patientUnderTest2();
        when(mockPatientRepository.save(any(Patient.class))).thenReturn(patient2);

        // Run the test
        final Patient result = patientServiceUnderTest.savePatient(patient);
    }

    @Test
    void testUpdatePatient() {
        // Setup
        final Patient patient = patientUnderTest();
        final Optional<Patient> optionalPatient = Optional.of(patientUnderTest());
        when(mockPatientRepository.findById(0)).thenReturn(optionalPatient);

        // Configure
        final Patient patient2 = patientUnderTest2();
        when(mockPatientRepository.save(any(Patient.class))).thenReturn(patient2);

        // Run the test
        final Patient result = patientServiceUnderTest.updatePatient(patient);
    }

    @Test
    void testCreatePatient_HasErrors() {
        // Setup
        final Patient patient = patientUnderTest();
        when(mockPatientRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Patient result = patientServiceUnderTest.updatePatient(patient);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testDeletePatient() {
        // Setup
        // Configure
        final Optional<Patient> optionalPatient = Optional.of(patientUnderTest());
        when(mockPatientRepository.findById(0)).thenReturn(optionalPatient);

        // Run the test
        patientServiceUnderTest.deletePatient(0);

        // Verify the results
        verify(mockPatientRepository).deleteById(0);
    }

    @Test
    void testDeletePatient_ReturnsBadRequest() {
        // Setup
        final Patient patient = patientUnderTest();
        when(mockPatientRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        patientServiceUnderTest.deletePatient(0);
    }
}
