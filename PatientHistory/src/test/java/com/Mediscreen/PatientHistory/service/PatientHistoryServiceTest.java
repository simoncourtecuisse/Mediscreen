package com.Mediscreen.PatientHistory.service;

import com.Mediscreen.PatientHistory.model.PatientHistory;
import com.Mediscreen.PatientHistory.repository.PatientHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientHistoryServiceTest {

    @Mock
    private PatientHistoryRepository mockPatientHistoryRepository;

    @InjectMocks
    private PatientHistoryService patientHistoryServiceUnderTest;

    public PatientHistory patHistUnderTest1() {
        String date = "2000-01-01T01:01:01";
        LocalDateTime creationDate = LocalDateTime.parse(date);
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setId("id1");
        patientHistory.setPatientId(1);
        patientHistory.setCreationDate(creationDate);
        patientHistory.setObservation("observation");
        return patientHistory;
    }
    public PatientHistory patHistUnderTest2() {
        String date = "2000-01-01T01:01:01";
        LocalDateTime creationDate = LocalDateTime.parse(date);
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setId("id2");
        patientHistory.setPatientId(2);
        patientHistory.setCreationDate(creationDate);
        patientHistory.setObservation("observation");
        return patientHistory;
    }

    @Test
    void testGetAllPatientHistory() {
        // Setup
        // Configure PatientHistoryRepository.findAll(...).
        final List<PatientHistory> patientHistoryList = List.of(patHistUnderTest1(), patHistUnderTest2());
        when(mockPatientHistoryRepository.findAll()).thenReturn(patientHistoryList);

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getAllPatientHistory();
    }

    @Test
    void testGetAllPatientHistory_ReturnsNoContent() {
        // Setup
        when(mockPatientHistoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getAllPatientHistory();

        // Verify the results
        assertEquals(result, Collections.emptyList());
    }

    @Test
    void testGetTheHistoryByPatientId() {
        // Setup
        // Configure PatientHistoryRepository.findAllByPatientId(...).
        final List<PatientHistory> patientHistoryList = List.of(patHistUnderTest1());
        when(mockPatientHistoryRepository.findAllByPatientId(0)).thenReturn(patientHistoryList);

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getTheHistoryByPatientId(0);
    }

    @Test
    void testGetTheHistoryByPatientId_ReturnsBadRequest() {
        // Setup
        when(mockPatientHistoryRepository.findAllByPatientId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getTheHistoryByPatientId(0);

        // Verify the results
        assertEquals(result, Collections.emptyList());
    }

    @Test
    void testGetPatientHistoryById() {
        // Setup
        final Optional<PatientHistory> patientHistory = Optional.of(patHistUnderTest1());
        when(mockPatientHistoryRepository.findById("id1")).thenReturn(patientHistory);

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.getPatientHistoryById("id1");
    }

    @Test
    void testGetPatientHistoryById_ReturnsBadRequest() {
        // Setup
        when(mockPatientHistoryRepository.findById("id1")).thenReturn(Optional.empty());

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.getPatientHistoryById("id1");

        // Verify the results
        assertNull(result);
    }

    @Test
    void testCreatePatientHistory() {
        // Setup
        final PatientHistory patientHistory1 = patHistUnderTest1();

        // Configure
        final PatientHistory patientHistory2 = patHistUnderTest2();
        when(mockPatientHistoryRepository.save(any(PatientHistory.class))).thenReturn(patientHistory1);

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.createPatientHistory(0, patientHistory1);
    }

    @Test
    void testUpdatePatientHistory() {
        // Setup
        final PatientHistory updatedPatientHistory = patHistUnderTest1();
        final Optional<PatientHistory> patientHistory1 = Optional.of(patHistUnderTest1());
        when(mockPatientHistoryRepository.findById("id1")).thenReturn(patientHistory1);

        // Configure
        final PatientHistory patientHistory2 = patHistUnderTest2();
        when(mockPatientHistoryRepository.save(any(PatientHistory.class))).thenReturn(patientHistory2);

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.updatePatientHistory("id1", updatedPatientHistory);
    }

    @Test
    void testUpdatePatientHistory_HasErrors() {
        // Setup
        final PatientHistory updatedPatientHistory = patHistUnderTest1();
        when(mockPatientHistoryRepository.findById("id1")).thenReturn(Optional.empty());

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.updatePatientHistory("id1", updatedPatientHistory);

        // Verify the results
        assertNull(result);
    }

    @Test
    void testDeletePatientHistoryById() {
        // Setup
        final Optional<PatientHistory> patientHistory = Optional.of(patHistUnderTest1());
        when(mockPatientHistoryRepository.findById("id1")).thenReturn(patientHistory);

        // Run the test
        patientHistoryServiceUnderTest.deletePatientHistoryById("id1");

        // Verify the results
        verify(mockPatientHistoryRepository).deletePatientHistoryById("id1");
    }

    @Test
    void testDeletePatientHistoryById_ReturnsBadRequest() {
        // Setup
        final PatientHistory patientHistory = patHistUnderTest1();
        when(mockPatientHistoryRepository.findById("id1")).thenReturn(Optional.empty());

        // Run the test
        patientHistoryServiceUnderTest.deletePatientHistoryById("id1");
    }
}
