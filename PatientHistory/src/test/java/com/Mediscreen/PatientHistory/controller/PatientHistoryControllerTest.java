package com.Mediscreen.PatientHistory.controller;

import com.Mediscreen.PatientHistory.model.PatientHistory;
import com.Mediscreen.PatientHistory.service.PatientHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientHistoryController.class)
class PatientHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientHistoryService mockPatientHistoryService;

    @Test
    void testGetAllPatientHistory() throws Exception {
        // Setup
        // Configure PatientHistoryService.getAllPatientHistory(...).
        final List<PatientHistory> patientHistoryList = List.of(
                new PatientHistory("id", 0, "observation", LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockPatientHistoryService.getAllPatientHistory()).thenReturn(patientHistoryList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllPatientHistory_PatientHistoryServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPatientHistoryService.getAllPatientHistory()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetPatientHistoryById() throws Exception {
        // Setup
        // Configure PatientHistoryService.getPatientHistoryById(...).
        final PatientHistory patientHistory = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryService.getPatientHistoryById("id")).thenReturn(patientHistory);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetPatientHistoryById_PatientHistoryServiceReturnsNull() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetTheHistoryForPatient() throws Exception {
        // Setup
        // Configure PatientHistoryService.getTheHistoryByPatientId(...).
        final List<PatientHistory> patientHistoryList = List.of(
                new PatientHistory("id", 0, "observation", LocalDateTime.of(2020, 1, 1, 0, 0, 0)));
        when(mockPatientHistoryService.getTheHistoryByPatientId(0)).thenReturn(patientHistoryList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/patient/{patientId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetTheHistoryForPatient_PatientHistoryServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPatientHistoryService.getTheHistoryByPatientId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/patient/{patientId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testCreatePatientHistory() throws Exception {
        // Setup
        // Configure PatientHistoryService.createPatientHistory(...).
        final PatientHistory patientHistory = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryService.createPatientHistory(eq(0), any(PatientHistory.class)))
                .thenReturn(patientHistory);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/patientHistory/patient/{patientId}/add", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testCreatePatientHistory_PatientHistoryServiceReturnsNull() throws Exception {
        // Setup
        when(mockPatientHistoryService.createPatientHistory(eq(0), any(PatientHistory.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/patientHistory/patient/{patientId}/add", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdatePatientHistory() throws Exception {
        // Setup
        // Configure PatientHistoryService.getPatientHistoryById(...).
        final PatientHistory patientHistory = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryService.getPatientHistoryById("id")).thenReturn(patientHistory);

        // Configure PatientHistoryService.updatePatientHistory(...).
        final PatientHistory patientHistory1 = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryService.updatePatientHistory(eq("id"), any(PatientHistory.class)))
                .thenReturn(patientHistory1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patientHistory/{id}", "id")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockPatientHistoryService).updatePatientHistory(eq("id"), any(PatientHistory.class));
    }

    @Test
    void testUpdatePatientHistory_PatientHistoryServiceGetPatientHistoryByIdReturnsNull() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patientHistory/{id}", "id")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDeletePatientHistory() throws Exception {
        // Setup
        // Configure PatientHistoryService.getPatientHistoryById(...).
        final PatientHistory patientHistory = new PatientHistory("id", 0, "observation",
                LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockPatientHistoryService.getPatientHistoryById("id")).thenReturn(patientHistory);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/patientHistory/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockPatientHistoryService).deletePatientHistoryById("id");
    }

    @Test
    void testDeletePatientHistory_PatientHistoryServiceGetPatientHistoryByIdReturnsNull() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/patientHistory/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
