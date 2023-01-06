package com.Mediscreen.Report.proxy;

import com.Mediscreen.Report.model.PatientHistoryModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "mediscreen-patientHistory", url = "localhost:8082")
public interface PatientHistoryProxy {

    @GetMapping("/patientHistory/patient/{patientId}")
    ResponseEntity<List<PatientHistoryModel>> getTheHistoryForPatient(@PathVariable("patientId") int patientId);

}
