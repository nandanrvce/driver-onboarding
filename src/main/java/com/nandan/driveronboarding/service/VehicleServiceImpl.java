package com.nandan.driveronboarding.service;

import com.nandan.driveronboarding.entities.Vehicle;
import com.nandan.driveronboarding.repository.VehicleRepository;
import com.nandan.driveronboarding.service.interfaces.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Override
    public Vehicle persistVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
