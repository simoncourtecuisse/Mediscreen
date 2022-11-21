package com.Mediscreen.Patients.controller;

import com.Mediscreen.Patients.model.Gender;
import com.Mediscreen.Patients.model.Patient;
import com.Mediscreen.Patients.service.PatientService;
import com.google.gson.JsonObject;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
class PatientControllerTest1 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService mockPatientService;

    @Test
    void testGetAllPatients() throws Exception {
        // Setup
        // Configure PatientService.getAllPatients(...).
        final List<Patient> patients = List.of(
                new Patient(0, "firstName", "lastName", LocalDate.of(2020, 1, 1), Gender.FEMALE, "address", "email",
                        "phoneNumber"));
        when(mockPatientService.getAllPatients()).thenReturn(patients);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllPatients_PatientServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPatientService.getAllPatients()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetPatientById() throws Exception {
        // Setup
        // Configure PatientService.getPatientById(...).
        final Patient patient = new Patient(0, "firstName", "lastName", LocalDate.of(2020, 1, 1), Gender.FEMALE,
                "address", "email", "phoneNumber");
        when(mockPatientService.getPatientById(0)).thenReturn(patient);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetPatientById_PatientServiceReturnsNull() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(0)).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testCreatePatient() throws Exception {
        // Setup
        // Configure PatientService.savePatient(...).
        final Patient patient = new Patient(0, "firstName", "lastName", LocalDate.of(2020, 1, 1), Gender.FEMALE,
                "address", "email", "phoneNumber");
        when(mockPatientService.savePatient(any(Patient.class))).thenReturn(patient);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", 1);
        jsonObject.addProperty("firstName", "John");
        jsonObject.addProperty("lastName", "Smith");
        jsonObject.addProperty("birthdate", String.valueOf(LocalDate.of(2020, 1, 1)));
        jsonObject.addProperty("gender", Gender.MALE.getDisplayGenderValue());
        jsonObject.addProperty("address", "DownTown Street 1");
        jsonObject.addProperty("email", "john.smith@example.com");
        jsonObject.addProperty("phoneNumber", "111-222-3333");
        final MockHttpServletResponse response = mockMvc.perform(post("/patient/add")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Patient Created", response.getContentAsString());
        verify(mockPatientService).savePatient(any(Patient.class));
    }

    @Test
    void testUpdatePatient() throws Exception {
        // Setup
        // Configure PatientService.getPatientById(...).
        final Patient patient = new Patient(0, "firstName", "lastName", LocalDate.of(2020, 1, 1), Gender.FEMALE,
                "address", "email", "phoneNumber");
        when(mockPatientService.getPatientById(0)).thenReturn(patient);

        // Configure PatientService.updatePatient(...).
        final Patient patient1 = new Patient(0, "firstName", "lastName", LocalDate.of(2020, 1, 1), Gender.FEMALE,
                "address", "email", "phoneNumber");
        when(mockPatientService.updatePatient(any(Patient.class))).thenReturn(patient1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patient/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockPatientService).updatePatient(any(Patient.class));
    }

    @Test
    void testUpdatePatient_PatientServiceGetPatientByIdReturnsNull() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(0)).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patient/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
