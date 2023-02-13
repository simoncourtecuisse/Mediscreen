package com.Mediscreen.PatientHistory.controller;

import com.Mediscreen.PatientHistory.model.PatientHistory;
import com.Mediscreen.PatientHistory.service.PatientHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
@RestController
@RequestMapping("/patientHistory")
public class PatientHistoryController {

    Logger LOGGER = LogManager.getLogger(PatientHistoryController.class);
    @Autowired
    PatientHistoryService patientHistoryService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatientHistory() {
        List<PatientHistory> allPatientHistory = patientHistoryService.getAllPatientHistory();
        if (allPatientHistory.isEmpty()) {
            LOGGER.error("No patient history found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LOGGER.info("Success find all patients history");
        return new ResponseEntity<>(allPatientHistory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientHistoryById(@PathVariable("id") String id) {
        PatientHistory patientHistory = patientHistoryService.getPatientHistoryById(id);
        if (patientHistory == null) {
            LOGGER.error("Failed to get patient history because it was not found");
            return new ResponseEntity<>("No patient history found with this id: " + id, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Success find patient history with this id: " + id);
        return new ResponseEntity<>(patientHistory, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getTheHistoryForPatient(@PathVariable("patientId") int patientId) {
        List<PatientHistory> patientHistoryList = patientHistoryService.getTheHistoryByPatientId(patientId);
//        if (patientHistoryList.isEmpty()) {
//            LOGGER.error("Failed to find any history because it was not found");
//            return new ResponseEntity<>("No history found for this PatientId: " + patientId, HttpStatus.BAD_REQUEST);
//        }
        LOGGER.info("Success find history for this PatientId: " + patientId);
        return new ResponseEntity<>(patientHistoryList, HttpStatus.OK);
    }

    @PostMapping("/patient/{patientId}/add")
    public ResponseEntity<?> createPatientHistory(@PathVariable("patientId") int patientId, @RequestBody PatientHistory observation) {
        PatientHistory newObservation = patientHistoryService.createPatientHistory(patientId, observation);
        if (newObservation == null) {
            LOGGER.error("Failed to create observation for PatientId: " + patientId);
            return new ResponseEntity<>("Failed to create observation for PatientId: " + patientId, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("New observation created for PatientId: " + patientId);
        return new ResponseEntity<>(newObservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatientHistory(@PathVariable("id") String id, @RequestBody PatientHistory patientHistory) {
        if (patientHistoryService.getPatientHistoryById(id) == null) {
            LOGGER.error("Failed to update observation because the patient history was not found");
            return new ResponseEntity<>("No patient history with id: " + id, HttpStatus.BAD_REQUEST);

        }
        patientHistory.setId(id);
        patientHistoryService.updatePatientHistory(id, patientHistory);
        LOGGER.info("Patient History updated successfully");
        return new ResponseEntity<>("Patient History updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientHistory(@PathVariable("id") String id) {
        if (patientHistoryService.getPatientHistoryById(id) != null) {
            patientHistoryService.deletePatientHistoryById(id);
            LOGGER.info("Patient History deleted successfully");
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        LOGGER.error("Failed to delete patient history because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
