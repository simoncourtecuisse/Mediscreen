package com.Mediscreen.PatientHistory.controller;

import com.Mediscreen.PatientHistory.model.PatientHistory;
import com.Mediscreen.PatientHistory.service.PatientHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    public static String jsonToString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
    void testGetAllPatientHistory() throws Exception {
        // Setup
        // Configure PatientHistoryService.getAllPatientHistory(...).
        final List<PatientHistory> patientHistoryList = List.of(patHistUnderTest1(), patHistUnderTest2());
        when(mockPatientHistoryService.getAllPatientHistory()).thenReturn(patientHistoryList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testGetAllPatientHistory_ReturnsNoContent() throws Exception {
        // Setup
        when(mockPatientHistoryService.getAllPatientHistory()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void testGetPatientHistoryById() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id1")).thenReturn(patHistUnderTest1());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/{id}", "id1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testGetPatientHistoryById_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id5")).thenReturn(patHistUnderTest1());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/{id}", "id1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testGetTheHistoryForPatient() throws Exception {
        // Setup
        // Configure PatientHistoryService.getTheHistoryByPatientId(...).
        final List<PatientHistory> patientHistoryList = List.of(patHistUnderTest1(), patHistUnderTest2());
        when(mockPatientHistoryService.getTheHistoryByPatientId(1)).thenReturn(patientHistoryList);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/patient/{patientId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testGetTheHistoryForPatient_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockPatientHistoryService.getTheHistoryByPatientId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patientHistory/patient/{patientId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testCreatePatientHistory() throws Exception {
        // Setup
        when(mockPatientHistoryService.createPatientHistory(eq(0), any(PatientHistory.class)))
                .thenReturn(patHistUnderTest1());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/patientHistory/patient/{patientId}/add", 0)
                        .content(jsonToString(patHistUnderTest1())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(mockPatientHistoryService).createPatientHistory(eq(0), any(PatientHistory.class));
    }

    @Test
    void testCreatePatientHistory_HasErrors() throws Exception {
        // Setup
        when(mockPatientHistoryService.createPatientHistory(eq(0), any(PatientHistory.class))).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/patientHistory/patient/{patientId}/add", 0)
                        .content(jsonToString(patHistUnderTest1())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testUpdatePatientHistory() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id1")).thenReturn(patHistUnderTest1());
        when(mockPatientHistoryService.updatePatientHistory(eq("id1"), any(PatientHistory.class))).thenReturn(patHistUnderTest2());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patientHistory/{id}", "id1")
                        .content(jsonToString(patHistUnderTest1())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(mockPatientHistoryService).updatePatientHistory(eq("id1"), any(PatientHistory.class));
    }

    @Test
    void testUpdatePatientHistory_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id1")).thenReturn(patHistUnderTest1());
        when(mockPatientHistoryService.updatePatientHistory(eq("id1"), any(PatientHistory.class))).thenReturn(patHistUnderTest2());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patientHistory/{id}", "id5")
                        .content(jsonToString(patHistUnderTest1())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testDeletePatientHistory() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id1")).thenReturn(patHistUnderTest1());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/patientHistory/{id}", "id1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(mockPatientHistoryService).deletePatientHistoryById("id1");
    }

    @Test
    void testDeletePatientHistory_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockPatientHistoryService.getPatientHistoryById("id1")).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/patientHistory/{id}", "id1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
