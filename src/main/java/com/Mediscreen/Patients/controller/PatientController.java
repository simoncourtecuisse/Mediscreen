package com.Mediscreen.Patients.controller;

import com.Mediscreen.Patients.model.Patient;
import com.Mediscreen.Patients.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    Logger LOGGER = LogManager.getLogger(PatientController.class);
    @Autowired
    PatientService patientService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients() {
        List<Patient> allPatients = patientService.getAllPatients();
        if (allPatients.isEmpty()) {
            LOGGER.error("No patients found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LOGGER.info("Success find all patients");
        return new ResponseEntity<>(allPatients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable("id") int id) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            LOGGER.error("Failed to get patient because the patient was not found");
            return new ResponseEntity<>("No patient found with this id: " + id, HttpStatus.BAD_REQUEST);

        }
        LOGGER.info("Success find patient with this id: " + id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient, BindingResult result) {
        ResponseEntity<?> errors = getBindingResultErrors(result);
        if (errors != null) return errors;
        Patient newPatient = patientService.savePatient(patient);
        LOGGER.info("New Patient, {} {}, created with id: " + newPatient.getId(), patient.getLastName(), patient.getFirstName());
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable("id") int id, @RequestBody Patient patient) {
        if (patientService.getPatientById(id) == null) {
            LOGGER.error("Failed to update patient because the patient was not found");
            return new ResponseEntity<>("No patient with id: " + id, HttpStatus.BAD_REQUEST);

        }
        patient.setId(id);
        patientService.updatePatient(patient);
        LOGGER.info("Patient updated successfully");
        return new ResponseEntity<>("Patient updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") int id) {
        if (patientService.getPatientById(id) != null) {
            patientService.deletePatient(id);
            LOGGER.info("Patient deleted successfully");
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        LOGGER.error("Failed to delete patient because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private ResponseEntity<?> getBindingResultErrors(BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            List<String> messages = new ArrayList<>();
            for (FieldError error : fieldErrors) {
                messages.add(error.getDefaultMessage());
            }
            LOGGER.error("Error message: " + messages);
            return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
