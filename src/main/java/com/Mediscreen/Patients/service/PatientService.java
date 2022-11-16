package com.Mediscreen.Patients.service;

import com.Mediscreen.Patients.model.Address;
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

    @Autowired
    private AddressService addressService;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        return optionalPatient.orElse(null);
    }

    public Patient savePatient(Patient patient) {
        Address address = new Address();

        address.setStreet(patient.getAddress().getStreet());
        address.setCity(patient.getAddress().getCity());
        address.setZipCode(patient.getAddress().getZipCode());
        address.setState(patient.getAddress().getState());
        address.setCountry(patient.getAddress().getCountry());

        patient.setAddress(address);

        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        Optional<Patient> updateP = patientRepository.findById(patient.getId());
        if (updateP.isEmpty()) {
            return null;
        }

        Address address = addressService.getAddressById(patient.getAddress().getId());

        address.setStreet(patient.getAddress().getStreet());
        address.setCity(patient.getAddress().getCity());
        address.setZipCode(patient.getAddress().getZipCode());
        address.setState(patient.getAddress().getState());
        address.setCountry(patient.getAddress().getCountry());

        patient.setAddress(address);

        return patientRepository.save(patient);
    }

    public void deletePatient(int id) {
        Optional<Patient> removePatient = patientRepository.findById(id);
        if (removePatient.isPresent()) {
            patientRepository.deleteById(id);
        }
    }
}
