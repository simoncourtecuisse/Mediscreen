package com.Mediscreen.Patients.service;

import com.Mediscreen.Patients.model.Address;
import com.Mediscreen.Patients.repository.AddressRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private static final Logger LOGGER = LogManager.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    public Address getAddressById(int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }
}
