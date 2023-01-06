package com.Mediscreen.Report.service;

import com.Mediscreen.Report.model.RiskLevel;
import com.Mediscreen.Report.model.GenderModel;
import com.Mediscreen.Report.model.PatientHistoryModel;
import com.Mediscreen.Report.model.PatientModel;
import com.Mediscreen.Report.proxy.PatientHistoryProxy;
import com.Mediscreen.Report.proxy.PatientProxy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class ReportService {

    private final PatientProxy patientProxy;
    private final PatientHistoryProxy patientHistoryProxy;

    public ReportService(PatientProxy patientProxy, PatientHistoryProxy patientHistoryProxy) {
        this.patientProxy = patientProxy;
        this.patientHistoryProxy = patientHistoryProxy;
    }

    public PatientModel getPatientData(int patientId) {
        return patientProxy.getPatientById(patientId).getBody();
    }

    public List<PatientHistoryModel> getTheHistoryForPatient(int patientId) {
        return patientHistoryProxy.getTheHistoryForPatient(patientId).getBody();
    }

    public RiskLevel calculRisk(int patientId) {
        PatientModel patientData = getPatientData(patientId);
        List<PatientHistoryModel> historyForPatient = getTheHistoryForPatient(patientId);

        if (patientId == 0 || patientData == null || historyForPatient == null) return null;

        long age = getPatientAge(patientData);
        GenderModel gender = patientData.getGender();
        int triggers = triggersCount(historyForPatient);
        RiskLevel riskLevel = RiskLevel.None;
        System.out.println(age);

        if (age > 30) {
            if (triggers >= 2 && triggers < 6) {
                riskLevel = RiskLevel.Borderline;
            } else if (triggers >= 6 && triggers < 8) {
                riskLevel = RiskLevel.EarlyOnset;
            } else if (triggers >= 8) {
                riskLevel = RiskLevel.InDanger;
            }
        } else if (age < 30) {
            if (gender.equals(GenderModel.MALE)) {
                if (triggers >= 3 && triggers < 5) {
                    riskLevel = RiskLevel.InDanger;
                } else if (triggers >= 5) {
                    riskLevel = RiskLevel.EarlyOnset;
                }
            } else if (gender.equals(GenderModel.FEMALE)) {
                if (triggers >= 4 && triggers < 7) {
                    riskLevel = RiskLevel.InDanger;
                } else if (triggers >= 7) {
                    riskLevel = RiskLevel.EarlyOnset;
                }
            }
        }
        return riskLevel;
    }

    public long getPatientAge (PatientModel patient) {
        return ChronoUnit.YEARS.between(patient.getBirthdate(), LocalDate.now());
    }

    public int triggersCount(List<PatientHistoryModel> historyForPatient) {
        int triggerNumber = 0;
        Set<String> triggers = new HashSet<>();
        triggers.add("Hémoglobine A1C");
        triggers.add("Microalbumine");
        triggers.add("Taille");
        triggers.add("Poids");
        triggers.add("Fume");
        triggers.add("Anormal");
        triggers.add("Cholestérol");
        triggers.add("Vertige");
        triggers.add("Rechute");
        triggers.add("Réaction");
        triggers.add("Anticorps");

        for (PatientHistoryModel patientHistoryModel : historyForPatient) {
            for (String trigger : triggers) {
                if (patientHistoryModel.getObservation().toLowerCase(Locale.ROOT).contains(trigger.toLowerCase(Locale.ROOT))) {
                    triggerNumber++;
                }
            }
        }
        return triggerNumber;
    }

    //public
}
