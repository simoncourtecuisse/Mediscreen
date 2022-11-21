package com.Mediscreen.Patients.service;

import com.Mediscreen.Patients.model.Patient;
import com.Mediscreen.Patients.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger LOGGER = LogManager.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        return optionalPatient.orElse(null);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        Optional<Patient> updateP = patientRepository.findById(patient.getId());
        if (updateP.isEmpty()) {
            return null;
        }
        return patientRepository.save(patient);
    }

    public void deletePatient(int id) {
        Optional<Patient> removePatient = patientRepository.findById(id);
        if (removePatient.isPresent()) {
            patientRepository.deleteById(id);
        }
    }
}
