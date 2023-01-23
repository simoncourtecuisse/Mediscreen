package com.Mediscreen.Report.model;

import java.time.LocalDateTime;

public class PatientHistoryModel {

    private String id;
    private int patientId;
    private String observation;
    private LocalDateTime creationDate;

    public PatientHistoryModel(String id, int patientId, String observation, LocalDateTime creationDate) {
        this.id = id;
        this.patientId = patientId;
        this.observation = observation;
        this.creationDate = creationDate;
    }
    public PatientHistoryModel() {
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
