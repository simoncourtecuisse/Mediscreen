package com.Mediscreen.Report.controller;

import com.Mediscreen.Report.model.RiskLevel;
import com.Mediscreen.Report.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
@RestController
@RequestMapping("/assess")
public class ReportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<?> getDiabetesAssessment(@PathVariable("patientId") int patientId) throws IOException {
        RiskLevel diabetesRiskLevel = reportService.calculRisk(patientId);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("diabetesRiskLevel", String.valueOf(diabetesRiskLevel));
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValue(new File("diabetesRiskLevel"), diabetesRiskLevel);
        if (diabetesRiskLevel == null) {
            LOGGER.error("No assessment with patientId: " + patientId);
            return ResponseEntity.badRequest().build();
        }
        LOGGER.info("Diabetes Assessment for patient with this id: " + patientId);
        return new ResponseEntity<>(diabetesRiskLevel, HttpStatus.OK);
    }

}
