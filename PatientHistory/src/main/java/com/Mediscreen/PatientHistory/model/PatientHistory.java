package com.Mediscreen.PatientHistory.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "patientHistory")
public class PatientHistory {

    @Id
    private String id;

    private int patientId;
    @Field("Practitioner's notes/recommendations")
    private String observation;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime creationDate;

    public PatientHistory(String id, int patientId, String observation, LocalDateTime creationDate) {
        this.id = id;
        this.patientId = patientId;
        this.observation = observation;
        this.creationDate = creationDate;
    }
    public PatientHistory() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
