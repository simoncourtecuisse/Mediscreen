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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientHistoryServiceTest {

    @Mock
    private PatientHistoryRepository mockPatientHistoryRepository;

    @InjectMocks
    private PatientHistoryService patientHistoryServiceUnderTest;

    @Test
    void testGetAllPatientHistory() {
        // Setup
        // Configure PatientHistoryRepository.findAll(...).
        final List<PatientHistory> patientHistoryList = List.of(
                new PatientHistory("id", 0, "observation", LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockPatientHistoryRepository.findAll()).thenReturn(patientHistoryList);

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getAllPatientHistory();

        // Verify the results
    }

    @Test
    void testGetAllPatientHistory_PatientHistoryRepositoryReturnsNoItems() {
        // Setup
        when(mockPatientHistoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getAllPatientHistory();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetTheHistoryByPatientId() {
        // Setup
        // Configure PatientHistoryRepository.findAllByPatientId(...).
        final List<PatientHistory> patientHistoryList = List.of(
                new PatientHistory("id", 0, "observation", LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockPatientHistoryRepository.findAllByPatientId(0)).thenReturn(patientHistoryList);

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getTheHistoryByPatientId(0);

        // Verify the results
    }

    @Test
    void testGetTheHistoryByPatientId_PatientHistoryRepositoryReturnsNoItems() {
        // Setup
        when(mockPatientHistoryRepository.findAllByPatientId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<PatientHistory> result = patientHistoryServiceUnderTest.getTheHistoryByPatientId(0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetPatientHistoryById() {
        // Setup
        // Configure PatientHistoryRepository.findById(...).
        final Optional<PatientHistory> patientHistory = Optional.of(
                new PatientHistory("id", 0, "observation", LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockPatientHistoryRepository.findById("id")).thenReturn(patientHistory);

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.getPatientHistoryById("id");

        // Verify the results
    }

    @Test
    void testGetPatientHistoryById_PatientHistoryRepositoryReturnsAbsent() {
        // Setup
        when(mockPatientHistoryRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.getPatientHistoryById("id");

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testCreatePatientHistory() {
        // Setup
        final PatientHistory patientHistory = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Configure PatientHistoryRepository.save(...).
        final PatientHistory patientHistory1 = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryRepository.save(any(PatientHistory.class))).thenReturn(patientHistory1);

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.createPatientHistory(0, patientHistory);

        // Verify the results
    }

    @Test
    void testUpdatePatientHistory() {
        // Setup
        final PatientHistory updatedPatientHistory = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Configure PatientHistoryRepository.findById(...).
        final Optional<PatientHistory> patientHistory = Optional.of(
                new PatientHistory("id", 0, "observation", LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockPatientHistoryRepository.findById("id")).thenReturn(patientHistory);

        // Configure PatientHistoryRepository.save(...).
        final PatientHistory patientHistory1 = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryRepository.save(any(PatientHistory.class))).thenReturn(patientHistory1);

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.updatePatientHistory("id", updatedPatientHistory);

        // Verify the results
    }

    @Test
    void testUpdatePatientHistory_PatientHistoryRepositoryFindByIdReturnsAbsent() {
        // Setup
        final PatientHistory updatedPatientHistory = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final PatientHistory result = patientHistoryServiceUnderTest.updatePatientHistory("id", updatedPatientHistory);

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testDeletePatientHistoryById() {
        // Setup
        // Configure PatientHistoryRepository.findById(...).
        final Optional<PatientHistory> patientHistory = Optional.of(
                new PatientHistory("id", 0, "observation", LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockPatientHistoryRepository.findById("id")).thenReturn(patientHistory);

        // Run the test
        patientHistoryServiceUnderTest.deletePatientHistoryById("id");

        // Verify the results
        verify(mockPatientHistoryRepository).deletePatientHistoryById("id");
    }

    @Test
    void testDeletePatientHistoryById_PatientHistoryRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockPatientHistoryRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        patientHistoryServiceUnderTest.deletePatientHistoryById("id");

        // Verify the results
        verify(mockPatientHistoryRepository).deletePatientHistoryById("id");
    }
}
