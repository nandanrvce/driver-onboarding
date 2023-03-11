package com.nandan.driveronboarding.mappers;

import com.nandan.driveronboarding.entities.Vehicle;
import com.nandan.driveronboarding.enums.Status;
import com.nandan.driveronboarding.requests.VehicleInformationRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class VehicleInformationMapper {

    public static Date getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        try {
            return dateFormat.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vehicle toVehicle(VehicleInformationRequest vehicleInformationRequest){
        return  Vehicle.builder()
                .color(vehicleInformationRequest.getColor())
                .drivingLicense(vehicleInformationRequest.getDrivingLicense())
                .model(vehicleInformationRequest.getModel())
                .drivingLicenseExpiration(vehicleInformationRequest.getDrivingLicenseExpiration())
                .make(vehicleInformationRequest.getMake())
                .createdAt(getCurrentDate())
                .fuelType(vehicleInformationRequest.getFuelType())
                .insuranceExpiration(vehicleInformationRequest.getInsuranceExpiration())
                .insurancePolicyNumber(vehicleInformationRequest.getInsurancePolicyNumber())
                .registrationNumber(vehicleInformationRequest.getRegistrationNumber())
                .seatingCapacity(vehicleInformationRequest.getSeatingCapacity())
                .status(Status.PENDING_APPROVAL)
                .year(vehicleInformationRequest.getYear())
                .build();
    }
}
