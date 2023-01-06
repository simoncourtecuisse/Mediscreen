package com.Mediscreen.Report.proxy;

import com.Mediscreen.Report.model.PatientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mediscreen-patient", url = "localhost:8081")
public interface PatientProxy {

    @GetMapping("/patient/{id}")
    ResponseEntity<PatientModel> getPatientById(@PathVariable("id") int patientId);

    @GetMapping("/patient/getFamily")
    List<PatientModel> getFamily(@RequestParam("lastName") String lastName);
}
