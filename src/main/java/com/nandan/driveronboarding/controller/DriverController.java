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
/**

 This class represents the controller for handling driver onboarding requests.
 It provides endpoints for uploading driver documents, providing vehicle information,
 checking document upload status, shipping tracking devices, and updating driver status.
 */
@RestController
@RequestMapping("/driver-onboarding/v1")
public class DriverController {

    @Autowired
    VehicleRegistration vehicleRegistration;

    @Autowired
    UserRegistration userRegistration;

    /**
     * Endpoint for uploading driver documents.
     *
     * @param fileUploadRequest The {@link FileUploadRequest} object containing the files to be uploaded.
     * @param bindingResult The {@link BindingResult} object to store validation errors.
     * @return A {@link ResponseEntity} object containing the response message.
     * @throws BindingErrorsException Thrown when there are binding errors in the request.
     */
    @PostMapping("/documents")
    public ResponseEntity<ResponseMessage> uploadFiles(@Valid @ModelAttribute FileUploadRequest fileUploadRequest,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorsException(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(vehicleRegistration.storeAllFiles(fileUploadRequest)));
    }

    /**
     * Endpoint for providing vehicle information.
     *
     * @param vehicleInformationRequest The {@link VehicleInformationRequest} object containing the vehicle information.
     * @param bindingResult The {@link BindingResult} object to store validation errors.
     * @return A {@link ResponseEntity} object containing the response message.
     * @throws BindingErrorsException Thrown when there are binding errors in the request.
     */
    @PostMapping("/vehicle-information")
    public ResponseEntity<ResponseMessage> vehicleInformation(@RequestBody VehicleInformationRequest vehicleInformationRequest,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorsException(bindingResult);
        }
        return vehicleRegistration.persistVehicleData(vehicleInformationRequest);
    }

    /**
     * Endpoint for getting documents status.
     *
     * @return A {@link ResponseEntity} object containing the tracking device status string.
     */
    @GetMapping("/documents/status")
    public ResponseEntity<Map<String,String>> documentStatus() {
        return ResponseEntity.ok(vehicleRegistration.getDocumentStatus());
    }

    /**
     * Endpoint for getting tracking device status.
     *
     * @return A {@link ResponseEntity} object with an empty body indicating the update was successful.
     */

    @GetMapping("/device")
    public ResponseEntity<String> shipTrackingDevice() {
        return ResponseEntity.ok(userRegistration.getDeviceStatus());
    }

    /**
     * Endpoint for updating driver status.
     *
     * @param driverRideStatusRequest The {@link DriverRideStatusRequest} object containing the driver status.
     * @return A {@link ResponseEntity} object with an empty body indicating the update was successful.
     */

    @PutMapping("/status")
    public ResponseEntity updateDriverStatus(@RequestBody DriverRideStatusRequest driverRideStatusRequest) {
        userRegistration.updateDriverStatus(driverRideStatusRequest);
        return ResponseEntity.ok().build();
    }
}
