package com.Mediscreen.PatientHistory.repository;

import com.Mediscreen.PatientHistory.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, String> {
    List<PatientHistory> findAllByPatientId(int patientId);
    void deletePatientHistoryById(String id);
}
