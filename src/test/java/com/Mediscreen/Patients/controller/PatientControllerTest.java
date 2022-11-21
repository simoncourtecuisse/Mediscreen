package com.Mediscreen.Patients.controller;

import com.Mediscreen.Patients.model.Gender;
import com.Mediscreen.Patients.model.Patient;
import com.Mediscreen.Patients.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService mockPatientService;

    public static String jsonToString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
    void testGetAllPatients() throws Exception {
        // Setup
        final List<Patient> patients = List.of(patientUnderTest(), patientUnderTest2());
        when(mockPatientService.getAllPatients()).thenReturn(patients);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testGetAllPatients_ReturnsNoContent() throws Exception {
        // Setup
        final List<Patient> patients = List.of();
        when(mockPatientService.getAllPatients()).thenReturn(patients);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void testGetPatientById() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(0)).thenReturn(patientUnderTest());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void testGetPatientById_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(5)).thenReturn(patientUnderTest());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/patient/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testCreatePatient() throws Exception {
        // Setup
        when(mockPatientService.savePatient(any(Patient.class))).thenReturn(patientUnderTest());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/patient/add")
                        .content(jsonToString(patientUnderTest())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(mockPatientService).savePatient(any(Patient.class));
    }

    @Test
    void testUpdatePatient() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(0)).thenReturn(patientUnderTest());
        when(mockPatientService.updatePatient(any(Patient.class))).thenReturn(patientUnderTest2());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patient/{id}", 0)
                        .content(jsonToString(patientUnderTest())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(mockPatientService).updatePatient(any(Patient.class));
    }

    @Test
    void testUpdatePatient_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(1)).thenReturn(patientUnderTest());
        when(mockPatientService.updatePatient(any(Patient.class))).thenReturn(patientUnderTest());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/patient/{id}", 5)
                        .content(jsonToString(patientUnderTest())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void testDeletePatient() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(0)).thenReturn(patientUnderTest());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/patient/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(mockPatientService).deletePatient(0);
    }

    @Test
    void testDeletePatient_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockPatientService.getPatientById(0)).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/patient/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}
