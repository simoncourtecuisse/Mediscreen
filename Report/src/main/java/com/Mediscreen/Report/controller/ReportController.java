package com.Mediscreen.Report.controller;

import com.Mediscreen.Report.model.PatientModel;
import com.Mediscreen.Report.model.RiskLevel;
import com.Mediscreen.Report.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        if (diabetesRiskLevel == null) {
            LOGGER.error("No assessment with patientId: " + patientId);
            return ResponseEntity.badRequest().build();
        }
        LOGGER.info("Diabetes Assessment for patient with this id: " + patientId);
        return new ResponseEntity<>(diabetesRiskLevel, HttpStatus.OK);
    }

    @PostMapping("/familyName")
    public ResponseEntity<?> getFamily(@RequestParam("lastName") String lastName) {
        List<PatientModel> family = reportService.getPatientByFamily(lastName);
//        List<RiskLevel> familyStream = family.stream()
//                       .map(PatientModel::getId)
//                       .map(reportService::calculRisk)
//                .collect(Collectors.toList());
        List<String> familyStream = family.stream()
                .map(patientModel -> {
                    RiskLevel risk = reportService.calculRisk(patientModel.getId());
                    long age = reportService.getPatientAge(patientModel);
                    return "patientId : " + patientModel.getId() +
                            " " + patientModel.getFirstName() +
                            "  " + patientModel.getLastName() +
                            " (age " + age + ") " +
                            "diabetes assessment is: " + risk;
                })
                .collect(Collectors.toList());
//                       .map(PatientModel::getId)
//                       .map(reportService::calculRisk)
//                               .collect(Collectors.toList());



     //   family.forEach(m->m.setRiskLevel(reportService.calculRisk(m.getId())));


//        map((patientmodel) -> {
//                    risk = calculRisk(patientmodel.getId());
//                    return 'patientId : ' + patientmodel.getId() + : ' risk is ': + risk;
//                }

//        List<RiskLevel> familyRisk = reportService.calculRisk(familyStream);
//
//        familyStream.forEach(reportService::calculRisk);
//
//
//
//        getDiabetesAssessment(familyStream);
//        List<RiskLevel> familyRisk = reportService.calculRisk(familyStream.stream().map(PatientModel::getId).collect(Collectors.toList()));
        System.out.println(familyStream);
        System.out.println(family);
        if (family.isEmpty()) {
            LOGGER.error("Failed to get patients with this family Name.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find the {} family" + lastName);
        return new ResponseEntity<>(familyStream, HttpStatus.OK);
    }
//    @PostMapping("/familyName")
//    public ResponseEntity<?> getFamily(@RequestParam("lastName") String lastName) {
//        System.out.println(lastName);
//        List<PatientModel> family = reportService.getPatientByFamily(lastName);
//        family.forEach(m->m.setRiskLevel(reportService.calculRisk(m.getId())));
////        List<RiskLevel> familyStream = family.stream()
////                        .map(PatientModel::getId)
////                        .map(reportService::calculRisk)
////                .collect(Collectors.toList());
//
//        System.out.println(family);
//        if (family.isEmpty()) {
//            LOGGER.error("Failed to get patients with this family Name.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        LOGGER.info("Success find the {} family" + lastName);
//        return new ResponseEntity<>(family, HttpStatus.OK);
//    }
}
