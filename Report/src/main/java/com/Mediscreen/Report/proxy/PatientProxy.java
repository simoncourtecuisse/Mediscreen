package com.Mediscreen.Report.proxy;

import com.Mediscreen.Report.model.PatientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient(name = "mediscreen-patient", url = "${mediscreen-patient.url.cross}", decode404 = true)
@FeignClient(name = "mediscreen-patient", url = "http://mediscreen-patient:8081")
public interface PatientProxy {

    @GetMapping("/patient/{id}")
    ResponseEntity<PatientModel> getPatientById(@PathVariable("id") int patientId);

//    @GetMapping("/patient/{lastName}")
//    ResponseEntity<List<PatientModel>> getFamily(@PathVariable("lastName") String lastName);
    @GetMapping("/patient/familyName")
    ResponseEntity<List<PatientModel>> getFamily(@RequestParam("lastName") String lastName);
}
