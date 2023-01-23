package com.Mediscreen.Report.controller;

import com.Mediscreen.Report.model.GenderModel;
import com.Mediscreen.Report.model.PatientModel;
import com.Mediscreen.Report.service.ReportService;
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

import static com.Mediscreen.Report.model.RiskLevel.None;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService mockReportService;

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
    @Test
    void testGetDiabetesAssessment() throws Exception {
        // Setup
        when(mockReportService.calculRisk(0)).thenReturn(None);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/assess/{patientId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "\"None\"");
    }

    @Test
    void testGetDiabetesAssessment_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockReportService.calculRisk(0)).thenReturn(null);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/assess/{patientId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

//    @Test
//    void testGetDiabetesAssessment_ThrowsIOException() throws Exception {
//        // Setup
//        when(mockReportService.calculRisk(0)).thenReturn(None);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(post("/assess/{patientId}", 0)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
//    }

    @Test
    void testGetFamily() throws Exception {
        // Setup
        final List<PatientModel> list = List.of(patientUnderTest(), patientUnderTest());
        when(mockReportService.getPatientByFamily("smith")).thenReturn(list);

        when(mockReportService.calculRisk(0)).thenReturn(None);
        when(mockReportService.getPatientAge(any(PatientModel.class))).thenReturn(0L);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/assess/familyName")
                        .param("lastName", "smith")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), ("[\"patientId : 0 John  Smith (age 0) diabetes assessment is: None\",\"patientId : 0 John  Smith (age 0) diabetes assessment is: None\"]"));
    }

    @Test
    void testGetFamily_ReturnsBadRequest() throws Exception {
        // Setup
        when(mockReportService.getPatientByFamily("lastName")).thenReturn(Collections.emptyList());
        when(mockReportService.calculRisk(0)).thenReturn(None);
        when(mockReportService.getPatientAge(any(PatientModel.class))).thenReturn(0L);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/assess/familyName")
                        .param("lastName", "lastName")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }
}
