package com.nandan.driveronboarding.entities;

import com.nandan.driveronboarding.enums.FuelType;
import com.nandan.driveronboarding.enums.Make;
import com.nandan.driveronboarding.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

}
