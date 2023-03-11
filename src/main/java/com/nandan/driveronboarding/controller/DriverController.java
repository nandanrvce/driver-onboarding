package com.nandan.driveronboarding.controller;

import com.nandan.driveronboarding.exception.BindingErrorsException;
import com.nandan.driveronboarding.requests.DriverRideStatusRequest;
import com.nandan.driveronboarding.requests.FileUploadRequest;
import com.nandan.driveronboarding.requests.VehicleInformationRequest;
import com.nandan.driveronboarding.responses.ResponseMessage;
import com.nandan.driveronboarding.usecase.UserRegistration;
import com.nandan.driveronboarding.usecase.VehicleRegistration;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/driver-onboarding/v1")
public class DriverController {

    @Autowired
    VehicleRegistration vehicleRegistration;

    @Autowired
    UserRegistration userRegistration;

    @PostMapping("/documents")
    public ResponseEntity<ResponseMessage> uploadFiles(@Valid @ModelAttribute FileUploadRequest fileUploadRequest,
                                                       BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorsException(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(vehicleRegistration.storeAllFiles(fileUploadRequest)));
    }

    @PostMapping("/vehicle-information")
    public ResponseEntity<ResponseMessage> vehicleInformation(@RequestBody VehicleInformationRequest vehicleInformationRequest,
                                                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorsException(bindingResult);
        }
        return vehicleRegistration.persistVehicleData(vehicleInformationRequest);
    }

    @GetMapping("/documents/status")
    public ResponseEntity<Map<String,String>> documentStatus() {
        return ResponseEntity.ok(vehicleRegistration.getDocumentStatus());
    }

    @GetMapping("/device")
    public ResponseEntity<String> shipTrackingDevice() {
        return ResponseEntity.ok(userRegistration.getDeviceStatus());
    }

    @PutMapping("/status")
    public ResponseEntity updateDriverStatus(@RequestBody DriverRideStatusRequest driverRideStatusRequest) {
        userRegistration.updateDriverStatus(driverRideStatusRequest);
        return ResponseEntity.ok().build();
    }
}
