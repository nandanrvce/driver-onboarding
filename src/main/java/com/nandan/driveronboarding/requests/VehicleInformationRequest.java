package com.nandan.driveronboarding.requests;

import com.nandan.driveronboarding.enums.FuelType;
import com.nandan.driveronboarding.enums.Make;
import com.nandan.driveronboarding.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
@Data
public class VehicleInformationRequest {
    private String drivingLicense;
    private String drivingLicenseExpiration;
    @Enumerated(EnumType.STRING)
    private Make make;
    private String model;
    private int year;
    private String color;
    private String registrationNumber;
    private String insurancePolicyNumber;
    private Date insuranceExpiration;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private int seatingCapacity;

}
