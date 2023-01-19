package com.Mediscreen.Report.service;

import com.Mediscreen.Report.model.GenderModel;
import com.Mediscreen.Report.model.PatientHistoryModel;
import com.Mediscreen.Report.model.PatientModel;
import com.Mediscreen.Report.model.RiskLevel;
import com.Mediscreen.Report.proxy.PatientHistoryProxy;
import com.Mediscreen.Report.proxy.PatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private PatientProxy mockPatientProxy;
    @Mock
    private PatientHistoryProxy mockPatientHistoryProxy;

    private ReportService reportServiceUnderTest;

    @BeforeEach
    void setUp() {
        reportServiceUnderTest = new ReportService(mockPatientProxy, mockPatientHistoryProxy);
    }

    public PatientModel patientUnderTest() {
        String date = "2000-01-01";
        LocalDate birthdate = LocalDate.parse(date);
        PatientModel patient = new PatientModel();
        patient.setFirstName("John");
        patient.setLastName("Smith");
        patient.setBirthdate(birthdate);
        patient.setAddress("Downtown street 1");
        patient.setGender(GenderModel.MALE);
        patient.setEmail("johnsmith@example.com");
        patient.setPhoneNumber("111-222-3333");
        return patient;
    }
    public PatientHistoryModel patHistUnderTest1() {
        String date = "2000-01-01T01:01:01";
        LocalDateTime creationDate = LocalDateTime.parse(date);
        PatientHistoryModel patientHistory = new PatientHistoryModel();
        patientHistory.setId("id1");
        patientHistory.setPatientId(0);
        patientHistory.setCreationDate(creationDate);
        patientHistory.setObservation("observation");
        return patientHistory;
    }
    public PatientHistoryModel patHistUnderTest2() {
        String date = "2000-01-01T01:01:01";
        LocalDateTime creationDate = LocalDateTime.parse(date);
        PatientHistoryModel patientHistory = new PatientHistoryModel();
        patientHistory.setId("id2");
        patientHistory.setPatientId(0);
        patientHistory.setCreationDate(creationDate);
        patientHistory.setObservation("observation");
        return patientHistory;
    }

    @Test
    void testGetPatientData() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                patientUnderTest(), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(0)).thenReturn(patientModelResponseEntity);

        // Run the test
        final PatientModel result = reportServiceUnderTest.getPatientData(0);
    }

    @Test
    void testGetTheHistoryForPatient() {
        // Setup
        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(patHistUnderTest1(), patHistUnderTest2()),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(0)).thenReturn(listResponseEntity);

        // Run the test
        final List<PatientHistoryModel> result = reportServiceUnderTest.getTheHistoryForPatient(0);
    }

    @Test
    void testGetTheHistoryForPatient_PatientHistoryProxyReturnsNoItems() {
        // Setup
        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = ResponseEntity.ok(Collections.emptyList());
        when(mockPatientHistoryProxy.getTheHistoryForPatient(0)).thenReturn(listResponseEntity);

        // Run the test
        final List<PatientHistoryModel> result = reportServiceUnderTest.getTheHistoryForPatient(0);

        // Verify the results
        assertEquals(result, Collections.emptyList());
    }

    @Test
    void testCalculRisk() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                patientUnderTest(), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(0)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(patHistUnderTest1(), patHistUnderTest2()),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(0)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(0);

        // Verify the results
//        assertEquals(result, RiskLevel.None);
    }

    @Test
    void testCalculRisk_PatientHistoryProxyReturnsNoItems() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                patientUnderTest(), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(0)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = ResponseEntity.ok(Collections.emptyList());
        when(mockPatientHistoryProxy.getTheHistoryForPatient(0)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(0);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testGetPatientAge() {
        // Run the test
        final long result = reportServiceUnderTest.getPatientAge(patientUnderTest());

        // Verify the results
        assertEquals(result, 23);
    }

    @Test
    void testTriggersCount() {
        // Setup
        final List<PatientHistoryModel> historyForPatient = List.of(patHistUnderTest1(), patHistUnderTest2());

        // Run the test
        final int result = reportServiceUnderTest.triggersCount(historyForPatient);

        // Verify the results
        assertEquals(result, 0);
    }

    @Test
    void testGetPatientByFamily() {
        // Setup
        final ResponseEntity<List<PatientModel>> listResponseEntity = new ResponseEntity<>(
                List.of(patientUnderTest(), patientUnderTest()), HttpStatus.OK);
        when(mockPatientProxy.getFamily("smith")).thenReturn(listResponseEntity);

        // Run the test
        final List<PatientModel> result = reportServiceUnderTest.getPatientByFamily("smith");
    }

    @Test
    void testGetPatientByFamily_PatientProxyReturnsNoItems() {
        // Setup
        final ResponseEntity<List<PatientModel>> listResponseEntity = ResponseEntity.ok(Collections.emptyList());
        when(mockPatientProxy.getFamily("smith")).thenReturn(listResponseEntity);

        // Run the test
        final List<PatientModel> result = reportServiceUnderTest.getPatientByFamily("smith");

        // Verify the results
        assertEquals(result, Collections.emptyList());
    }
}
