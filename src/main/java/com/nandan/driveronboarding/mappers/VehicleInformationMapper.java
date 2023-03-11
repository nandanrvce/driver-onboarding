package com.nandan.driveronboarding.mappers;

import com.nandan.driveronboarding.entities.Vehicle;
import com.nandan.driveronboarding.requests.VehicleInformationRequest;

public final class VehicleInformationMapper {
    public static Vehicle toVehicle(VehicleInformationRequest vehicleInformationRequest){
        return  Vehicle.builder()
                .color(vehicleInformationRequest.getColor())
                .drivingLicense(vehicleInformationRequest.getDrivingLicense())
                .model(vehicleInformationRequest.getModel())
                .drivingLicenseExpiration(vehicleInformationRequest.getDrivingLicenseExpiration())
                .make(vehicleInformationRequest.getMake())
                .build();
    }
}
