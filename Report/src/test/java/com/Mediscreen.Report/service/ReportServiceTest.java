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
        patientHistory.setPatientId(1);
        patientHistory.setCreationDate(creationDate);
        patientHistory.setObservation("observation");
        return patientHistory;
    }
    public PatientHistoryModel patHistUnderTest2() {
        String date = "2000-01-01T01:01:01";
        LocalDateTime creationDate = LocalDateTime.parse(date);
        PatientHistoryModel patientHistory = new PatientHistoryModel();
        patientHistory.setId("id2");
        patientHistory.setPatientId(2);
        patientHistory.setCreationDate(creationDate);
        patientHistory.setObservation("observation");
        return patientHistory;
    }

    @Test
    void testGetPatientData() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                patientUnderTest(), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(1)).thenReturn(patientModelResponseEntity);

        // Run the test
        final PatientModel result = reportServiceUnderTest.getPatientData(1);
    }

    @Test
    void testGetTheHistoryForPatient() {
        // Setup
        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(patHistUnderTest1(), patHistUnderTest2()),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(1)).thenReturn(listResponseEntity);

        // Run the test
        final List<PatientHistoryModel> result = reportServiceUnderTest.getTheHistoryForPatient(1);
    }

    @Test
    void testGetTheHistoryForPatient_PatientHistoryProxyReturnsNoItems() {
        // Setup
        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = ResponseEntity.ok(Collections.emptyList());
        when(mockPatientHistoryProxy.getTheHistoryForPatient(1)).thenReturn(listResponseEntity);

        // Run the test
        final List<PatientHistoryModel> result = reportServiceUnderTest.getTheHistoryForPatient(1);

        // Verify the results
        assertEquals(result, Collections.emptyList());
    }

    @Test
    void testCalculRiskUnder30() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        2,"john", "doe", LocalDate.of(2020, 1, 1), GenderModel.MALE, "Downtown street 1",
                        "johndoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(2)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(patHistUnderTest1(), patHistUnderTest2()),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(2)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(2);

        // Verify the results
        assertEquals(result, RiskLevel.None);
    }


    @Test
    void testCalculRiskOver30() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        2,"john", "doe", LocalDate.of(1990, 1, 1), GenderModel.MALE, "Downtown street 1",
                        "johndoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(2)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(patHistUnderTest1(), patHistUnderTest2()),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(2)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(2);

        // Verify the results
        assertEquals(result, RiskLevel.None);
    }

    @Test
    void testCalculRiskOver30Border() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        3,"john", "doe", LocalDate.of(1990, 1, 1), GenderModel.MALE, "Downtown street 1",
                        "johndoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(3)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(new PatientHistoryModel("id", 3, "Hémoglobine A1C, Microalbumine, Taille", LocalDateTime.of(2020, 1, 1, 0, 0, 0))),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(3)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(3);

        // Verify the results
        assertEquals(result, RiskLevel.Borderline);
    }

    @Test
    void testCalculRiskOver30Early() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        4,"john", "doe", LocalDate.of(1990, 1, 1), GenderModel.MALE, "Downtown street 1",
                        "johndoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(4)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(new PatientHistoryModel("id", 4, "Hémoglobine A1C, Microalbumine, Taille, Fume, Anormal, Cholestérol", LocalDateTime.of(2020, 1, 1, 0, 0, 0))),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(4)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(4);

        // Verify the results
        assertEquals(result, RiskLevel.EarlyOnset);
    }

    @Test
    void testCalculRiskOver30Danger() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        5,"john", "doe", LocalDate.of(1990, 1, 1), GenderModel.MALE, "Downtown street 1",
                        "johndoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(5)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(new PatientHistoryModel("id", 5, "Hémoglobine A1C, Microalbumine, Taille, Fume, Anormal, Cholestérol, Vertige, Rechute", LocalDateTime.of(2020, 1, 1, 0, 0, 0))),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(5)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(5);

        // Verify the results
        assertEquals(result, RiskLevel.InDanger);
    }

    @Test
    void testCalculRiskUnder30MaleDanger() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        6,"john", "doe", LocalDate.of(2020, 1, 1), GenderModel.MALE, "Downtown street 1",
                        "johndoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(6)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(new PatientHistoryModel("id", 6, "Hémoglobine A1C, Microalbumine, Taille, Fume", LocalDateTime.of(2020, 1, 1, 0, 0, 0))),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(6)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(6);

        // Verify the results
        assertEquals(result, RiskLevel.InDanger);
    }

    @Test
    void testCalculRiskUnder30MaleEarly() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        7,"john", "doe", LocalDate.of(2020, 1, 1), GenderModel.MALE, "Downtown street 1",
                        "johndoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(7)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(new PatientHistoryModel("id", 7, "Hémoglobine A1C, Microalbumine, Taille, Fume, Anormal", LocalDateTime.of(2020, 1, 1, 0, 0, 0))),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(7)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(7);

        // Verify the results
        assertEquals(result, RiskLevel.EarlyOnset);
    }

    @Test
    void testCalculRiskUnder30FemaleDanger() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        8,"jane", "doe", LocalDate.of(2020, 1, 1), GenderModel.FEMALE, "Downtown street 1",
                        "janedoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(8)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(new PatientHistoryModel("id", 8, "Hémoglobine A1C, Microalbumine, Taille, Fume", LocalDateTime.of(2020, 1, 1, 0, 0, 0))),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(8)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(8);

        // Verify the results
        assertEquals(result, RiskLevel.InDanger);
    }

    @Test
    void testCalculRiskUnder30FemaleEarly() {
        // Setup
        final ResponseEntity<PatientModel> patientModelResponseEntity = new ResponseEntity<>(
                new PatientModel(
                        9,"jane", "doe", LocalDate.of(2020, 1, 1), GenderModel.FEMALE, "Downtown street 1",
                        "janedoe@example.com", "111-222-3333"), HttpStatus.OK);
        when(mockPatientProxy.getPatientById(9)).thenReturn(patientModelResponseEntity);

        final ResponseEntity<List<PatientHistoryModel>> listResponseEntity = new ResponseEntity<>(
                List.of(new PatientHistoryModel("id", 9, "Hémoglobine A1C, Microalbumine, Taille, Fume, Anormal, Cholestérol, Vertige", LocalDateTime.of(2020, 1, 1, 0, 0, 0))),
                HttpStatus.OK);
        when(mockPatientHistoryProxy.getTheHistoryForPatient(9)).thenReturn(listResponseEntity);

        // Run the test
        final RiskLevel result = reportServiceUnderTest.calculRisk(9);

        // Verify the results
        assertEquals(result, RiskLevel.EarlyOnset);
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
