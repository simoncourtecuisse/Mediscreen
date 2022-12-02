package com.Mediscreen.PatientHistory.service;

import com.Mediscreen.PatientHistory.model.PatientHistory;
import com.Mediscreen.PatientHistory.repository.PatientHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientHistoryService {

    @Autowired
    private PatientHistoryRepository patientHistoryRepository;

    public List<PatientHistory> getAllPatientHistory() {
        return patientHistoryRepository.findAll();
    }

    public List<PatientHistory> getTheHistoryByPatientId(int patientId) {
        return patientHistoryRepository.findAllByPatientId(patientId);
    }

    public PatientHistory getPatientHistoryById(String id) {
        Optional<PatientHistory> patientHistory = patientHistoryRepository.findById(id);
        return patientHistory.orElse(null);
    }

    public PatientHistory createPatientHistory(int patientId, PatientHistory patientHistory) {
        patientHistory.setPatientId(patientId);
        return patientHistoryRepository.save(patientHistory);
    }

    public PatientHistory updatePatientHistory(String id, PatientHistory updatedPatientHistory) {
        Optional<PatientHistory> patientHistory = patientHistoryRepository.findById(id);
        if (patientHistory.isPresent()) {
            PatientHistory newPatientHistory = patientHistory.get();
            if (updatedPatientHistory.getPatientId() == 0) return null;
            newPatientHistory.setPatientId(updatedPatientHistory.getPatientId());
            newPatientHistory.setObservation(updatedPatientHistory.getObservation());
            return patientHistoryRepository.save(newPatientHistory);
        }
        return null;
    }

    public void deletePatientHistoryById(String id) {
        Optional<PatientHistory> patientHistory = patientHistoryRepository.findById(id);
        if (patientHistory.isPresent()) {
            patientHistoryRepository.deletePatientHistoryById(id);
        }
    }
}